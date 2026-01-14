package com.paymentchain.billing.exception;



import com.paymentchain.billing.enums.ErrorCodesEnum;
import com.paymentchain.billing.enums.ExceptionType;
import lombok.extern.slf4j.Slf4j;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * @author earana
 * Standard http communication have five levels of response codes
 * 100-level (Informational) — Server acknowledges a request, it mean that request was received and understood, it is transient response , alert client for awaiting response
 * 200-level (Success) — Server completed the request as expected
 * 300-level (Redirection) — Client needs to perform further actions to complete the request
 * 400-level (Client error) — Client sent an invalid request
 * 500-level (Server error) — Server failed to fulfill a valid request due to an error with server
 * <p>
 * The goal of handler exception is provide to customer with appropriate code and
 * additional comprehensible information to help troubleshoot the problem.
 * The message portion of the body should be present as user interface, event if
 * customer send an Accept-Language header (en or french ie) we should translate the title part
 * to customer language if we support internationalization, detail is intended for developer
 * of clients, so the translation is not necessary. If more than one error is need to report , we can
 * response a list of errors.
 */
@Slf4j
//This annotation is a Specialization of @Component for classes that declare @ExceptionHandler, @InitBinder, or @ModelAttribute methods to be shared across multiple @Controller classes
//Indicate that this class attends a controller class and can have a body in response
@RestControllerAdvice
public class ApiExceptionHandler {

    //Allow define a method for handler this particular exception in crosswise, as a global exception handler
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleBusinessRuleExceptions(BusinessRuleException ex) {
        log.error(ex.getMessage());
        var response = new StandarizedApiExceptionResponse(ExceptionType.BUSINESS_RULE.value(), ex.getHttpStatus().toString(), ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleBusinessRuleExceptions(TechnicalException ex) {
        log.error(ex.getMessage(), ex);
        var response = new StandarizedApiExceptionResponse(ExceptionType.TECHNICAL.value(), ex.getHttpStatus().toString(), ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleNoContentException(IOException ex) {
        log.error("Input Ouput Error", ex);
        var response = new StandarizedApiExceptionResponse(ExceptionType.TECHNICAL.value(), ErrorCodesEnum.GENERIC_INPUT_OUTPUT_ERROR.getDefaultMessage(), ErrorCodesEnum.GENERIC_INPUT_OUTPUT_ERROR.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleNoContentException(TimeoutException ex) {
        log.error("Time out error", ex);
        var response = new StandarizedApiExceptionResponse(ExceptionType.TECHNICAL.value(), ErrorCodesEnum.GENERIC_TIMEOUT_ERROR.getDefaultMessage(), ErrorCodesEnum.GENERIC_TIMEOUT_ERROR.getCode(), getDetail(ex));
        return new ResponseEntity<>(response, HttpStatus.GATEWAY_TIMEOUT);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleExceptions(Exception ex) {
        log.error("Unknown error", ex);
        var response = new StandarizedApiExceptionResponse(ExceptionType.TECHNICAL.value(), ErrorCodesEnum.GENERIC_UNKNOWN_ERROR.getDefaultMessage(), ErrorCodesEnum.GENERIC_UNKNOWN_ERROR.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, ErrorCodesEnum.GENERIC_UNKNOWN_ERROR.getDefaultHttpStatus());
    }

    private String getDetail(Exception ex) {
        return Optional.ofNullable(ex.getMessage()).orElse(ex.toString());
    }
}
