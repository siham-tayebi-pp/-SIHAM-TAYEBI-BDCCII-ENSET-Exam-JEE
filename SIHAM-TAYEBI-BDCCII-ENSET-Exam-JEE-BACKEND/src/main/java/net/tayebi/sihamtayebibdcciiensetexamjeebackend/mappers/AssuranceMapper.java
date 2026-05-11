package net.tayebi.sihamtayebibdcciiensetexamjeebackend.mappers;

import net.tayebi.sihamtayebibdcciiensetexamjeebackend.dtos.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

// AssuranceMapper.java
@Component
public class AssuranceMapper {

    public ClientDTO fromClient(Client client) {
        ClientDTO dto = new ClientDTO();
        BeanUtils.copyProperties(client, dto);
        return dto;
    }

    public Client fromClientDTO(ClientDTO dto) {
        Client client = new Client();
        BeanUtils.copyProperties(dto, client);
        return client;
    }

    public ContratAssuranceDTO fromContrat(ContratAssurance contrat) {
        ContratAssuranceDTO dto;

        if (contrat instanceof ContratAutomobile ca) {
            ContratAutomobileDTO d = new ContratAutomobileDTO();
            BeanUtils.copyProperties(contrat, d);
            d.setNumeroImmatriculation(ca.getNumeroImmatriculation());
            d.setMarqueVehicule(ca.getMarqueVehicule());
            d.setModeleVehicule(ca.getModeleVehicule());
            d.setTypeContrat("AUTOMOBILE");
            dto = d;

        } else if (contrat instanceof ContratHabitation ch) {
            ContratHabitationDTO d = new ContratHabitationDTO();
            BeanUtils.copyProperties(contrat, d);
            d.setTypeLogement(ch.getTypeLogement());
            d.setAdresse(ch.getAdresse());
            d.setSuperficie(ch.getSuperficie());
            d.setTypeContrat("HABITATION");
            dto = d;

        } else if (contrat instanceof ContratSante cs) {
            ContratSanteDTO d = new ContratSanteDTO();
            BeanUtils.copyProperties(contrat, d);
            d.setNiveauCouverture(cs.getNiveauCouverture());
            d.setNombrePersonnesCouvertes(cs.getNombrePersonnesCouvertes());
            d.setTypeContrat("SANTE");
            dto = d;

        } else {
            dto = new ContratAssuranceDTO();
            BeanUtils.copyProperties(contrat, dto);
        }

        if (contrat.getClient() != null)
            dto.setClientId(contrat.getClient().getId());
        return dto;
    }

    public PaiementDTO fromPaiement(Paiement p) {
        PaiementDTO dto = new PaiementDTO();
        BeanUtils.copyProperties(p, dto);
        if (p.getContrat() != null) dto.setContratId(p.getContrat().getId());
        return dto;
    }
}
