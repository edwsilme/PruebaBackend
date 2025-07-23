package com.example.PruebaEdwslme.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "empleados")
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Basic
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    @Basic
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    @Basic
    @Column(name = "tipo_documento", nullable = false, length = 50)
    private String tipoDocumento;
    @Basic
    @Column(name = "numero_documento", nullable = false, length = 50)
    private String numeroDocumento;
    @Basic
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    @Basic
    @Column(name = "fecha_vinculacion", nullable = false)
    private LocalDate fechaVinculacion;
    @Basic
    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;
    @Basic
    @Column(name = "salario", nullable = false)
    private Double salario;
    @Basic
    @Column(name = "edad_actual", nullable = false)
    private String edadActual;
    @Basic
    @Column(name = "tiempo_vinclulacion", nullable = false)
    private String tiempoVinclulacion;
}
