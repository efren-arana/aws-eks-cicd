package com.paymentchain.billing.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodesEnum {

    //00X Authentication errors

    /**
     * Code: 001
     * Description: Invalid JWT token format
     */
    INVALID_JWT_FORMAT("001", "Invalid JWT token format", HttpStatus.BAD_REQUEST),
    /**
     * Code: 002
     * Description: Invalid token type
     */
    INVALID_JWT_TYPE("002", "Invalid token type", HttpStatus.BAD_REQUEST),
    /**
     * Code: 003
     * Description: Token expired
     */
    INVALID_JWT_EXPIRED("003", "Token expired", HttpStatus.BAD_REQUEST),
    /**
     * Code: 004
     * Description: Token manipulated
     */
    INVALID_JWT_TAMPERED("004", "Token manipulated", HttpStatus.BAD_REQUEST),
    INVALID_SAML_SIGNATURE("005", "Invalid Signature", HttpStatus.UNAUTHORIZED),
    //ORCHESTRATOR BUSINESS ERROR
    /**
     * Code: 1001
     * Description: IDENTIFIER_ID not found
     */
    IDENTIFIER_ID_NOT_FOUND("1001", "Indetification Document number not found", HttpStatus.NOT_ACCEPTABLE),       
    
    /**
     * Code: 1002
     * Description: RSA response not valid total dose after recovery (Pauta completa)
     */
    EXTERNAL_SYSTEM_UNKNOWn_RESPONSE("1002", "External system unavailable or no responding", HttpStatus.SERVICE_UNAVAILABLE),    
   

    //GENERIC ERRORS 15xx

    /**
     * Code: 1500
     * Description: unknown error
     */
    GENERIC_UNKNOWN_ERROR("1500", "unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * Code: 1501
     * Description: Time out error
     */
    GENERIC_TIMEOUT_ERROR("1501", "Time out error", HttpStatus.GATEWAY_TIMEOUT),
    /**
     * Code: 1502
     * Description: Input Ouput Error
     */
    GENERIC_INPUT_OUTPUT_ERROR("1502", "Input Ouput Error", HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * Code: 1503
     * Description: Invalid request
     */
    GENERIC_VALIDATION_REQUEST_ERROR("1503", "Invalid request", HttpStatus.BAD_REQUEST),
    /**
     * Code: 1504
     * Description: Connection Error
     */
    GENERIC_CONNECTION_ERROR("1504", "Connection Error",HttpStatus.SERVICE_UNAVAILABLE),

    ;

    private final String code;

    private final String defaultMessage;

    private final HttpStatus defaultHttpStatus;


}
