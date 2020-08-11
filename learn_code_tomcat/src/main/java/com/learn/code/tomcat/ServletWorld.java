package com.learn.code.tomcat;

import java.io.IOException;

public class ServletWorld extends MyServlet{
    @Override
    public void doGet(MyRequest request, MyResponse response) {
        try {
            response.write("world get ~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest request, MyResponse response) {
        try {
            response.write("world post ~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}