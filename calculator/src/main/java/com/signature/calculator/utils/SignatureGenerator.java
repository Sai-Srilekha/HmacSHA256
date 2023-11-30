package com.signature.calculator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.signature.calculator.model.Request;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class SignatureGenerator {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public String generateSignature(byte[] data,String secretKey){
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(),"HmacSHA256");
            sha256Hmac.init(secretKeySpec);

            byte[] hash = sha256Hmac.doFinal(data);
            return Base64.getEncoder().encodeToString(hash);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return "Hello";
    }

    public boolean isSignatureValid(Request request,String sign){
        String signature = getSignature(request);
        System.out.println("Sign:"+sign);
        System.out.println("Signature:"+signature);
        return sign != null && sign.equals(signature);
    }

    public String getSignature(Request request){
        Gson gson = new Gson();
        String json = gson.toJson(request);
        byte[] data = json.getBytes();
        return generateSignature(data,"hello");
    }

}
