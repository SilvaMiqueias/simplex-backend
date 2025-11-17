package com.example.financial.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/test")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test(){
      return   new ResponseEntity<>("Testando point auth", HttpStatus.OK);
    }
}
