package com.proyecto.apidevops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

    @GetMapping("/")
    public String index() {
        return "¡Holaaaaaa! El Pipeline funciona.";
    }

    @GetMapping("/estado")
    public String status() {
        return "El sistema está corriendo correctamente.";
    }
}
