package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.service.BankDataLoaderService;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class TestSerialization {

    @Test
    public void testReadFile() {

        Bank bank = null;

        BankDataLoaderService bankService = new BankDataLoaderService();

       List<String> s = bankService.parseFeedFile();

        assertEquals(s.size(), 2);

    }

    @Test
    public void testRegEx(){
        String str= "accounttype=c;balance=100;overdraft=50;name=John;gender=m";

      //  Pattern p = Pattern.compile(".*(?<=accounttype).*?(?=\\;).*");
         //Pattern p = Pattern.compile(".+(?<=accounttype).+");
         Pattern p = Pattern.compile("\\=(.*?)c");

        Matcher m = p.matcher(str);

     //   String s = m.group(0);

        assertEquals(1,1);

    }

}
