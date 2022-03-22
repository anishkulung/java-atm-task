package com.progressoft.induction.atm;

import com.progressoft.induction.atm.Impl.ATMImpl;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;

import javax.annotation.processing.SupportedSourceVersion;
import java.math.BigDecimal;
import java.util.*;

public class Main {
    HashMap<String,BigDecimal> mainMap = new HashMap<>();
    public HashMap<String, BigDecimal> getMainMap() {
        return mainMap;
    }

    public void setMainMap(List<Banknote> banknote){
        for (Banknote bn: banknote
             ) {
            mainMap.computeIfPresent(bn.name(),(k,v)->v.add(new BigDecimal(1)));
            mainMap.putIfAbsent(bn.name(),BigDecimal.valueOf(1));
        }
    }

    String askAccountNumber(){
        Scanner scanInput = new Scanner(System.in);
        System.out.println("Enter your Account Number:");
        String accountNumber = scanInput.nextLine().trim();
        return accountNumber;
    }

    int askChoiceOperationNumber(){
        Scanner scanInput = new Scanner(System.in);
        System.out.println("Enter 1 for withdraw:");
        System.out.println("Enter 2 for check balance: ");
        System.out.println("Enter 3 for Exit:");
        System.out.println("Enter number :");
        int choiceNumber = scanInput.nextInt();
        return choiceNumber;
    }

    BigDecimal takeInputAmount(){
        Scanner scanInput = new Scanner(System.in);
        System.out.println("Enter amount to withdraw:");
        BigDecimal amount = scanInput.nextBigDecimal();
        return amount;
    }

    public static void main(String args[]){
       // your code here
        Main main = new Main();
        ATMImpl atmImpl = new ATMImpl();
        label1:
        while(true){
            String accountNumber = main.askAccountNumber();
            if(main.validateAccountNumber(accountNumber)){
               int operationChoice = main.askChoiceOperationNumber();
                switch (operationChoice){
                    case 1:
                        BigDecimal amount = main.takeInputAmount();
                        List<Banknote> withdrawnNotes = atmImpl.withdraw(accountNumber,amount);
                        main.setMainMap(withdrawnNotes);

                        System.out.println("Withdrawn amount: "+amount);

                        for(Map.Entry<String, BigDecimal> entry: main.mainMap.entrySet()){
                            System.out.println(entry.getKey()+": "+ entry.getValue());
                        }
                        main.mainMap.clear();
                        continue label1;
                    case 3:
                        System.exit(0);
                    case 2:
                        BigDecimal accountBalance = atmImpl.accountBalance(accountNumber);
                        System.out.println("Your Account Balance: "+ accountBalance);
                        continue label1;
                }
        }
        }
    }
    public Boolean validateAccountNumber(String inputAccountNumber) {
        // your code
        BankingSystemImpl bankingSystemImpl = new BankingSystemImpl();
        if(bankingSystemImpl.getAccountBalance(inputAccountNumber)==null){
            return false;
        }
        return true;
    }
}
