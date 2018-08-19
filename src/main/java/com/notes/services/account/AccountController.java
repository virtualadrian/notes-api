package com.notes.services.account;

import com.notes.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody final AccountModel registering) {
        try {
            return Ok(accountService.createNewDisabledAccount(registering));
        } catch (AccountException e) {
            return Conflict(new AccountMessage(AccountMessageType.ACCOUNT_EXISTS, e.getMessage()));
        }
    }

    @RequestMapping(value = "/register/confirm", method = RequestMethod.POST)
    public ResponseEntity confirmRegistration(
        @RequestBody final AccountConfirmationModel confirmation) {
        if (accountService.resetUserPassword(confirmation)) {
            return Ok();
        } else {
            return Conflict(new AccountMessage(AccountMessageType.ACCOUNT_ACTIVATION_INVALID,
                "Could not activate the account."));
        }
    }

    @RequestMapping(value = "/password/reset/send", method = RequestMethod.POST)
    public ResponseEntity sendPasswordReset(
        @RequestBody final AccountModel account) {
        if (accountService.sendPasswordReset(account)) {
            return Ok();
        } else {
            return Conflict(new AccountMessage(AccountMessageType.ACCOUNT_EMAIL_INVALID,
                "Could not reset the password."));
        }
    }

    @RequestMapping(value = "/password/set", method = RequestMethod.PUT)
    public ResponseEntity updatePassword(
        @RequestBody final AccountPasswordModel passwordModel) {
        if (accountService.updateCurrentUserPassword(passwordModel)) {
            return Ok();
        } else {
            return Conflict(new AccountMessage(AccountMessageType.ACCOUNT_ACTIVATION_INVALID,
                "Could not change the password."));
        }
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
    public ResponseEntity passwordResetSet(
        @RequestBody final AccountConfirmationModel confirmationModel) {
        if (accountService.resetUserPassword(confirmationModel)) {
            return Ok();
        } else {
            return Conflict(new AccountMessage(AccountMessageType.ACCOUNT_ACTIVATION_INVALID,
                "Could not change the password."));
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<AccountModel> update(@RequestBody final AccountModel updating) {
        return Ok(accountService.update(updating));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<AccountModel> get(@PathVariable("id") long id) {
        return Ok(accountService.find(id));
    }
}
