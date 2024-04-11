package br.com.marlonbarbearia.barbershop.hairjob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HairJobRepository extends JpaRepository<HairJob, Long> {

    @Query("SELECT new br.com.marlonbarbearia.barbershop.hairjob.HairJobDTO(h.id, h.name, h.price, h.durationInMinutes) " +
            "FROM HairJob h")
    List<HairJobDTO> findAllHairJobs();

    @Query("SELECT new br.com.marlonbarbearia.barbershop.hairjob.HairJobDTO(h.id, h.name, h.price, h.durationInMinutes) " +
            "FROM HairJob h " +
            "WHERE h.id = :hairJobId")
    Optional<HairJobDTO> findHairJobDTOById(@Param("hairJobId") Long hairJobId);

    @Query("SELECT h " +
            "FROM HairJob h " +
            "WHERE h.name = :hairJobName")
    Optional<HairJob> findHairJobByHairJobName(@Param("hairJobName") String hairJobName);

    @Query("SELECT h " +
            "FROM HairJob h " +
            "WHERE h.id = :id")
    Optional<HairJob> findHairJobById(@Param("id") Long id);
}
