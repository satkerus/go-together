package org.go.together.interfaces;

import org.go.together.dto.ResponseDto;
import org.go.together.dto.filter.FormDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FindClient {
    @PostMapping("/find")
    ResponseDto<Object> find(@RequestBody FormDto formDto);
}
