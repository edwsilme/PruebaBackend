package com.example.PruebaEdwslme.controllers;

import com.example.PruebaEdwslme.dto.EmpleadoConsultaDTO;
import com.example.PruebaEdwslme.service.EmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<?> listarEmpleados() {
        return this.empleadoService.listarEmpleados();
    }

    @GetMapping("/consultar")
    public ResponseEntity<?> consultarEmpleado(EmpleadoConsultaDTO consultaEmpleados) {
        return this.empleadoService.verificarEmpleado(consultaEmpleados);
    }
}
