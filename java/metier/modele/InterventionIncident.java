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
public class InterventionIncident extends Intervention {
    
    public InterventionIncident(Client client, Date dateInter, 
             String description, String statut){
        super(client, dateInter, description, statut);
    }

    public InterventionIncident() {
    }
    
    
}
