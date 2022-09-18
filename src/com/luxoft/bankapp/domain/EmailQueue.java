package com.luxoft.bankapp.domain;

import java.util.LinkedList;
import java.util.Queue;

public class EmailQueue {

    private Queue<Email> queue = new LinkedList<>();

    public void add(Email email) {
        queue.add(email);
    }

    public Email get(){
        return queue.poll();
    }

    public int size(){
        return queue.size();
    }

}
