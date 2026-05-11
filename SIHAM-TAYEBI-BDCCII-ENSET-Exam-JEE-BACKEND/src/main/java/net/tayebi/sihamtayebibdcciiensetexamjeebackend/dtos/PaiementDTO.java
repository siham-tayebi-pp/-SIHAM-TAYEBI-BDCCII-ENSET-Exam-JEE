package net.tayebi.sihamtayebibdcciiensetexamjeebackend.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.TypePaiement;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaiementDTO {
    private Long id;
    private LocalDate date;
    private double montant;
    private TypePaiement type;
    private Long contratId;
}

