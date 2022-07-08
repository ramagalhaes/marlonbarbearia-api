package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.barber.Barber;
import br.com.marlonbarbearia.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {

    @Id
    @SequenceGenerator(name = "sequence_id_appointment", sequenceName = "sequence_id_appointment")
    @GeneratedValue(generator ="sequence_id_appointment")
    private Long id;

    @Column(name="fk_barber_id")
    private Long barberId;
    private LocalDateTime createdAt;

    @Column(name="fk_customer_id")
    private Long customerId;
    private LocalDateTime date;

}
