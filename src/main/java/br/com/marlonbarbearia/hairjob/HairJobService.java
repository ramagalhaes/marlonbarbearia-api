package br.com.marlonbarbearia.hairjob;

import java.util.List;

public interface HairJobService {
    HairJobDTO findHairJobById(Long hairJobId);
    HairJob findHairJobEntityById(Long hairJobId);
    List<HairJobDTO> findAllHairJobs();
    void editHairJob(Long hairJobId, HairJobRequest hairJobRequest);
    void createHairJob(HairJobRequest hairJobRequest);
    void deleteHairJob(Long hairJobId);
}
