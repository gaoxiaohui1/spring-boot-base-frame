package com.base.frame.apollotest.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Value("${config.common.key:default}")
    private String cck;

    @Value("${config.private.key:default}")
    private String cpk;

    /**
     * @return
     * @Value实时更新
     */
    @ApiOperation(value = "@Value实时更新", notes = "@Value实时更新")
    @RequestMapping(path = "value", method = RequestMethod.GET)
    public String getConfigValue() {
        return cpk + ";" + cck;
    }

    /**
     * 获取key对应的value
     *
     * @return
     */
    @ApiOperation(value = "获取key对应的value", notes = "获取key对应的value")
    @RequestMapping(path = "key/{key}", method = RequestMethod.GET)
    public String getConfigValue(@PathVariable String key) {
        //只能获取到公共配置（关联的namespace）
        Config configCommon = ConfigService.getConfig("base.frame");
        String valueCommon = configCommon.getProperty(key, "default");
        //只能获取到私有配置
        Config configPrivate = ConfigService.getAppConfig();
        String valuePrivate = configPrivate.getProperty(key, "default");
        return "{" + key + ":" + valueCommon + ":" + valuePrivate + "}";
    }
}
