package br.com.marlonbarbearia.account;

import br.com.marlonbarbearia.account.confirmation.AccountConfirmation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static br.com.marlonbarbearia.account.AccountStatus.CONFIRMED;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account implements UserDetails {

    @ElementCollection(fetch = FetchType.LAZY)
    private Collection<GrantedAuthority> authorities;

    @SequenceGenerator(name = "sequence_id_account", sequenceName = "sequence_id_account")
    @GeneratedValue(generator = "sequence_id_account")
    @Id
    protected Long id;
    private String password;
    private String phoneNumber;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<AccountType> roles;
    @OneToOne(mappedBy = "account")
    private AccountConfirmation confirmation;
    private LocalDateTime createdAt;
    private Integer status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AccountStatus getStatus() {
        return AccountStatus.fromCode(status);
    }

    public void confirmAccount() {
        status = CONFIRMED.getCode();
    }

    public boolean isAccountConfirmed() {
        return CONFIRMED.equals(getStatus());
    }
}




