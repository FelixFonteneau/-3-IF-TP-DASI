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
    
}