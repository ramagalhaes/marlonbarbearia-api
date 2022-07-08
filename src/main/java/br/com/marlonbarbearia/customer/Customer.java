package br.com.marlonbarbearia.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

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

}
