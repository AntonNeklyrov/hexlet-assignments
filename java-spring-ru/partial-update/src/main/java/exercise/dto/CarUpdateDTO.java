package exercise.dto;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class CarUpdateDTO {

    private JsonNullable<String> model;

    private JsonNullable<String> manufacturer;

    private JsonNullable<Integer> enginePower;

}

