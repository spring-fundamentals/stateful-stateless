package com.example.statefulstateless;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class SendEmailServiceTest {

    @Autowired
    private SendEmailService sendEmailService;

    @Test
    void sequentialCalls() {
        this.sendEmailService.sendEmail("hermine.granger@hogwarts.edu", "Good Morning Ms. Granger.");
        this.sendEmailService.sendEmail("ron.weasley@hogwarts.edu", "Good Morning Mr. Weasley.");
        this.sendEmailService.sendEmail("harry.potter@hogwarts.edu", "Good Morning Mr. Potter.");
    }

    @Test
    void parallelCalls() throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<Void> request1 = createRequestFor("hermine.granger@hogwarts.edu", "Good Morning Ms. Granger.");
        Callable<Void> request2 = createRequestFor("ron.weasley@hogwarts.edu", "Good Morning Mr. Weasley.");
        Callable<Void> request3 = createRequestFor("harry.potter@hogwarts.edu", "Good Morning Mr. Potter.");

        executorService.invokeAll(List.of(request1, request2, request3));
    }

    private Callable<Void> createRequestFor(String mail, String content) {
        return () -> {
            this.sendEmailService.sendEmail(mail, content);
            return null;
        };
    }
}