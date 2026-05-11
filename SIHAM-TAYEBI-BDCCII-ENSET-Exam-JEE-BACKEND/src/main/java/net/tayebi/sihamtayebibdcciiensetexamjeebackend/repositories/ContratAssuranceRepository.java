package net.tayebi.sihamtayebibdcciiensetexamjeebackend.repositories;

import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.ContratAssurance;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.Paiement;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratAssuranceRepository extends JpaRepository<ContratAssurance, Long> {
    List<ContratAssurance> findByClientId(Long clientId);
    List<ContratAssurance> findByStatut(StatutContrat statut);
}
