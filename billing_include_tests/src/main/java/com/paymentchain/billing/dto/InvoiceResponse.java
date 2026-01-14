/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.billing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
 * @author earana
 */
@Schema(name = "InvoiceResponse", description = "Model represent a invoice on database")
@Data
public class InvoiceResponse {
    
       @Schema(name = "invoiceId", requiredMode = Schema.RequiredMode.REQUIRED,example = "2", defaultValue = "1", description = "Unique Id of iinvoice  on database")
       private long invoiceId;
         @Schema(name = "customer", requiredMode = Schema.RequiredMode.REQUIRED,example = "2", defaultValue = "1", description = "Unique Id of customer that represent the owner of invoice")
   private long customer;
       @Schema(name = "number", requiredMode = Schema.RequiredMode.REQUIRED,example = "3", defaultValue = "8", description = "Number given on fisical invoice")
   private String number;
   private String detail;
   private double amount;  
    
}
