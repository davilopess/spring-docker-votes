package br.com.pauta.controller;

import br.com.pauta.document.Pauta;
import br.com.pauta.document.Voto;
import br.com.pauta.dto.PautaDTO;
import br.com.pauta.dto.SessaoDTO;
import br.com.pauta.dto.VotoDTO;
import br.com.pauta.service.PautaService;
import br.com.pauta.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pauta")
@Tag(name = "Pauta", description = "Endpoints para controle de pautas")
public class PautaController {

    @Autowired
    PautaService service;

    @Autowired
    SessaoService sessaoService;

    @GetMapping
    @Operation(summary = "Listar todas as pautas", description = "Listar pautas",
            tags = {"Pauta"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PautaDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public List<PautaDTO> getPautas(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar uma pauta por id", description = "Buscar pauta",
            tags = {"Pauta"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = PautaDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public PautaDTO getPautaId(@PathVariable String id){
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Salvar uma pauta", description = "Salvar Pauta",
            tags = {"Pauta"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = PautaDTO.class)
                                    )
                            })
            }
    )
    public PautaDTO savePauta(@RequestBody PautaDTO pautaDTO){
        return service.save(pautaDTO);
    }

    @PutMapping(value = "/sessao")
    @Operation(summary = "Abrir sessão", description = "Salvar Pauta",
            tags = {"Pauta"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = Pauta.class)
                                    )
                            })
            }
    )
    public Pauta openSession(@RequestBody SessaoDTO sessaoDTO) {
        return sessaoService.openSession(sessaoDTO);
    }

    @PutMapping(value = "/sessao/voto")
    @Operation(summary = "Adicionar um voto em uma sessão", description = "Adicionar voto",
            tags = {"Pauta"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = Voto.class)
                                    )
                            })
            }
    )
    public Voto addVoteToSession(@RequestBody VotoDTO votoDTO) {
        return sessaoService.addVoteToSession(votoDTO);
    }
}
