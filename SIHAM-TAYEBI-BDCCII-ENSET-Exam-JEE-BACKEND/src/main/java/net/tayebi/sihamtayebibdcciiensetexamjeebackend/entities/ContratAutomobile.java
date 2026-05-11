package net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("AUTO")   // valeur dans la colonne TYPE_CONTRAT
@Data @NoArgsConstructor @AllArgsConstructor
public class ContratAutomobile extends ContratAssurance {
    private String numeroImmatriculation; // ex: "12345-A-7" comem libele dial les produis
    private String marqueVehicule;        // ex: "Toyota" ou bmw
    private String modeleVehicule;        // ex: "Corolla" smodlee 2029 par epxle
}
