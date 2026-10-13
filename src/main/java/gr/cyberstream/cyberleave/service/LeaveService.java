package gr.cyberstream.cyberleave.service;

import gr.cyberstream.cyberleave.dto.CreateLeaveRequestDto;
import gr.cyberstream.cyberleave.dto.UpdateLeaveStatusDto;
import gr.cyberstream.cyberleave.model.LeaveRequest;
import gr.cyberstream.cyberleave.model.LeaveStatus;
import gr.cyberstream.cyberleave.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @Transactional(readOnly = true)
    public List<LeaveRequest> getAllLeaves(Optional<LeaveStatus> status) {
        return status.map(leaveRequestRepository::findByStatus)
                     .orElseGet(leaveRequestRepository::findAll);
    }

    @Transactional(readOnly = true)
    public Optional<LeaveRequest> getLeaveById(Long id) {
        return leaveRequestRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<LeaveRequest> getLeavesByEmployee(String employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    public LeaveRequest createLeave(CreateLeaveRequestDto dto) {
        if (dto.endDate().isBefore(dto.startDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        LeaveRequest request = new LeaveRequest(
            dto.employeeId(),
            dto.employeeName(),
            dto.leaveType(),
            dto.startDate(),
            dto.endDate(),
            dto.reason()
        );
        return leaveRequestRepository.save(request);
    }

    public Optional<LeaveRequest> updateLeaveStatus(Long id, UpdateLeaveStatusDto dto) {
        return leaveRequestRepository.findById(id).map(request -> {
            request.setStatus(dto.status());
            return leaveRequestRepository.save(request);
        });
    }

    public boolean deleteLeave(Long id) {
        if (leaveRequestRepository.existsById(id)) {
            leaveRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
