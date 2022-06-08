package com.formssi.mall.exception.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.entity.resp.ErrorBody;
import com.formssi.mall.common.entity.resp.RespCode;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.exception.exception.StateCodeException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.Servlet;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTransientConnectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 全局Facade通知处理
 * 主要做两个事情 1. 全局异常拦截并处理  2.DTO参数绑定
 */
@RestControllerAdvice
@ConditionalOnClass(value = Servlet.class)
public class GlobalFacadeAdvice {

    /**
     * INTERNAL_SERVER_ERROR RespCode
     */
    private static final RespCode INTERNAL_SERVER_ERROR = RespCode.INTERNAL_SERVER_ERROR;

    /**
     * BAD_REQUEST RepsCode
     */
    private static final RespCode BAD_REQUEST = RespCode.BAD_REQUEST;

    /**
     * 未声明的未知异常
     * @param e UndeclaredThrowableException
     * @return INTERNAL_SERVER_ERROR CommonResp
     */
    @ExceptionHandler(value = UndeclaredThrowableException.class)
    public CommonResp<ErrorBody> throwable(UndeclaredThrowableException e) {
        e.printStackTrace();
        return CommonResp.error(INTERNAL_SERVER_ERROR, ErrorBody.build(e.getCause()));
    }

    /**
     * 空指针异常
     * @param e NullPointerException
     * @return NULL_POINTER_EXCEPTION CommonResp
     */
    @ExceptionHandler(value = NullPointerException.class)
    public CommonResp<ErrorBody> nullPointerException(NullPointerException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        StackTraceElement ste = e.getStackTrace()[0];
        Map<String, Object> metadata = errorBody.getMetadata();
        metadata.put("position", ste.getClassName() + "#" + ste.getMethodName() + ":" + ste.getLineNumber());
        metadata.put("fileName", ste.getFileName());
        errorBody.setMetadata(metadata);
        return CommonResp.error(RespCode.NULL_POINTER_EXCEPTION, errorBody);
    }

    /**
     * 异常级别
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResp<ErrorBody> exception(Exception e) {
        e.printStackTrace();
        return CommonResp.error(INTERNAL_SERVER_ERROR, ErrorBody.build(e));
    }

    /**
     * http 异常
     */
    @ExceptionHandler(value = HttpStatusCodeException.class)
    public CommonResp<ErrorBody> httpStatusCodeException(HttpStatusCodeException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        HttpStatus statusCode = e.getStatusCode();
        Map<String, Object> metadata = errorBody.getMetadata();
        metadata.put("statusCode", statusCode.value());
        metadata.put("RespBody", e.getResponseBodyAsString());
        errorBody.setMetadata(metadata);
        return CommonResp.error(String.valueOf(statusCode.value()), statusCode.getReasonPhrase(), errorBody);
    }

    /**
     * 不支持的请求媒介类型
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public CommonResp<ErrorBody> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        List<MediaType> supportedMediaTypes = e.getSupportedMediaTypes();
        List<String> support = new ArrayList<>();
        for (MediaType mediaType : supportedMediaTypes) {
            support.add(mediaType.getType());
        }
        Map<String, Object> metadata = errorBody.getMetadata();
        MediaType contentType = e.getContentType();
        if (contentType != null) {
            metadata.put("contentType", contentType.getSubtype());
        }
        metadata.put("support", support);
        errorBody.setMetadata(metadata);
        return CommonResp.error(RespCode.UNSUPPORTED_MEDIA_TYPE, errorBody);
    }

    /**
     * 请求方法不支持
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public CommonResp<ErrorBody> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        Map<String, Object> metadata = errorBody.getMetadata();
        metadata.put("method", e.getMethod());
        metadata.put("supportedMethods", e.getSupportedMethods());
        return CommonResp.error(RespCode.METHOD_NOT_ALLOWED, errorBody);
    }

    /**
     * 请求参数(Parameter)缺失
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResp<ErrorBody> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        errorBody.getMetadata().put("parameterName", e.getParameterName());
        return CommonResp.error(BAD_REQUEST, errorBody);
    }

    /**
     * 数据读取异常
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public CommonResp<ErrorBody> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException fe = (InvalidFormatException) e.getCause();
            errorBody.getMetadata().put("requiredType", fe.getTargetType().getName());
            errorBody.getMetadata().put("targetValue", fe.getValue());
        }
        return CommonResp.error(BAD_REQUEST, errorBody);
    }

    /**
     * 请求参数(RequestPart)缺失
     */
    @ExceptionHandler(value = MissingServletRequestPartException.class)
    public CommonResp<ErrorBody> missingServletRequestPartException(MissingServletRequestPartException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        errorBody.getMetadata().put("partName", e.getRequestPartName());
        return CommonResp.error(BAD_REQUEST, errorBody);
    }

