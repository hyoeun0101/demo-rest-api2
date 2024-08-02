package com.example.demorestapi2.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    @DisplayName("빌더 테스트")
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Sporing REST API")
                .description("description")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    @DisplayName("자바 빈 테스트")
    public void javaBean() {
        //given
        String name = "Event";
        String description = "Spring";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

}