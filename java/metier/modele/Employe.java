/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Transient;


/**
 *
 * @author ffonteneau
 */

@Entity
//public class Employe  extends Personne {
public class Employe extends Personne {
    protected Integer numEmploye;
    protected boolean statut;
    protected Integer heureDebut;
    protected Integer heureFin;
    
    public Employe (){}
    
    public Employe(String nom, String prenom, Date date, String civilite, 
            String numTel, String mail, String adresse, 
            boolean statut, Integer heureDebut, Integer heureFin, String mdp){
        super(nom, prenom, date, civilite, numTel, mail, adresse, mdp);
        this.statut = statut;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.numEmploye = id;
    }

    public Integer getNumEmploye() {
        return numEmploye;
    }

    public boolean isStatut() {
        return statut;
    }

    public Integer getHeureDebut() {
        return heureDebut;
    }

    public Integer getHeureFin() {
        return heureFin;
    }

    public void setNumEmploye(Integer numEmploye) {
        this.numEmploye = numEmploye;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public void setHeureDebut(Integer heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(Integer heureFin) {
        this.heureFin = heureFin;
    }
    
    
}