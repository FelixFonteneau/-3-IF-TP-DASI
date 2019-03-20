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
public class InterventionAnimale extends Intervention {
    protected String typeAnimal;
    
    public InterventionAnimale(Client client, Date dateInter, 
             String description, String typeAnimal, String statut){
        super(client, dateInter, description, statut);
        this.typeAnimal = typeAnimal;
    }
    
    public InterventionAnimale(){}

    public String getTypeAnimal() {
        return typeAnimal;
    }

    public void setTypeAnimal(String typeAnimal) {
        this.typeAnimal = typeAnimal;
    }
}