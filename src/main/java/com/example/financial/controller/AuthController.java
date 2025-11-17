package com.example.financial.controller;



import com.example.financial.dto.LoginDTO;
import com.example.financial.dto.PasswordDTO;
import com.example.financial.dto.RecoveryJwtTokenDTO;
import com.example.financial.dto.UserDTO;
import com.example.financial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> login(@RequestBody LoginDTO loginDTO){
        RecoveryJwtTokenDTO token = userService.authenticateUser(loginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/login-customer")
    public ResponseEntity<RecoveryJwtTokenDTO> loginCustomer(@RequestBody LoginDTO loginDTO){
        RecoveryJwtTokenDTO token = userService.authenticateUserCustomer(loginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/create-customer")
    public ResponseEntity<?> createUserCustomer(@RequestBody UserDTO userDTO) {
        userService.createUserCustomer(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<RecoveryJwtTokenDTO> updateUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.updateUser(userDTO),HttpStatus.CREATED);
    }

    @PutMapping("/update-password")
    public ResponseEntity<RecoveryJwtTokenDTO> updatePassword(@RequestBody PasswordDTO passwordDTO){
        return new ResponseEntity<>(userService.updatePassword(passwordDTO),HttpStatus.CREATED);
    }

    @GetMapping("/get-user")
    public ResponseEntity<String> getUser(@RequestParam String email){
        return new ResponseEntity<>("Novinho é o cara", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest(){
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest(){
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }
}
