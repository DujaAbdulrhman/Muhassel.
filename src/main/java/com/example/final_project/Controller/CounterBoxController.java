package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.CounterBoxDTO;
import com.example.final_project.Model.CounterBox;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.CounterBoxService;
import com.example.final_project.Service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/counterbox")
public class CounterBoxController {


    private final CounterBoxService counterBoxService;
    private final SalesService salesService;

    //DUJA-4
    @PostMapping("/create")
    public ResponseEntity createCounterBox(@AuthenticationPrincipal MyUser account, @RequestBody @Valid CounterBoxDTO counterBoxDTO) {
        counterBoxService.createCounterBox(account.getId(),counterBoxDTO);
        return ResponseEntity.status(200).body("Counter box created successfully");
    }

    //DUJA
    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(counterBoxService.getAllCounterBoxes());
    }


    @GetMapping("/get")
    public ResponseEntity getById(@AuthenticationPrincipal MyUser account){
        return ResponseEntity.status(200).body(counterBoxService.getCounterBox(account.getId()));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid CounterBox counterBox,@AuthenticationPrincipal MyUser account){
        counterBoxService.updateCounterBox(counterBox, account.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@AuthenticationPrincipal MyUser account){
        counterBoxService.deleteCounterBox(account.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Deleted successfully"));
    }

    //--------------------------------------------






    //DUJA-5
    //open  the counter box
    /// Auth->Accountant
    @PatchMapping("/open/{boxId}")
    public ResponseEntity openCounterBox(@AuthenticationPrincipal MyUser Accountant, @PathVariable Integer boxId) {
        counterBoxService.openCounterBox(boxId,Accountant.getId());
        return ResponseEntity.status(200).body("CounterBox opened successfully");
    }

     //DUJA-6
    //close the counter box
    /// Auth-> Accountant
    @PostMapping("/close/{boxId}")
    public ResponseEntity<String> closeCounterBox(@AuthenticationPrincipal MyUser Accountant, @PathVariable Integer boxId) {
        String result = counterBoxService.closeCounterBox(boxId,Accountant.getId());
        return ResponseEntity.status(200).body(result);
    }


    // Endpoint 7
    // Auto
//    @Scheduled
    @PutMapping("/close-opened-counter-box")
    public ResponseEntity closeCounterBoxAuto(){
        counterBoxService.closeCounterBoxAuto();
        return ResponseEntity.status(200).body(new ApiResponse("counter Box is closed"));
    }

}
