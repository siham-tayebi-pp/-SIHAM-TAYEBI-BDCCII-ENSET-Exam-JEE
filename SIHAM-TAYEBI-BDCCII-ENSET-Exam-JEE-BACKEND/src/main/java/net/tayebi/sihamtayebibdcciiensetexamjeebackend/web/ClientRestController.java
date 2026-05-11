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
public class ClientRestController {

    private AssuranceServiceImpl assuranceService;

    @GetMapping("/clients")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public List<ClientDTO> listClients() {
        return assuranceService.listClients();
    }

    @GetMapping("/clients/search")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public List<ClientDTO> searchClients(@RequestParam(defaultValue = "") String keyword) {
        return assuranceService.searchClients(keyword);
    }

    @GetMapping("/clients/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_CLIENT','SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public ClientDTO getClient(@PathVariable Long id) throws ClientNotFoundException {
        return assuranceService.getClient(id);
    }

    @PostMapping("/clients")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ClientDTO saveClient(@RequestBody ClientDTO dto) {
        return assuranceService.saveClient(dto);
    }

    @PutMapping("/clients/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ClientDTO updateClient(@PathVariable Long id, @RequestBody ClientDTO dto)
            throws ClientNotFoundException {
        dto.setId(id);
        return assuranceService.updateClient(dto);
    }

    @DeleteMapping("/clients/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void deleteClient(@PathVariable Long id) throws ClientNotFoundException {
        assuranceService.deleteClient(id);
    }

    // Tous les contrats d'un client
    @GetMapping("/clients/{id}/contrats")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_CLIENT','SCOPE_ROLE_EMPLOYE','SCOPE_ROLE_ADMIN')")
    public List<ContratAssuranceDTO> getContratsClient(@PathVariable Long id) {
        return assuranceService.getContratsByClient(id);
    }
}
