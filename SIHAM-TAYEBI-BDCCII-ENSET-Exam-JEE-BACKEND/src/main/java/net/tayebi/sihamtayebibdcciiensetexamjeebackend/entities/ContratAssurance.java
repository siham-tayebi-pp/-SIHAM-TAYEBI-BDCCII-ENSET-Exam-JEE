package net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.StatutContrat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)       // UNE seule table BD
@DiscriminatorColumn(name = "TYPE_CONTRAT", length = 5)     // colonne discriminante
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class ContratAssurance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateSouscription;

    @Enumerated(EnumType.STRING) // stocke "VALIDE" et non 1 ou 0 ou 2 c c quon fiat en cours
    private StatutContrat statut;

    private LocalDate dateValidation;
    private double montantCotisation;
    private int dureeContrat;          // en mois
    private double tauxCouverture;     // en pourcentage

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "contrat", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Paiement> paiements;
}
