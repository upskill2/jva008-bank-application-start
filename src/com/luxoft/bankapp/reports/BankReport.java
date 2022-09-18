package com.luxoft.bankapp.reports;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

import java.util.*;

public class BankReport {

  public  int getNumberOfClients(Bank bank) {

      System.out.println("Number of clients is " + bank.getClients().size());
        return bank.getClients().size();
    }

    public  int getNumberOfAccounts(Bank bank) {

        int numberOfAccounts = 0;

        for (Client c : bank.getClients()) {
            numberOfAccounts += c.getAccounts().size();
        }

        System.out.println("Number of accounts is " + numberOfAccounts);

        return numberOfAccounts;
    }

    public SortedSet<Client> getClientsSorted(Bank bank) {

      SortedSet<Client> sortedSet = new TreeSet<>(new Comparator<Client>() {
          @Override
          public int compare(Client o1, Client o2) {
              return o1.getName().compareTo(o2.getName());
          }
      });

        sortedSet.addAll(bank.getClients());


        System.out.println("Client sorted by name " + sortedSet.toString());


        return sortedSet;
    }


    public double getTotalSumInAccounts(Bank bank) {

        int totalSumOfAccounts = 0;

        for (Client c : bank.getClients()) {
            for (Account acc : c.getAccounts()) {
                totalSumOfAccounts += acc.getBalance();
            }

        }

        System.out.println("Total sum of all accounts is " + totalSumOfAccounts);

        return totalSumOfAccounts;
    }

    public  SortedSet<Account> getAccountsSortedBySum(Bank bank) {

        TreeSet<Account> accountSet = new TreeSet<>(new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                return (int) (o1.getBalance()-o2.getBalance());
            }
        });

        for (Client c : bank.getClients()) {
            accountSet.addAll(c.getAccounts());

        }

        System.out.println("All accounts sorted by sum are " + accountSet);

        return accountSet;
    }

    public double getBankCreditSum(Bank bank) {

        double totalCreditSum = 0;

        for (Client c : bank.getClients()) {
            for (Account acc : c.getAccounts()) {
                if (acc instanceof CheckingAccount) {
                    totalCreditSum += ((CheckingAccount) acc).getOverdraft();
                }

            }

        }

        System.out.println("Total sum of all Overdrafts is " + totalCreditSum);

        return totalCreditSum;


    }

    public  Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {

        Map<Client, Collection<Account>> customerAndAccountsMap = new HashMap<>();

        for (Client cl : bank.getClients()) {
            customerAndAccountsMap.put(cl, cl.getAccounts());

        }

        for (Map.Entry<Client,Collection<Account>> entry: customerAndAccountsMap.entrySet()){
            System.out.println("Printing All info about accounts: Client = " + entry.getKey() + ", Accounts = " + entry.getValue());
        }


        return customerAndAccountsMap;
    }

    public  Map<String, List<Client>> getClientsByCity(Bank bank) {

        Map<String, List<Client>> clientByCitySortedMap = new TreeMap<>();

        for (Client client : bank.getClients()) {
            String city = client.getCity();
            if (!clientByCitySortedMap.containsKey(city)) {
                List<Client> tempList = new ArrayList<>();
                tempList.add(client);
                clientByCitySortedMap.put(city, tempList);
            } else {
                List<Client> list = clientByCitySortedMap.get(city);
                list.add(client);
                clientByCitySortedMap.put(city, list);
            }

        }


        for (Map.Entry<String,List<Client>> entry: clientByCitySortedMap.entrySet()){
            System.out.println("Printing Client info by Cities: City = " + entry.getKey() + ", Clients = " + entry.getValue());
        }


        return clientByCitySortedMap;
    }

}
