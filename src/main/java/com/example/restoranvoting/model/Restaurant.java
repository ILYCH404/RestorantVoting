package com.example.restoranvoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", orphanRemoval = true)
    private List<Meal> menu;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> visitors;

    public Restaurant(String description, List<Meal> menu, List<User> visitors, String address) {
        this.description = description;
        this.menu = menu;
        this.visitors = visitors;
        this.address = address;
    }

    public Restaurant(Integer id, String description, List<Meal> menu, List<User> visitors, String address) {
        super(id);
        this.description = description;
        this.menu = menu;
        this.visitors = visitors;
        this.address = address;
    }
}

