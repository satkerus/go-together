package org.go.together.validation;

import org.go.together.dto.PaidThingDto;
import org.go.together.validation.impl.CommonValidator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaidThingValidator extends CommonValidator<PaidThingDto> {
    @Override
    public void getMapsForCheck(PaidThingDto dto) {
        super.STRINGS_FOR_BLANK_CHECK = Map.of("paid thing name", PaidThingDto::getName);
    }
}
