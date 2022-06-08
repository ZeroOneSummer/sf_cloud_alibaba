package com.formssi.mall.cms.infrastructure.util;

import com.formssi.mall.cms.domain.entity.CmsArea;
import com.formssi.mall.cms.infrastructure.repository.CmsAreaRepositoryServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
@Service
public class GetArea {
    @Autowired
    private CmsAreaRepositoryServiceImpl cmsAreaRepositoryService;

    public final static String ROOT_HTML_URL = "http://www.mca.gov.cn/article/sj/xzqh/2020/20201201.html";

    public void fangfa() throws IOException {
        Document parse = Jsoup.parse(new File("E:\\xianshiqu\\a.html"), "UTF-8");
        Elements allElements = parse.getAllElements().select("tr");
        String sheng = "130000";
        int b = 1;
        String shi=null;
        for (int i = 3; i <= allElements.size() - 10; i++) {
            Element element = allElements.get(i);
            String[] s = element.text().split(" ");
            if (s.length == 1) {
                //处理无区域编号的对象
                cmsAreaRepositoryService.save(new CmsArea("46030" + b, 3, s[0], "460300"));
                b++;
            } else {
                //处理有区域编号的对象
                //省
                if(s[1].contains("省")||s[1].contains("自治区")||s[1].contains("自治洲")){
                    sheng=s[0];
                    cmsAreaRepositoryService.save(new CmsArea(s[0], 1, s[1], null));
                    continue;
                }
                //直辖市
                if (s[1].contains("北京市")||s[1].contains("天津市")||s[1].contains("上海市")||s[1].contains("重庆市")) {
                    cmsAreaRepositoryService.save(new CmsArea(s[0], 1, s[1], null));
                    shi=s[0];
                    continue;
                }
                //普通市
                if (s[1].contains("市")) {
                    if (s[0].substring(1, 2) .equals(sheng.substring(1, 2)) ) {
                        cmsAreaRepositoryService.save(new CmsArea(s[0], 2, s[1], sheng));
                        shi=s[0];
                        continue;
                    }
                }
                //区县
                if(s[1].contains("区")||s[1].contains("县"))
                if (s[0].substring(1, 2) .equals(shi.substring(1, 2)) ) {
                    cmsAreaRepositoryService.save(new CmsArea(s[0], 3, s[1], shi));
                    continue;
                }
                //特别行政区
                cmsAreaRepositoryService.save(new CmsArea(s[0], 1, s[1], null));
            }
        }
    }
}