package com.lebus.swingy.util;

import com.lebus.swingy.model.PlayerCharacter;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

public class ValidateInput {

    Validator validator;

    public ValidateInput(Validator validator) {
        this.validator = validator;
    }

    public ArrayList<String> validate(PlayerCharacter playerCharacter) {

        Set<ConstraintViolation<PlayerCharacter>> cvs = validator.validate(playerCharacter);
        ArrayList<String> errors = new ArrayList<>();

        for (ConstraintViolation<PlayerCharacter> cv : cvs) {
            errors.add(cv.getMessage());
        }
        return errors;
    }
}
