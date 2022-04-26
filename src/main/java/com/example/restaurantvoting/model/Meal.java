package com.example.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class Meal extends BaseEntity {

    @Column(name = "description", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "price", nullable = false)
    @Range(min = 30, max = 50000)
    private Integer price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Menu> menu = new HashSet<>();

    public Meal(Meal meal) {
        this(meal.id, meal.description, meal.price);
    }

    public Meal(String description, Integer price) {
        this(null, description, price);
    }

    public Meal(Integer id, String description, Integer price) {
        super(id);
        this.description = description;
        this.price = price;
    }
}
