package org.go.together.service;

import com.google.common.collect.ImmutableMap;
import org.go.together.dto.LanguageDto;
import org.go.together.dto.filter.FieldMapper;
import org.go.together.logic.services.CrudService;
import org.go.together.mapper.LanguageMapper;
import org.go.together.model.Language;
import org.go.together.repository.LanguageRepository;
import org.go.together.validation.LanguageValidator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class LanguageService extends CrudService<LanguageDto, Language> {
    private final LanguageMapper languageMapper;
    private final LanguageRepository languageRepository;

    protected LanguageService(LanguageRepository languageRepository, LanguageMapper languageMapper,
                              LanguageValidator languageValidator) {
        super(languageRepository, languageMapper, languageValidator);
        this.languageMapper = languageMapper;
        this.languageRepository = languageRepository;
    }

    public Collection<LanguageDto> getLanguages() {
        return languageMapper.entitiesToDtos(languageRepository.findAll());

    }

    @Override
    public String getServiceName() {
        return "language";
    }

    @Override
    public Map<String, FieldMapper> getMappingFields() {
        return ImmutableMap.<String, FieldMapper>builder()
                .put("id", FieldMapper.builder()
                        .currentServiceField("id").build()).build();
    }
}
