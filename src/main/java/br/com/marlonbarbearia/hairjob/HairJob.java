package br.com.marlonbarbearia.hairjob;

import br.com.marlonbarbearia.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HairJob {

    @SequenceGenerator(name = "sequence_id_hair-job", sequenceName = "sequence_id_hair-job")
    @GeneratedValue(generator = "sequence_id_hair-job")
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer durationInMinutes;

    @ManyToMany(mappedBy = "hairJobs")
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Appointment> appointments;
}
