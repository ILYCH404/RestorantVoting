package com.example.restaurantvoting.web.restaraunt;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.web.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueValidatorForRestaurant implements Validator {

    private RestaurantRepository restaurantRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(Class<?> clazz) {
        return Restaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurant = (Restaurant) target;

        if (StringUtils.hasText(restaurant.getAddress()) || StringUtils.hasText(restaurant.getDescription())) {
            restaurantRepository.findByAddressIgnoreCase(restaurant.getAddress())
                    .ifPresent(dbRest -> {
                        if (checkPutMethod(restaurant, dbRest)) return;
                        errors.rejectValue("address", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_ADDRESS);
                            });
            restaurantRepository.findByDescriptionIgnoreCase(restaurant.getDescription())
                    .ifPresent(dbRest -> {
                        if (checkPutMethod(restaurant, dbRest)) return;
                        errors.rejectValue("description", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_DESCRIPTION);
                    });
                    }
    }

    private boolean checkPutMethod(Restaurant restaurant, Restaurant dbRest) {
        if (request.getMethod().equals("PUT")) {
            int dbId = dbRest.id();

            if (restaurant.getId() != null && dbId == restaurant.id()) return true;

            String requestURI = request.getRequestURI();
            if (requestURI.endsWith("/" + dbId))
                return true;
        }
        return false;
    }
}
