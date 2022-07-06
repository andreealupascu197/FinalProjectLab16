package ro.fasttrackit.finalproject.service;


import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.domain.Medication;
import ro.fasttrackit.finalproject.domain.Type;
import ro.fasttrackit.finalproject.domain.Usage;
import ro.fasttrackit.finalproject.repository.PostgresTreatmentRepository;
import ro.fasttrackit.finalproject.repository.TreatmentRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TreatmentService {
    private TreatmentRepository tratamentRepository = new PostgresTreatmentRepository();

    public List<MedicamentDTO> listMedications() {
        List<MedicamentDTO> medicaments = new ArrayList<>();
        for (Medication tratament : tratamentRepository.findAll()) {
            medicaments.add(new MedicamentDTO(
                    tratament.name(),
                    tratament.quantity(),
                    tratament.price(),
                    Date.valueOf(String.valueOf(tratament.expiryDate())),
                    tratament.usage(),
                    tratament.type()

            ));
        }
        return medicaments;
    }

    public boolean addTreatment(String name, String quantity, String price, Date expiryDate, Usage usage, Type type) {

        int q = Integer.parseInt(quantity);
        double pr = Integer.parseInt(price);


        boolean wasAdded = true;
        //save history
        String treatment = name + " " + quantity + " " + price;


        tratamentRepository.save(new MedicamentDTO(name, q, pr, expiryDate, usage, type));
        return wasAdded;
    }

}
