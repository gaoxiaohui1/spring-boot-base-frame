package com.base.frame.aop.aspect;

import com.base.frame.common.exception.KnownException;
import com.base.frame.common.exception.ParamValidErrorException;
import com.base.frame.common.tools.mail.MailService;
import com.base.frame.model.base.BaseResult;
import com.base.frame.model.base.ResultGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务层异常捕获拦截器
 */
@Aspect
@Component
@Order(-999)
public class ServiceErrorAspect {
    private static final Logger log = LoggerFactory.getLogger(ServiceErrorAspect.class);

    /**
     * 邮件发送
     */
    @Autowired
    private MailService mailService;

    /**
     * 报警邮件收信人
     */
    @Value("${spring.mail.to}")
    private String mailTo;

    /**
     * 异常处理
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("execution(public com.base.frame.model.base.BaseResult com.base.frame.service..*.*(..))")
    public Object handException(ProceedingJoinPoint jp) throws Throwable {
        try {
            Object rvt = jp.proceed();
            return rvt;
        } catch (Throwable e) {
            BaseResult result = ResultGenerator.fail("发生内部错误");
            //region 异常处理
            try {
                //已知异常不再，记录
                if (e instanceof KnownException) {
                    result.setMessage(e.getMessage());
                } else if (e instanceof ParamValidErrorException) {
                    result.setMessage(e.getMessage());
                    result.setCode(300);
                } else {
                    StringBuilder sb = new StringBuilder();
                    Object[] args = jp.getArgs();
                    if (args != null && args.length > 0) {
                        for (int i = 0; i < args.length; i++) {
                            if (args == null) {
                                sb.append("Index:" + i + ",Data:null\r\n");
                            } else {
                                sb.append("Index:" + i + ",Data:" + args[i] + "\r\n");
                            }
                        }
                    }
                    final MethodSignature methodSignature = (MethodSignature) jp.getSignature();
                    log.error("服务发生异常,\r\n方法：" + methodSignature.getDeclaringType().getName() + "." + methodSignature.getName() + "\r\n参数：" + sb.toString(), e);
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("\r\n方法：" + methodSignature.getDeclaringType().getName() + "." + methodSignature.getName());
                        stringBuilder.append("\r\n参数：" + sb.toString());
                        stringBuilder.append("\r\n Exception：" + e.toString());
                        stringBuilder.append("\r\n StackTrace：");
                        for (int i = 0; i < e.getStackTrace().length; i++) {
                            stringBuilder.append("\r\n" + e.getStackTrace()[i].toString());
                        }
                        mailService.sendSimpleMailAsync(mailTo.split(","), "base_frame异常", stringBuilder.toString());
                    } catch (Exception mailEx) {
                        log.error("base_frame异常结果发送邮件异常", mailEx);
                    }
                    result.setMessage("发生内部错误");
                    result.setCode(500);
                }
            } catch (Exception ee) {
                log.error("异常结果生成异常", ee);
                result.setMessage("发生内部错误");
                result.setCode(1000);
            }
            if (result.getData() != null && result.getData() instanceof String) {
                result.setData(null);
            }
            //endregion
            return result;
        }

    }
}
