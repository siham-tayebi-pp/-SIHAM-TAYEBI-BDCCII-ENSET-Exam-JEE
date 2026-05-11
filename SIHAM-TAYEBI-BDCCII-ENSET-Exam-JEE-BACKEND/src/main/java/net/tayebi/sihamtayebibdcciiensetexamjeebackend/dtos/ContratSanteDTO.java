package net.tayebi.sihamtayebibdcciiensetexamjeebackend.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.NiveauCouverture;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContratSanteDTO extends ContratAssuranceDTO {
    private NiveauCouverture niveauCouverture;
    private int nombrePersonnesCouvertes;
}
