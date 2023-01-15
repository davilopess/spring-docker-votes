package br.com.pauta.service;


import br.com.pauta.dto.PautaDTO;

import java.util.List;

public interface PautaService{
    List<PautaDTO> findAll();

    PautaDTO findById(String id);

    PautaDTO save(PautaDTO pautaDTO);
}
