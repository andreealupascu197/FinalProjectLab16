package ro.fasttrackit.finalproject.dataTransferObject;

import ro.fasttrackit.finalproject.domain.TimeOfDay;

import java.util.List;

public record AdministrationMethodDTO(
        Integer frequency,
        List<TimeOfDay> timesOfDay,
        Boolean beforeEating
) {

}
