package ru.simplewebapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.simplewebapp.model.Account;
import ru.simplewebapp.service.AccountService;
import ru.simplewebapp.util.exception.LockedAccountException;
import ru.simplewebapp.util.exception.WrongPinException;

@Controller
public class RootController {

    @Autowired
    AccountService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "/index.jsp";
    }

    @RequestMapping(value = "/pin_enter", method = RequestMethod.POST)
    public String showEnterPinPage(Model model,
                                   @RequestParam String card) {

        if (service.checkCardNumber(card)) {
            model.addAttribute("card", card);
            return "/pin_enter.jsp";
        }
        model.addAttribute("message", "Card isn't found");
        return "/failed.jsp";
    }

    @RequestMapping(value = "/cabinet", method = RequestMethod.POST)
    public String checkPinCodeAndEnterToSystem(Model model,
                                               @RequestParam String pin,
                                               @RequestParam String card) {
        try {
            Account account = service.checkAndGetAccount(card, pin);
            model.addAttribute("card", account.getNumber());
            return "/cabinet.jsp";
        } catch (LockedAccountException | WrongPinException exception) {
            model.addAttribute("message", exception.getMessage());
            return "/failed.jsp";
        }

    }
}
