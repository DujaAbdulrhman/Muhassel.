package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.DTO.TaxPayerDTO;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.TaxPayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/tax-payer")
@RequiredArgsConstructor
public class TaxPayerController {

    private final TaxPayerService taxPayerService;



    @GetMapping("/get-all-tax-payers")
    public ResponseEntity getAllTaxTaxPayers (@AuthenticationPrincipal MyUser audit){
        return ResponseEntity.status(200).body(taxPayerService.getAllTaxTaxPayers(audit.getId()));
    }

    // authority -> permitAll
    @PostMapping("/tax-payer-register")
    public ResponseEntity register (@RequestBody @Valid TaxPayerDTO taxPayerDTO ){
        taxPayerService.register(taxPayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer is registered successfully "));
    }

    // authority -> TaxPayer
    @PutMapping("/update")
    public ResponseEntity updateTaxPayer(@AuthenticationPrincipal MyUser taxPayer , @Valid@RequestBody TaxPayerDTO taxPayerDTO ){
        taxPayerService.updateTaxPayer(taxPayer.getId(), taxPayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer information has been updated successfully "));
    }

    // authority -> TaxPayer
    @DeleteMapping("/delete/")
    public ResponseEntity deleteTaxPayer(@AuthenticationPrincipal MyUser taxPayer){
        taxPayerService.deleteTaxPayer(taxPayer.getId());
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer has been deleted successfully "));
    }


    /// 13
    // authority -> TaxPayer
    //sahar - 1
    @PostMapping("/add-accountant/{businessId}") //last edit
    public ResponseEntity addAccountant (@RequestBody @Valid AccountantDTO accountantDTO, @AuthenticationPrincipal MyUser taxPayer,@PathVariable Integer businessId){
        taxPayerService.addAccountant(taxPayer.getId(),businessId,accountantDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the accountant is added successfully "));
    }


    // Endpoint 40
    // authority -> TaxPayer
    @PutMapping("/activate-accountant/{accountantId}")
    public ResponseEntity activateAccountant(@AuthenticationPrincipal MyUser taxPayer, @PathVariable Integer accountantId){
          taxPayerService.activateAccountant(taxPayer.getId(), accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("accountant is active"));
    }


    // Endpoint 41
    // authority -> TaxPayer
    @PutMapping("/de-activate-accountant/{accountantId}")
    public ResponseEntity deActivateAccountant(@AuthenticationPrincipal MyUser taxPayer, @PathVariable Integer accountantId){
        taxPayerService.deActivateAccountant(taxPayer.getId(), accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("accountant is nonActive"));
    }

    //DUJA-8
    // Displays all accountants associated with the taxpayer across all branches affiliated with him
    @GetMapping("/taxpayers/accountants")
    public ResponseEntity<List<Map<String, Object>>> getAccountantsByTaxPayerId(@PathVariable Integer taxPayerId) {
        return ResponseEntity.status(200).body(taxPayerService.getAccountantsByTaxPayerId(taxPayerId));
    }

    // authority -> TaxPayer
    //sahar - 2

    @PutMapping("block-inactive-accountant/{accountantId}")
    public ResponseEntity blockInnActiveAccountant(@AuthenticationPrincipal MyUser taxPayer, @PathVariable Integer accountantId ){
        taxPayerService.blockUnnActiveAccountant(taxPayer.getId(),accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("the  accountant has been inactivated"));
    }


}
