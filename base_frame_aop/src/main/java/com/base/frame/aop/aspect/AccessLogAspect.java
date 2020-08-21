package com.base.frame.aop.aspect;

import com.base.frame.aop.IRecordAccessLog;
import com.base.frame.aop.anation.AccessLogAnnotation;
import com.base.frame.common.tools.data.text.ToolJson;
import com.base.frame.common.tools.data.text.ToolStr;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Date;

@Aspect
@Component
public class AccessLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(AccessLogAspect.class);

    @Autowired
    IRecordAccessLog accessLog;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Pointcut("@annotation(com.base.frame.aop.anation.AccessLogAnnotation)")
    public void accessLogPoint() {
    }

    @Around(value = "accessLogPoint()")
    public Object doAround(ProceedingJoinPoint jp) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = null;
        try {
            result = jp.proceed();
        } catch (Throwable e) {
            stopWatch.stop();
            insertAccessLog(jp, stopWatch.getTotalTimeMillis(), e);
            throw new RuntimeException(e);
        }
        stopWatch.stop();
        insertAccessLog(jp, stopWatch.getTotalTimeMillis(), result);
        return result;
    }


    /**
     * @param jp
     * @param runTime
     * @param result
     */
    private void insertAccessLog(ProceedingJoinPoint jp, long runTime, Object result) {
        try {
            Method sMethod = ((MethodSignature) jp.getSignature()).getMethod();
            AccessLogAnnotation accessLogAnnotation = sMethod.getAnnotation(AccessLogAnnotation.class);
            String url = httpServletRequest.getScheme().concat("://").concat(httpServletRequest.getServerName()).concat(httpServletRequest.getRequestURI());
            String headers = "cookie：".concat(ToolStr.isEmpty(httpServletRequest.getHeader("Cookie")) ? "" : httpServletRequest.getHeader("Cookie"));
            StringBuilder stringBuilder = new StringBuilder();
            if (httpServletRequest.getMethod().equalsIgnoreCase("GET") && httpServletRequest.getQueryString() != null) {
                try {
                    stringBuilder.append("get->").append(URLDecoder.decode(httpServletRequest.getQueryString(), "utf-8")).append("->");
                } catch (UnsupportedEncodingException e) {
                }
            }
            if (!ToolStr.isEmpty(ToolJson.modelToJson(jp.getArgs()))) {
                stringBuilder.append("post");
                for (Object arg : jp.getArgs()) {
                    if (arg instanceof BindingResult) {
                        continue;
                    }
                    if (arg instanceof HttpServletRequest) {
                        continue;
                    }
                    stringBuilder.append("->").append(ToolJson.modelToJson(arg));
                }
            }
            accessLog.save(url, headers, stringBuilder.toString(), httpServletRequest.getMethod()
                    , "", accessLogAnnotation.name(), ToolJson.modelToJson(result)
                    , 0L
                    , ToolStr.isSpace(httpServletRequest.getHeader("Referer")) ? "" : httpServletRequest.getHeader("Referer")
                    , runTime, new Date());
        } catch (Exception e) {
            logger.error("insertAccessLog异常", e);
        }
    }
}