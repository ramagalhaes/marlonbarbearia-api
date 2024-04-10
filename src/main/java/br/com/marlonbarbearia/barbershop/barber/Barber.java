package br.com.marlonbarbearia.barbershop.barber;

import br.com.marlonbarbearia.account.Account;
import br.com.marlonbarbearia.appointment.Appointment;
import br.com.marlonbarbearia.barbershop.BarberShop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
public class Barber extends Account {

    private String lastName;
    private String name;
    @OneToMany(mappedBy = "barber")
    @JsonIgnore
    @ToString.Exclude
    private transient List<Appointment> appointments;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private BarberShop shop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Barber barber = (Barber) o;
        return id != null && Objects.equals(id, barber.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

