package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.TreatmentDTO;
import ro.fasttrackit.finalproject.domain.Treatment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryTreatmentRepository implements TreatmentRepository {
    private List<Treatment> medicaments = new ArrayList<>();

    @Override
    public List<Treatment> findAll() {
        return Collections.unmodifiableList(medicaments);
    }

    @Override
    public void save(TreatmentDTO medicament) {
        medicaments.add(new Treatment(medicaments.size(),medicament.name(), medicament.quantity(), medicament.price()));
    }
}
