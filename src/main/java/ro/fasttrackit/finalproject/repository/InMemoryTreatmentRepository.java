package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.domain.Medication;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryTreatmentRepository implements TreatmentRepository {
    private List<Medication> medicaments = new ArrayList<>();

    @Override
    public List<Medication> findAll() {
        return Collections.unmodifiableList(medicaments);
    }

    @Override
    public void save(MedicamentDTO medicament) {
        medicaments.add(new Medication(
                medicaments.size()
                ,medicament.name()
                ,medicament.quantity()
                ,medicament.price()
                ,Date.valueOf(String.valueOf(medicament.expiryDate()))
                ,medicament.usage()
                ,medicament.type()));
    }
}
