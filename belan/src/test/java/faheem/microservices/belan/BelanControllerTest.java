package faheem.microservices.belan;

import com.fasterxml.jackson.databind.ObjectMapper;

import faheem.microservices.belan.controller.BelanController;
import faheem.microservices.belan.entity.Belan;
import faheem.microservices.belan.exceptions.BelanNotAddedException;
import faheem.microservices.belan.service.BelanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BelanController.class)
public class BelanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BelanService belanService;

    @InjectMocks
    private BelanController belanController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(belanController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void whenAddBelanWithValidBelan_thenReturnsStatusCreated() throws Exception {
        Belan belan = new Belan();
        belan.setBelanName("Test Belan");
        belan.setBelanColor("Red");

        when(belanService.addBelan(any(Belan.class))).thenReturn(belan);

        mockMvc.perform(post("/belan/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(belan)))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenAddBelanWithNullBelan_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/belan/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddBelanThrowsBelanNotAddedException_thenReturnsInternalServerError() throws Exception {
        Belan belan = new Belan();
        belan.setBelanName("Test Belan");
        belan.setBelanColor("Red");

        doThrow(new BelanNotAddedException("Could not add Belan")).when(belanService).addBelan(any(Belan.class));

        mockMvc.perform(post("/belan/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(belan)))
                .andExpect(status().isInternalServerError());
    }
}