package com.example.PruebaEdwslme.service;

import com.example.PruebaEdwslme.Integration.IntegrationSoap;
import com.example.PruebaEdwslme.dto.EmpleadoConsultaDTO;
import com.example.PruebaEdwslme.dto.RequestDto;
import com.example.PruebaEdwslme.dto.ValidarDto;
import com.example.PruebaEdwslme.entities.EmpleadoEntity;
import com.example.PruebaEdwslme.entities.repositories.EmpleadoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final IntegrationSoap soapIntegration;

    public EmpleadoService(EmpleadoRepository empleadoRepository, IntegrationSoap soapIntegration) {
        this.empleadoRepository = empleadoRepository;
        this.soapIntegration = soapIntegration;
    }

    public ResponseEntity<?> listarEmpleados() {
        var result = empleadoRepository.findAll();
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<?> verificarEmpleado(EmpleadoConsultaDTO consultaEmpleado) {
        try {
            var validar = ValidarDto.builder()
                    .nombres(consultaEmpleado.getNombres())
                    .apellidos(consultaEmpleado.getApellidos())
                    .tipoDocumento(consultaEmpleado.getTipoDocumento())
                    .numeroDocumento(consultaEmpleado.getNumeroDocumento())
                    .fechaNacimiento(consultaEmpleado.getFechaNacimiento())
                    .fechaVinculacion(consultaEmpleado.getFechaVinculacion())
                    .cargo(consultaEmpleado.getCargo())
                    .salario(consultaEmpleado.getSalario())
                    .build();
            validarEmpleado(validar);

            LocalDate hoy = LocalDate.now();
            Period edad = Period.between(consultaEmpleado.getFechaNacimiento(), hoy);
            Period vinculacion = Period.between(consultaEmpleado.getFechaVinculacion(), hoy);
            String edadFormated = String.format("%d años, %d meses y %d días", edad.getYears(), edad.getMonths(), edad.getDays());
            String vincludadoFormated = String.format("%d años, %d meses y %d días", vinculacion.getYears(), vinculacion.getMonths(), vinculacion.getDays());

            var data = RequestDto.builder()
                    .nombres(consultaEmpleado.getNombres())
                    .apellidos(consultaEmpleado.getApellidos())
                    .tipoDocumento(consultaEmpleado.getTipoDocumento())
                    .numeroDocumento(consultaEmpleado.getNumeroDocumento())
                    .fechaNacimiento(consultaEmpleado.getFechaNacimiento())
                    .fechaVinculacion(consultaEmpleado.getFechaVinculacion())
                    .cargo(consultaEmpleado.getCargo())
                    .salario(consultaEmpleado.getSalario())
                    .edadActual(edadFormated)
                    .tiempoVinclulacion(vincludadoFormated)
                    .build();
            var result = soapIntegration.enviarSolicitudSoap(data);
            if (result.getSuccess()) {
                EmpleadoEntity empleado = new EmpleadoEntity(
                        null,
                        data.getNombres(),
                        data.getApellidos(),
                        data.getTipoDocumento(),
                        data.getNumeroDocumento(),
                        data.getFechaNacimiento(),
                        data.getFechaVinculacion(),
                        data.getCargo(),
                        data.getSalario(),
                        data.getEdadActual(),
                        data.getTiempoVinclulacion()
                );
                var save = empleadoRepository.save(empleado);
                return ResponseEntity.ok().body(save);
            }
            return ResponseEntity.badRequest().body(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    private void validarEmpleado(ValidarDto dto) {
        validarCampoTexto(dto.getNombres(), "El nombre es obligatorio");
        validarCampoTexto(dto.getApellidos(), "El apellido es obligatorio");
        validarCampoTexto(dto.getTipoDocumento(), "El tipo de documento es obligatorio");
        validarCampoTexto(dto.getNumeroDocumento(), "El número de documento es obligatorio");
        validarCampoTexto(dto.getCargo(), "El cargo no debe estar vacío");

        if (dto.getFechaNacimiento() == null)
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");

        if (Period.between(dto.getFechaNacimiento(), LocalDate.now()).getYears() < 18)
            throw new IllegalArgumentException("El empleado debe ser mayor de edad.");

        if (dto.getFechaVinculacion() == null)
            throw new IllegalArgumentException("La fecha de vinculación es obligatoria");

        if (dto.getFechaVinculacion().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de vinculación no puede ser mayor a la fecha actual.");
    }

    private void validarCampoTexto(String valor, String mensajeError) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
    }
}
