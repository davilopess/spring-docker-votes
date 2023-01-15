package br.com.pauta.schedule;

import br.com.pauta.document.Pauta;
import br.com.pauta.dto.ResultadoDTO;
import br.com.pauta.service.SessaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class PautaSchedule {

    private static final Logger logger = LoggerFactory.getLogger(PautaSchedule.class);

    @Autowired
    SessaoService sessaoService;

    @Scheduled(fixedDelay = 1000)
    public void closeSessionVerify(){
        List<Pauta> pautasOpen = sessaoService.checkPautasWithOpenSessions();

        pautasOpen.stream().filter(Pauta::sessionExpired).forEach(p -> {
            sessaoService.closeSession(p);
            p.setTimeToClose(null);
            logger.info("Sessão de votação da pauta " + p.getName() + " encerrada.");

            ResultadoDTO resultado = sessaoService.resultOfSession(p);
            String resultTemplate = "Com " + resultado.total() + " votos, o resultado da pauta "
                    + p.getName() + " foi contabilizado em: " + resultado.getVotoSim()+ " (Sim) e " + resultado.getVotoNao() + " (Não).";
            logger.info(resultTemplate);
        });
    }
}
