package com.pollos.supermercado.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProveedorRequestDTO {

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombre;

    @NotBlank(message = "El NIT es obligatorio")
    @Pattern(regexp = "^[0-9\\-]{6,20}$", message = "El NIT debe contener solo números o guiones")
    private String nit;

    @Size(max = 30, message = "El teléfono no puede superar 30 caracteres")
    private String telefono;

    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 120, message = "El correo no puede superar 120 caracteres")
    private String correo;

    @Size(max = 255, message = "La dirección no puede superar 255 caracteres")
    private String direccion;
}
