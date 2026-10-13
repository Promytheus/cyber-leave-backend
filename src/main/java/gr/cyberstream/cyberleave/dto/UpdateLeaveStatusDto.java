package gr.cyberstream.cyberleave.dto;

import gr.cyberstream.cyberleave.model.LeaveStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateLeaveStatusDto(
    @NotNull(message = "Status is required")
    LeaveStatus status
) {}
