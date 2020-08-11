package com.learn.code.tomcat;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;

@Data
public class MyRequest {
    private String url;
    private String method;

    public MyRequest(InputStream inputStream) throws IOException {
        String httpRequest = "";
        byte[] httpRequestBytes = new byte[1024];
        int length = inputStream.read(httpRequestBytes);
        if (length > 0) {
            httpRequest = new String(httpRequestBytes);
            String httpHead = httpRequest.split("\n")[0];
            url = httpHead.split("\\s")[1].split("\\?")[0];
            method = httpHead.split("\\s")[0];
//            System.out.println("httpRequest->" + httpRequest);
            System.out.println("MyRequest->this->" + this);
        }
    }
}
