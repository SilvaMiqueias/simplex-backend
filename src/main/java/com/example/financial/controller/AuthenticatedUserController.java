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
@RequestMapping("/authenticated")
@Tag(name = "Usuários", description = "Endpoints autenticado de usuários")
public class AuthenticatedUserController {

    @Autowired
    private UserService userService;

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

}
