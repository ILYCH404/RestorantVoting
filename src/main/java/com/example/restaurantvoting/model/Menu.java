package com.example.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Menu extends BaseEntity {

    @Column(name = "date_time", columnDefinition = "DATE DEFAULT NOW()")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate added = LocalDate.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (
                    name = "menu_meal",
                    joinColumns = @JoinColumn(name = "menu_id", nullable = false),
                    inverseJoinColumns = @JoinColumn(name = "meal_id", nullable = false)
            )
    private Set<Meal> menu;

    @OneToOne
    @JoinColumn
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Menu(Integer id, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
    }

    public Menu(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
