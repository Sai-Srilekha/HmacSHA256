package com.signature.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.signature.calculator.dto.RequestDTO;
import com.signature.calculator.dto.ResponseDTO;
import com.signature.calculator.model.Request;
import com.signature.calculator.model.Response;
import com.signature.calculator.utils.SignatureGenerator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CalculatorService {

    private ModelMapper modelMapper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private SignatureGenerator signatureGenerator;
    public Response addNumbers(Request request,String sign ){
        boolean isValid = signatureGenerator.isSignatureValid(request,sign);
        if (isValid){
            RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
            int result = request.getNum1()+ request.getNum2();
            ResponseDTO responseDTO = new ResponseDTO(result);
            return modelMapper.map(responseDTO, Response.class);
        }
        else{
            System.out.println("Signature invalid");
            return new Response();
        }
    }

}
