package net.tayebi.sihamtayebibdcciiensetexamjeebackend;

import net.tayebi.sihamtayebibdcciiensetexamjeebackend.entities.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.enums.*;
import net.tayebi.sihamtayebibdcciiensetexamjeebackend.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SihamTayebiBdcciiEnsetExamJeeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SihamTayebiBdcciiEnsetExamJeeBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            ClientRepository clientRepository,
            ContratAssuranceRepository contratRepository,
            PaiementRepository paiementRepository) {
        return args -> {

            // ── Cree 5 clients ──
            List<String> noms = List.of("Ahmed Alaoui", "Fatima Benali",
                    "Youssef Tazi", "Meryem Idrissi", "Karim Zidane");
            noms.forEach(nom -> {
                Client c = new Client();
                c.setNom(nom);
                c.setEmail(nom.toLowerCase().replace(" ", ".") + "@gmail.com");
                clientRepository.save(c);
            });

            // ── Créeee des contrats pour chaque client ──
            clientRepository.findAll().forEach(client -> {

                // Contrat Automobile
                ContratAutomobile auto = new ContratAutomobile();
                auto.setDateSouscription(LocalDate.now().minusMonths(6));
                auto.setStatut(StatutContrat.VALIDE);
                auto.setDateValidation(LocalDate.now().minusMonths(5));
                auto.setMontantCotisation(1200.0);
                auto.setDureeContrat(12);
                auto.setTauxCouverture(80.0);
                auto.setClient(client);
                auto.setNumeroImmatriculation("1234-A-" + client.getId());
                auto.setMarqueVehicule("Toyota");
                auto.setModeleVehicule("Corolla");
                ContratAssurance savedAuto = contratRepository.save(auto);

                // Paiements pour le contrat auto
                for (int i = 1; i <= 3; i++) {
                    Paiement p = new Paiement();
                    p.setDate(LocalDate.now().minusMonths(i));
                    p.setMontant(auto.getMontantCotisation() / 12);
                    p.setType(TypePaiement.MENSUALITE);
                    p.setContrat(savedAuto);
                    paiementRepository.save(p);
                }

                // Contrat Habitation
                ContratHabitation hab = new ContratHabitation();
                hab.setDateSouscription(LocalDate.now().minusMonths(3));
                hab.setStatut(StatutContrat.EN_COURS);
                hab.setMontantCotisation(800.0);
                hab.setDureeContrat(24);
                hab.setTauxCouverture(70.0);
                hab.setClient(client);
                hab.setTypeLogement(TypeLogement.APPARTEMENT);
                hab.setAdresse(client.getId() + " Rue Hassan II, Casablanca");
                hab.setSuperficie(75.0 + client.getId() * 10);
                contratRepository.save(hab);

                // Contrat Santé
                ContratSante sante = new ContratSante();
                sante.setDateSouscription(LocalDate.now().minusWeeks(2));
                sante.setStatut(StatutContrat.RESILIE);
                sante.setMontantCotisation(2400.0);
                sante.setDureeContrat(12);
                sante.setTauxCouverture(90.0);
                sante.setClient(client);
                sante.setNiveauCouverture(NiveauCouverture.PREMIUM);
                sante.setNombrePersonnesCouvertes(4);
                contratRepository.save(sante);
            });

            System.out.println("========================================");
            System.out.println("Base de données alimentée avec succès !");
            System.out.println("Clients     : " + clientRepository.count());
            System.out.println("Contrats    : " + contratRepository.count());
            System.out.println("Paiements   : " + paiementRepository.count());
            System.out.println("========================================");
        };
    }
}
