package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.domain.AdministrationMethod;
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
    public void save(MedicamentDTO medicamentDTO) {
        AdministrationMethod administrationMethod = new AdministrationMethod(
                medicaments.size()
                ,medicamentDTO.administrationMethodDTO().frequency()
                ,medicamentDTO.administrationMethodDTO().timeOfDay()
                ,medicamentDTO.administrationMethodDTO().beforeEating());

        medicaments.add(new Medication(
                medicaments.size()
                ,medicamentDTO.name()
                ,medicamentDTO.quantity()
                ,medicamentDTO.price()
                ,Date.valueOf(String.valueOf(medicamentDTO.expiryDate()))
                ,medicamentDTO.usage()
                ,medicamentDTO.type()
                ,administrationMethod));
    }
}
