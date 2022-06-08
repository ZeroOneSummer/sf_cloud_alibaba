package com.formssi.mall.common.service.impl;

import com.formssi.mall.common.service.Templates;
import com.formssi.mall.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.List;

/**
 * @author jiangyaoting
 */
@Service
public class TemplatesImpl implements Templates {

    @Autowired
    private TemplateEngine templateEngine;


    /**
     * 获得验证码模板
     *
     * @param email
     * @param code
     * @param timeout
     * @return
     */
    @Override
    public String getCaptchaTempl(String email, String code, int timeout) {
        Context context = new Context();
        context.setVariable("title", "验证码");
        context.setVariable("email", email);
        context.setVariable("code", code);
        context.setVariable("date", DateUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        return templateEngine.process("/mailCode", context);
    }

    /**
     * 获得活动模板
     *
     * @param email
     * @param type
     * @return
     */
    @Override
    public String getActiveTempl(String email, String subject, String text, List<String> images, String type) {
        Context context = new Context();
        context.setVariable("type", type);
        context.setVariable("images", images);
        return templateEngine.process("/ActiveTemplates", context);
    }

    /**
     * 获得预警模板
     *
     * @param email
     * @param type
     * @return
     */
    @Override
    public String getWarningTempl(String email, String subject, String text, List<String> images, String type) {
        Context context = new Context();
        context.setVariable("type", type);
        context.setVariable("email", email);
        context.setVariable("text", text);
        context.setVariable("date", DateUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        return templateEngine.process("/WarningTemplates", context);
    }

}
