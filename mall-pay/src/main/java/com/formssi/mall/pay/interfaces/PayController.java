package com.formssi.mall.pay.interfaces;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.entity.resp.ErrorBody;
import com.formssi.mall.common.entity.resp.RespCode;
import com.formssi.mall.pay.application.PayService;
import com.formssi.mall.pay.domain.dto.PayOrderDto;
import com.formssi.mall.pay.domain.dto.PayRefundOrderDto;
import com.formssi.mall.pay.infrastructure.config.AlipayProperties;
import com.formssi.mall.pay.infrastructure.config.PayTypeServiceMap;
import com.formssi.mall.pay.infrastructure.enumeration.PayTypeEnum;
import com.formssi.mall.pay.infrastructure.utils.PayUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * 支付控制器
 *
 * @author user
 * @date 2022/05/09 13:43:45
 */
@RestController
@Slf4j
@RequestMapping("/alipay/")
public class PayController {

    @Resource
    private AlipayProperties properties;

    @PostMapping("placeOrder")
    public CommonResp placeOrder(@RequestBody @Valid PayOrderDto dto) {
        String code = dto.getCode();
        if (StringUtils.isBlank(code)) {
            log.info("code不能为空！");
            return CommonResp.error(RespCode.BAD_REQUEST, ErrorBody.build(new Throwable("code不能为空！")));
        }
        PayService payService = PayTypeServiceMap.SERVICE_HAND_MAP.get(PayTypeEnum.queryPayTypeEnumByCode(code));
        if (payService != null) {
            return CommonResp.ok(payService.placeOrder(dto));
        }
        return CommonResp.ok();
    }

    @GetMapping("queryOrder")
    public CommonResp queryOrder(@RequestParam("code") String code, @RequestParam("outTradeNo") String outTradeNo) {
        if (StringUtils.isBlank(code)) {
            log.info("code不能为空！");
            return CommonResp.error(RespCode.BAD_REQUEST, ErrorBody.build(new Throwable("code不能为空！")));
        }
        PayService payService = PayTypeServiceMap.SERVICE_HAND_MAP.get(PayTypeEnum.queryPayTypeEnumByCode(code));
        if (payService != null) {
            return CommonResp.ok(payService.queryOrder(outTradeNo));
        }
        return CommonResp.ok();
    }

    @PostMapping("refund")
    public CommonResp refund(@RequestBody @Valid PayRefundOrderDto dto) {
        String code = dto.getCode();
        if (StringUtils.isBlank(code)) {
            log.info("code不能为空！");
            return CommonResp.error(RespCode.BAD_REQUEST, ErrorBody.build(new Throwable("code不能为空！")));
        }
        PayService payService = PayTypeServiceMap.SERVICE_HAND_MAP.get(PayTypeEnum.queryPayTypeEnumByCode(code));
        if (payService != null) {
            return CommonResp.ok(payService.refund(dto));
        }
        return CommonResp.ok();
    }

    @GetMapping("queryRefundOrder")
    public CommonResp queryRefundOrder(@RequestParam("code") String code, @RequestParam("outTradeNo") String outTradeNo) {
        if (StringUtils.isBlank(code)) {
            log.info("code不能为空！");
            return CommonResp.error(RespCode.BAD_REQUEST, ErrorBody.build(new Throwable("code不能为空！")));
        }
        PayService payService = PayTypeServiceMap.SERVICE_HAND_MAP.get(PayTypeEnum.queryPayTypeEnumByCode(code));
        if (payService != null) {
            return CommonResp.ok(payService.queryRefundOrder(outTradeNo));
        }
        return CommonResp.ok();
    }

    @GetMapping("closeOrder")
    public CommonResp closeOrder(@RequestParam("code") String code, @RequestParam("outTradeNo") String outTradeNo) {
        if (StringUtils.isBlank(code)) {
            log.info("code不能为空！");
            return CommonResp.error(RespCode.BAD_REQUEST, ErrorBody.build(new Throwable("code不能为空！")));
        }
        PayService payService = PayTypeServiceMap.SERVICE_HAND_MAP.get(PayTypeEnum.queryPayTypeEnumByCode(code));
        if (payService != null) {
            return CommonResp.ok(payService.closeOrder(outTradeNo));
        }
        return CommonResp.ok();
    }

    @PostMapping("appPay")
    public CommonResp appPay(@RequestBody @Valid PayOrderDto dto) {
        String code = dto.getCode();
        if (StringUtils.isBlank(code)) {
            log.info("code不能为空！");
            return CommonResp.error(RespCode.BAD_REQUEST, ErrorBody.build(new Throwable("code不能为空！")));
        }
        PayService payService = PayTypeServiceMap.SERVICE_HAND_MAP.get(PayTypeEnum.queryPayTypeEnumByCode(code));
        if (payService != null) {
            return CommonResp.ok(payService.appPay(dto));
        }
        return CommonResp.ok();
    }

    @PostMapping("wapPay")
    public CommonResp wapPay(@RequestBody @Valid PayOrderDto dto) {
        String code = dto.getCode();
        if (StringUtils.isBlank(code)) {
            log.info("code不能为空！");
            return CommonResp.error(RespCode.BAD_REQUEST, ErrorBody.build(new Throwable("code不能为空！")));
        }
        PayService payService = PayTypeServiceMap.SERVICE_HAND_MAP.get(PayTypeEnum.queryPayTypeEnumByCode(code));
        if (payService != null) {
            return CommonResp.ok(payService.wapPay(dto));
        }
        return CommonResp.ok();
    }

    @GetMapping("returnUrl")
    public void returnUrl() {
        log.info("同步回调");
    }

    @PostMapping("notifyUrl")
    public void notifyUrl(HttpServletRequest request) throws AlipayApiException {
        log.info("异步回调开始~~~~~");
        Map<String, String> params = PayUtils.convertRequestParamsToMap(request);
        String paramsJson = JSON.toJSONString(params);
        log.info("⽀付宝回调，{}", paramsJson);
        // 调⽤SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, properties.getAlipayPublicKey(), properties.getCharset(), properties.getSignType());
        if (signVerified) {
            log.info("⽀付宝SDK验证签名成功~");
            String outTradeNo = params.get("out_trade_no");
            // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
            if (StringUtils.isBlank(outTradeNo)) {
                throw new AlipayApiException("out_trade_no错误");
            }
            log.info("我真的查库了，你相信我~");
            // 2、判断total_amount是否确实为该订单的实际⾦额（即商户订单创建时的⾦额），
            String amount = params.get("total_amount");
            if (StringUtils.isBlank(amount)) {
                throw new AlipayApiException("total_amount错误");
            }
            log.info("我就是金额 ~");
            // 3、验证app_id是否为该商户本⾝。
            if (!StringUtils.equalsIgnoreCase(params.get("app_id"), properties.getAppId())) {
                throw new AlipayApiException("app_id不⼀致");
            }
            log.info("是的，我就是配置中的appId~");
        }
    }
}