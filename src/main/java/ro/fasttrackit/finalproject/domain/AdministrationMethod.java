package ro.fasttrackit.finalproject.domain;

public record AdministrationMethod(
        long idUser,
        Integer frequency,
        TimeOfDay timeOfDay,
        Boolean beforeEating
) {
}
