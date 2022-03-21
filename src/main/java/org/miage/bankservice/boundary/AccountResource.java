package org.miage.bankservice.boundary;

import org.miage.bankservice.entity.Account;
import org.miage.bankservice.entity.BankResponse;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Account.class)
public class AccountResource {

    private final Environment environment;
    private final AccountRepository ar;

    public AccountResource(AccountRepository ar, Environment env) {
        this.ar = ar;
        this.environment = env;
    }

    @GetMapping("/{accountName}/debit/{amount}")
    @Transactional
    public BankResponse debitAccount(@PathVariable String accountName, @PathVariable double amount) {
        var account = ar.findByClientName(accountName);
        if (account.isEmpty())
            return new BankResponse(false, accountName, Integer.parseInt(environment.getProperty("local.server.port")));

        var existingAccount = account.get();
        if (existingAccount.getBalance() < amount) {
            return new BankResponse(false, accountName, Integer.parseInt(environment.getProperty("local.server.port")));
        }

        return new BankResponse(true, accountName, Integer.parseInt(environment.getProperty("local.server.port")));
    }
}
