package br.com.marlonbarbearia.barber;

public class BarberMapper {

    public static BarberResponse BarberEntityToResponse(Barber barber) {
        return BarberResponse.builder()
                .id(barber.getId())
                .name(barber.getName())
                .phoneNumber(barber.getPhoneNumber())
                .build();
    }

}
