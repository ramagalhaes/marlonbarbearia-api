package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.barber.Barber;
import br.com.marlonbarbearia.customer.Customer;
import br.com.marlonbarbearia.hairjob.HairJob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private LocalDateTime date;
    private Integer durationInMinutes;
    private BigDecimal price;
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "appointment_id")},
            inverseJoinColumns = @JoinColumn(name = "hairjob_id")
    )
    private Set<HairJob> hairJobs;

}
