package com.example.financial.service;



import com.example.financial.dto.LoginDTO;
import com.example.financial.dto.PasswordDTO;
import com.example.financial.dto.RecoveryJwtTokenDTO;
import com.example.financial.dto.UserDTO;
import com.example.financial.model.Role;
import com.example.financial.model.User;
import com.example.financial.model.enumerador.RoleName;
import com.example.financial.repository.UserRepository;
import com.example.financial.security.JwtTokenService;
import com.example.financial.security.MFAService;
import com.example.financial.security.SecurityConfig;
import com.example.financial.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MFAService mfaService;


    public RecoveryJwtTokenDTO authenticateUser(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();

        if (!userDetailsImp.getUser().getRoles().get(0).getName().equals(RoleName.ROLE_ADMINISTRATOR))
            return null;

        if (!userDetailsImp.getUser().getMfaEnabled()) {
            User user = userDetailsImp.getUser();

            String tempToken = jwtTokenService.generateTempToken(userDetailsImp);
            String secret = mfaService.generateSecret();
            user.setMfaSecret(secret);
            user.setMfaEnabled(true);
            userRepository.save(user);

            String otpAuthURL = mfaService.getOtpAuthURL(user.getUsername(), secret, "Simplex");
            String qrCodeBase64 = mfaService.generateQrCodeBase64(otpAuthURL);

            return new RecoveryJwtTokenDTO("MFA_REQUIRED:" + tempToken, "MFA_REQUIRED:" + tempToken, qrCodeBase64 );
        }

        String tempToken = jwtTokenService.generateTempToken(userDetailsImp);
        return new RecoveryJwtTokenDTO("MFA_REQUIRED:" + tempToken, null, null);

    }

    public RecoveryJwtTokenDTO authenticateUserCustomer(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();



//        if (!userDetailsImp.getUser().getRoles().get(0).getName().equals(RoleName.ROLE_CUSTOMER))
//            return null;

        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetailsImp), "", "");
    }

    public void createUser(UserDTO userDTO){
        Role role = roleService.findRole(userDTO.getRole());
        User newUser = User.builder().username(userDTO.getUsername())
                .password(securityConfig.passwordEncoder().encode(userDTO.getPassword()))
                .name(userDTO.getName())
                .roles(List.of( role != null ? role : Role.builder().name(userDTO.getRole()).build()))
                .build();

        userRepository.save(newUser);
    }

    public void createUserCustomer(UserDTO userDTO){

        if(existEmail(userDTO.getUsername()))
             throw new IllegalArgumentException("Email já cadastrado");

        User newUser = User.builder().username(userDTO.getUsername())
                .password(securityConfig.passwordEncoder().encode(userDTO.getPassword()))
                .name(userDTO.getName())
                .roles(List.of(roleService.findRole(RoleName.ROLE_CUSTOMER)))
                .build();

        userRepository.save(newUser);
    }


    public RecoveryJwtTokenDTO updateUser(UserDTO userDTO){
        User user = this.userRepository.findById(userDTO.getId().longValue()).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        User verifyEmail = this.userRepository.findByUsername(userDTO.getUsername()).orElse(null);

       if(verifyEmail != null){
           if(verifyEmail.getId().intValue() != userDTO.getId()){
               throw new RuntimeException("Ação não permitida");
           }
       }

       user.setName(userDTO.getName());
       user.setUsername(userDTO.getUsername());
       userRepository.save(user);

      return   authenticateUser(new LoginDTO(user.getUsername(), userDTO.getPassword()));
    }

    public RecoveryJwtTokenDTO updatePassword(PasswordDTO passwordDTO){
        User user = this.userRepository.findByUsername(passwordDTO.getUsername()).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if(!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())){
                throw new RuntimeException("As senhas são diferente!");
        }

        user.setPassword(securityConfig.passwordEncoder().encode(passwordDTO.getPassword()));
        userRepository.save(user);

        return   authenticateUser(new LoginDTO(user.getUsername(), passwordDTO.getConfirmPassword()));
    }


    public Boolean existEmail(String email){
      return this.userRepository.findByUsername(email).isPresent();
    }

    public User findByUsername(String username){
        return this.userRepository.findByUsername(username).orElse(null);
    }

    public void save(User user){
        userRepository.save(user);
    }

}
