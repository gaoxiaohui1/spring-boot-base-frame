package com.base.frame.aop.aspect;

import com.base.frame.aop.IRecordAccessLog;
import com.base.frame.aop.anation.AccessLogAnation;
import com.base.frame.common.tools.data.text.ToolJson;
import com.base.frame.common.tools.data.text.ToolStr;
import com.base.frame.common.tools.mail.MailService;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import com.base.frame.model.context.UserInfoContextHolder;
import com.base.frame.model.dto.UserDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.util.Date;

/**
 * controller处理拦截器
 */
@Component
@Order(-999)
@Aspect
public class ControllerAspect {
    private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);
    @Autowired
    private MailService mailService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IRecordAccessLog accessLog;

    /**
     * 报警邮件收信人
     */
    @Value("${spring.mail.to}")
    private String mailTo;

    /**
     * 请求处理
     *
     * @param jp
     * @return
     */
    @Around("execution(public com.base.frame.model.base.BaseResult com.base.frame.api.controller..*.*(..))")
    public Object handRequest(ProceedingJoinPoint jp) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = null;
        try {
            //region 设置用户上下文
            UserDto userDto = new UserDto();
            userDto.setId(1L);
            userDto.setUserName("ggg");
            UserInfoContextHolder.setUser(userDto);
            //endregion
            result = jp.proceed();
        } catch (Throwable e) {
            result = ResultGenerator.fail(e.getMessage());
        } finally {
            if (UserInfoContextHolder.hasUser()) {
                UserInfoContextHolder.clearUser();
            }
        }
        stopWatch.stop();
        String content = getRequestInfo(jp, stopWatch.getTotalTimeMillis(), result);
        emailRequestInfo("请求信息", content);
        insertAccessLog(jp, stopWatch.getTotalTimeMillis(), result);
        return result;
    }

    /**
     * 获取请求信息
     *
     * @param jp
     * @return
     */
    private String getRequestInfo(ProceedingJoinPoint jp, long runTime, Object result) {
        try {
            if (httpServletRequest != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("url：").append(httpServletRequest.getScheme()).append("://").append(httpServletRequest.getServerName()).append(httpServletRequest.getRequestURI());
                stringBuilder.append("\r\n");
                stringBuilder.append("method：").append(httpServletRequest.getMethod());
                stringBuilder.append("\r\n");
                stringBuilder.append("cookie：").append(httpServletRequest.getHeader("Cookie"));
                stringBuilder.append("\r\n");
                stringBuilder.append("referUrl：").append(httpServletRequest.getHeader("Referer"));
                stringBuilder.append("\r\n");
                if (httpServletRequest.getQueryString() != null) {
                    try {
                        stringBuilder.append("queryString：").append(URLDecoder.decode(httpServletRequest.getQueryString(), "utf-8"));
                        stringBuilder.append("\r\n");
                    } catch (UnsupportedEncodingException e) {
                        log.error("getRequestInfo_getQueryString", e);
                    }
                }
                stringBuilder.append("args：").append(ToolJson.modelToJson(jp.getArgs()));
                stringBuilder.append("\r\n");
                stringBuilder.append("返回：").append(ToolJson.modelToJson(result));
                stringBuilder.append("\r\n");
                stringBuilder.append("执行时间：").append(runTime);
                stringBuilder.append("\r\n");
                return stringBuilder.toString();
            }
        } catch (Exception ex) {
            log.error("getRequestInfo", ex);
        }
        return "";
    }

    /**
     * 邮件请求信息
     *
     * @param subject
     * @param content
     */
    private void emailRequestInfo(String subject, String content) {
        try {
            mailService.sendSimpleMailAsync(mailTo.split(","), subject, content);
        } catch (Exception mailEx) {
            log.error("emailRequestInfo发送邮件异常", mailEx);
        }
    }

    /**
     * 是否记录请求信息
     *
     * @param jp
     * @return
     */
    private Boolean ifRecordAccessLog(ProceedingJoinPoint jp) {
        for (Annotation annotation : jp.getSignature().getDeclaringType().getAnnotations()) {
            if (AccessLogAnation.class.equals(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数据库记录请求信息
     *
     * @param jp
     * @param runTime
     * @param result
     */
    private void insertAccessLog(ProceedingJoinPoint jp, long runTime, Object result) {
        try {
            Boolean ifRecordAccessLog = ifRecordAccessLog(jp);
            if (ifRecordAccessLog) {
                String url = httpServletRequest.getScheme().concat("://").concat(httpServletRequest.getServerName()).concat(httpServletRequest.getRequestURI());
                String headers = "cookie：".concat(ToolStr.isSpace(httpServletRequest.getHeader("Cookie")) ? "" : httpServletRequest.getHeader("Cookie"));
                String params = ToolStr.isSpace(ToolJson.modelToJson(jp.getArgs())) ? "" : ToolJson.modelToJson(jp.getArgs());
                if (httpServletRequest.getMethod().equalsIgnoreCase("GET") && httpServletRequest.getQueryString() != null) {
                    try {
                        params = URLDecoder.decode(httpServletRequest.getQueryString(), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                BaseResult baseResult = (BaseResult) result;
                accessLog.save(url, headers, params, httpServletRequest.getMethod()
                        , String.valueOf(baseResult.getCode()), baseResult.getMessage(), ToolJson.modelToJson(result)
                        , 0L
                        , ToolStr.isSpace(httpServletRequest.getHeader("Referer")) ? "" : httpServletRequest.getHeader("Referer")
                        , runTime, new Date());
            }
        } catch (Exception e) {
            log.error("insertAccessLog异常", e);
        }
    }
}