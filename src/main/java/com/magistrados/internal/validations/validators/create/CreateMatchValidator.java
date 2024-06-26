package com.magistrados.internal.validations.validators.create;

import com.magistrados.internal.validations.ErrorMessage;
import com.magistrados.internal.validations.Validator;
import com.magistrados.internal.validations.exceptions.ValidationException;
import com.magistrados.models.create.CreateMatch;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateMatchValidator extends Validator<CreateMatch> {

    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void validate(CreateMatch object) throws ValidationException {
        if (NumberUtils.toLong(object.idTimeA(), 0) < 1) {
            this.addError("ID do time A", ErrorMessage.NOT_ID);
        }
        if (NumberUtils.toLong(object.idTimeB(), 0) < 1) {
            this.addError("ID do time B", ErrorMessage.NOT_ID);
        }
        if (NumberUtils.isCreatable(object.idTimeA()) && NumberUtils.isCreatable(object.idTimeB()) && object.getIdTimeA().equals(object.getIdTimeB())) {
            this.addError("ID times", ErrorMessage.NOT_REPEATABLE);
        }

        try {
            LocalDate.parse(object.data(), DATE_FORMATTER);
        } catch (Exception ex) {
            this.addError("data", ErrorMessage.NOT_VALID_DATE);
        }
        try {
            LocalTime.parse(object.horario(), TIME_FORMATTER);
        } catch (Exception ex) {
            this.addError("data", ErrorMessage.NOT_VALID_TIME);
        }
        if (object.local() == null || object.local().isBlank()) {
            this.addError("local", ErrorMessage.NOT_NULLABLE);
        }

        this.throwPossibleErrors();
    }
}
