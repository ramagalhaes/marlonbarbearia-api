package br.com.marlonbarbearia.barbershop;

import br.com.marlonbarbearia.account.Account;
import br.com.marlonbarbearia.barbershop.barber.Barber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BarberShop extends Account {

    private String name;
    @OneToMany(mappedBy = "shop")
    private Set<Barber> barbers;
}
