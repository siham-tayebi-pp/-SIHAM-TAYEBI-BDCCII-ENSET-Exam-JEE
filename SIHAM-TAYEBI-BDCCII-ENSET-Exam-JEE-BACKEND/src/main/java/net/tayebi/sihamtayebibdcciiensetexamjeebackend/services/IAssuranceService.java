package net.tayebi.sihamtayebibdcciiensetexamjeebackend.services;

import net.tayebi.sihamtayebibdcciiensetexamjeebackend.dtos.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.exceptions.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.*;

import java.util.List;

public interface IAssuranceService {

    // Les meth metiers du Clients
    ClientDTO saveClient(ClientDTO clientDTO);
    List<ClientDTO> listClients();
    List<ClientDTO> searchClients(String keyword);
    ClientDTO getClient(Long id) throws ClientNotFoundException;
    ClientDTO updateClient(ClientDTO clientDTO) throws ClientNotFoundException;
    void deleteClient(Long id) throws ClientNotFoundException;

    // Les meth metiers du Contrats
    ContratAssuranceDTO saveContrat(ContratAssuranceDTO dto) throws ClientNotFoundException;
    List<ContratAssuranceDTO> listContrats();
    List<ContratAssuranceDTO> getContratsByClient(Long clientId);
    List<ContratAssuranceDTO> getContratsByStatut(StatutContrat statut);
    ContratAssuranceDTO getContrat(Long id) throws ContratNotFoundException;
    ContratAssuranceDTO updateStatutContrat(Long id, StatutContrat statut) throws ContratNotFoundException;
    void deleteContrat(Long id) throws ContratNotFoundException;

    // Les meth metiers du Paiements
    PaiementDTO savePaiement(PaiementDTO dto) throws ContratNotFoundException;
    List<PaiementDTO> getPaiementsByContrat(Long contratId);
}
