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
public class HairJobService {

    private final HairJobRepository repository;

    public HairJobDTO findHairJobDTOById(Long hairJobId) {
        return repository.findHairJobDTOById(hairJobId)
                .orElseThrow(() -> new ObjectNotFoundException(hairJobId, HairJob.class.getSimpleName()));
    }

    public HairJob findHairJobById(Long hairJobId) {
        return repository.findById(hairJobId)
                .orElseThrow(() -> new ObjectNotFoundException(hairJobId, HairJob.class.getSimpleName()));
    }

    public Optional<HairJob> findHairJobByName(String hairJobName) {
        return repository.findHairJobByHairJobName(hairJobName);
    }

    public List<HairJobDTO> findAllHairJobs() {
        return repository.findAllHairJobs();
    }

    public void editHairJob(Long hairJobId, CreateHairJobRequest createHairJobRequest) {
        HairJob existingHairJob = findHairJobById(hairJobId);
        repository.save(
                HairJob.builder()
                        .id(existingHairJob.getId())
                        .name(createHairJobRequest.name())
                        .price(createHairJobRequest.price())
                        .durationInMinutes(createHairJobRequest.durationInMinutes())
                        .build()
        );
    }

    public void createHairJob(CreateHairJobRequest createHairJobRequest) {
        boolean hairJobExists = findHairJobByName(createHairJobRequest.name()).isPresent();
        if(hairJobExists) {
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

    public void deleteHairJob(Long hairJobId) {
        repository.findHairJobById(hairJobId);
        repository.deleteById(hairJobId);
    }

}
