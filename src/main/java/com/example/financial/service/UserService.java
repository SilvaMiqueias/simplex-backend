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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

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

    public RecoveryJwtTokenDTO authenticateUser(LoginDTO loginDTO) {
        return authenticate(loginDTO, true);
    }

    public RecoveryJwtTokenDTO authenticateUserCustomer(LoginDTO loginDTO) {
        return authenticate(loginDTO, false);
    }

    private RecoveryJwtTokenDTO authenticate(LoginDTO loginDTO, boolean requireAdminRole) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
        User user = userDetailsImp.getUser();

//        if (requireAdminRole && !user.getRoles().get(0).getName().equals(RoleName.ROLE_ADMINISTRATOR)) {
//            return null;
//        }

        String tempToken = jwtTokenService.generateTempToken(userDetailsImp);

        if (!user.getMfaEnabled()) {
            String secret = mfaService.generateSecret();
            user.setMfaSecret(secret);
            user.setMfaEnabled(true);
            userRepository.save(user);

            return new RecoveryJwtTokenDTO("", "MFA_REQUIRED:" + tempToken, getQrCode(user.getUsername(), secret), "2fa_setup", user.getRoles().get(0).getName());
        }

        return new RecoveryJwtTokenDTO("", "MFA_REQUIRED:" + tempToken, getQrCode(user.getUsername(), user.getMfaSecret()), "2fa_required", user.getRoles().get(0).getName());
    }

    public String getQrCode(String email, String secret) {
        String otpAuthURL = mfaService.getOtpAuthURL(email, secret, "Simplex");
        return mfaService.generateQrCodeBase64(otpAuthURL);
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

    public UserDTO getByEmail(String username){
        UserDTO dto = new UserDTO();
        User user = this.userRepository.findByUsername(username).orElse(null);
        if(user != null){
            dto.setId(user.getId().intValue());
            dto.setUsername(user.getUsername());
            dto.setName(user.getName());
        }
        return dto;
    }


    public void save(User user){
        userRepository.save(user);
    }

}
