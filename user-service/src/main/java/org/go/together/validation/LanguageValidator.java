package org.go.together.validation;

import org.go.together.dto.LanguageDto;
import org.springframework.stereotype.Component;

@Component
public class LanguageValidator extends Validator<LanguageDto> {
    @Override
    public void getMapsForCheck(LanguageDto dto) {

    }
}
