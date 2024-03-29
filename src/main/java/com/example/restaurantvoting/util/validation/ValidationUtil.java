package com.example.restaurantvoting.util.validation;

import com.example.restaurantvoting.HasId;
import com.example.restaurantvoting.error.IllegalRequestDataException;
import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@UtilityClass
public class ValidationUtil {

    private static final Random RANDOM = new Random();

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static boolean checkTime() {
        if (!LocalTime.now().isBefore(LocalTime.of(23, 0, 0))) {
            throw new IllegalRequestDataException("You can change your voice only until 23:00");
        }
        return true;
    }

    public static void checkTimeForUpdateMenu(LocalDate date) {
        if (!date.isBefore(LocalDate.now())) {
            throw new IllegalRequestDataException("The menu has been updated today.");
        }
    }

    public static void checkMealCount(Integer count) {
        if (count < 4) {
            throw new IllegalRequestDataException("There must be at least 5 items in the food list to form a menu");
        }
    }

    public int crateRandomNumberWithMin(int min, int max) {
        return min + RANDOM.nextInt((max + 1) - min);
    }

    public int crateRandomNumberWithMax(int max) {
        return RANDOM.nextInt(max);
    }
}