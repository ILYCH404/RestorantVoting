package com.example.restoranvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Meal extends BaseEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "price")
    @Range(min = 100, max = 10000)
    private BigDecimal price;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;

    public Meal(Date added, String description, BigDecimal price, int calories) {
        this(null, description, price);
    }

    public Meal(Integer id, String description, BigDecimal price) {
        super(id);
        this.description = description;
        this.price = price;
    }
}
