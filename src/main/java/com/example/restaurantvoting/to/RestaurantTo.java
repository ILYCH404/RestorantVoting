package com.example.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 50)
    String description;

    @NotBlank
    @Size(min = 2, max = 70)
    String address;

    public RestaurantTo(Integer id, String description, String address) {
        super(id);
        this.description = description;
        this.address = address;
    }
}
