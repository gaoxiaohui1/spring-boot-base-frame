package com.learn.code.tomcat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyServletMapping {
    private String servletName;
    private String url;
    private String clazz;
}
