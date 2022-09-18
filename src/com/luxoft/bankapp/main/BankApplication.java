package com.luxoft.bankapp.main;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Gender;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.reports.BankReport;
import com.luxoft.bankapp.reports.BankReportsStreams;
import com.luxoft.bankapp.service.BankDataLoaderService;
import com.luxoft.bankapp.service.BankService;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BankApplication {

    private static Bank bank;

    public static void main(String[] args) {

        if(args[1].equals("–loadbank=files/bank.data")){
            bank = BankService.readBank();
        } else {

            bank = new Bank();

            if (args[0].equals("–loadfeed=files/clientFile.txt")) {

                BankDataLoaderService loader = new BankDataLoaderService();

                for (String s : loader.parseFeedFile()) {
                    addClientsFromFile(s);
                }
            }

            modifyBank();
        }

        if(args[2].contains("–Xstopsendnote=after")){


            stopSendingNotifications();
        }

        if(args[3].equals("-statistics")){

            BankReport report = new BankReport();
            BankReportsStreams streams = new BankReportsStreams();

            System.out.println("-----Started to Print Bank Reports--------");
            report.getNumberOfClients(bank);
            streams.getNumberOfClients(bank);
            System.out.println("-------------------");
            report.getNumberOfAccounts(bank);
            streams.getNumberOfAccounts(bank);
            System.out.println("-------------------");
            report.getClientsSorted(bank);
            streams.getClientsSorted(bank);
            System.out.println("-------------------");
            report.getTotalSumInAccounts(bank);
            streams.getTotalSumInAccounts(bank);
            System.out.println("-------------------");
            report.getAccountsSortedBySum(bank);
            streams.getAccountsSortedBySum(bank);
            System.out.println("-------------------");
            report.getBankCreditSum(bank);
            streams.getBankCreditSum(bank);
            System.out.println("-------------------");
            report.getCustomerAccounts(bank);
            streams.getCustomerAccounts(bank);
            System.out.println("-------------------");
            report.getClientsByCity(bank);
            streams.getClientsByCity(bank);
            System.out.println("-----Ended to Print Bank Reports--------");
        }

        printBalance();
        BankService.printMaximumAmountToWithdraw(bank);
    }

    private static void stopSendingNotifications() {


    }
    private static void addClientsFromFile(String line) {

        String[] s1 = line.split(";");
        Map<String, String> inputFileMap = new HashMap<>();

        for (String s : s1) {
            String[] temp = s.split("=");
            inputFileMap.put(temp[0], temp[1]);
        }
        //Crate client
        Client client1 = new Client(inputFileMap.get("name"), parseGender(inputFileMap.get("gender")), inputFileMap.get("city"), LocalDate.of(1999,Month.AUGUST,20));

        //Add birthday
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate birthdate = LocalDate.parse(inputFileMap.get("birthday"),formatter);
        client1.setBirthday(birthdate);

        //Create Account
        Account account1 = null;
        Account account2 = null;
        if (inputFileMap.get("accounttype").equals("c")) {
            account1 = new CheckingAccount(3, Integer.parseInt(inputFileMap.get("balance")), Integer.parseInt(inputFileMap.get("overdraft")));
            //Add accounts to client
            client1.addAccount(account1);
        } else {
            account2 = new SavingAccount(4, Integer.parseInt(inputFileMap.get("balance")));
            //Add accounts to client
            client1.addAccount(account2);
        }

        try {
            BankService.addClient(bank, client1);
        } catch (ClientExistsException e) {
            System.out.format("Cannot add an already existing client: %s%n", client1.getName());
        }

    }

    private static Gender parseGender(String s) {

        if (s.equals("m")) {
            return Gender.MALE;
        }

        return Gender.FEMALE;
    }


    private static void modifyBank() {
        Client client1 = new Client("John", Gender.MALE, "Vinnytsia", LocalDate.of(1987, Month.APRIL,4));
        Account account1 = new SavingAccount(1, 100);
        Account account2 = new CheckingAccount(2, 100, 20);
        client1.addAccount(account1);
        client1.addAccount(account2);

        try {
            BankService.addClient(bank, client1);
        } catch (ClientExistsException e) {
            System.out.format("Cannot add an already existing client: %s%n", client1.getName());
        }

        account1.deposit(100);
        try {
            account1.withdraw(10);
        } catch (OverdraftLimitExceededException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
        }

        try {
            account2.withdraw(90);
        } catch (OverdraftLimitExceededException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
        }

        try {
            account2.withdraw(100);
        } catch (OverdraftLimitExceededException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
        }

        try {
            BankService.addClient(bank, client1);
        } catch (ClientExistsException e) {
            System.out.format("Cannot add an already existing client: %s%n", client1);
        }
    }

    private static void printBalance() {
        System.out.format("%nPrint balance for all clients%n");
        for (Client client : bank.getClients()) {
            System.out.println("Client: " + client);
            for (Account account : client.getAccounts()) {
                System.out.format("Account %d : %.2f%n", account.getId(), account.getBalance());
            }
        }
    }

}
