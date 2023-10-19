package com.example.statefulstateless;

import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    private String receiverAddress;

    public void sendEmail(String receiverAddress, String content) {
        this.receiverAddress = receiverAddress;

        someProcessing();

        send(content);
    }

    private void send(String content) {
        System.out.println("Send email with content '" + content + "' to " + this.receiverAddress);
    }

    private void someProcessing() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
