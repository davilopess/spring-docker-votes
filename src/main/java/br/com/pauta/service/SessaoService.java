package br.com.pauta.service;

import br.com.pauta.dto.SessaoDTO;
import br.com.pauta.dto.VotoDTO;
import br.com.pauta.document.Pauta;
import br.com.pauta.document.Voto;
import br.com.pauta.dto.ResultadoDTO;

import java.util.List;

public interface SessaoService {

    Pauta openSession(SessaoDTO sessaoDTO);

    Voto addVoteToSession(VotoDTO votoDTO);

    List<Pauta> checkPautasWithOpenSessions();

    Pauta closeSession(Pauta pauta);

    ResultadoDTO resultOfSession(Pauta pauta);

}
