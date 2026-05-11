package net.tayebi.sihamtayebibdcciiensetexamjeebackend.repositories;

import jakarta.transaction.Transactional;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.ContratAssurance;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.Paiement;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContratAssuranceRepository extends JpaRepository<ContratAssurance, Long> {
    List<ContratAssurance> findByClientId(Long clientId);
    List<ContratAssurance> findByStatut(StatutContrat statut);
    @Modifying
    @Transactional
    @Query("delete from Paiement p where p.contrat.id = :contratId")
    void deleteByContratId(@Param("contratId") Long contratId);
}
