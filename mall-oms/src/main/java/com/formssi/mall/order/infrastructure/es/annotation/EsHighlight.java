package com.formssi.mall.order.infrastructure.es.annotation;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsHighlight {

    /**
     * filed name
     */
    String name() default "";

    /**
     * The size of a fragment in characters
     * @return
     */
    int fragmentSize() default HighlightBuilder.DEFAULT_FRAGMENT_CHAR_SIZE;

    /**
     * The (maximum) number of fragments
     * @return
     */
    int numberOfFragments() default  HighlightBuilder.DEFAULT_NUMBER_OF_FRAGMENTS;

    /**
     * The offset from the start of the fragment to the start of the highlight
     * @return
     */
    int fragmentOffset() default -1;
}
