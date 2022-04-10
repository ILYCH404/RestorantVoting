package com.example.restoranvoting.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Vote extends BaseEntity {

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    @JoinColumn(name = "restaurant_id")
    @OneToOne
    private Restaurant restaurant;

    @Column(name = "vote")
    private boolean vote;

    @Column(name = "time", columnDefinition = "TIMESTAMP DEFAULT NOW()", updatable = false)
    private LocalDateTime DateTime;

    public Vote(Integer id, User user, Restaurant restaurant, boolean vote, LocalDateTime DateTime) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.vote = vote;
        this.DateTime = DateTime;
    }

    public Vote(User user, Restaurant restaurant, boolean vote, LocalDateTime DateTime) {
        this.user = user;
        this.restaurant = restaurant;
        this.vote = vote;
        this.DateTime = DateTime;
    }
}
