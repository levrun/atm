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
        return "index";
    }

    @RequestMapping(value = "/show_pin_pad", method = RequestMethod.POST)
    public String showEnterPinPage(Model model,
                        @RequestParam(name = "card") String cardNumber) {

        if (service.checkCardNumber(cardNumber)) {
            model.addAttribute("card", cardNumber);
            return "show_pin_pad";
        }
        model.addAttribute("message", "Card isn't found");
        return "failed";
    }

    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public String checkBalance(Model model,
                        @RequestParam(name = "card") String number) {
        Account account = service.getBalanceByNumber(number);
        model.addAttribute("account", account);
        return "balance";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdraw(Model model,
                          @RequestParam(name = "card") long cardNumber) {

        model.addAttribute("card", cardNumber);
        return "withdraw";
    }

    @RequestMapping(value = "/withdraw_result", method = RequestMethod.POST)
    public String showWithdrawResultPage(Model model,
                           @RequestParam(name = "card") String cardNumber,
                           @RequestParam(name = "sum") int sum) {

        Account accountAfterWithdraw = service.withdraw(cardNumber, sum);
        model.addAttribute("account", accountAfterWithdraw);
        model.addAttribute("card", cardNumber);
        model.addAttribute("sum", sum);

        return "withdraw_result";
    }

    @RequestMapping(value = "/private_cabinet", method = RequestMethod.POST)
    public String checkPinCodeAndEnterToSystem(Model model,
                               @RequestParam(name = "pin") String pin,
                               @RequestParam(name = "card") String cardNumber) {
        try {
            service.checkAndGetAccount(cardNumber, pin);
        } catch (LockedAccountException exception) {
            model.addAttribute("message", "Card is blocked");
            return "failed";
        } catch (WrongPinException exception) {
            model.addAttribute("card", cardNumber);
            model.addAttribute("message", "incorrect pin code.");
            return "show_pin_pad";
        }

        return "private_cabinet";
    }

    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    public String logout(Model model) {
        return "redirect:/";
    }

}
