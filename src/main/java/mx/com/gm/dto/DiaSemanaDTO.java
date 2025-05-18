package mx.com.gm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DiaSemanaDTO {
    String dia;
    double porcentajeCumplimiento;
}
