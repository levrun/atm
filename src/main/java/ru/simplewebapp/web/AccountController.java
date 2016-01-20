package ru.simplewebapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.simplewebapp.model.Account;
import ru.simplewebapp.service.AccountService;
import ru.simplewebapp.util.AccountTO;

@RestController
public class AccountController {

    @Autowired
    AccountService service;

    @RequestMapping(value = "/balance/{card}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountTO get(@PathVariable("card") String number) {
        Account account = service.getBalanceByNumber(number);
        return new AccountTO(account);
    }

    @RequestMapping(value = "/withdraw/{card}", method = RequestMethod.POST)
    public void withdraw(@PathVariable("card") String number,
                         @RequestParam("amount") int amount) {
        service.withdraw(number, amount);
    }

}
