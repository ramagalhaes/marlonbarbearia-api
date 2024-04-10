package br.com.marlonbarbearia.account;

import br.com.marlonbarbearia.account.confirmation.ConfirmAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public record AccountController(AccountService service) {


    @PostMapping("/confirmation")
    public ResponseEntity<Void> confirmAccount(@RequestBody ConfirmAccountRequest request) {
        service.confirmAccount(request);
        return ResponseEntity.noContent().build();
    }

}
