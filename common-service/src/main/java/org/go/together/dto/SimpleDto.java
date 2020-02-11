package org.go.together.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.go.together.interfaces.Dto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleDto implements Dto {
    private UUID id;
    private String name;
}
