package com.example.demorestapi2.events;

import com.example.demorestapi2.config.CommonConstants;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors) {
        if (eventDto.getMaxPrice() > 0 && eventDto.getBasePrice() > eventDto.getMaxPrice()) {
            errors.rejectValue("basePrice", CommonConstants.WRONG_VALUE_CODE, CommonConstants.WRONG_VALUE_MSG);
            errors.rejectValue("maxPrice", CommonConstants.WRONG_VALUE_CODE, CommonConstants.WRONG_VALUE_MSG);
        }

        if (eventDto.getEndEventDateTime().isBefore(eventDto.getBeginEventDateTime()) ||
                eventDto.getEndEventDateTime().isBefore(eventDto.getBeginEnrollmentDateTime()) ||
                eventDto.getEndEventDateTime().isBefore(eventDto.getCloseEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", CommonConstants.WRONG_VALUE_CODE, CommonConstants.WRONG_VALUE_MSG);
        }

        if (eventDto.getBeginEventDateTime().isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("beginEventDateTime", CommonConstants.WRONG_VALUE_CODE, CommonConstants.WRONG_VALUE_MSG);
        }
    }
}
