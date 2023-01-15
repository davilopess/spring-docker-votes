package br.com.pauta.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoDTO {

    @Builder.Default
    private Integer votoSim = 0;

    @Builder.Default
    private Integer votoNao = 0;

    public Integer total(){
        return this.votoSim + this.votoNao;
    }
}
