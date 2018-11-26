package doyonbenoit.projetRPC.controleur;

import doyonbenoit.projetRPC.OAD.CombatOad;
import doyonbenoit.projetRPC.OAD.CompteOad;
import doyonbenoit.projetRPC.OAD.ExamenOad;
import doyonbenoit.projetRPC.OTD.CompteOtd;
import doyonbenoit.projetRPC.entite.*;
import doyonbenoit.projetRPC.enumeration.EnumGroupe;
import doyonbenoit.projetRPC.enumeration.EnumInfoCompte;
import doyonbenoit.projetRPC.enumeration.EnumRole;
import doyonbenoit.projetRPC.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/Compte")
public class ControleurCompte {

    @Autowired
    CompteOad compteOad;

    @Autowired
    CompteService compteService;

    @Autowired
    ExamenOad examenOad;

    @Autowired
    CombatOad combatOad;

    @PostMapping(value = "/inscription")
    public ResponseEntity<Void> ajoutCompte(@RequestBody CompteOtd compteOtd) {
        ResponseEntity<Void> valeurRetour = null;

        Compte compte = compteService.nouveauCompte(compteOtd);

        valeurRetour = (!compte.equals(null)) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build() ;

        return valeurRetour;
    }

    @GetMapping(value = "/{courriel}")
    public Compte afficheCompte(@PathVariable String courriel){
        return  compteOad.findByCourriel(courriel);
    }

    @GetMapping(value = "/ceinture/{strCeinture}")
    public List<Compte> afficheCompteParCeinture(@PathVariable String strCeinture){
        EnumGroupe ceinture = EnumGroupe.valueOf(strCeinture.toUpperCase());

        return compteOad.findByGroupe(new Groupe(ceinture.ordinal() + 1, ceinture));
    }

    @GetMapping(value = "/tout")
    public HashMap<String, List> afficheToutCompte() {

        List<Compte> lstCompte = compteOad.findAll();

        List<String> lstCourriel = lstCompte.stream()
                .map(Compte::getCourriel)
                .collect(Collectors.toList());

        HashMap<String,List> courriel = new HashMap<>();
        courriel.put("lstCourriel", lstCourriel);

        return courriel;
    }

    @GetMapping(value = "/PointCredit/{courriel}")
    public HashMap<String,Object> afficheComptePointCredit(@PathVariable String courriel) {
        SalleCombatAndroid salleCombatAndroid = new SalleCombatAndroid();

        HashMap<String,Object> infoCompteComplet = salleCombatAndroid.calculePoint(courriel, combatOad, compteOad, examenOad);

        HashMap<String, Object> compteComplet = new HashMap<>();
        compteComplet.put(EnumInfoCompte.COMPTE.getNom(),infoCompteComplet.get(EnumInfoCompte.COMPTE.getNom()));
        compteComplet.put(EnumInfoCompte.POINT.getNom(), infoCompteComplet.get(EnumInfoCompte.POINT.getNom()));
        compteComplet.put(EnumInfoCompte.CREDIT.getNom(), infoCompteComplet.get(EnumInfoCompte.CREDIT.getNom()));

        return compteComplet;
    }
}
