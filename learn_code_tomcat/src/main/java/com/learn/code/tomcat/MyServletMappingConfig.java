package com.learn.code.tomcat;

import java.util.ArrayList;
import java.util.List;

public class MyServletMappingConfig {
    public static List<MyServletMapping> servletMappingList=new ArrayList<>();
    static {
        servletMappingList.add(new MyServletMapping("hello","/h","com.learn.code.tomcat.ServletHello"));
        servletMappingList.add(new MyServletMapping("world","/w","com.learn.code.tomcat.ServletWorld"));
    }
}
