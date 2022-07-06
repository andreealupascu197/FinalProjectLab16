package ro.fasttrackit.finalproject.domain;

import java.util.List;

public record AdministrationMethod(
        long idUser,
        Integer frequency,
        List<TimeOfDay> timesOfDay,
        Boolean beforeEating
) {
}
