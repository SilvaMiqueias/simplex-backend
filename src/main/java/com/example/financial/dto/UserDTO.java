package com.example.financial.dto;

import com.example.financial.model.enumerador.RoleName;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String name;
    RoleName role;
    private String image;
}
