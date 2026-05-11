package net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.TypePaiement;

import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Paiement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double montant;

    @Enumerated(EnumType.STRING)
    private TypePaiement type; // MENSUALITE, PAIEMENT_ANNUEL, PAIEMENT_EXCEPTIONNEL

    @ManyToOne
    @JoinColumn(name = "contrat_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ContratAssurance contrat;
}
