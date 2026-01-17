package com.example.financial.dto;

import com.example.financial.model.enumerador.RoleName;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryJwtTokenDTO {
    private String token;
    private String tempToken;
    private String qrCode;
    private String status;
    private RoleName role;
}
