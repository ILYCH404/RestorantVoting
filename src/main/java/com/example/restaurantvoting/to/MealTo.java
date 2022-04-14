package com.example.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MealTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 120)
    String description;

    @Range(min = 30, max = 50000)
    int price;

    public MealTo(Integer id, String description, int price) {
        super(id);
        this.description = description;
        this.price = price;
    }
}
