package com.signature.calculator.controller;

import com.signature.calculator.model.Request;
import com.signature.calculator.model.Response;
import com.signature.calculator.service.CalculatorService;
import com.signature.calculator.utils.SignatureGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculator")
@AllArgsConstructor
public class CalcController {

    private CalculatorService calculatorService;

    private SignatureGenerator signatureGenerator;
    @PostMapping
    public ResponseEntity<?> getSum(@RequestHeader("signature") String signature,
                                    @RequestBody Request request){
       Response result = calculatorService.addNumbers(request,signature);
       return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getSignature(@RequestBody Request request){
        String signature = signatureGenerator.getSignature(request);
        return new ResponseEntity<>(signature,HttpStatus.OK);
    }

}
