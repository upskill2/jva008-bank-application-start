package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.service.EmailService;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;


public class TestEmailService {

    @Test
    public void testEmailQueueImpl() {
        EmailService emailService = new EmailService();

        Client client = new Client("Smith John", Gender.MALE, "Kyiv", LocalDate.of(2009,12,12));
        client.addAccount(new SavingAccount(1, 1000.0));
        client.addAccount(new CheckingAccount(2, 1000.0, 100.0));

        Email email1 = new Email(client, "@toABC", "@from Client 1");
        Email email2 = new Email(client, "@toDEF", "@from Client 1");

        emailService.sendNotificationEmail(email1);
        emailService.sendNotificationEmail(email2);


        assertEquals(2, emailService.getSize());

    }

    @Test
    public void testSendingAnEmail() {
        EmailService emailService = new EmailService();

        Client client = new Client("Smith John", Gender.MALE, "Kyiv", LocalDate.of(2009,12,12));
        client.addAccount(new SavingAccount(1, 1000.0));
        client.addAccount(new CheckingAccount(2, 1000.0, 100.0));

        Email email1 = new Email(client, "@toABC", "@from Client 1");
        Email email2 = new Email(client, "@toDEF", "@from Client 1");

        emailService.sendNotificationEmail(email1);
        emailService.sendNotificationEmail(email2);


        emailService.run();

        assertEquals(0, emailService.getSize());

    }

}
