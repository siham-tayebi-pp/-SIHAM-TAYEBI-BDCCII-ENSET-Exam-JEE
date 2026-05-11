package net.tayebi.sihamtayebibdcciiensetexamjeebackend.services;

import net.tayebi.sihamtayebibdcciiensetexamjeebackend.repositories.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.dtos.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.exceptions.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.mappers.AssuranceMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional @AllArgsConstructor @Slf4j
public class AssuranceServiceImpl implements IAssuranceService {

    private ClientRepository clientRepository;
    private ContratAssuranceRepository contratRepository;
    private PaiementRepository paiementRepository;
    private AssuranceMapper mapper;

    // ─── Clients ───────────────────────────────────────────────────
    @Override
    public ClientDTO saveClient(ClientDTO dto) {
        log.info("Saving client: {}", dto.getNom());

        Client client = mapper.fromClientDTO(dto);

        // 🔥 FORCE INSERT PROPRE
        client.setId(null);

        return mapper.fromClient(clientRepository.save(client));
    }

    @Override
    public List<ClientDTO> listClients() {
        return clientRepository.findAll().stream()
                .map(mapper::fromClient).collect(Collectors.toList());
    }

    @Override
    public List<ClientDTO> searchClients(String keyword) {
        return clientRepository.findByNomContainingIgnoreCase(keyword).stream()
                .map(mapper::fromClient).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Long id) throws ClientNotFoundException {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable id=" + id));
        return mapper.fromClient(client);
    }

    @Override
    public ClientDTO updateClient(ClientDTO dto) throws ClientNotFoundException {

        if (dto.getId() == null)
            throw new IllegalArgumentException("Client id obligatoire pour update");

        Client client = clientRepository.findById(dto.getId())
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable"));

        client.setNom(dto.getNom());
        client.setEmail(dto.getEmail());

        return mapper.fromClient(clientRepository.save(client));
    }

    @Transactional
    public void deleteClient(Long id) throws ClientNotFoundException {
        log.info("Deleting Client");

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable id=" + id));

        // 1. récupérer contrats du client
        List<ContratAssurance> contrats = contratRepository.findAll()
                .stream()
                .filter(c -> c.getClient().getId().equals(id))
                .toList();

        for (ContratAssurance contrat : contrats) {

            // 2. supprimer paiements liés au contrat
            paiementRepository.deleteAll(
                    paiementRepository.findAll()
                            .stream()
                            .filter(p -> p.getContrat().getId().equals(contrat.getId()))
                            .toList()
            );

            // 3. supprimer contrat
            contratRepository.deleteById(contrat.getId());
        }

        // 4. supprimer client
        clientRepository.deleteById(id);
    }

    // ─── Contrats ====
    @Override
    public ContratAssuranceDTO saveContrat(ContratAssuranceDTO dto) throws ClientNotFoundException {

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable"));

        ContratAssurance contrat;

        if (dto instanceof ContratAutomobileDTO d) {
            ContratAutomobile ca = new ContratAutomobile();

            BeanUtils.copyProperties(d, ca);

            ca.setId(null); // 🔥 IMPORTANT
            ca.setClient(client);

            contrat = ca;

        } else if (dto instanceof ContratHabitationDTO d) {
            ContratHabitation ch = new ContratHabitation();

            BeanUtils.copyProperties(d, ch);

            ch.setId(null);
            ch.setClient(client);

            contrat = ch;

        } else if (dto instanceof ContratSanteDTO d) {
            ContratSante cs = new ContratSante();

            BeanUtils.copyProperties(d, cs);

            cs.setId(null);
            cs.setClient(client);

            contrat = cs;

        } else {
            throw new IllegalArgumentException("Type de contrat inconnu");
        }

        contrat.setDateSouscription(
                contrat.getDateSouscription() != null ? contrat.getDateSouscription() : LocalDate.now()
        );

        contrat.setStatut(
                contrat.getStatut() != null ? contrat.getStatut() : StatutContrat.EN_COURS
        );

        return mapper.fromContrat(contratRepository.save(contrat));
    }
    @Override
    public List<ContratAssuranceDTO> listContrats() {
        return contratRepository.findAll().stream()
                .map(mapper::fromContrat).collect(Collectors.toList());
    }

    @Override
    public List<ContratAssuranceDTO> getContratsByClient(Long clientId) {
        return contratRepository.findByClientId(clientId).stream()
                .map(mapper::fromContrat).collect(Collectors.toList());
    }

    @Override
    public List<ContratAssuranceDTO> getContratsByStatut(StatutContrat statut) {
        return contratRepository.findByStatut(statut).stream()
                .map(mapper::fromContrat).collect(Collectors.toList());
    }

    @Override
    public ContratAssuranceDTO getContrat(Long id) throws ContratNotFoundException {
        ContratAssurance c = contratRepository.findById(id)
                .orElseThrow(() -> new ContratNotFoundException("Contrat introuvable id=" + id));
        return mapper.fromContrat(c);
    }

    @Override
    public ContratAssuranceDTO updateStatutContrat(Long id, StatutContrat statut)
            throws ContratNotFoundException {

        ContratAssurance c = contratRepository.findById(id)
                .orElseThrow(() -> new ContratNotFoundException("Contrat introuvable"));

        c.setStatut(statut);

        if (statut == StatutContrat.VALIDE && c.getDateValidation() == null) {
            c.setDateValidation(LocalDate.now());
        }

        return mapper.fromContrat(contratRepository.save(c));
    }

    @Override
    public void deleteContrat(Long id) throws ContratNotFoundException {

        ContratAssurance contrat = contratRepository.findById(id)
                .orElseThrow(() -> new ContratNotFoundException("Contrat introuvable"));

        // 🔥 delete paiements propre
        List<Paiement> paiements = paiementRepository.findByContratId(id);
        paiementRepository.deleteAll(paiements);

        // 🔥 detach relation propre
        contrat.setClient(null);

        contratRepository.delete(contrat);
    }
    // ─── Paiements cncn
    public PaiementDTO savePaiement(PaiementDTO dto) throws ContratNotFoundException {

        if (dto.getContratId() == null)
            throw new IllegalArgumentException("contratId obligatoire");

        ContratAssurance contrat = contratRepository.findById(dto.getContratId())
                .orElseThrow(() -> new ContratNotFoundException("Contrat introuvable"));

        Paiement p = new Paiement();

        BeanUtils.copyProperties(dto, p);

        p.setId(null); // 🔥 IMPORTANT
        p.setContrat(contrat);

        if (p.getDate() == null)
            p.setDate(LocalDate.now());

        return mapper.fromPaiement(paiementRepository.save(p));
    }
    @Override
    public List<PaiementDTO> getPaiementsByContrat(Long contratId) {
        return paiementRepository.findByContratId(contratId).stream()
                .map(mapper::fromPaiement).collect(Collectors.toList());
    }
}
