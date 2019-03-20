/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author ffonteneau
 */
@Entity
public class InterventionLivraison extends Intervention {
    protected String objet;
    protected String entreprise;

    public InterventionLivraison(String objet, String entreprise, Client client, 
             Date dateInter, String description, String statut){
        super(client, dateInter, description, statut);
        this.objet = objet;
        this.entreprise = entreprise;
    }

    public InterventionLivraison() {
    }

    public String getObjet() {
        return objet;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
}
