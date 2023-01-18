package br.com.marlonbarbearia.user;

import br.com.marlonbarbearia.enums.UserType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@Setter
@Table(name = "tb_user")
public class User {

    @SequenceGenerator(name = "sequence_id_user", sequenceName = "sequence_id_user")
    @GeneratedValue(generator = "sequence_id_user")
    @Id
    private Long id;
    private String phoneNumber;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserType> roles;
}
