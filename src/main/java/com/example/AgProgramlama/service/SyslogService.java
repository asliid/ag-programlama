package com.example.AgProgramlama.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SyslogService {
    public void startSyslogListener() throws IOException {
        DatagramSocket socket = new DatagramSocket(514);  // Syslog UDP portu
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received syslog message: " + message);
            // Veritabanına kaydetme
        }
    }
}