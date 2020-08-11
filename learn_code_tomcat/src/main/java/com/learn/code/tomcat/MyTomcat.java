package com.learn.code.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyTomcat {
    private int port = 10010;
    private Map<String, String> urlServletMap = new HashMap<>();

    private void initServletMapping() {
        for (MyServletMapping servletMapping : MyServletMappingConfig.servletMappingList) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
        }
    }

    private void dispatch(MyRequest request, MyResponse response) {
        String clazz = urlServletMap.get(request.getUrl());
        if (null == clazz) {
            try {
                response.write("url not exists");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Class<MyServlet> myServletClass = (Class<MyServlet>) Class.forName(clazz);
            MyServlet myServlet = myServletClass.newInstance();
            myServlet.service(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MyTomcat(int port) {
        this.port = port;
    }

    public void start() {
        initServletMapping();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("MyTomcat is starting now ...");
            while (true) {
                System.out.println("waiting requests's arrive ...");
                Socket socket = serverSocket.accept();
                System.out.println("request is coming ...");
                System.out.println("request start ...");
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                MyRequest request = new MyRequest(inputStream);
                MyResponse response = new MyResponse(outputStream);

                dispatch(request, response);
                socket.close();
                System.out.println("request end ...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new MyTomcat(10010).start();
    }
}
