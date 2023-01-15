package br.com.pauta.mapper;

import br.com.pauta.document.Pauta;
import br.com.pauta.dto.PautaDTO;

public class PautaMapper {
    public static Pauta toEntity(PautaDTO pautaDTO) {
        return Pauta.builder()
                .id(pautaDTO.getId())
                .name(pautaDTO.getName())
                .sessionOpen(pautaDTO.getSessionOpen())
                .sessionClosed(pautaDTO.getSessionClosed())
                .minutes(pautaDTO.getMinutes())
                .timeToClose(pautaDTO.getTimeToClose())
                .votos(pautaDTO.getVotos())
                .build();
    }

    public static PautaDTO toDto(Pauta pauta) {
        return PautaDTO.builder()
                .id(pauta.getId())
                .name(pauta.getName())
                .sessionOpen(pauta.getSessionOpen())
                .sessionClosed(pauta.getSessionClosed())
                .minutes(pauta.getMinutes())
                .timeToClose(pauta.getTimeToClose())
                .votos(pauta.getVotos())
                .build();
    }
}
