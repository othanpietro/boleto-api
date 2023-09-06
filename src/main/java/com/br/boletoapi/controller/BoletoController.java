package com.br.boletoapi.controller;

import com.br.boletoapi.model.BoletoDTO;
import com.br.boletoapi.model.PagamentoDTO;
import com.br.boletoapi.model.entidades.Boleto;
import com.br.boletoapi.service.BoletoService;
import com.br.boletoapi.service.PagamentosService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/boleto")
@Validated
public class BoletoController {

    private final BoletoService boletoService;
    private final PagamentosService pagamentosService;


    public BoletoController(BoletoService boletoService, PagamentosService pagamentosService) {
        this.boletoService = boletoService;
        this.pagamentosService = pagamentosService;
    }

    @ApiOperation(httpMethod = "POST", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Emite um boleto.")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Emite boleto"),
            @ApiResponse(code = 500, message = "Não foi possível cadastrar boleto")})
    @PostMapping()
    public ResponseEntity<BoletoDTO> postEmiteBoleto(@RequestBody @Valid BoletoDTO boletoDTO){
        return ResponseEntity.ok(boletoService.emiteBoleto(boletoDTO));
    }

    @ApiOperation(httpMethod = "POST", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Gera pagamento de um boleto.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Boleto pago com sucesso. "),
            @ApiResponse(code = 500, message = "Não foi pagar boleto. ")})
    @PostMapping("/pagar")
    public ResponseEntity<BoletoDTO> postPagaBoleto(@RequestBody @Valid BoletoDTO boletoDTO){
        return ResponseEntity.ok(boletoService.pagaBoleto(boletoDTO));
    }

    @ApiOperation(httpMethod = "POST", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Gera pagamento de um boleto.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Boleto pago com sucesso. "),
            @ApiResponse(code = 500, message = "Não foi pagar boleto. ")})
    @PostMapping("/associado/pagar")
    public ResponseEntity<BoletoDTO> postPagaBoletoPorAssociado(@RequestBody @Valid PagamentoDTO pagamentoDTO){
        return ResponseEntity.ok(boletoService.pagaBoletoPorAssociado(pagamentoDTO));
    }


    @GetMapping("/associado/{uuid}")
    @ApiOperation(httpMethod = "GET", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Retona boletos de um associado pelo uuid")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação feita com sucesso."),
            @ApiResponse(code = 500, message = "Não foi possível carregar boletos.")})
    public ResponseEntity<List<Boleto>> getBoletosAssociado(@PathVariable String uuid){
        return ResponseEntity.ok(boletoService.buscaBoletosPorUuid(uuid));
    }

    @PostMapping("/pagamentos")
    @ApiOperation(httpMethod = "POST", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Cria uma carga para um usuário.")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Carga criada com sucesso."),
            @ApiResponse(code = 500, message = "Não foi possível criar uma carga.")})
    public ResponseEntity<Object> postCarga(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(pagamentosService.arquivoPagamentos(file));
    }


}
