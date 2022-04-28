package com.example.restaurantvoting.web.restaraunt;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.web.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UniqueValidatorForRestaurant implements Validator {

    private RestaurantRepository restaurantRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Restaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurant = (Restaurant) target;

        if (StringUtils.hasText(restaurant.getAddress()) || StringUtils.hasText(restaurant.getDescription())) {
            restaurantRepository.findByAddressIgnoreCase(restaurant.getAddress().toLowerCase())
                    .ifPresent(dbRest ->
                            errors.rejectValue("address", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_ADDRESS));
            restaurantRepository.findByDescriptionIgnoreCase(restaurant.getDescription().toLowerCase())
                    .ifPresent(dbRest ->
                            errors.rejectValue("description", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_DESCRIPTION));
        }
    }
}
