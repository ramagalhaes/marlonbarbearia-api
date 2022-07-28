package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.appointment.Appointment;
import br.com.marlonbarbearia.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

    @SequenceGenerator(name = "sequence_id_customer", sequenceName = "sequence_id_customer")
    @GeneratedValue(generator = "sequence_id_customer")
    @Id
    private Long id;
    private String name;
    private String phoneNumber;
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    @ToString.Exclude
    private List<Appointment> appointments;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserType> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
