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
        return repository.findHairJobById(hairJobId)
                .orElseThrow(() -> new ObjectNotFoundException(hairJobId, HairJob.class.getSimpleName()));
    }

    @Override
    public HairJob findHairJobEntityById(Long hairJobId) {
        return repository.findById(hairJobId)
                .orElseThrow(() -> new ObjectNotFoundException(hairJobId, HairJob.class.getSimpleName()));
    }

    private Optional<HairJobResponse> findHairJobByHairJobName(String hairJobName) {
        return repository.findHairJobByHairJobName(hairJobName);
    }

    @Override
    public List<HairJobResponse> findAllHairJobs() {
        return repository.findAllHairJobs();
    }

    @Override
    public void editHairJob(Long hairJobId, HairJobRequest hairJobRequest) {
        HairJob existingHairJob = findHairJobEntityById(hairJobId);
        repository.save(
                HairJob.builder()
                        .id(existingHairJob.getId())
                        .name(hairJobRequest.name())
                        .price(hairJobRequest.price())
                        .durationInMinutes(hairJobRequest.durationInMinutes())
                        .build()
        );
    }

    @Override
    public void createHairJob(HairJobRequest hairJobRequest) {
        if(!findHairJobByHairJobName(hairJobRequest.name()).isEmpty()) {
            throw new ObjectAlreadyExistsException("Hairjob with name: [" + hairJobRequest.name() + "] already exists");
        }
        repository.save(
                HairJob.builder()
                        .name(hairJobRequest.name())
                        .price(hairJobRequest.price())
                        .durationInMinutes((hairJobRequest.durationInMinutes()))
                        .build()
        );
    }

    @Override
    public void deleteHairJob(Long hairJobId) {
        repository.findHairJobById(hairJobId);
        repository.deleteById(hairJobId);
    }

}
