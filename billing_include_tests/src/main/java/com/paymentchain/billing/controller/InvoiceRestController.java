/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.controller;

import com.paymentchain.billing.common.InvoiceRequestMapper;
import com.paymentchain.billing.common.InvoiceResponseMapper;
import com.paymentchain.billing.dto.InvoiceRequest;
import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import com.paymentchain.billing.exception.BusinessRuleException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.paymentchain.billing.respository.InvoiceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author earana
 */
@Tag(name = "Billing API", description = "This APi serve all functionality for management Invoices")
@RestController
@RequestMapping("/billing/v1")
public class InvoiceRestController {

    @Autowired
    InvoiceRepository billingRepository;

    @Autowired
    InvoiceRequestMapper irm;

    @Autowired
    InvoiceResponseMapper irspm;

    @Operation(description = "Return all invoices bundled into Response", summary = "Return 204 if no data found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exito"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping()
    public List<InvoiceResponse> list() throws BusinessRuleException {
        List<Invoice> findAll = billingRepository.findAll();
        Supplier<BusinessRuleException> exceptionSupplier = () -> new BusinessRuleException("NO_FOUND", "The are no elements", HttpStatus.NOT_FOUND);
        return Optional.ofNullable(findAll)
                .filter(list -> !list.isEmpty())
                .map(irspm::InvoiceListToInvoiceResposeList)
                .orElseThrow(exceptionSupplier);
    }

    @GetMapping("/{id}")
    public InvoiceResponse get(@PathVariable String id) throws BusinessRuleException {
        Optional<Invoice> findById = billingRepository.findById(Long.valueOf(id));
        return findById.map(irspm::InvoiceToInvoiceRespose)
                .orElseThrow(() -> new BusinessRuleException("NO_FOUND", "The are no elements", HttpStatus.NOT_FOUND));
    }

    @Operation(description = "Return all invoices Where request Param is greather or equals", summary = "Return 204 if no data found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exito"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/amount")
    public List<InvoiceResponse> list(@RequestParam("amount") int amount) throws BusinessRuleException {
        List<Invoice> findAll = billingRepository.findAll().stream().filter( x-> x.getAmount()>= amount).toList();
        Supplier<BusinessRuleException> exceptionSupplier = () -> new BusinessRuleException("NO_FOUND", "The are no elements", HttpStatus.NOT_FOUND);
        return Optional.ofNullable(findAll)
                .filter(list -> !list.isEmpty())
                .map(irspm::InvoiceListToInvoiceResposeList)
                .orElseThrow(exceptionSupplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody InvoiceRequest input) throws BusinessRuleException {
        Optional<Invoice> dtoOptional = billingRepository.findById(Long.valueOf(id));
        if (dtoOptional.isPresent()) {
            Invoice dtoTransformed = irm.InvoiceRequestToInvoice(input);
            dtoTransformed.setId(dtoOptional.get().getId());
            var dto = billingRepository.save(dtoTransformed);
            return ResponseEntity.ok(irspm.InvoiceToInvoiceRespose(dto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody InvoiceRequest input) {
        Invoice InvoiceRequestToInvoice = irm.InvoiceRequestToInvoice(input);
        Invoice save = billingRepository.save(InvoiceRequestToInvoice);
        InvoiceResponse dto = irspm.InvoiceToInvoiceRespose(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws BusinessRuleException {
        Optional<Invoice> dto = billingRepository.findById(Long.valueOf(id));
        if (!dto.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        billingRepository.delete(dto.get());
        return ResponseEntity.ok().build();
    }

}
