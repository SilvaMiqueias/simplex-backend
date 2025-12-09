package com.example.financial.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MfaDTO {
    String secret;
    String qrCode;
}
