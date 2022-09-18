package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.annotations.ActiveRecord;
import com.luxoft.bankapp.annotations.ActiveRecordEntity;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class TestAnnotation {
    final static String introspectClass = "com.luxoft.bankapp.annotations.ActiveRecord";

    @Test
    public void testActiveRecords() {

        CheckingAccount checkingAccount = new CheckingAccount(5, 25.5, 10.2);

        String checkingAccountAll = checkingAccount.getAll();
        String checkingAccountById = checkingAccount.getById(5);

        assertEquals(checkingAccountAll, "SELECT * FROM CHECKING_ACCOUNT");
        assertEquals(checkingAccountById, "SELECT * FROM CHECKING_ACCOUNT WHERE columnKeyName = 5");


        SavingAccount savingAccount = new SavingAccount(6, 26.8);
        String savingAccountAll = savingAccount.getAll();
        String savingAccountById = savingAccount.getById(6);

        assertEquals(savingAccountAll, "SELECT * FROM SAVING_ACCOUNT");
        assertEquals(savingAccountById, "SELECT * FROM SAVING_ACCOUNT WHERE columnKeyName = 6");


    }


}
