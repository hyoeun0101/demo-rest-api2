package com.example.demorestapi2.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    @DisplayName("Event 빌더로 생성")
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Sporing REST API")
                .description("description")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    @DisplayName("Event 자바 빈으로 생성")
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

    @ParameterizedTest
    @MethodSource("paramsForTestFree")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        //given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();
        //when
        event.update();
        //then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private static Object[] paramsForTestFree() {
        return new Object[] {
                new Object[] {0, 0, true},
                new Object[] {100,0, false},
                new Object[] {0,100, false},
                new Object[] {100,200, false},

        };
    }


    @ParameterizedTest
    @MethodSource("paramsForTestOffline")
    public void testOffline(String location, boolean isOffline) {
        //given
        Event event = Event.builder()
                .location(location)
                .build();
        //when
        event.update();
        //then
        assertThat(event.isOffline()).isEqualTo(isOffline);

    }

    private static Object[] paramsForTestOffline() {
        return new Object[] {
                new Object[] {"   ", false},
                new Object[] {"강남역", true},
                new Object[] {null, false},
        };
    }




}