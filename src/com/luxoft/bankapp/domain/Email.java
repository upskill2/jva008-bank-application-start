package com.luxoft.bankapp.domain;

public class Email {

    private Client client;
    private String to;
    private String from;

    public Email(Client client, String to, String from) {
        this.client = client;
        this.to = to;
        this.from = from;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Email{" +
                "client=" + client +
                ", to='" + to + '\'' +
                ", from='" + from + '\'' +
                '}';
    }

}
