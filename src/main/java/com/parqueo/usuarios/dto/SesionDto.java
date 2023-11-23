package com.parqueo.usuarios.dto;

import com.parqueo.usuarios.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SesionDto {
    private String tipoUsuario;
    private Person person;
}
