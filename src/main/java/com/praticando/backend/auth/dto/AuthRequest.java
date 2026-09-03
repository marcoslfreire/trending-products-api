package com.praticando.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;



//Por que record e não uma classe comum? Records (Java 16+) são feitos exatamente pra isso: um objeto imutável que só carrega dados, sem lógica.
// O compilador já gera equals, hashCode, toString e os getters sozinho — menos código repetido, e comunica a intenção "isso é só um pacote de dados"
// (DTO = Data Transfer Object).
//
//Por que @NotBlank? É o Bean Validation (Hibernate Validator,
// que já está no projeto) validando a entrada antes de qualquer lógica de negócio rodar — se vier username vazio, o Quarkus já barra a requisição com 400,
// sem você escrever if manual.

public record AuthRequest (

        @NotBlank String username,
        @NotBlank String password

        ){}
