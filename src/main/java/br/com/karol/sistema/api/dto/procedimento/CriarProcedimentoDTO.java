package br.com.karol.sistema.api.dto.procedimento;

import java.time.LocalTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CriarProcedimentoDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private LocalTime duracao;
    @NotNull
    @DecimalMin(value = "50.0", inclusive = true, message = "O valor deve ser maior ou igual a 50")
    private Double valor;
}