/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ClientDAO;
import dao.EmployeDAO;
import dao.InterventionDAO;
import dao.JpaUtil;
import dao.PersonneDAO;
import java.util.List;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Intervention;
import metier.modele.Personne;
import static util.GeoTest.getLatLng;
import static util.GeoTest.getTripDurationByBicycleInMinute;

/**
 *
 * @author ffonteneau
 */
public class Service {
    
    
    public static void inscription(Client c) {
        System.out.println("[Service:Log] inscription de "+c.getNom());
        boolean inscrit = true;
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try{
            ClientDAO.inscrire(c); 
            JpaUtil.validerTransaction();
        } catch(Exception e){
            inscrit = false;
            System.out.println("[Service:Log] echec d'inscription de "+c.getNom());
            JpaUtil.annulerTransaction();
            e.printStackTrace();  
        } 
        if (inscrit){
            System.out.println("[Service:Log] inscription de "+c.getNom()+" terminée");
            //envoie du mail
        }
        JpaUtil.fermerEntityManager();
    }
    
    private static Employe rechercherEmploye(Intervention i){
        List<Employe> listeEmploye;
        Employe emp = null;
        JpaUtil.creerEntityManager();
        listeEmploye = EmployeDAO.employeDispo();
        if(listeEmploye != null){
            //dans tous les employes disponibles rechrcher le plus proche
            emp = listeEmploye.get(0);
            for(Employe e : listeEmploye){
                LatLng coordsClient = getLatLng(i.getClient().getAdresse());
                LatLng coordsCurrent = getLatLng(emp.getAdresse());
                LatLng coordsComp = getLatLng(e.getAdresse());
                if(getTripDurationByBicycleInMinute(coordsClient, coordsComp) < getTripDurationByBicycleInMinute(coordsClient, coordsCurrent)) {
                    emp = e;
                }
            }
        }
        JpaUtil.fermerEntityManager();
        return emp;
    }
        
    public static void demandeInter(Intervention inter) { 
        System.out.println("[Service:Log] "+inter.getClient().getNom()+" demande intervention");
        Employe emp = rechercherEmploye(inter); 
        if (emp != null){
            inter.setEmploye(emp);
            inter.setStatut("EN COURS");
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            
            try{
                InterventionDAO.demandeInterv(inter);  // ajouter l'envoi du mail
                JpaUtil.validerTransaction();
            } catch(Exception e){
                System.out.println("[Service:Log] erreur d'ajout d'intervention");
                JpaUtil.annulerTransaction();
                e.printStackTrace();  // ajouter l'envoi du mail
            }
            JpaUtil.fermerEntityManager();
        } else {
            System.out.println("[Service:Log] Intervention refusée, aucun employe disponible");
            //refuser l'intervention, notifier le client !
        }
    }
        
        //Envoie pointernull si Personne non trouvée
    public static Personne authentification(String mail, String mdp) {
        Personne personne = null;
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction(); 
        try {
            personne = PersonneDAO.authentification(mail, mdp);
            System.out.println("[Service:Log] "+personne.getNom()+" connecté");
        } catch(Exception e) {
            System.out.println("[Service:Log] echec de connexion de "+mail);
            JpaUtil.annulerTransaction();
            //authentification non complete
        }
        JpaUtil.fermerEntityManager();
        return personne;
    }
    

    //récupère l'historique des intervention d'un client, renvoie une 
    //une liste vide ou pointeur null si aucune intervention
    public static List<Intervention> historiqueClient(Client c) {
        System.out.println("[Service:Log] demande historique de "+c.getNom());
        List<Intervention> historique = null;
        JpaUtil.creerEntityManager();
        historique = ClientDAO.historique(c);
        JpaUtil.fermerEntityManager();
        return historique;
    }
    
    
}
