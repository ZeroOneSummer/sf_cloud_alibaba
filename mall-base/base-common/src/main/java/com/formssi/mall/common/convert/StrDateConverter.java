package com.formssi.mall.common.convert;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.lang3.time.DateUtils;
import org.dozer.DozerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StrDateConverter extends DozerConverter<String, Date> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StrDateConverter.class);

    public StrDateConverter() {
        super(String.class, Date.class);
    }

    /**
     * String -> Date
     */
    @Override
    public Date convertTo(String source, Date destination) {
        String pattern = DateFormatEnum.DATE_P1.toString();
        try {
            if (StringUtils.isNotBlank(source)){
                if(isRightDateStr(source, DateFormatEnum.DATE_P1.getPattern())) {
                    pattern = DateFormatEnum.DATE_P1.getPattern();
                }else if(isRightDateStr(source, DateFormatEnum.DATE_P2.getPattern())){
                    pattern = DateFormatEnum.DATE_P2.getPattern();
                }else if(isRightDateStr(source, DateFormatEnum.DATE_P3.getPattern())){
                    pattern = DateFormatEnum.DATE_P3.getPattern();
                }else if(isRightDateStr(source, DateFormatEnum.DATE_P4.getPattern())){
                    pattern = DateFormatEnum.DATE_P4.getPattern();
                }else if(isRightDateStr(source, DateFormatEnum.DATE_P5.getPattern())){
                    pattern = DateFormatEnum.DATE_P5.getPattern();
                }else{
                    LOGGER.error("不支持的日期类型转换: {}", source);
                    throw new RuntimeException("不支持的日期类型转换: " + source);
                }
            }
            return StringUtils.isBlank(source) ? null : DateUtils.parseDate(source, pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date -> String
     */
    @Override
    public String convertFrom(Date source, String destination) {
        FastDateFormat df = FastDateFormat.getInstance(DateFormatEnum.DATE_P1.getPattern(), null, null);
        return source == null ? null : df.format(source);
    }

    /**
     * 判断是否是对应的格式的日期字符串
     */
    public static boolean isRightDateStr(String dateStr, String datePattern){
        DateFormat dateFormat  = new SimpleDateFormat(datePattern);
        try {
            dateFormat.setLenient(false);
            Date date = dateFormat.parse(dateStr);
            String newDateStr = dateFormat.format(date);
            return dateStr.equals(newDateStr);
        } catch (ParseException e) {
            return false;
        }
    }

    private enum DateFormatEnum {
        DATE_P1("yyyy-MM-dd HH:mm:ss"),
        DATE_P2("yyyy-MM-dd"),
        DATE_P3("yyyy/MM/dd HH:mm:ss"),
        DATE_P4("yyyy/MM/dd"),
        DATE_P5("yyyyMMdd")
        ;
        private final String pattern;

        DateFormatEnum(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }
}