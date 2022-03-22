package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.BankingSystem;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
//import com.progressoft.induction.atm.exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.*;

public class BankingSystemImpl implements BankingSystem {
   Map<String, BigDecimal> accountBalanceMap = new HashMap<String, BigDecimal>();
   EnumMap<Banknote,Integer> atmCashMap = new EnumMap<>(Banknote.class);

    public BankingSystemImpl() {
        atmCashMap.put(Banknote.FIFTY_JOD,10);
        atmCashMap.put(Banknote.TWENTY_JOD,20);
        atmCashMap.put(Banknote.TEN_JOD,100);
        atmCashMap.put(Banknote.FIVE_JOD,100);

        accountBalanceMap.put("123456789", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("111111111", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("222222222", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("333333333", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("444444444", BigDecimal.valueOf(1000.0));
    }

    public Map<String, BigDecimal> getAccountBalanceMap(){return this.accountBalanceMap;}
    public void setAccountBalanceMap(Map<String, BigDecimal> accountBalanceMap){this.accountBalanceMap = accountBalanceMap;}
    public EnumMap<Banknote, Integer> getAtmCashMap(){return this.atmCashMap;}

    public BigDecimal sumOfMoneyInAtm(){
        // Your code
        BigDecimal totalAmountInAtm= BigDecimal.ZERO;
        Banknote[] banknotes = Banknote.values();
        for (Banknote banknote: banknotes
             ) {
//            System.out.println("No of "+notes.name()+ " "+ atmCashMap.get(notes) );
            BigDecimal noOfNotes = BigDecimal.valueOf(atmCashMap.get(banknote));
            totalAmountInAtm = totalAmountInAtm.add(banknote.getValue().multiply(BigDecimal.valueOf(atmCashMap.get(banknote))));
        }
//        System.out.println(totalAmountInAtm);
        return totalAmountInAtm;
    }


    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        //your code
        return accountBalanceMap.get(accountNumber);
    }

    @Override
    public void debitAccount(String accountNumber, BigDecimal amount) {
        //your code
        accountBalanceMap.computeIfPresent(accountNumber,(k,v) -> v.subtract(amount));

    }

}
