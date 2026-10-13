package gr.cyberstream.cyberleave.controller;

import gr.cyberstream.cyberleave.dto.CreateLeaveRequestDto;
import gr.cyberstream.cyberleave.dto.UpdateLeaveStatusDto;
import gr.cyberstream.cyberleave.model.LeaveRequest;
import gr.cyberstream.cyberleave.model.LeaveStatus;
import gr.cyberstream.cyberleave.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/leaves")
public class LeaveRequestController {

    private final LeaveService leaveService;

    public LeaveRequestController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaves(@RequestParam(required = false) LeaveStatus status) {
        return leaveService.getAllLeaves(Optional.ofNullable(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveById(@PathVariable Long id) {
        return leaveService.getLeaveById(id)
                           .map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getLeavesByEmployee(@PathVariable String employeeId) {
        return leaveService.getLeavesByEmployee(employeeId);
    }

    @PostMapping
    public ResponseEntity<?> createLeave(@Valid @RequestBody CreateLeaveRequestDto dto) {
        try {
            LeaveRequest created = leaveService.createLeave(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLeaveStatusDto dto) {
        return leaveService.updateLeaveStatus(id, dto)
                           .map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        if (leaveService.deleteLeave(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
