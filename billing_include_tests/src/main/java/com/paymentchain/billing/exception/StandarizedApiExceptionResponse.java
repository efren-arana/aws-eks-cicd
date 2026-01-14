/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paymentchain.billing.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author earana
 * The effort to standardize rest API error reports  is support by ITEF
 * (Internet Engineering Task Force, open standard organization  that which develop and promotes voluntary internet standards)
 * in RFC 7807 which created a generalized error-handling schema composed by five parts.
 * 1- type — A URI identifier that categorizes the error
 * 2-title — A brief, human-readable message about the error
 * 3-code —  The unique error code
 * 4-detail — A human-readable explanation of the error
 * 5-instance — A URI that identifies the specific occurrence of the error
 * Standarized is optional but have advantage, it is use for facebook and twitter ie
 * https://graph.facebook.com/oauth/access_token?
 * https://api.twitter.com/1.1/statuses/update.json?include_entities=true
 */
@Schema(description = "This model is used to return errors in RFC 7807 which created a generalized error-handling schema composed by five parts")
@NoArgsConstructor
@Data
public class StandarizedApiExceptionResponse {
	@Schema(description = "The unique uri identifier that categorizes the error", name = "type",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-authorized")
    private String type;


	@Schema(description ="A brief, human-readable message about the error", name = "title",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "The user does not have autorization")
    private String title;

	@Schema(description ="The unique error code", name = "code",
            required = false, example = "192")
    private String code;

	@Schema(description = "A human-readable explanation of the error", name = "detail",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "The user does not have the propertly persmissions to acces the "
            + "resource, please contact with us efren-f@hotmail.com")
    private String detail;

	@Schema(description = "A URI that identifies the specific occurrence of the error", name = "detail",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-authorized/01")
    private String instance;

    public StandarizedApiExceptionResponse(String type, String title, String code, String detail) {
        super();
        this.title = title;
        this.code = code;
        this.detail = detail;
        this.type = type;
    }
}
