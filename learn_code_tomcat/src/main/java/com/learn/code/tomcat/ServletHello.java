package com.learn.code.tomcat;

import java.io.IOException;

public class ServletHello extends MyServlet{
    @Override
    public void doGet(MyRequest request, MyResponse response) {
        try {
            response.write("hello get ~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest request, MyResponse response) {
        try {
            response.write("hello post ~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
