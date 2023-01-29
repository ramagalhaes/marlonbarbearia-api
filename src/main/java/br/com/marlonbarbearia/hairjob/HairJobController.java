package br.com.marlonbarbearia.hairjob;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/hair-jobs")
@AllArgsConstructor
public class HairJobController {

    private final HairJobService service;

    @PostMapping
    public ResponseEntity<Void> createHairJob(@RequestBody HairJobRequest hairJobRequest) {
        service.createHairJob(hairJobRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{hairJobId}")
    public ResponseEntity<Void> editHairJob(@PathVariable("hairJobId") Long hairJobId,
                                              @RequestBody HairJobRequest hairJobRequest) {
        service.editHairJob(hairJobId, hairJobRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<HairJobDTO>> findAllHairJobs() {
        List<HairJobDTO> hairJobResponses = service.findAllHairJobs();
        return ResponseEntity.ok().body(hairJobResponses);
    }

    @GetMapping("/{hairJobId}")
    public ResponseEntity<HairJobDTO> findHairJobById(@PathVariable("hairJobId") Long hairJobId) {
        HairJobDTO hairJobDTO = service.findHairJobById(hairJobId);
        return ResponseEntity.ok().body(hairJobDTO);
    }

}
