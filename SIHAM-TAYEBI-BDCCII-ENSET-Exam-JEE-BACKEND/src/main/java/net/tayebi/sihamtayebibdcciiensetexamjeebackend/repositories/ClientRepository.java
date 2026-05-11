package net.tayebi.sihamtayebibdcciiensetexamjeebackend.repositories;

import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.Client;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNomContainingIgnoreCase(String keyword);
}
