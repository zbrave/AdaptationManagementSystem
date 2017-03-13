package org.o7planning.springmvcsecurity.validator;

import org.o7planning.springmvcsecurity.model.UniInfo;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UniValidator implements Validator {

	// The classes is supported to Validate
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UniInfo.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
 
        // Check the fields of ApplicantInfo.
        // (See more in property file: messages/validator.property)
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.uniForm.name");
      
    }

}
