package br.com.marlonbarbearia.hairjob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HairJobRepository extends JpaRepository<HairJob, Long> {

    @Query("SELECT new br.com.marlonbarbearia.hairjob.HairJobResponse(h.id, h.name, h.price, h.durationInSeconds) " +
            "FROM HairJob h")
    List<HairJobResponse> findAllHairJobs();

    @Query("SELECT new br.com.marlonbarbearia.hairjob.HairJobResponse(h.id, h.name, h.price, h.durationInSeconds) " +
            "FROM HairJob h " +
            "WHERE h.id = :hairJobId")
    Optional<HairJobResponse> findHairJobById(@Param("hairJobId") Long hairJobId);

    @Query("SELECT new br.com.marlonbarbearia.hairjob.HairJobResponse(h.id, h.name, h.price, h.durationInSeconds) " +
            "FROM HairJob h " +
            "WHERE h.name = :hairJobName")
    Optional<HairJobResponse> findHairJobByHairJobName(@Param("hairJobName") String hairJobName);
}
