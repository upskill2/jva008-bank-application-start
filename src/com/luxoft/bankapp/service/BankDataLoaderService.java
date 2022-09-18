package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BankDataLoaderService {

    private static final String FILE_BANK_DATA = "files/clientFile.txt";

    public List<String> parseFeedFile(){

        StringBuilder sb = new StringBuilder();
        List<String> res = new ArrayList<>();

        try(FileReader fr = new FileReader(FILE_BANK_DATA);
            BufferedReader br = new BufferedReader(fr)){

            String s = "";
            while((s=br.readLine())!=null){
                res.add(s);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return res;
    }


}
