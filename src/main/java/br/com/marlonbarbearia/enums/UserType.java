package br.com.marlonbarbearia.enums;

import lombok.Getter;
import org.hibernate.ObjectNotFoundException;

@Getter
public enum UserType {

    BARBER("ROLE_BARBER", 1),
    CUSTOMER("ROLE_CUSTOMER", 2);

    private final int code;
    private final String description;

    UserType(String description, int code) {
        this.code = code;
        this.description = description;
    }

    public static UserType toEnum(int code) {
        for(UserType type : UserType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new ObjectNotFoundException(code, UserType.class.getSimpleName());
    }

}
