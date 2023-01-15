package br.com.pauta.service.impl;

import br.com.pauta.dto.CpfResponseDTO;
import br.com.pauta.exceptions.RestTemplateResponseErrorHandler;
import br.com.pauta.service.ValidarCpf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidarCpfImpl implements ValidarCpf {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidarCpfImpl.class);
    @Override
    public boolean validar(String cpf) {
        try{
            String uri = "https://user-info.herokuapp.com/users/{cpf}";
            RestTemplate restTemplate = new RestTemplateBuilder()
                    .errorHandler(new RestTemplateResponseErrorHandler())
                    .build();

           CpfResponseDTO result = restTemplate.getForObject(uri, CpfResponseDTO.class, cpf);

            return result.getStatus().equals("ABLE_TO_VOTE") ? true : false;
        } catch (Exception e) {
            LOGGER.error("Erro na validação de cpf", e);
            throw e;
        }
    }
}
