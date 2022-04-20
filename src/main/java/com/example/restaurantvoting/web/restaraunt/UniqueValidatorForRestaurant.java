package com.example.restaurantvoting.web.restaraunt;

import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.to.RestaurantTo;
import com.example.restaurantvoting.web.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UniqueValidatorForRestaurant implements Validator {

    RestaurantRepository restaurantRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return RestaurantTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RestaurantTo restaurantTo = (RestaurantTo) target;

        if (StringUtils.hasText(restaurantTo.getAddress()) || StringUtils.hasText(restaurantTo.getDescription())) {
            restaurantRepository.getByAddress(restaurantTo.getAddress())
                    .ifPresent(dbRest ->
                            errors.rejectValue("address", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_ADDRESS));
            restaurantRepository.findByDescription(restaurantTo.getDescription())
                    .ifPresent(dbRest ->
                            errors.rejectValue("description", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_DESCRIPTION));
        }
    }
}
