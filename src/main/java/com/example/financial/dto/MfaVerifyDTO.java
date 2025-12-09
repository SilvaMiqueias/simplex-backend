package com.example.financial.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MfaVerifyDTO {
    String tempSecret;
    Integer code;
}
