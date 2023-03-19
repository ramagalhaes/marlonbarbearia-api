package br.com.marlonbarbearia.barber;

import br.com.marlonbarbearia.appointment.Appointment;
import br.com.marlonbarbearia.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Barber extends User {

    @OneToMany(mappedBy = "barber")
    @JsonIgnore
    @ToString.Exclude
    private List<Appointment> appointments;

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

