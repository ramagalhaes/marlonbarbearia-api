package br.com.marlonbarbearia.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountStatus {

    CREATED("CREATED", 1),
    CONFIRMED("CONFIRMED", 2),
    FREEZE("FREEZE", 3);

    private final String status;
    private final Integer code;

    public static AccountStatus fromCode(Integer code) {
        for(AccountStatus status : AccountStatus.values()) {
            if(status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalStateException("AccountStatus with code: " + code + " does not exists");
    }
}
