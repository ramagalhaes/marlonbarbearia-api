package br.com.marlonbarbearia.barber;

import br.com.marlonbarbearia.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Barber implements Serializable {

    private static final Long serialVersionUID = 1L;

    @SequenceGenerator(name = "sequence_id_barber", sequenceName = "sequence_id_barber")
    @GeneratedValue(generator = "sequence_id_barber")
    @Id
    private Long id;
    private String name;
    private String lastName;
    private String phoneNumber;

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

