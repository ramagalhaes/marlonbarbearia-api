package br.com.marlonbarbearia.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppointmentStatus {

    CREATED("CREATED", 1),
    CONFIRMED("CONFIRMED", 2),
    CANCELED("CANCELED", 3),
    COMPLETED("COMPLETED", 4);

    private final String status;
    private final Integer code;

    public static AppointmentStatus fromCode(Integer code) {
        for(AppointmentStatus status : AppointmentStatus.values()) {
            if(status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalStateException("AppointmentStatus with code: " + code + " does not exists");
    }
}
