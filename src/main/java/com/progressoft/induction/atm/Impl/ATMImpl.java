package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.ATM;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.Main;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ATMImpl implements ATM {
    BankingSystemImpl bankingSystem = new BankingSystemImpl();
    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {

        // Your code here
        if(!bankingSystem.accountBalanceMap.containsKey(accountNumber)) {
            throw new AccountNotFoundException("Account does not exist.");
        }else{
            BigDecimal bankBalance = bankingSystem.getAccountBalance(accountNumber);

            if (amount.compareTo(bankBalance) > 0) {
                throw new InsufficientFundsException("Your account does not have sufficient balance.");

            }
            if (amount.compareTo(bankingSystem.sumOfMoneyInAtm()) > 0) {
                throw new NotEnoughMoneyInATMException("Atm does not have enough money");
            }
            BigDecimal tempAmount = amount;
            ArrayList<Banknote> banknoteList = new ArrayList<>();
            while (tempAmount.compareTo(BigDecimal.ZERO) > 0) {
                Banknote[] banknotes = Banknote.values();
                for (Banknote banknote : banknotes) {
                    if (tempAmount.compareTo(banknote.getValue()) >= 0) {
                        banknoteList.add(banknote);
                        if (bankingSystem.atmCashMap.get(banknote) > 0) {
                            int remainingBanknoteInAtm = bankingSystem.atmCashMap.get(banknote) - 1;
                            bankingSystem.atmCashMap.replace(banknote, remainingBanknoteInAtm);
                            tempAmount = tempAmount.subtract(banknote.getValue());
                        }
                    }
                }
            }
            bankingSystem.debitAccount(accountNumber, amount);
            return banknoteList;
        }
    }

   public BigDecimal accountBalance(String accountNumber){
        return bankingSystem.getAccountBalance(accountNumber);
   }
}
