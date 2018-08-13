package com.base.frame.model.base;

/**
 *
 */
public class ResultGenerator {
    //region 成功

    /**
     * 成功
     *
     * @return
     */
    public static BaseResult success() {
        BaseResult result = new BaseResult();
        result.setCode(1);
        return result;
    }

    /**
     * 成功
     *
     * @param message
     * @return
     */
    public static BaseResult success(String message) {
        BaseResult result = new BaseResult();
        result.setCode(1);
        result.setMessage(message);
        return result;
    }

    /**
     * 成功
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> success(T t) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(1);
        result.setData(t);
        return result;
    }

    /**
     * 成功
     *
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> success(T t, String message) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(1);
        result.setMessage(message);
        result.setData(t);
        return result;
    }
    //endregion

    //region 失败

    /**
     * 失败
     *
     * @param message
     * @return
     */
    public static BaseResult fail(String message) {
        BaseResult result = new BaseResult();
        result.setCode(0);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败
     *
     * @param message
     * @param errorCode
     * @return
     */
    public static BaseResult fail(String message, Integer errorCode) {
        BaseResult result = new BaseResult();
        result.setCode(0);
        result.setMessage(message);
        result.setErrorCode(errorCode);
        return result;
    }

    /**
     * 失败
     *
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> fail(T t, String message) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(0);
        result.setMessage(message);
        result.setData(t);
        return result;
    }

    /**
     * 失败
     *
     * @param t
     * @param message
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> fail(T t, String message, Integer errorCode) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(0);
        result.setMessage(message);
        result.setErrorCode(errorCode);
        result.setData(t);
        return result;
    }
    //endregion
}
