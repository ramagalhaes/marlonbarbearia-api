package br.com.marlonbarbearia.barber;

public class BarberMapper {

    private BarberMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static BarberResponse barberEntityToResponse(Barber barber) {
        return BarberResponse.builder()
                .id(barber.getId())
                .name(barber.getName())
                .phoneNumber(barber.getPhoneNumber())
                .build();
    }

}
