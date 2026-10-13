package gr.cyberstream.cyberleave;

import gr.cyberstream.cyberleave.dto.CreateLeaveRequestDto;
import gr.cyberstream.cyberleave.model.LeaveStatus;
import gr.cyberstream.cyberleave.model.LeaveType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LeaveRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnInitialLeaves() throws Exception {
        mockMvc.perform(get("/api/v1/leaves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].employeeId", notNullValue()));
    }

    @Test
    void shouldCreateNewLeaveRequest() throws Exception {
        CreateLeaveRequestDto dto = new CreateLeaveRequestDto(
                "EMP-003",
                "Charlie Brown",
                LeaveType.SICK,
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(4),
                "Doctor appointment"
        );

        mockMvc.perform(post("/api/v1/leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.employeeId", is("EMP-003")))
                .andExpect(jsonPath("$.status", is(LeaveStatus.PENDING.name())));
    }

    @Test
    void shouldFilterLeavesByEmployee() throws Exception {
        mockMvc.perform(get("/api/v1/leaves/employee/EMP-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId", is("EMP-001")));
    }

    @Test
    void shouldAccessActuatorRoot() throws Exception {
        mockMvc.perform(get("/actuator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links", notNullValue()));
    }

    @Test
    void shouldAccessActuatorHealth() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")));
    }

    @Test
    void shouldServeOpenApiYaml() throws Exception {
        mockMvc.perform(get("/openapi.yaml"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Cyber Leave Management API")))
                .andExpect(content().string(containsString("openapi: 3.0.3")));
    }

    @Test
    void shouldServeSwaggerUiIndex() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SwaggerUIBundle")))
                .andExpect(content().string(containsString("/openapi.yaml")));
    }

    @Test
    void shouldRedirectSwaggerUiHtml() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/swagger-ui/index.html"));
    }
}
