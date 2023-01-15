package br.com.pauta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CpfUnableException  extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CpfUnableException(String ex) {
        super(ex);
    }
}