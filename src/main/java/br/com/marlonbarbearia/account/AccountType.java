package br.com.marlonbarbearia.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.ObjectNotFoundException;

@Getter
@AllArgsConstructor
public enum AccountType {

    BARBER("ROLE_BARBER", 1),
    BARBER_SHOP("ROLE_BARBER_SHOP", 2),
    CUSTOMER("ROLE_CUSTOMER", 3);


    private final String description;
    private final int code;

    public static AccountType toEnum(int code) {
        for(AccountType type : AccountType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new ObjectNotFoundException(code, AccountType.class.getSimpleName());
    }

}
