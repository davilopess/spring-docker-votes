package br.com.pauta.service.impl;

import br.com.pauta.document.Pauta;
import br.com.pauta.document.Voto;
import br.com.pauta.dto.ResultadoDTO;
import br.com.pauta.dto.SessaoDTO;
import br.com.pauta.dto.VotoDTO;
import br.com.pauta.exceptions.CpfDuplicadoException;
import br.com.pauta.exceptions.RequiredObjectIsNullException;
import br.com.pauta.exceptions.ResourceNotFoundException;
import br.com.pauta.repository.PautaRepository;
import br.com.pauta.schedule.PautaSchedule;
import br.com.pauta.service.SessaoService;
import br.com.pauta.service.ValidarCpf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SessaoServiceImpl implements SessaoService {

    @Autowired
    PautaRepository pautaRepository;

    @Autowired
    ValidarCpf validarCpf;

    private static final Logger logger = LoggerFactory.getLogger(SessaoServiceImpl.class);
    @Override
    public Pauta openSession(SessaoDTO sessaoDTO) {
        Pauta pauta = pautaRepository.findById(sessaoDTO.getPautaId())
                .orElseThrow(() -> new ResourceNotFoundException("Pauta nao informada para esse ID!"));

        if(pauta.getSessionOpen() == false){
            pauta.setMinutes(sessaoDTO.getMinutes() != null ? sessaoDTO.getMinutes() : pauta.getMinutes());
            pauta.setTimeToClose(Instant.now().plusSeconds(pauta.getMinutes() * 60));
            pauta.setSessionOpen(true);
        }

        logger.info("Abrindo sessão de votação.");

        return pautaRepository.save(pauta);
    }

    @Override
    public Voto addVoteToSession(VotoDTO votoDTO){
        Pauta pauta = pautaRepository.findById(votoDTO.getPautaId())
                .orElseThrow(() -> new ResourceNotFoundException("Pauta nao informada para esse ID!"));

        validateVoteInSession(pauta, votoDTO.getAssociadoCpf());
        Voto voto = new Voto();
        voto.setVoto(votoDTO.getVoto());
        voto.setAssociadoCpf(votoDTO.getAssociadoCpf());
        pauta.addVoto(voto);

        pautaRepository.save(pauta);

        logger.info("Adicionado voto a sessão.");
        return voto;
    }

    @Override
    public List<Pauta> checkPautasWithOpenSessions(){
        return pautaRepository.findBySessionOpenTrueAndSessionClosedFalse();
    }

    @Override
    public Pauta closeSession(Pauta pauta) {
        pauta.setSessionClosed(true);
        return pautaRepository.save(pauta);
    }

    public boolean validateVoteInSession(Pauta pauta, String cpf){
        if (pauta.sessionExpired()) throw new ResourceNotFoundException("Sessão expirada.");
        if (pauta.cpfAlreadyVoted(cpf)) throw new CpfDuplicadoException("Voto já foi realizado por esse associado.");
        if(!validarCpf.validar(cpf)) throw new RequiredObjectIsNullException("Associado não pode realizar o voto.");
        return true;
    }

    public ResultadoDTO resultOfSession(Pauta pauta){
        ResultadoDTO resultado = ResultadoDTO.builder().build();

         pauta.getVotos().forEach(voto -> {
             if (voto.getVoto().equals("Sim")) {
                 resultado.setVotoSim(resultado.getVotoSim() + 1);
             } else if(voto.getVoto().equals("Não")){
                 resultado.setVotoNao(resultado.getVotoNao() + 1);
             }
         });
        return resultado;
    }

}
