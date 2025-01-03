package com.example.Accounts.Repository;

import com.example.Accounts.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    // Finds an account by its unique account number.
    Account findByAccountNumber(String accountNumber);
}

