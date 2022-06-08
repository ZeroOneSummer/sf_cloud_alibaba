package com.formssi.mall.common.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorBody {

    private String throwable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS", timezone = "GMT+8")
    private Date throwTime;

    private String message;

    private String stackTrace;

    private Map<String, Object> metadata;

    public static ErrorBody build(Throwable e) {
        return build(e, false);
    }

    public static ErrorBody build(Throwable e, boolean stackTrace) {
        return new ErrorBody(
                e.getClass().getName(), new Date(),
                e.getMessage(),
                stackTrace ? collectStackTrace(e) : null,
                new TreeMap<>());
    }

    private static String collectStackTrace(Throwable e) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw, true)) {
            e.printStackTrace(pw);
            return sw.toString();
        } catch (IOException ex) {
            return "collectStackTrace exception : " + ex.getMessage();
        }
    }

}
