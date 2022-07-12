package br.com.marlonbarbearia.customer;

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
public class Customer {

    @SequenceGenerator(name = "sequence_id_customer", sequenceName = "sequence_id_customer")
    @GeneratedValue(generator = "sequence_id_customer")
    @Id
    private Long id;
    private String name;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Appointment> appointments;

}
