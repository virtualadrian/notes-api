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

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody final AccountModel registering) {
        try {
            return Ok(accountService.createNewDisabledAccount(registering));
        } catch (AccountException e) {
            return Conflict(new AccountMessage(AccountMessageType.ACCOUNT_EXISTS, e.getMessage()));
        }
    }

    @RequestMapping(value="/register/confirm/{token}", method = RequestMethod.POST)
    public ResponseEntity confirmRegistration(@PathVariable("token") final String token) {
        return accountService.checkTokenAndEnableUser(token) ?
            Ok() :
            Conflict(new AccountMessage(AccountMessageType.ACCOUNT_ACTIVATION_INVALID, "Invalid Token."));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<AccountModel>> getAll() {
        return Ok(accountService.findall(0, 1));
    }

    @RequestMapping(value = "/{page}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<AccountModel>> getAll(
        @PathVariable("page") int page,
        @PathVariable("pageSize") int pageSize) {
        return Ok(accountService.findall(page, pageSize));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<AccountModel> get(@PathVariable("id") long id) {
        return Ok(accountService.find(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountModel> create(@RequestBody final AccountModel creating) {
        return Ok(accountService.create(creating));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<AccountModel> update(@RequestBody final AccountModel updating) {
        return Ok(accountService.update(updating));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        accountService.delete(id);
        return Ok();
    }
}
