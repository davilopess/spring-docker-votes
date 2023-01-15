package br.com.pauta.document;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Jacksonized
@Document
@Data
@Builder
public class Pauta {

    @Id
    private String id;
    private String name;
    private Boolean sessionClosed;
    private Boolean sessionOpen;

    @Builder.Default
    private Integer minutes = 1;
    private Instant timeToClose;
    @Builder.Default
    List<Voto> votos = new ArrayList<>();

    public void addVoto(Voto voto){
        votos.add(voto);
    }
    public boolean sessionExpired(){
        return this.timeToClose.isBefore(Instant.now()) && !this.sessionClosed;
    }
    public boolean cpfAlreadyVoted(String cpf){
        return this.votos.stream().anyMatch(voto -> voto.getAssociadoCpf().equals(cpf));
    }
}
