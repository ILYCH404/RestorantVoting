package com.example.restoranvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restoraunt")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "address")
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Set<Meal> meals;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> visitors;

    public Restaurant(String description, Set<Meal> meals, Set<User> visitors, String address) {
        this.description = description;
        this.meals = meals;
        this.visitors = visitors;
        this.address = address;
    }

    public Restaurant(Integer id, String description, Set<Meal> meals, Set<User> visitors, String address) {
        super(id);
        this.description = description;
        this.meals = meals;
        this.visitors = visitors;
        this.address = address;
    }
}

