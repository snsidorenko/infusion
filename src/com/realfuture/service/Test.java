package com.realfuture.service;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Serge on 27.01.2017.
 */
public class Test {
    static final int minPort = 10;
    static final int maxPort = 10000;
    static final int timeout = 200;
    static final int page = 100;
    List<Integer> scan(InetAddress inetAddress) {
        List<Integer> openPortsList = new ArrayList<Integer>(0xFF);
        try {
            System.out.print(inetAddress.getCanonicalHostName());
            NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
            byte[] bc = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bc.length; i++) {
                sb.append(String.format("%02X%s", bc[i], (i < bc.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Scanner> scanners = new ArrayList<Scanner>();
        System.out.println(new Date() + " scanning ports: ");
        int i = minPort;
        while(i < maxPort){
            scanners.add(new Scanner(inetAddress,openPortsList, i, i + page > maxPort?maxPort:i+page));
            i += page;
        }
        ThreadGroup gr = new ThreadGroup("scanners");
        for (Scanner s : scanners) {
            new Thread(gr, s).start();
        }
        while(gr.activeCount() > 0)            ;
        System.out.println(new Date() + " stop: " + openPortsList.size());
        return openPortsList;
    }
    public static void main(String[] args){
        try {
            Object[] ids = new Test().scan(InetAddress.getByName("192.168.0.100")).toArray();
            Arrays.sort(ids);
            for(Object i : ids)
                System.out.println(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Scanner implements Runnable{
        private InetAddress address;
        private List<Integer> opened;
        private int start;
        private int end;

        public Scanner(InetAddress address, List<Integer> opened, int start, int end) {
            this.address = address;
            this.opened = opened;
            this.start = start;
            this.end = end;
        }

        public void run(){
            for (int port = start; port <= end; port++) {
                try {
                    InetSocketAddress isa = new InetSocketAddress(address, port);
                    Socket socket = new Socket();
                    socket.connect(isa,timeout);
                    opened.add(port);
                    socket.close();
                } catch (IOException ioe) {
                    //System.out.println(""+ioe.getMessage());
                }
            }
        }
    }
}
