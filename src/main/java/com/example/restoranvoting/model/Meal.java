package com.example.restoranvoting.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Meal extends BaseEntity {
    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;
    @Column(name = "description")
    @NotBlank
    private String description;
    @Column(name = "price")
    @Range(min = 100, max = 10000)
    private int price;
    @Column(name = "calories")
    @Range(min = 10, max = 5000)
    private int calories;

    public Meal(LocalDateTime dateTime, String description, int price, int calories) {
        this(null, dateTime, description, price, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int price, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.price = price;
        this.calories = calories;
    }
}
