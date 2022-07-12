package br.com.marlonbarbearia.hairjob;

import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class HairJobServiceImpl implements HairJobService {

    private final HairJobRepository repository;

    @Override
    public HairJobResponse findHairJobById(Long hairJobId) {
        return this.repository.findHairJobById(hairJobId)
                .orElseThrow(() -> new ObjectNotFoundException(hairJobId, HairJob.class.getSimpleName()));
    }

    @Override
    public HairJob findHairJobEntityById(Long hairJobId) {
        return this.repository.findById(hairJobId)
                .orElseThrow(() -> new ObjectNotFoundException(hairJobId, HairJob.class.getSimpleName()));
    }

    private Optional<HairJobResponse> findHairJobByHairJobName(String hairJobName) {
        return this.repository.findHairJobByHairJobName(hairJobName);
    }

    @Override
    public List<HairJobResponse> findAllHairJobs() {
        return this.repository.findAllHairJobs();
    }

    @Override
    public void editHairJob(Long hairJobId, HairJobRequest hairJobRequest) {
        HairJob existingHairJob = this.findHairJobEntityById(hairJobId);
        this.repository.save(
                HairJob.builder()
                        .id(existingHairJob.getId())
                        .name(hairJobRequest.name())
                        .price(hairJobRequest.price())
                        .durationInSeconds(hairJobRequest.durationInSeconds())
                        .build()
        );
    }

    @Override
    public void createHairJob(HairJobRequest hairJobRequest) {
        if(!this.findHairJobByHairJobName(hairJobRequest.name()).isEmpty()) {
            throw new ObjectAlreadyExistsException("Hairjob with name: [" + hairJobRequest.name() + "] already exists");
        }
        this.repository.save(
                HairJob.builder()
                        .name(hairJobRequest.name())
                        .price(hairJobRequest.price())
                        .durationInSeconds((hairJobRequest.durationInSeconds()))
                        .build()
        );
    }

    @Override
    public void deleteHairJob(Long hairJobId) {
        this.repository.findHairJobById(hairJobId);
        this.repository.deleteById(hairJobId);
    }

}
