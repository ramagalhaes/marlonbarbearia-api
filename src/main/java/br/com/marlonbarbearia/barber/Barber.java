package br.com.marlonbarbearia.barber;

import br.com.marlonbarbearia.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Barber {

    @SequenceGenerator(name = "sequence_id_barber", sequenceName = "sequence_id_barber")
    @GeneratedValue(generator = "sequence_id_barber")
    @Id
    private Long id;
    private String name;
    private String phoneNumber;

    @OneToMany(mappedBy = "barber")
    @JsonIgnore
    private List<Appointment> appointments;
}

