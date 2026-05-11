package net.tayebi.sihamtayebibdcciiensetexamjeebackend.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.TypeLogement;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContratHabitationDTO extends ContratAssuranceDTO {
    private TypeLogement typeLogement;
    private String adresse;
    private double superficie;
}
