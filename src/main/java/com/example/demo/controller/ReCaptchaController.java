package com.example.demo.controller;

import java.io.IOException;

import com.example.demo.domain.VerifyRecaptcha;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReCaptchaController {
    @ResponseBody
    @PostMapping(value = "VerifyRecaptcha")
    public ResponseEntity<?> VerifyRecaptcha(String recaptcha) throws IOException {
        VerifyRecaptcha.setSecretKey("키값");
        return ResponseEntity.status(HttpStatus.OK).body(VerifyRecaptcha.verify(recaptcha));
    }

}
