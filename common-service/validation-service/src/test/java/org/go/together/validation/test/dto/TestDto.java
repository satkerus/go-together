package org.go.together.validation.test.dto;

import lombok.Data;
import org.go.together.dto.SimpleDto;
import org.go.together.interfaces.Dto;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class TestDto implements Dto {
    private UUID id;
    private String name;
    private Long number;
    private Date date;
    private Date startDate;
    private Date endDate;
    private Long startNumber;
    private Long endNumber;
    private Double latitude;
    private Double longitude;
    private SimpleDto simpleDto;
    private Set<UUID> elements;
    private Set<JoinTestDto> joinTestEntities;
    private Set<ManyJoinDto> manyJoinEntities;
}
