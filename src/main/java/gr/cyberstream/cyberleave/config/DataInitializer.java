package gr.cyberstream.cyberleave.config;

import gr.cyberstream.cyberleave.model.LeaveRequest;
import gr.cyberstream.cyberleave.model.LeaveStatus;
import gr.cyberstream.cyberleave.model.LeaveType;
import gr.cyberstream.cyberleave.repository.LeaveRequestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(LeaveRequestRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                LeaveRequest leave1 = new LeaveRequest(
                    "EMP-001",
                    "Alice Smith",
                    LeaveType.ANNUAL,
                    LocalDate.now().plusDays(5),
                    LocalDate.now().plusDays(10),
                    "Annual summer vacation"
                );
                leave1.setStatus(LeaveStatus.APPROVED);

                LeaveRequest leave2 = new LeaveRequest(
                    "EMP-002",
                    "Bob Johnson",
                    LeaveType.REMOTE_WORK,
                    LocalDate.now().plusDays(1),
                    LocalDate.now().plusDays(3),
                    "Remote sprint planning"
                );

                repository.saveAll(List.of(leave1, leave2));
            }
        };
    }
}
