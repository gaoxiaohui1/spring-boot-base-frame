package com.base.frame.aop.aspect;

import com.base.frame.common.exception.ParamValidErrorException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.util.Set;

/**
 * 参数校验拦截器
 */
@Aspect
@Order(-998) // 异常处理之内
@Component
public class ParamsValidAspect {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final ExecutableValidator validator = factory.getValidator().forExecutables();

    /**
     * 参数校验
     * @param jp
     * @throws Throwable
     */
    @Before("execution(public com.base.frame.model.base.BaseResult com.base.frame.service..*.*(..))")
    public void checkParam(JoinPoint jp) throws Throwable {
        final Object[] args = jp.getArgs();
        if (args.length == 0) {
            return;
        }
        final Signature signature = jp.getSignature();
        final MethodSignature methodSignature = (MethodSignature) signature;
        Set<ConstraintViolation<Object>> validResult = validator.validateParameters(jp.getThis(), methodSignature.getMethod(), args);
        if (!validResult.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Object> constraintViolation : validResult) {
                sb.append(constraintViolation.getMessage() + ",");
            }
            String result = sb.toString();
            if (result != null && result.length() > 0) {
                result = result.substring(0, result.length() - 1);
            }
            throw new ParamValidErrorException(result);
        }


    }
}
