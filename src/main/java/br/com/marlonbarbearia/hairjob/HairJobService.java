package br.com.marlonbarbearia.hairjob;

import java.util.List;

public interface HairJobService {
    HairJobDTO findHairJobById(Long hairJobId);
    HairJob findHairJobEntityById(Long hairJobId);
    List<HairJobDTO> findAllHairJobs();
    void editHairJob(Long hairJobId, CreateHairJobRequest createHairJobRequest);
    void createHairJob(CreateHairJobRequest createHairJobRequest);
    void deleteHairJob(Long hairJobId);
}
