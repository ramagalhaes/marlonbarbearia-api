package br.com.marlonbarbearia.hairjob;

import java.util.List;
import java.util.Optional;

public interface HairJobService {
    HairJobDTO findHairJobDTOById(Long hairJobId);
    HairJob findHairJobById(Long hairJobId);
    List<HairJobDTO> findAllHairJobs();
    void editHairJob(Long hairJobId, CreateHairJobRequest createHairJobRequest);
    void createHairJob(CreateHairJobRequest createHairJobRequest);
    void deleteHairJob(Long hairJobId);
    public Optional<HairJob> findHairJobByName(String hairJobName);
}
