package com.example.Accounts.Controller;

import com.example.Accounts.Model.Account;
import com.example.Accounts.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestParam String name, @RequestParam Double initialBalance) {
        return accountService.createAccount(name, initialBalance);
    }

    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @PutMapping("/{accountNumber}/withdraw")
    public Account withdraw(@PathVariable String accountNumber, @RequestParam Double amount) {
        return accountService.withdrawMoney(accountNumber, amount);
    }

    @PutMapping("/{accountNumber}/deposit")
    public Account deposit(@PathVariable String accountNumber, @RequestParam Double amount) {
        return accountService.depositMoney(accountNumber, amount);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @PutMapping("/{fromAccountNumber}/transfer/{toAccountNumber}")
    public Account transfer(@PathVariable String fromAccountNumber, @PathVariable String toAccountNumber, @RequestParam Double amount) {
        return accountService.transferMoney(fromAccountNumber, toAccountNumber, amount);
    }
}

