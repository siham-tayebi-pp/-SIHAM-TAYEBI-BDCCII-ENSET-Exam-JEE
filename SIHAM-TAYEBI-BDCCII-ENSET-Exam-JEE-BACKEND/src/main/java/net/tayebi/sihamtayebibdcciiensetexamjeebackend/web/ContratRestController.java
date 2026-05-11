package net.tayebi.sihamtayebibdcciiensetexamjeebackend.web;

import net.tayebi.sihamtayebibdcciiensetexamjeebackend.repositories.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.dtos.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.exceptions.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.services.AssuranceServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@RestController
@AllArgsConstructor @Slf4j
@CrossOrigin("*")
@RequestMapping("/api")
public class ContratRestController {

    private AssuranceServiceImpl assuranceService;

    @GetMapping("/contrats")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public List<ContratAssuranceDTO> listContrats() {
        return assuranceService.listContrats();
    }

    @GetMapping("/contrats/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_CLIENT','SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public ContratAssuranceDTO getContrat(@PathVariable Long id) throws ContratNotFoundException {
        return assuranceService.getContrat(id);
    }

    @GetMapping("/contrats/statut/{statut}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public List<ContratAssuranceDTO> getContratsByStatut(@PathVariable StatutContrat statut) {
        return assuranceService.getContratsByStatut(statut);
    }

    // ─ Création des 3 types de contrats
    @PostMapping("/contrats/automobile")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public ContratAssuranceDTO saveAutomobile(@RequestBody ContratAutomobileDTO dto)
            throws ClientNotFoundException {
        return assuranceService.saveContrat(dto);
    }

    @PostMapping("/contrats/habitation")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public ContratAssuranceDTO saveHabitation(@RequestBody ContratHabitationDTO dto)
            throws ClientNotFoundException {
        return assuranceService.saveContrat(dto);
    }

    @PostMapping("/contrats/sante")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public ContratAssuranceDTO saveSante(@RequestBody ContratSanteDTO dto)
            throws ClientNotFoundException {
        return assuranceService.saveContrat(dto);
    }

    // ── Changement de statut
    @PatchMapping("/contrats/{id}/statut")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public ContratAssuranceDTO updateStatut(@PathVariable Long id,
                                            @RequestParam StatutContrat statut)
            throws ContratNotFoundException {
        return assuranceService.updateStatutContrat(id, statut);
    }

    @DeleteMapping("/contrats/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void deleteContrat(@PathVariable Long id) throws ContratNotFoundException {
        assuranceService.deleteContrat(id);
    }

    // ── Paiements
    @GetMapping("/contrats/{id}/paiements")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_CLIENT','SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public List<PaiementDTO> getPaiements(@PathVariable Long id) {
        return assuranceService.getPaiementsByContrat(id);
    }

    @PostMapping("/paiements")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public PaiementDTO savePaiement(@RequestBody PaiementDTO dto) throws ContratNotFoundException {
        return assuranceService.savePaiement(dto);
    }
}
