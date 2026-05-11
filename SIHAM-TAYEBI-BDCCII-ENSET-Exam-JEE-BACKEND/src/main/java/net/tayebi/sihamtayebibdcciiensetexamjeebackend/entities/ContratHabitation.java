package net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities;


import jakarta.persistence.*;
import lombok.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.TypeLogement;

@Entity
@DiscriminatorValue("HABIT")
@Data @NoArgsConstructor @AllArgsConstructor
public class ContratHabitation extends ContratAssurance {

    @Enumerated(EnumType.STRING)
    private TypeLogement typeLogement;   // APPARTEMENT, MAISON, LOCAL_COMMERCIAL

    private String adresse;
    private double superficie;           // b m²
}
