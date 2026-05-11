package net.tayebi.sihamtayebibdcciiensetexamjeebackend.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContratAutomobileDTO extends ContratAssuranceDTO {
    private String numeroImmatriculation;
    private String marqueVehicule;
    private String modeleVehicule;
}
