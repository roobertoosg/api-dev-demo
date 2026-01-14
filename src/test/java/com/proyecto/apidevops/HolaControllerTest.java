package com.proyecto.apidevops;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HolaControllerTest {

    @Autowired
    private HolaController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void saludoTest() {
        assertThat(controller.index()).contains("El Pipeline funciona");
    }
}
