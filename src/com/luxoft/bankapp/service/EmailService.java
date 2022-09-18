package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Email;
import com.luxoft.bankapp.domain.EmailQueue;

public class EmailService implements Runnable{

    EmailQueue emailQueue = new EmailQueue();
    Object monitor = new Object();
    boolean shouldTerminate = false;

    public EmailService(){
        Thread t = new Thread(this);
        t.start();


    }

    @Override
    public void run() {

        synchronized (monitor){

            while(getSize()>0){

                Email email = emailQueue.get();
                System.out.println(email.toString());

                monitor.notify();
                Thread.yield();

            }
        }


    }


    public void close(boolean terminate){

        shouldTerminate=true;
    }

    public void sendNotificationEmail(Email email) {

        emailQueue.add(email);
    }

    public int getSize(){
       return emailQueue.size();
    }

}
