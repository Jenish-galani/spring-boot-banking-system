package com.example.Accounts.Service;

import com.example.Accounts.Model.Account;
import com.example.Accounts.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository; // Injects the AccountRepository for database operations.

    // Creates a new account with a name, initial balance, and unique account number.
    public Account createAccount(String name, Double initialBalance) {
        Account account = new Account();
        account.setName(name);
        account.setBalance(initialBalance);
        account.setAccountNumber("ACC" + System.currentTimeMillis()); // Auto incrementing account number
        return accountRepository.save(account);
    }

    // Retrieves an account by its unique account number.
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber));
    }

    // Withdraws a specified amount from the account if sufficient balance exists.
    public Account withdrawMoney(String accountNumber, Double amount) {
        Account account = getAccountByNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Insufficient balance");
        }
    }

    // Deposits a specified amount into the account.
    public Account depositMoney(String accountNumber, Double amount) {
        Account account = getAccountByNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    // Transfers a specified amount between two accounts if the source has sufficient balance.
    public Account transferMoney(String fromAccountNumber, String toAccountNumber, Double amount) {
        Account fromAccount = getAccountByNumber(fromAccountNumber).orElseThrow(() -> new RuntimeException("From Account not found"));
        Account toAccount = getAccountByNumber(toAccountNumber).orElseThrow(() -> new RuntimeException("To Account not found"));

        if (fromAccount.getBalance() >= amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountRepository.save(fromAccount);
            return accountRepository.save(toAccount);
        } else {
            throw new RuntimeException("Insufficient balance");
        }
    }
}


