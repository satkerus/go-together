package org.go.together.logic.find.enums;

import org.go.together.dto.SimpleDto;
import org.go.together.logic.repository.utils.sql.SqlOperator;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum FindSqlOperator {
    LIKE(SqlOperator.LIKE, dtos -> dtos.iterator().next().getId()),
    EQUAL(SqlOperator.EQUAL, dtos -> dtos.iterator().next().getId()),
    START_DATE(SqlOperator.GREATER_OR_EQUAL, dtos -> dtos.iterator().next().getId()),
    END_DATE(SqlOperator.LESS_OR_EQUAL, dtos -> dtos.iterator().next().getId()),
    IN(SqlOperator.IN, dtos -> dtos.stream().map(SimpleDto::getId).collect(Collectors.toSet()));

    private final SqlOperator operator;
    private final Function<Collection<SimpleDto>, Object> getSearchObjectFromDtos;

    FindSqlOperator(SqlOperator operator,
                    Function<Collection<SimpleDto>, Object> getSearchObjectFromDtos) {
        this.operator = operator;
        this.getSearchObjectFromDtos = getSearchObjectFromDtos;
    }

    public SqlOperator getOperator() {
        return operator;
    }

    public Function<Collection<SimpleDto>, Object> getSearchObjectFromDtos() {
        return getSearchObjectFromDtos;
    }
}