    /**
     * 参数转换异常
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public CommonResp<ErrorBody> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        Map<String, Object> metadata = errorBody.getMetadata();
        Class<?> requiredType = e.getRequiredType();
        if (requiredType != null) {
            metadata.put("requiredType", requiredType.getName());
        }
        metadata.put(e.getName(), e.getValue());
        return CommonResp.error(BAD_REQUEST, errorBody);
    }

    /**
     * 参数验证异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResp<ErrorBody> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        if (e.getBindingResult().getFieldError() != null) {
            FieldError fieldError = e.getBindingResult().getFieldError();
            if (fieldError != null) {
                errorBody.getMetadata().put(fieldError.getField(), fieldError.getDefaultMessage());
                errorBody.setMessage(fieldError.getDefaultMessage());
            }
        }
        return CommonResp.error(BAD_REQUEST, errorBody.getMessage(), errorBody);
    }

    /**
     * 参数验证异常
     */
    @ExceptionHandler(value = BindException.class)
    public CommonResp<ErrorBody> bindException(BindException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> list = bindingResult.getAllErrors();
        List<Map<String, Object>> dataList = new ArrayList<>(list.size());
        Map<String, Object> fieldError = new TreeMap<>();
        String defaultMessage = null;
        for (ObjectError error : list) {
            if (error instanceof FieldError) {
                FieldError field = (FieldError) error;
                defaultMessage = field.getDefaultMessage();
                fieldError.put(field.getField(), field.getRejectedValue());
                dataList.add(fieldError);
            }
        }
        errorBody.getMetadata().put("errorList", dataList);
        int length = 100;
        // 提示大于100个字符就取参数错误提示
        if (defaultMessage == null || defaultMessage.length() > length) {
            defaultMessage = BAD_REQUEST.getMessage();
        }
        return CommonResp.error(BAD_REQUEST, defaultMessage, errorBody);
    }

    /**
     * 无效请求头异常
     */
    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public CommonResp<ErrorBody> missingRequestHeaderException(MissingRequestHeaderException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        errorBody.getMetadata().put("headerName", e.getHeaderName());
        return CommonResp.error(RespCode.MISSING_REQ_HEADER, errorBody);
    }

    /**
     * SQL 异常
     */
    @ExceptionHandler(value = SQLException.class)
    public CommonResp<ErrorBody> sqlException(SQLException e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        Map<String, Object> metadata = errorBody.getMetadata();
        metadata.put("SQLState", e.getSQLState());
        metadata.put("errorCode", e.getErrorCode());
        errorBody.setMetadata(metadata);
        return CommonResp.error(RespCode.SQL_EXCEPTION, errorBody);
    }

    /**
     * 服务连接异常
     */
    @ExceptionHandler(value = ConnectException.class)
    public CommonResp<ErrorBody> connectException(ConnectException e) {
        e.printStackTrace();
        return CommonResp.build(RespCode.CONNECT_EXCEPTION, ErrorBody.build(e));
    }

    /**
     * 数据库SQL语法错误
     */
    @ExceptionHandler(value = {SQLSyntaxErrorException.class, SQLDataException.class})
    public CommonResp<ErrorBody> sqlSyntaxErrorException(Exception e) {
        e.printStackTrace();
        ErrorBody errorBody = ErrorBody.build(e);
        return CommonResp.build(RespCode.SQL_EXCEPTION, errorBody);
    }


    /**
     * 服务器数据库发生异常
     */
    @ExceptionHandler(value = {SQLTransientConnectionException.class})
    public CommonResp<ErrorBody> sqlTransientConnectionException(Exception e) {
        e.printStackTrace();
        return CommonResp.build(RespCode.DATABASE_EXCEPTION, ErrorBody.build(e));
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(value = {ClassCastException.class})
    public CommonResp<ErrorBody> classCastException(ClassCastException e) {
        e.printStackTrace();
        return CommonResp.build(INTERNAL_SERVER_ERROR, ErrorBody.build(e));
    }

    /**
     * 自定义 状态码异常
     */
    @ExceptionHandler(value = StateCodeException.class)
    public CommonResp<ErrorBody> stateCodeException(StateCodeException e) {
        e.printStackTrace();
        return CommonResp.build(e.getStateCode(), e.getMessage(), ErrorBody.build(e));
    }


    /**
     * 自定义 业务异常
     */
    @ExceptionHandler(value = BusinessException.class)
    public CommonResp<Serializable> businessException(BusinessException e) {
        e.printStackTrace();
        return CommonResp.build(e.getStateCode(), e.getMessage(), e.getData());
    }


}
