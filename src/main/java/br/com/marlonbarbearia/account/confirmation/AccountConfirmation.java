package br.com.marlonbarbearia.account.confirmation;

import br.com.marlonbarbearia.account.Account;
import br.com.marlonbarbearia.exceptions.ConfirmationTokenException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;

import static br.com.marlonbarbearia.account.confirmation.AccountConfirmationStatus.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AccountConfirmation {

    @SequenceGenerator(name = "sequence_id_account_confirmation", sequenceName = "sequence_id_account_confirmation")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expireDate;
    private LocalDateTime confirmationDate;
    @Getter(AccessLevel.NONE)
    private Integer status;
    private static final Random RANDOM = new Random();


    public AccountConfirmation(Account account, LocalDateTime createdAt, LocalDateTime expireDate) {
        this.account = account;
        this.createdAt = createdAt;
        this.expireDate = expireDate;
        token = generateNewToken();
        this.status = PENDING.getCode();
    }

    public AccountConfirmationStatus getStatus() {
        return AccountConfirmationStatus.fromCode(status);
    }

    public boolean isExpired(Clock clock) {
        return getStatus().equals(EXPIRED) || LocalDateTime.now(clock).isAfter(expireDate);
    }

    public boolean validateToken(String token, Clock clock) {
        if (!canValidate(clock)) {
            setNewExpirationDate(clock);
            this.token = generateNewToken();
            return false;
        }
        if (!token.equals(this.token)) {
            throw new ConfirmationTokenException("Invalid confirmation attempt, tokens does not match!");
        }
        this.confirmationDate = LocalDateTime.now(clock);
        status = CONFIRMED.getCode();
        return true;
    }

    private boolean canValidate(Clock clock) {
        return !isExpired(clock);
    }

    private void setNewExpirationDate(Clock clock) {
        expireDate = LocalDateTime.now(clock).plusDays(3);
    }

    private String generateNewToken() {
        return String.format("%06d", RANDOM.nextInt(999999));
    }
}
