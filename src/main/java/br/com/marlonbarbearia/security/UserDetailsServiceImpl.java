package br.com.marlonbarbearia.security;

import br.com.marlonbarbearia.account.Account;
import br.com.marlonbarbearia.account.AccountRepository;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findAccountByPhoneNumber(username);
        return Account.builder()
                .authorities(
                        account.getRoles()
                                .stream()
                                .map(role ->  new SimpleGrantedAuthority(role.getDescription()))
                                .collect(toList())
                )
                .id(account.getId())
                .password(account.getPassword())
                .phoneNumber(account.getPhoneNumber())
                .roles(account.getRoles())
                .build();
    }

    public Account findAccountByPhoneNumber(String phoneNumber) {
        Optional<Account> userOptional = accountRepository.findUserByPhoneNumber(phoneNumber);
        return userOptional.orElseThrow(
                () -> new ObjectNotFoundException(phoneNumber, Account.class.getSimpleName())
        );
    }
}
