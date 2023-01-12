package pl.javastart.healtycalc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HealthyCalcApplicationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {

    }

    @Test
    void shouldRespondWithOkForPositiveValues() throws Exception {
        mockMvc.perform(get("/api/bmi").param("weight", "100").param("height", "150"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRespondWithBadRequestForNegativeWeight() throws Exception {
        mockMvc.perform(get("/api/bmi").param("weight", "-100").param("height", "150"))
                .andExpectAll(
                        status().isBadRequest(),
                        header().string("reason", "invalid data, weight and height parameteres must be positive numbers")
                );
    }
}
