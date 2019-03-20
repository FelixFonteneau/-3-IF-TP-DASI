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
public abstract class  Personne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    protected String nom;
    protected String prenom;
    @Temporal(TemporalType.DATE) protected Date dateNaissance;
    protected String civilite;
    protected String numTel;
    protected String mail;
    protected String adresse;
    protected String mdp;
    
    public Personne(){}
    
    public Personne(String nom, String prenom, Date date, String civilite, 
            String numTel, String mail, String adresse, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = date;
        this.civilite = civilite;
        this.numTel = numTel;
        this.mail = mail;
        this.adresse = adresse;
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getCivilite() {
        return civilite;
    }

    public String getNumTel() {
        return numTel;
    }

    public String getMail() {
        return mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public Integer getId() {
        return id;
    }

    public String getMdp() {
        return mdp;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
