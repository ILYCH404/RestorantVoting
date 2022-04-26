package com.example.restaurantvoting.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {

    @Column(name = "description", unique = true)
    @NotBlank
    @Size(min = 2, max = 50)
    private String description;

    @Column(name = "address", unique = true)
    @NotBlank
    private String address;

    public Restaurant(Restaurant restaurant) {
        this(restaurant.id, restaurant.description, restaurant.getAddress());
    }

    public Restaurant(String description, String address) {
        this.description = description;
        this.address = address;
    }

    public Restaurant(Integer id, String description, String address) {
        super(id);
        this.description = description;
        this.address = address;
    }
}

