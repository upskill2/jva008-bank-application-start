package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestBirthdayStream {

    Bank bank;

    @Before
    public void beforeEachTest() throws ClientExistsException {

        bank = new Bank();
        Client client1 = new Client("Smith John", Gender.MALE, "Kyiv", LocalDate.of(2022, 9, 26));
        client1.addAccount(new SavingAccount(1, 1000.0));
        client1.addAccount(new CheckingAccount(2, 1000.0, 100.0));

        Client client2 = new Client("Smith Michelle", Gender.FEMALE, "Zhytomyr", LocalDate.of(2022, 10, 1));
        client2.addAccount(new SavingAccount(3, 2000.0));
        client2.addAccount(new CheckingAccount(4, 1500.0, 200.0));

        Client client3 = new Client("Some Client", Gender.FEMALE, "Zhytomyr", LocalDate.of(2015, 10, 1));
        client3.addAccount(new SavingAccount(5, 2000.0));
        client3.addAccount(new CheckingAccount(6, 1500.0, 200.0));

        BankService.addClient(bank, client1);
        BankService.addClient(bank, client2);
        BankService.addClient(bank, client3);

    }

    @Test
    public void testBirthdayStream() throws ClientExistsException {


        LocalDate dateNow = LocalDate.now();
        LocalDate dateIn30days = dateNow.plusDays(30);
        int dat = dateNow.getDayOfMonth();
        List<LocalDate> list = bank.getClients().stream().map(Client::getBirthday).collect(Collectors.toList());


        long getClientsBirthdayIn30Days = bank.getClients().stream().map(i -> i.convertDate(i.getBirthday())).filter(t -> t >= 0 && t < 32).count();

        assertEquals(2, getClientsBirthdayIn30Days);


    }

    @Test
    public void testStreamMapWithBirthdays() {

        Map<Client, Month> resMap = bank.getClients().stream().collect(Collectors.toMap(Function.identity(),client -> client.getBirthday().getMonth()));

        assertEquals(3, resMap.size());


        Map<String, Month> testMap = new HashMap<>();
        for (Map.Entry<Client,Month> entrySet: resMap.entrySet()) {
                Client client =  entrySet.getKey();
                testMap.put(client.getName(), client.getBirthday().getMonth()) ;
        }

        assertEquals(testMap.get("Smith John"), Month.SEPTEMBER);
        assertEquals(testMap.get("Smith Michelle"), Month.OCTOBER);
        assertEquals(testMap.get("Some Client"), Month.OCTOBER);




    }

}
