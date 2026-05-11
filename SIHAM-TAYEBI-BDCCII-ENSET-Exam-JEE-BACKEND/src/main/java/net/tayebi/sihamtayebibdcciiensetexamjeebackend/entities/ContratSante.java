package net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities;


import jakarta.persistence.*;
import lombok.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.NiveauCouverture;

@Entity
@DiscriminatorValue("SANTE")
@Data @NoArgsConstructor @AllArgsConstructor
public class ContratSante extends ContratAssurance {

    @Enumerated(EnumType.STRING)
    private NiveauCouverture niveauCouverture; // BASIQUE, INTERMEDIAIRE, PREMIUM

    private int nombrePersonnesCouvertes;
}
