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
    public HairJobDTO findHairJobById(Long hairJobId) {
        return repository.findHairJobById(hairJobId)
                .orElseThrow(() -> new ObjectNotFoundException(hairJobId, HairJob.class.getSimpleName()));
    }

    @Override
    public HairJob findHairJobEntityById(Long hairJobId) {
        return repository.findById(hairJobId)
                .orElseThrow(() -> new ObjectNotFoundException(hairJobId, HairJob.class.getSimpleName()));
    }

    private Optional<HairJobDTO> findHairJobByHairJobName(String hairJobName) {
        return repository.findHairJobByHairJobName(hairJobName);
    }

    @Override
    public List<HairJobDTO> findAllHairJobs() {
        return repository.findAllHairJobs();
    }

    @Override
    public void editHairJob(Long hairJobId, CreateHairJobRequest createHairJobRequest) {
        HairJob existingHairJob = findHairJobEntityById(hairJobId);
        repository.save(
                HairJob.builder()
                        .id(existingHairJob.getId())
                        .name(createHairJobRequest.name())
                        .price(createHairJobRequest.price())
                        .durationInMinutes(createHairJobRequest.durationInMinutes())
                        .build()
        );
    }

    @Override
    public void createHairJob(CreateHairJobRequest createHairJobRequest) {
        if(!findHairJobByHairJobName(createHairJobRequest.name()).isEmpty()) {
            throw new ObjectAlreadyExistsException("Hairjob with name: [" + createHairJobRequest.name() + "] already exists");
        }
        repository.save(
                HairJob.builder()
                        .name(createHairJobRequest.name())
                        .price(createHairJobRequest.price())
                        .durationInMinutes((createHairJobRequest.durationInMinutes()))
                        .build()
        );
    }

    @Override
    public void deleteHairJob(Long hairJobId) {
        repository.findHairJobById(hairJobId);
        repository.deleteById(hairJobId);
    }

}
