package br.com.marlonbarbearia.account.confirmation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountConfirmationStatus {

    PENDING("PENDING", 1),
    CONFIRMED("CONFIRMED", 2),
    EXPIRED("EXPIRED", 3);

    private final String status;
    private final Integer code;

    public static AccountConfirmationStatus fromCode(int code) {
        for(AccountConfirmationStatus status : AccountConfirmationStatus.values()) {
            if(status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalStateException("AccountConfirmationStatus with code: " + code + " does not exists");
    }
}
