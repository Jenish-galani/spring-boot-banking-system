package com.example.Accounts.Controller;

import com.example.Accounts.Model.Account;
import com.example.Accounts.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Marks the class as a REST controller to handle API requests.
@RequestMapping("/api/accounts") // Base URL for all account-related API endpoints.
public class AccountController {

    @Autowired
    private AccountService accountService; // Injects the AccountService to handle business logic.

    // http://localhost:8080/api/accounts?name=Virat Kohli&initialBalance=10000.00    -->  API Call
            // Create Account of virat kohli
    // {
    //     "id": 3,
    //     "accountNumber": "ACC1735906186293",
    //     "name": "Virat Kohli",
    //     "balance": 10000.0
    // }
    @PostMapping
    public Account createAccount(@RequestParam String name, @RequestParam Double initialBalance) {
        return accountService.createAccount(name, initialBalance);
    }

    // http://localhost:8080/api/accounts/ACC1735906186293    -->  API Call
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // http://localhost:8080/api/accounts/ACC1735906186293/withdraw?amount=100    -->  API Call
    @PutMapping("/{accountNumber}/withdraw")
    public Account withdraw(@PathVariable String accountNumber, @RequestParam Double amount) {
        return accountService.withdrawMoney(accountNumber, amount);
    }

    // http://localhost:8080/api/accounts/ACC1735906186293/deposit?amount=100    -->  API Call
    @PutMapping("/{accountNumber}/deposit")
    public Account deposit(@PathVariable String accountNumber, @RequestParam Double amount) {
        return accountService.depositMoney(accountNumber, amount);
    }

    // Handles RuntimeExceptions and returns a custom error message with a 400 Bad Request status.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // API endpoint to transfer money between two accounts.
    // http://localhost:8080/api/accounts/ACC1735906186293/transfer/ACC1735906290244?amount=5000    -->  API Call
    @PutMapping("/{fromAccountNumber}/transfer/{toAccountNumber}")
    public Account transfer(@PathVariable String fromAccountNumber, @PathVariable String toAccountNumber, @RequestParam Double amount) {
        return accountService.transferMoney(fromAccountNumber, toAccountNumber, amount);
    }
}

