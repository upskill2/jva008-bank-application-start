package com.luxoft.bankapp.annotations;


import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;

import java.lang.annotation.Annotation;
import java.util.Optional;

public abstract class ActiveRecord {

    public String getById(int id) {
       return String.format("SELECT * FROM %s WHERE columnKeyName = %d", getTableName(), id);

    }

    public String getAll() {
        return String.format("SELECT * FROM %s", getTableName());

    }

    String getTableName() {

        String tableName="";

        if(getClass()==CheckingAccount.class){
            Class<CheckingAccount> aClass = CheckingAccount.class;
            Annotation[] annotations = aClass.getAnnotations();


            for (Annotation annot :annotations) {
                if(annot instanceof ActiveRecordEntity){
                    ActiveRecordEntity myAnnot = (ActiveRecordEntity) annot;

                    tableName = myAnnot.tableName();
                    //    s2 = myAnnot.columnKeyName();

                }
            }

        } else {

            Class<SavingAccount> bClass = SavingAccount.class;
            Annotation[] annotations2 = bClass.getAnnotations();

            for (Annotation annot2 :annotations2) {
                if(annot2 instanceof ActiveRecordEntity){
                    ActiveRecordEntity myAnnot = (ActiveRecordEntity) annot2;
                    tableName = myAnnot.tableName();

                }
            }
        }
      //works
     String s12 =  getClass().getAnnotation(ActiveRecordEntity.class).columnKeyName();
            String s13 =  getClass().getAnnotation(ActiveRecordEntity.class).tableName();

       return tableName;

    }


}