package com.base.frame.service.common;

import com.base.frame.common.exception.KnownException;
import com.base.frame.model.base.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * service断言
 */
public class BaseService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 断言结果成功（Code="100"），否则抛出异常
     *
     * @param name   Result结果的描述，如 查询部门
     * @param result
     */
    protected void assertResult(BaseResult result, String name, boolean isKnow) {
        String msg = null;
        if (result == null) {
            msg = name + "错误:返回为空";
        } else if (result.getCode() != 1) {
            msg = String.format("%1$s错误:%2$s", name, result.getMessage());
        }
        if (msg != null) {
            if (isKnow) {
                throw new KnownException("错误:" + msg);
            } else {
                throw new RuntimeException("错误:" + msg);
            }
        }
    }

    /**
     * 断言对象不为空，否则抛出异常
     *
     * @param obj
     * @param message
     */
    protected void assertNotNull(Object obj, String message, boolean isKnow) {
        if (obj == null) {
            if (isKnow) {
                throw new KnownException("错误:" + message);
            } else {
                throw new RuntimeException("错误:" + message);
            }

        }
    }

    /**
     * 断言列表有值，否则抛出异常
     *
     * @param obj
     * @param message
     */
    protected void assertHasItem(List obj, String message, boolean isKnow) {
        if (obj == null || obj.size() == 0) {
            if (isKnow) {
                throw new KnownException("错误:" + message);
            } else {
                throw new RuntimeException("错误:" + message);
            }

        }
    }


    /**
     * 断言结果，如不成功，抛出异常
     *
     * @param input
     * @param target
     * @param message
     * @param isKnow
     */
    protected void assertEqual(Object input, Object target, String message, boolean isKnow) {
        if (input == null) {
            if (target == null) {
                return;
            }
            if (isKnow) {
                throw new KnownException("错误:" + message);
            } else {
                throw new RuntimeException("错误:" + message);
            }
        }
        if (!input.equals(target)) {
            if (isKnow) {
                throw new KnownException("错误:" + message);
            } else {
                throw new RuntimeException("错误:" + message);
            }

        }
    }

    /**
     * 断言表达式为true，如不成功，抛出异常
     *
     * @param exp
     * @param message
     * @param isKnow
     */
    protected void assertTrue(boolean exp, String message, boolean isKnow) {
        if (!exp) {
            if (isKnow) {
                throw new KnownException("错误:" + message);
            } else {
                throw new RuntimeException("错误:" + message);
            }

        }
    }
}
