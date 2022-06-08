package com.formssi.mall.cms.infrastructure.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class test {
    public final static String START_HTML_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/11.html";
    @Test
    public void fangfa() throws IOException {
        Document document = Jsoup.connect(START_HTML_URL).get();
        Elements select = document.getElementsByClass("citytr");
        for (int i = 0; i < select.size(); i++) {
            Element element = select.get(i);
            Elements td = element.select("td");
            String text = td.text();
            System.out.println(text);
            String shi = text.substring(0, 6);
            System.out.println(shi);
            String substring = text.substring(13);
            System.out.println(substring);
        }
    }
}
