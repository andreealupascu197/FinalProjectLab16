package ro.fasttrackit.finalproject.service;


import ro.fasttrackit.finalproject.dataTransferObject.TreatmentDTO;
import ro.fasttrackit.finalproject.domain.Treatment;
import ro.fasttrackit.finalproject.repository.PostgresTreatmentRepository;
import ro.fasttrackit.finalproject.repository.TreatmentRepository;

import java.util.ArrayList;
import java.util.List;

public class TreatmentService {
    private TreatmentRepository tratamentRepository= new PostgresTreatmentRepository();

    public List<TreatmentDTO> listMedications() {
        List<TreatmentDTO> medicaments = new ArrayList<>();
        for (Treatment tratament : tratamentRepository.findAll()) {
            medicaments.add(new TreatmentDTO(
                    tratament.name(),
                    tratament.quantity(),
                    tratament.price()

            ));
        }
        return medicaments;
    }

    public boolean addTreatment(String name, String quantity, String price) {

        int q = Integer.parseInt(quantity);
        int pr = Integer.parseInt(price);

        boolean wasAdded=true;
        //save history
        String treatment = name + " " + quantity + " " + price;


        tratamentRepository.save(new TreatmentDTO(name, q, pr));
        return wasAdded;
    }

}
