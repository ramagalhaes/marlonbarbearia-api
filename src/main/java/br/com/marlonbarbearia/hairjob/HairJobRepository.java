package br.com.marlonbarbearia.hairjob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HairJobRepository extends JpaRepository<HairJob, Long> {

    @Query("SELECT new br.com.marlonbarbearia.hairjob.HairJobDTO(h.id, h.name, h.price, h.durationInMinutes) " +
            "FROM HairJob h")
    List<HairJobDTO> findAllHairJobs();

    @Query("SELECT new br.com.marlonbarbearia.hairjob.HairJobDTO(h.id, h.name, h.price, h.durationInMinutes) " +
            "FROM HairJob h " +
            "WHERE h.id = :hairJobId")
    Optional<HairJobDTO> findHairJobById(@Param("hairJobId") Long hairJobId);

    @Query("SELECT new br.com.marlonbarbearia.hairjob.HairJobDTO(h.id, h.name, h.price, h.durationInMinutes) " +
            "FROM HairJob h " +
            "WHERE h.name = :hairJobName")
    Optional<HairJobDTO> findHairJobByHairJobName(@Param("hairJobName") String hairJobName);
}
