package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.ItemsDTO;
import com.example.final_project.DTO.SaleDTO;
import com.example.final_project.DTO.SaleRequestDTO;
import com.example.final_project.DTO.ProductDTO;
import com.example.final_project.Model.ItemSale;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Service.ItemSaleService;
import com.example.final_project.Service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final ItemSaleService itemSaleService;

    @GetMapping("/get")
    public ResponseEntity getAllSales(){
        return ResponseEntity.status(HttpStatus.OK).body(salesService.getAllSales());
    }

    // authority -> Accountant
    @PutMapping("/update/{id}")
    public ResponseEntity updateSales(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id, @Valid @RequestBody  Sales sales){
        salesService.updateSales(myUser.getId(),id, sales);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales  is updated"));
    }

    // authority -> Accountant
    @DeleteMapping("delete/{saleId}")
    public ResponseEntity deleteSales(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer saleId){
        salesService.deleteSales(myUser.getId(),saleId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales is deleted"));
    }




   /* @GetMapping("/sales-summary/{branchId}")
    public ResponseEntity<Map<String, Double>> getSalesByBranch(@AuthenticationPrincipal MyUser accountant) {
       return ResponseEntity.status(200).body(salesService.getSalesSummaryByBranch(accountant.getId()));
    }*/

    // authority -> Accountant
    //sahar - 3
    @PostMapping("add-sale/{boxId}")
    public ResponseEntity addSales(@AuthenticationPrincipal MyUser accountant, @PathVariable Integer boxId, @RequestBody @Valid SaleDTO saleDTO ) {
        salesService.addSales(accountant.getId(), boxId,saleDTO);
        return ResponseEntity.status(200).body("Sale made successfully");
    }


    // authority -> Accountant
    /// Integer accountantId, Integer saleId, ProductDTO productDTO
    @PutMapping("/add-product-in-sale/{saleId}")
    public ResponseEntity addProductInSale(@AuthenticationPrincipal MyUser accountant, @PathVariable Integer saleId, @Valid @RequestBody ItemsDTO itemsDTO){
        salesService.addProductInSale(accountant.getId(), saleId,itemsDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(" Product is added to invoice successfully"));
    }

    /// ?????????????????????????????????
    @GetMapping("/by-taxpayer/{taxPayerId}")
    public ResponseEntity getSalesByTaxPayerId(@AuthenticationPrincipal MyUser taxPayer) {
        List<Sales> sales = salesService.getSalesByTaxPayerId(taxPayer.getId());
        return ResponseEntity.status(200).body(sales);
    }

    // authority -> Accountant
    //sahar - 4
    @PutMapping("/confirm-sale/{saleId}")
    public ResponseEntity confirmSale(@AuthenticationPrincipal MyUser accountant, @PathVariable Integer saleId){
        salesService.confirmSale(accountant.getId(), saleId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(" The sales confirmed successfully"));
    }


    // authority -> Accountant
    //sahar - 5
    @GetMapping("/print-sale/{saleId}")
    public ResponseEntity<byte[]> printInvoice(@AuthenticationPrincipal MyUser accountant, @PathVariable Integer saleId) {
        byte[] pdf = salesService.printInvoice(accountant.getId(),saleId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tax-report-" + saleId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    /// ?????????????????????????????????
//    @PutMappingapping("/update-product-quantity/{accountantId}/{itemSaleId}/{quantity}")
//    public ResponseEntity updateProductQuantity(@PathVariable Integer accountantId, @PathVariable Integer itemid, @PathVariable Integer quantity) {
//
//        ItemSale updatedItemSale = salesService.updateProductQuantity(accountantId, itemid, quantity);
//        return ResponseEntity.status(200).body(updatedItemSale);
//    }
//
//    @GetMapping("/print-sale/{accountantId}/{saleId}")
//    public ResponseEntity<byte[]> printInvoice(@PathVariable Integer accountantId,@PathVariable Integer saleId) {
//        byte[] pdf = salesService.printInvoice(accountantId,saleId);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tax-report-" + saleId + ".pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdf);
//    }

    //DUJA-7
    @PutMapping("/update-quantity/{saleId}/{itemId}/{quantity}")
    public ResponseEntity updateItemQuantity(@AuthenticationPrincipal MyUser accountant,@PathVariable Integer saleId, @PathVariable Integer itemId, @PathVariable Integer quantity) {
        salesService.updateProductQuantity(accountant.getId(), saleId, itemId, quantity);
        return ResponseEntity.status(200).body(new ApiResponse("Quantity updated successfully"));
    }


    @PutMapping("/refund-sale/{saleId}")
    public ResponseEntity refundSale(@AuthenticationPrincipal MyUser accountant,@PathVariable Integer saleId) {
        salesService.refundSale(accountant.getId(), saleId);
        return ResponseEntity.status(200).body(new ApiResponse("Sale refunded"));
    }



}
