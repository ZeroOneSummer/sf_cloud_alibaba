package com.formssi.mall.order.infrastructure.es.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsHighlightStyled {

    /**
     * the default opening tag
     * @return
     */
    String[] preTags() default "<em>";


    /**
     * the default closing tag
     * @return
     */
    String[] postTags() default "</em>";


}
