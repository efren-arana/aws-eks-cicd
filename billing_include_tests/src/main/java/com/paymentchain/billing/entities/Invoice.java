/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 *
 * @author earana
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private long id;
   
   private long customerId;

   private String number;
   private String detail;
   private double amount;  
}
