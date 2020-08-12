package org.go.together.dto;

import lombok.Data;
import org.go.together.interfaces.Dto;

import java.util.UUID;

@Data
public class NotificationDto implements Dto {
    private UUID id;
    private UUID producerId;
}
