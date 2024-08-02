package com.example.demorestapi2.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("이벤트 생성 성공")
    public void createEvent() throws Exception {
        //given
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEventDateTime(LocalDateTime.now())
                .endEventDateTime(LocalDateTime.now().plusDays(30))
                .beginEnrollmentDateTime(LocalDateTime.now())
                .closeEnrollmentDateTime(LocalDateTime.now().plusDays(30))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto)));

        //then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.toString()));
    }

    @Test
    @DisplayName("이벤트 입력값 제한")
    public void restrict_input_value() throws Exception {
        //given
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2024, 1, 2, 12, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endEventDateTime(LocalDateTime.of(2024, 1, 2, 12, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)));

        //then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("빈 입력 값인 경우에 Bad Request(400) 응답")
    public void bad_request_when_null_input_value() throws Exception {
        //given
        EventDto eventDto = EventDto.builder().build();
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto)));

        //then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest());
    }


}
