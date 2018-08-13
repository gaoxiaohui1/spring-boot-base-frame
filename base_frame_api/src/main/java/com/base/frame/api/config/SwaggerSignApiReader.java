package com.base.frame.api.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * header传参swagger设置
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
public class SwaggerSignApiReader implements springfox.documentation.spi.service.OperationBuilderPlugin {
    private TypeResolver resolver;

    public SwaggerSignApiReader(TypeResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void apply(OperationContext context) {
        List<Parameter> parameters = new ArrayList<>();
        Parameter optuserParameter = new ParameterBuilder()
                .name("sign")
                .description("签名")
                .defaultValue(null)
                .required(false)
                .type(resolver.resolve(String.class))
                .allowMultiple(false)
                //这里写interger不行，swagger应该有个映射关系
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .parameterAccess("")
                .build();
        parameters.add(optuserParameter);
        //.parameters方法内部是合并逻辑
        context.operationBuilder().parameters(parameters);
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
