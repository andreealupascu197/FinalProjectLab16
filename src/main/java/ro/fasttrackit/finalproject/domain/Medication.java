package ro.fasttrackit.finalproject.domain;

import java.time.LocalDate;
import java.util.Date;

public record Medication(
        long id,
        String name,
        Integer quantity,
        Double price,
        Date expiryDate,
        Usage usage,
        Type type,
        AdministrationMethod administrationMethod


) {

}
