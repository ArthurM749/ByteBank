package com.bytebank.models;

public record TransacaoDTO(
        String tipo,
        double valor,
        String dataHora
) {
}
