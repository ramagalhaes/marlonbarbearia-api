package br.com.marlonbarbearia.user;

import br.com.marlonbarbearia.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Table(name = "tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements UserDetails {

    @ElementCollection(fetch = FetchType.LAZY)
    private Collection<GrantedAuthority> authorities;

    @SequenceGenerator(name = "sequence_id_user", sequenceName = "sequence_id_user")
    @GeneratedValue(generator = "sequence_id_user")
    @Id
    protected Long id;
    private String lastName;
    private String name;
    private String password;
    private String phoneNumber;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserType> roles;

    public Long getId() {
        return this.id;
    }

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
}




