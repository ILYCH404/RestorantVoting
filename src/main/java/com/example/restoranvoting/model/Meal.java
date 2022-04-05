package com.example.restoranvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "date_time", columnDefinition = "TIMESTAMP DEFAULT NOW()", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date added = new Date();

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "price")
    @Range(min = 100, max = 10000)
    private BigDecimal price;

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 5000)
    private int calories;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;

    public Meal(Date added, String description, BigDecimal price, int calories) {
        this(null, added, description, price, calories);
    }

    public Meal(Integer id, Date added, String description, BigDecimal price, int calories) {
        super(id);
        this.added = added;
        this.description = description;
        this.price = price;
        this.calories = calories;
    }
}
