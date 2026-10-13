package gr.cyberstream.cyberleave.dto;

import gr.cyberstream.cyberleave.model.LeaveType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateLeaveRequestDto(
    @NotBlank(message = "Employee ID is required")
    String employeeId,

    @NotBlank(message = "Employee name is required")
    String employeeName,

    @NotNull(message = "Leave type is required")
    LeaveType leaveType,

    @NotNull(message = "Start date is required")
    LocalDate startDate,

    @NotNull(message = "End date is required")
    LocalDate endDate,

    String reason
) {}
