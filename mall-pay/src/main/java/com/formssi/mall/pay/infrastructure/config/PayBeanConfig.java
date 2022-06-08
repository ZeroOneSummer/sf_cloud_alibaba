package com.formssi.mall.pay.infrastructure.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formssi.mall.pay.infrastructure.utils.WechatPayConstant;
import com.wechat.pay.contrib.apache.httpclient.Credentials;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.*;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码加载读取秘钥/定时获取微信签名验证器/获取http请求对象，会自动的处理签名和验签
 *
 * @author user
 */
@Configuration
@Slf4j
public class PayBeanConfig {

    private static final int TWO_ZERO_ZERO = 200;

    @Resource
    private WechatPropertiesV3 payConfig;

    /**
     * 加载秘钥
     *
     * @return PrivateKey
     * @throws IOException IOException
     */
    public PrivateKey getPrivateKey() throws IOException {
        InputStream inputStream = new ClassPathResource(payConfig.getPrivateKeyPath().replace("classpath:", "")).getInputStream();
        String content = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    /**
     * 定时获取微信签名验证器，自动获取微信平台证书（证书里面包括微信平台公钥）
     * 使用定时更新的签名验证器，不需要传入证书
     *
     * @return CertificatesVerifier
     */
//    @Bean
    public CertificatesVerifier getCertificatesVerifier() throws IOException {
        PrivateKeySigner signer = new PrivateKeySigner(payConfig.getMchSerialNo(), getPrivateKey());
        Credentials credentials = new WechatPay2Credentials(payConfig.getMchId(), signer);
        byte[] apiV3Key = payConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8);
        CertificatesVerifier verifier;
        try {
            verifier = this.autoUpdateCert(credentials, apiV3Key);
        } catch (GeneralSecurityException | IOException var6) {
            throw new RuntimeException(var6);
        }
        return verifier;
    }

    private CertificatesVerifier autoUpdateCert(Credentials credentials, byte[] apiV3Key) throws IOException, GeneralSecurityException {
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create().withCredentials(credentials).withValidator((validator) -> true).build();

        label80:
        {
            try {
                label81:
                {
                    HttpGet httpGet = new HttpGet(WechatPayConstant.CERTIFICATES);
                    httpGet.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
                    CloseableHttpResponse response = httpClient.execute(httpGet);

                    label82:
                    {
                        try {
                            int statusCode = response.getStatusLine().getStatusCode();
                            String body = EntityUtils.toString(response.getEntity());
                            if (statusCode == TWO_ZERO_ZERO) {
                                List<X509Certificate> newCertList = this.deserializeToCerts(apiV3Key, body);
                                if (newCertList.isEmpty()) {
                                    log.warn("Cert list is empty");
                                    break label82;
                                }

                                return new CertificatesVerifier(newCertList);
                            } else {
                                log.warn("Auto update cert failed, statusCode = {}, body = {}", statusCode, body);
                            }
                        } catch (Throwable var9) {
                            if (response != null) {
                                try {
                                    response.close();
                                } catch (Throwable var8) {
                                    var9.addSuppressed(var8);
                                }
                            }

                            throw var9;
                        }

                        response.close();
                        break label81;
                    }

                    response.close();
                    break label80;
                }
            } catch (Throwable var10) {
                if (httpClient != null) {
                    try {
                        httpClient.close();
                    } catch (Throwable var7) {
                        var10.addSuppressed(var7);
                    }
                }

                throw var10;
            }

            httpClient.close();

            return null;
        }

        httpClient.close();

        return null;
    }

    private List<X509Certificate> deserializeToCerts(byte[] apiV3Key, String body) throws GeneralSecurityException, IOException {
        AesUtil aesUtil = new AesUtil(apiV3Key);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode dataNode = mapper.readTree(body).get("data");
        List<X509Certificate> newCertList = new ArrayList<>();
        if (dataNode != null) {
            int i = 0;

            for (int count = dataNode.size(); i < count; ++i) {
                JsonNode node = dataNode.get(i).get("encrypt_certificate");
                String cert = aesUtil.decryptToString(node.get("associated_data").toString().replace("\"", "").getBytes(StandardCharsets.UTF_8), node.get("nonce").toString().replace("\"", "").getBytes(StandardCharsets.UTF_8), node.get("ciphertext").toString().replace("\"", ""));
                CertificateFactory cf = CertificateFactory.getInstance("X509");
                X509Certificate x509Cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert.getBytes(StandardCharsets.UTF_8)));

                try {
                    x509Cert.checkValidity();
                } catch (CertificateNotYetValidException | CertificateExpiredException var14) {
                    continue;
                }

                newCertList.add(x509Cert);
            }
        }

        return newCertList;
    }

    /**
     * 获取http请求对象，会自动的处理签名和验签，
     * 并进行证书自动更新
     *
     * @return CloseableHttpClient
     */
//    @Bean("wechatPayClient")
    public CloseableHttpClient getWechatPayClient(Verifier verifier) throws IOException {
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create().withMerchant(payConfig.getMchId(), payConfig.getMchSerialNo(), getPrivateKey()).withValidator(new WechatPay2Validator(verifier));
        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        return builder.build();
    }
}