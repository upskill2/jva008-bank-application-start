package com.luxoft.bankapp.reports;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BankReportsStreams {

    public int getNumberOfClients(Bank bank) {

        int res = bank.getClients().size();
        System.out.println("Number of clients is " + res);

        return res;

    }

    public int getNumberOfAccounts(Bank bank) {
        List<Set<Account>> res = bank.getClients().stream().map(Client::getAccounts).collect(Collectors.toList());
        int res1 = (int) bank.getClients().stream().mapToInt(i -> i.getAccounts().size()).sum();

        System.out.println("Number of accounts is " + res1);

        return res1;

    }

    public Set<Client> getClientsSorted(Bank bank) {

        Set<Client> res = bank.getClients().stream().sorted(Comparator.comparing(Client::getName)).collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println("Client sorted by name " + res);

        return res;
    }

    public double getTotalSumInAccounts(Bank bank) {

        Function<Client,Stream<Account>> func = client -> client.getAccounts().stream();
        double res = bank.getClients().stream().flatMap(func).mapToDouble(Account::getBalance).sum();
                System.out.println("Total sum of all accounts is " + res);

        return res;
    }

    public  SortedSet<Account> getAccountsSortedBySum(Bank bank) {


        List<Account> res1 = bank.getClients().stream().flatMap(client -> client.getAccounts().stream()).sorted(Comparator.comparingDouble(Account::getBalance)).collect(Collectors.toList());

        SortedSet<Account> res =  res1.stream().collect(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Account::getBalance))));
        System.out.println("All accounts sorted by sum are " + res);


            return res;
    }

    public double getBankCreditSum(Bank bank) {

        List<Account> resTemp = bank.getClients().stream().flatMap(client -> client.getAccounts().stream()).filter(account -> account instanceof CheckingAccount).collect(Collectors.toList());
        double res= resTemp.stream().mapToDouble(Account::getOverdraft).sum();
        System.out.println("Total sum of all Overdrafts is " + res );

        return res;

    }

    public Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {

        Map<Client,Collection<Account>> map =bank.getClients().stream().collect(Collectors.toMap(Function.identity(), Client::getAccounts));


        Map<Client,Set<Set<Account>>> map1 = bank.getClients().stream().collect(Collectors.groupingBy(Function.identity(),Collectors.mapping(Client::getAccounts,Collectors.toSet())));

        for (Map.Entry<Client,Collection<Account>> entry: map.entrySet()){
            System.out.println("Printing All info about accounts: Client = " + entry.getKey() + ", Accounts = " + entry.getValue());
        }

        return map;

    }

    public  Map<String, List<Client>> getClientsByCity(Bank bank) {


        Map<String, List<Client>> map = bank.getClients().stream().collect(Collectors.groupingBy(Client::getCity));
        Map<String, List<Client>> mapSorted =  map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldV,newV)->oldV,LinkedHashMap::new));



  for (Map.Entry<String,List<Client>> entry: mapSorted.entrySet()){
            System.out.println("Printing Client info by Cities: City = " + entry.getKey() + ", Clients = " + entry.getValue());
        }

        return mapSorted;

    }
}

