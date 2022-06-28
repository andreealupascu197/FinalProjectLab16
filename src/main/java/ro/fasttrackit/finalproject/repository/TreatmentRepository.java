package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.TreatmentDTO;
import ro.fasttrackit.finalproject.domain.Treatment;

import java.util.List;

public interface TreatmentRepository {
    List<Treatment> findAll();

    void save(TreatmentDTO medicament);
}
