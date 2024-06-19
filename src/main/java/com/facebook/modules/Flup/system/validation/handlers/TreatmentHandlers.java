package com.facebook.modules.Flup.system.validation.handlers;

import com.facebook.modules.Flup.system.validation.Error;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TreatmentHandlers {
    private final String message;

    public TreatmentHandlers(final String message) {
        this.message = message;
    }

    public Error exec() {
        return new Error(this.handlerConstrain());
    }

    private String handlerConstrain() {
        if (this.message.contains("org.hibernate.exception.ConstraintViolationException")) {
            var listArray = new ArrayList<>();
            listArray.add(Arrays.stream(StringUtils.substringsBetween(this.message, "[", "]")).collect(Collectors.toList()));
            return "There is already %s".formatted(listArray.get(0).toString().split(",\\s*")[1]).replace("]", "");
        }
        return this.message;
    }
}
