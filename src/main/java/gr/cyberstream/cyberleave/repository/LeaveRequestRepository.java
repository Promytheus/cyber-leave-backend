package gr.cyberstream.cyberleave.repository;

import gr.cyberstream.cyberleave.model.LeaveRequest;
import gr.cyberstream.cyberleave.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(String employeeId);

    List<LeaveRequest> findByStatus(LeaveStatus status);
}
