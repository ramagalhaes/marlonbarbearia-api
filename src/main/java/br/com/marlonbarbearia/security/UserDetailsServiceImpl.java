package br.com.marlonbarbearia.security;

import br.com.marlonbarbearia.user.User;
import br.com.marlonbarbearia.user.UserRepository;
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

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByPhoneNumber(username);
        return UserSpringSecurity.builder()
                .id(user.getId())
                .password(user.getPassword())
                .username(user.getPhoneNumber())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(role ->  new SimpleGrantedAuthority(role.getDescription()))
                                .collect(toList())
                )
                .build();
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findUserByPhoneNumber(phoneNumber);
        return userOptional.orElseThrow(
                () -> new ObjectNotFoundException(phoneNumber, User.class.getSimpleName())
        );
    }
}
