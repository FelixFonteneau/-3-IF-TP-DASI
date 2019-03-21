/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ffonteneau
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Intervention implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    protected Client client;
    protected Employe employe;
    @Temporal(TemporalType.DATE) protected Date dateInter;
    @Temporal(TemporalType.DATE) protected Date dateFin;
    protected String description;
    protected String statut;
    protected String commentaire;
    public Intervention(Client client, Date dateInter, 
            String description, String statut){
        this.client = client;
        this.employe = employe;
        this.dateInter = dateInter;
        this.description = description;
        this.statut = statut;
    }

    public Intervention() {
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getStatut() {
        return statut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    

    public Client getClient() {
        return client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Date getDateInter() {
        return dateInter;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public void setDateInter(Date dateNaissance) {
        this.dateInter = dateNaissance;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
