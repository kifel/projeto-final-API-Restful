package br.org.serratec.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Object> handleEmailException(EmailException ex) {
        EmailException emailException = new EmailException(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(emailException);
    }

    @ExceptionHandler(NomeUsuarioException.class)
    public ResponseEntity<Object> handleNomeUsuarioException(NomeUsuarioException ex) {
        NomeUsuarioException nomeUsuarioException = new NomeUsuarioException(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(nomeUsuarioException);
    }

    @ExceptionHandler(CpfException.class)
    public ResponseEntity<Object> handleCpfException(CpfException ex) {
        CpfException cpfException = new CpfException(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(cpfException);
    }

    @ExceptionHandler(FindIdException.class)
    public ResponseEntity<Object> handleFindIdException(FindIdException ex) {
        FindIdException findIdException = new FindIdException(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(findIdException);
    }

    @ExceptionHandler(QtdEstoqueException.class)
    public ResponseEntity<Object> handleQtdEstoqueException(QtdEstoqueException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

    @ExceptionHandler(PedidoIdException.class)
    public ResponseEntity<Object> handlePedidoIdException(PedidoIdException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

    @ExceptionHandler(ProdutoIdException.class)
    public ResponseEntity<Object> handleProdutoIdException(ProdutoIdException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex) {
        return ResponseEntity.unprocessableEntity().body("Cep Inv??lido !");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.unprocessableEntity().body("Aquivo muito poderoso, m??ximo suportado 1 MegaByte !");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> DataIntegrityViolationException(DataIntegrityViolationException exc) {
        return ResponseEntity.unprocessableEntity()
                .body("Imposs??vel realizar essa op????o, viola????o de chave estrangeira ou viola????o no banco de dados !");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> NoSuchElementException(NoSuchElementException exc) {
        return ResponseEntity.unprocessableEntity().body("Id n??o encontrado ou null");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> erros = new ArrayList<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.add(erro.getField() + ":" + erro.getDefaultMessage());
        }

        ErroResposta erroResposta = new ErroResposta(status.value(),
                "Existem campos inv??lidos. Confira o preenchimento", LocalDateTime.now(), erros);

        return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErroResposta erroResposta = new ErroResposta(status.value(),
                "Existem campos inv??lidos. Confira o preenchimento", LocalDateTime.now(), null);

        return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
    }
}
