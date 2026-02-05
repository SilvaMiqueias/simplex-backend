package com.example.financial.controller;



import com.example.financial.dto.*;
import com.example.financial.model.User;
import com.example.financial.security.JwtTokenService;
import com.example.financial.security.MFAService;
import com.example.financial.security.UserDetailsImp;
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

    @Autowired
    private MFAService mfaService;

    @Autowired
    private JwtTokenService jwtTokenService;

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
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update-password")
    public ResponseEntity<RecoveryJwtTokenDTO> updatePassword(@RequestBody PasswordDTO passwordDTO){
        return new ResponseEntity<>(userService.updatePassword(passwordDTO),HttpStatus.CREATED);
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getUser(@RequestParam String email){
        return new ResponseEntity<>(userService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest(){
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest(){
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

    @PostMapping("/mfa/verify")
    public ResponseEntity<RecoveryJwtTokenDTO> verifyMfa(@RequestBody MfaVerifyDTO mfaDTO) {

        if (mfaDTO.getTempToken().startsWith("MFA_REQUIRED:")) {
            mfaDTO.setTempToken(mfaDTO.getTempToken().substring("MFA_REQUIRED:".length()));
        }

        String username = jwtTokenService.getSubjectFromToken(mfaDTO.getTempToken());
        User user = userService.findByUsername(username);

        if (!mfaService.validateTotp(user.getMfaSecret(), mfaDTO.getCode())) {
            throw new RuntimeException("Código inválido!");
        }

        UserDetailsImp userDetails = new UserDetailsImp(user);
        RecoveryJwtTokenDTO token = new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails), "", "", "", user.getRoles().get(0).getName());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
