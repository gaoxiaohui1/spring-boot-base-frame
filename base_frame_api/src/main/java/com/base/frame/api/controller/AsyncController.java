package com.base.frame.api.controller;

import com.base.frame.model.base.BaseResult;
import com.base.frame.service.async.AsyncService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/async")
public class AsyncController {
    @Autowired
    AsyncService asyncService;

    /**
     * 异步无返回
     *
     * @return
     */
    @ApiOperation(value = "异步无返回", notes = "异步无返回")
    @RequestMapping(path = "asyncVoid", method = RequestMethod.GET)
    public BaseResult asyncVoid() {
        BaseResult result = asyncService.asyncVoid();
        return result;
    }

    /**
     * 异步有返回
     *
     * @return
     */
    @ApiOperation(value = "异步有返回", notes = "异步有返回")
    @RequestMapping(path = "async", method = RequestMethod.GET)
    public BaseResult async() {
        BaseResult result = asyncService.async();
        return result;
    }

    /**
     * 异步混合返回
     *
     * @return
     */
    @ApiOperation(value = "异步混合返回", notes = "异步混合返回")
    @RequestMapping(path = "asyncMix", method = RequestMethod.GET)
    public BaseResult asyncMix() {
        BaseResult result = asyncService.asyncMix();
        return result;
    }
}
