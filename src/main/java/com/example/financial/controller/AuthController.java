package com.example.financial.controller;


import com.example.financial.dto.*;
import com.example.financial.model.User;
import com.example.financial.security.JwtTokenService;
import com.example.financial.security.MFAService;
import com.example.financial.security.UserDetailsImp;
import com.example.financial.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth/users")
@Tag(name = "Usuários", description = "Endpoints para autenticação e gerenciamento de usuários")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private MFAService mfaService;

    @Autowired
    private JwtTokenService jwtTokenService;


    @Operation(summary = "Login do usuário", description = "Autentica um usuário  retorna um token JWT temporário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> login(@RequestBody LoginDTO loginDTO){
        RecoveryJwtTokenDTO token = userService.authenticateUser(loginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

//    @PostMapping("/login-customer")
//    public ResponseEntity<RecoveryJwtTokenDTO> loginCustomer(@RequestBody LoginDTO loginDTO){
//        RecoveryJwtTokenDTO token = userService.authenticateUserCustomer(loginDTO);
//        return new ResponseEntity<>(token, HttpStatus.OK);
//    }

    @Operation(summary = "Criar usuário administrador", description = "Cria um novo usuário administrador no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Criar usuário cliente", description = "Cria um novo usuário cliente no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/create-customer")
    public ResponseEntity<?> createUserCustomer(@RequestBody UserDTO userDTO) {
        userService.createUserCustomer(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar senha do usuário", description = "Atualiza a senha do usuário e retorna novo token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/update-password")
    public ResponseEntity<RecoveryJwtTokenDTO> updatePassword(@RequestBody PasswordDTO passwordDTO){
        return new ResponseEntity<>(userService.updatePassword(passwordDTO),HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar usuário por email", description = "Retorna os dados de um usuário com base no email fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getUser(@RequestParam String email){
        return new ResponseEntity<>(userService.getByEmail(email), HttpStatus.OK);
    }

    @Operation(summary = "Teste de autenticação de cliente", description = "Endpoint de teste para validar autenticação de cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente autenticado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest(){
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @Operation(summary = "Teste de autenticação de administrador", description = "Endpoint de teste para validar autenticação de administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador autenticado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest(){
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

    @Operation(summary = "Verificação MFA (multi-factor authentication)", description = "Verifica código MFA e retorna token JWT se válido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Código MFA válido, token gerado"),
            @ApiResponse(responseCode = "400", description = "Código MFA inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
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
