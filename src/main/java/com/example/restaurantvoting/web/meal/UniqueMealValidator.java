package com.example.restaurantvoting.web.meal;

import com.example.restaurantvoting.model.Meal;
import com.example.restaurantvoting.repository.MealRepository;
import com.example.restaurantvoting.web.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UniqueMealValidator implements Validator {

    private MealRepository mealRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Meal.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Meal meal = (Meal) target;

        if (StringUtils.hasText(meal.getDescription())) {
            mealRepository.findByDescription(meal.getDescription())
                    .ifPresent(dbRest ->
                            errors.rejectValue("description", "", GlobalExceptionHandler.MEAL_EXCEPTION_DUPLICATE_DESCRIPTION));
        }
    }
}
