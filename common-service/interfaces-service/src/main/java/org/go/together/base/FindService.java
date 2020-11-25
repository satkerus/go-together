package org.go.together.base;

import org.go.together.compare.FieldMapper;
import org.go.together.dto.Dto;
import org.go.together.dto.ResponseDto;
import org.go.together.dto.form.FormDto;

import java.util.Map;
import java.util.UUID;

public interface FindService<D extends Dto> {
    default ResponseDto<Object> find(FormDto formDto) {
        return find(null, formDto);
    }

    ResponseDto<Object> find(UUID requestId, FormDto formDto);

    String getServiceName();

    default Map<String, FieldMapper> getMappingFields() {
        return null;
    }
}
