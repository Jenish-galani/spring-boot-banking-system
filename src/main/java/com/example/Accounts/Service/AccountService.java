package com.example.Accounts.Service;

import com.example.Accounts.Model.Account;
import com.example.Accounts.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(String name, Double initialBalance) {
        Account account = new Account();
        account.setName(name);
        account.setBalance(initialBalance);
        account.setAccountNumber("ACC" + System.currentTimeMillis()); // Auto incrementing account number
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber));
    }

    public Account withdrawMoney(String accountNumber, Double amount) {
        Account account = getAccountByNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Insufficient balance");
        }
    }

    public Account depositMoney(String accountNumber, Double amount) {
        Account account = getAccountByNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account transferMoney(String fromAccountNumber, String toAccountNumber, Double amount) {
        Account fromAccount = getAccountByNumber(fromAccountNumber).orElseThrow(() -> new RuntimeException("From Account not found"));
        Account toAccount = getAccountByNumber(toAccountNumber).orElseThrow(() -> new RuntimeException("To Account not found"));

        if (fromAccount.getBalance() >= amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountRepository.save(fromAccount);
            return accountRepository.save(toAccount);
        }else {
            throw new RuntimeException("Insufficient balance");
        }
    }
}

