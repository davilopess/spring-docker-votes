package br.com.pauta.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class SessaoDTO {

    @NonNull
    private String pautaId;

    @Builder.Default
    private Integer minutes = 1;

}
