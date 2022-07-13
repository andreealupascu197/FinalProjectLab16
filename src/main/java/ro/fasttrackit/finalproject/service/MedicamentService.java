package ro.fasttrackit.finalproject.service;


import ro.fasttrackit.finalproject.dataTransferObject.AdministrationMethodDTO;
import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.domain.*;
import ro.fasttrackit.finalproject.repository.PostgresTreatmentRepository;
import ro.fasttrackit.finalproject.repository.TreatmentRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicamentService {
    private TreatmentRepository medicamentRepository = new PostgresTreatmentRepository();


    public List<MedicamentDTO> listMedications() {
        List<MedicamentDTO> medicaments = new ArrayList<>();
        for (Medication medicament : medicamentRepository.findAll()) {
            AdministrationMethodDTO administrationMethodDTO = new AdministrationMethodDTO(
                    medicament.administrationMethod().frequency(),
                    medicament.administrationMethod().timeOfDay(),
                    medicament.administrationMethod().beforeEating()
            );
            medicaments.add(new MedicamentDTO(
                    medicament.name(),
                    medicament.quantity(),
                    medicament.price(),
                    medicament.expiryDate(),
                    medicament.usage(),
                    medicament.type(),
                    administrationMethodDTO

            ));
        }
        return medicaments;
    }

    public boolean addMedicament(
            String name,
            String quantity,
            String price,
            String expiryDate,
            String usage,
            String type,
            String frequency,
            String timeOfDay,
            String beforeEating) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expiryDt;
        try {
            Date convertedCurrentDate = sdf.parse(expiryDate);
            expiryDt = convertedCurrentDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int q = Integer.parseInt(quantity);
        double pr = Integer.parseInt(price);

        Usage usg = Usage.valueOf(usage);
        Type tp = Type.valueOf(type);
        int freq= Integer.parseInt(frequency);
        TimeOfDay timeDay = TimeOfDay.valueOf(timeOfDay);
        boolean beforeEat = Boolean.parseBoolean(beforeEating);
        AdministrationMethodDTO adminMethod = new AdministrationMethodDTO(freq,timeDay,beforeEat);



        boolean wasAdded = true;
        //save history
        String medicament = name + " " + quantity + " " + price + " " + expiryDate + " " + usage + " " + type;


        medicamentRepository.save(new MedicamentDTO(name, q, pr, expiryDt, usg, tp, adminMethod));
        return wasAdded;
    }
}
