package br.com.marlonbarbearia.account.confirmation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountConfirmationRepository extends JpaRepository<AccountConfirmation, Long> {

    @Query("SELECT ac FROM AccountConfirmation ac " +
            "WHERE ac.account.id = :accountId " +
            "AND ac.status = 1")
    Optional<AccountConfirmation> findPendingConfirmationByAccountId(@Param("accountId") Long accountId);

}
