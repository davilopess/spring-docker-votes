package br.com.pauta.document;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
public class Voto {

    private String voto;
    private String associadoCpf;

}

