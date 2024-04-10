package br.com.marlonbarbearia.account;

import br.com.marlonbarbearia.account.confirmation.AccountConfirmation;
import br.com.marlonbarbearia.account.confirmation.AccountConfirmationRepository;
import br.com.marlonbarbearia.account.confirmation.ConfirmAccountRequest;
import br.com.marlonbarbearia.exceptions.ConfirmationTokenException;
import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountConfirmationRepository accountConfirmationRepository;
    private final Clock clock;


    public void isPhoneNumberTaken(String phoneNumber) {
        log.info("AccountService::isPhoneNumberTaken() - Checking if phone number: {} is taken", phoneNumber);
        Optional<Account> userOptional = accountRepository.findUserByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()) {
            throw new ObjectAlreadyExistsException("Phone Number: ["+ phoneNumber +"] is already in use");
        }
    }

    public static Account getAuthenticatedUser() {
        try {
            return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public void confirmAccount(ConfirmAccountRequest request) {
        log.info("AccountService::confirmAccount() - Confirm account request received: {}", request);
        Account account = findById(request.accountId());
        AccountConfirmation pendingConfirmation = account.getConfirmation();
        boolean isConfirmed = pendingConfirmation.validateToken(request.token(), clock);
        accountConfirmationRepository.save(pendingConfirmation);
        if (isConfirmed) {
            account.confirmAccount();
            accountRepository.save(account);
        } else {
            accountConfirmationRepository.save(pendingConfirmation);
            throw new ConfirmationTokenException("Token expired, sending new confirmation token!");
        }
        log.info("AccountService::confirmAccount() - Account: {} confirmed successfully", request.accountId());
    }

    public void createAccountConfirmation(Account account) {
        log.info("AccountService.createAccountConfirmation() - Creating account confirmation for: {}", account);
         accountConfirmationRepository.save(
                 new AccountConfirmation(account, LocalDateTime.now(clock), LocalDateTime.now(clock).plusSeconds(3))
         );
    }

    public Account findById(Long id) {
        log.info("AccountService.findById() - Searching for account with id: {}", id);
        return accountRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(id, Account.class.getSimpleName())
        );
    }

}
