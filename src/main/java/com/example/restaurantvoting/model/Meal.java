package com.example.restaurantvoting.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class Meal extends BaseEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "price", nullable = false)
    @Range(min = 30, max = 50000)
    private Integer price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    private Menu menu;

    public Meal(String description, Integer price) {
        this(null, description, price);
    }

    public Meal(Integer id, String description, Integer price) {
        super(id);
        this.description = description;
        this.price = price;
    }
}
