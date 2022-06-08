package com.formssi.mall.common;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author jiangyaoting
 */
@SpringBootTest
public class TemplateTest {

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void Demo_Test(){
        Context context = new Context();
        context.setVariable("title","111");
        String content = templateEngine.process("/mailCode", context);
    }

}
