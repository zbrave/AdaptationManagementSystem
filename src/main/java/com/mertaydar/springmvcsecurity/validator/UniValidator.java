package com.mertaydar.springmvcsecurity.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mertaydar.springmvcsecurity.model.UniInfo;

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
