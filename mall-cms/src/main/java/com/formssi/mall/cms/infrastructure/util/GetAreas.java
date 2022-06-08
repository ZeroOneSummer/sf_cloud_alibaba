package com.formssi.mall.cms.infrastructure.util;

import com.formssi.mall.cms.domain.entity.CmsArea;
import com.formssi.mall.cms.infrastructure.repository.CmsAreaRepositoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Service
@Slf4j
public class GetAreas {
    @Autowired
    private CmsAreaRepositoryServiceImpl cmsAreaRepositoryService;


    public final static String ROOT_HTML_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/index.html";
    public final static String START_HTML_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/";

    /**
     *解析网址，保存数据到库
     */
    public void save() throws IOException, InterruptedException {
        Integer sheng = 100000;
        String shi = null;
        String xian = null;
        Document parse = Jsoup.connect(ROOT_HTML_URL).get();
        Elements provincetr = parse.getElementsByClass("provincetr");
        for (int i = 0; i < provincetr.size(); i++) {
            //获得每行省份对象
            Element element = provincetr.get(i);
            //获得每行的省份对象
            Elements td = element.select("td");
            for (int i1 = 0; i1 < td.size(); i1++) {
                //定义省份code
                sheng = sheng + 10000;
                //获得每个省份
                Element element1 = td.get(i1);
                //获得每个标签的href
                String href = element1.select("a").attr("href");
                //获得每个省份的text
                String text = element1.text();
                //获得市级的url
                String shiurl = START_HTML_URL + href;
                //保存省对象
                cmsAreaRepositoryService.save(new CmsArea(sheng.toString(), 1, text, null));
                //获得市级对象
                Document document = Jsoup.connect(shiurl).get();
                Elements citytr = document.getElementsByClass("citytr");
                //遍历citytr
                for (int i2 = 0; i2 < citytr.size(); i2++) {
                    //获得每个citytr
                    Element element2 = citytr.get(i2);
                    //拿到市级每个td
                    Elements td1 = element2.select("td");
                    //每个县级的url
                    String url = td1.select("a").attr("href");
                    //拼接县级url
                    String xianurl = START_HTML_URL + url;
                    //市级的text
                    String text1 = td1.text();
                    //市级code
                    shi = text1.substring(0, 12);
                    //市级name
                    String shiname = text1.substring(13);
                    cmsAreaRepositoryService.save(new CmsArea(shi, 2, shiname, sheng.toString()));
                    //获取县级对象
                    Document document2;
                    try {
                        document2 = Jsoup.connect(xianurl).timeout(3000).get();
                    } catch (SocketTimeoutException e) {
                        try {
                            document2 = Jsoup.connect(xianurl).timeout(6000).get();
                        } catch (SocketTimeoutException ioException) {
                            try {
                                document2 = Jsoup.connect(xianurl).timeout(9000).get();
                            } catch (SocketTimeoutException exception) {
                                log.info(xianurl + "解析失败");
                                continue;
                            }
                        }
                    }
                    Elements select = document2.getElementsByClass("countytr");
                    for (int i3 = 0; i3 < select.size(); i3++) {
                        //获得每个县级tr对象
                        Element element3 = select.get(i3);
                        //获得每个县级td对象
                        Elements td2 = element3.select("td");
                        //获得县级url
                        String url2 = td2.select("a").attr("href");
                        //县级text
                        String text2 = td2.text();
                        //县级code
                        xian = text2.substring(0, 12);
                        //县级name
                        String xianname = text2.substring(13);
                        cmsAreaRepositoryService.save(new CmsArea(xian, 3, xianname, shi));
                        //拼接街道url
                        //中间拼接
                        String substring = text2.substring(0, 2);
                        String jieurl = START_HTML_URL + substring + "/" + url2;
                        if (StringUtils.isEmpty(url2)) {
                            continue;
                        }
                        //获取街道对象
                        Document document3;
                        try {
                            document3 = Jsoup.connect(jieurl).timeout(3000).get();
                        } catch (SocketTimeoutException e) {
                            try {
                                document3 = Jsoup.connect(jieurl).timeout(6000).get();
                            } catch (SocketTimeoutException ioException) {
                                try {
                                    document3 = Jsoup.connect(jieurl).timeout(9000).get();
                                } catch (SocketTimeoutException exception) {
                                    log.info(jieurl + "解析失败");
                                    continue;
                                }
                            }
                        }
                        Elements towntr = document3.getElementsByClass("towntr");
                        for (int i4 = 0; i4 < towntr.size(); i4++) {
                            //获取每个街道tr对象
                            Element element4 = towntr.get(i4);
                            //获取每个街道td对象
                            Elements td3 = element4.select("td");
                            //街道text
                            String text3 = td3.text();
                            //街道code
                            String jie = text3.substring(0, 12);
                            //街道name
                            String jiename = text3.substring(13);
                            cmsAreaRepositoryService.save(new CmsArea(jie, 4, jiename, xian));
                        }
                    }
                }
            }
        }
        gethainan();
    }

    /**
     * 处理特殊省份数据
     */
    private void gethainan() throws IOException {
        String parentcode="460400000000";
        String path="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2021/46/4604.html";
        Document document = null;
        try {
            document = Jsoup.connect(path).get();
        } catch (SocketTimeoutException e) {
            try {
                document = Jsoup.connect(path).timeout(6000).get();
            } catch (IOException ioException) {
                log.error("解析失败:"+path);
                return;
            }
        }
        Elements towntr = document.getElementsByClass("towntr");
        for (int i = 0; i < towntr.size(); i++) {
            Element element = towntr.get(i);
            Elements td = element.select("td");
            String text = td.text();
            String substring = text.substring(0, 13);
            String substring1 = text.substring(13);
            cmsAreaRepositoryService.save(new CmsArea(substring,4,substring1,parentcode));

        }
    }


}