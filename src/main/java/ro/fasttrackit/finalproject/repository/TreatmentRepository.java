package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.domain.AdministrationMethod;
import ro.fasttrackit.finalproject.domain.Medication;

import java.util.List;

public interface TreatmentRepository {
    List<Medication> findAll();

    void save(MedicamentDTO medicamentDTO);
}
