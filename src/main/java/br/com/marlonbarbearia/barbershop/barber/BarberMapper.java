package br.com.marlonbarbearia.barbershop.barber;

public class BarberMapper {

    private BarberMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static BarberDTO entityToResponse(Barber barber) {
        return BarberDTO.builder()
                .id(barber.getId())
                .name(barber.getName())
                .phoneNumber(barber.getPhoneNumber())
                .build();
    }

}
