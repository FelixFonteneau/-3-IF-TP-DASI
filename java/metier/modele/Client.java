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
public class Client extends Personne {
    
    public Client (){}
    
    public Client(String nom, String prenom, Date date, String civilite, String numTel, String mail, String adresse, String mdp){
        super(nom, prenom, date, civilite, numTel, mail, adresse, mdp);
    }
}
