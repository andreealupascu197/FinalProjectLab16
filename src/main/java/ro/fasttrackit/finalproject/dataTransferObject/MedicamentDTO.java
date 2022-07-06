package ro.fasttrackit.finalproject.dataTransferObject;

import ro.fasttrackit.finalproject.domain.Type;
import ro.fasttrackit.finalproject.domain.Usage;

import java.util.Date;

public record MedicamentDTO(
        String name,
        Integer quantity,
        Double price,
        Date expiryDate,
        Usage usage,
        Type type
) {
}
