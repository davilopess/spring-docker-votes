package br.com.pauta.repository;

import br.com.pauta.document.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaRepository  extends MongoRepository<Pauta, String> {

    public List<Pauta> findBySessionOpenTrueAndSessionClosedFalse();
}
