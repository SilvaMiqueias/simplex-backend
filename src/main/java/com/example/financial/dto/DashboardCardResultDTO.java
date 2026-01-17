package com.example.financial.dto;

import com.example.financial.dto.interface_dto.DashboardCardDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCardResultDTO {

    private DashboardCardDTO currentMonth;

    private DashboardCardDTO previousMonth;
}
