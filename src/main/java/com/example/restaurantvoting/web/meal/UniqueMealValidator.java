package com.example.restaurantvoting.web.meal;

import com.example.restaurantvoting.model.Meal;
import com.example.restaurantvoting.repository.MealRepository;
import com.example.restaurantvoting.web.GlobalExceptionHandler;
import com.example.restaurantvoting.web.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueMealValidator implements Validator {

    private MealRepository mealRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Meal.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target,@NonNull Errors errors) {
        Meal meal = (Meal) target;

        if (StringUtils.hasText(meal.getDescription())) {
            mealRepository.findByDescriptionIgnoreCase(meal.getDescription())
                    .ifPresent(dbMeal -> {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbMeal.id();

                            if (meal.getId() != null && dbId == meal.id()) return;

                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId))
                                return;
                        }

                            errors.rejectValue("description", "", GlobalExceptionHandler.MEAL_EXCEPTION_DUPLICATE_DESCRIPTION);
                    });
        }
    }
}
