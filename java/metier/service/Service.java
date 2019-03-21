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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Intervention;
import metier.modele.Personne;
import util.DebugLogger;
import static util.GeoTest.getLatLng;
import static util.GeoTest.getTripDurationByBicycleInMinute;
import util.Message;

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
            DebugLogger.log("[Service:Log] echec d'inscription de "+c.getNom(), e);
            JpaUtil.annulerTransaction();
        } 
        if (inscrit){
                        //envoie du mail
            StringWriter corps = new StringWriter();
            PrintWriter mailWriter = new PrintWriter(corps);
        
            mailWriter.println("Bonjour "+c.getPrenom()+",");
            mailWriter.println();
            mailWriter.println("  Ceci est un mail vous confirmant votre inscription à PROACT'IF !");
            mailWriter.println();
            mailWriter.println("  Cordialement,");
            mailWriter.println();
            mailWriter.println("    L'équipe PROACT'IF");

            Message.envoyerMail(
                "pro@actif.fr",
                c.getMail(),
                "[PROACT'IF] Confirmation inscription",
                corps.toString()
            );
            //System.out.println("[Service:Log] inscription de "+c.getNom()+" terminée");
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
        //System.out.println("[Service:Log] "+inter.getClient().getNom()+" demande intervention");
        Employe emp = rechercherEmploye(inter); 
        if (emp != null){
            inter.setEmploye(emp);
            inter.setStatut("EN COURS");
            emp.setStatut(false);

            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            
            try{
                InterventionDAO.demandeInterv(inter);  
                EmployeDAO.miseAjourEmploye(emp);
                JpaUtil.validerTransaction();
                
                StringWriter message = new StringWriter();
                PrintWriter notificationWriter = new PrintWriter(message);
                notificationWriter.println("Bonjour, "+emp.getPrenom());
                notificationWriter.println("Vous avez une nouvelle intervention !");
                String typeInter = inter.getClass().getName();
                typeInter = typeInter.substring(26);
                notificationWriter.println("C'est une intervention de type : "+typeInter);
                notificationWriter.println("Adresse : "+inter.getClient().getAdresse());
                notificationWriter.println("Descriptif : "+inter.getDescription());
                Message.envoyerNotification(
                    emp.getNumTel(),
                    message.toString()
                );
            } catch(Exception e){
                DebugLogger.log("[Service:Log] erreur d'ajout d'intervention : ",e);
                JpaUtil.annulerTransaction();
                e.printStackTrace();  // ajouter l'envoi du mail
            }
            JpaUtil.fermerEntityManager();
            
        } else {
            DebugLogger.log("[Service:Log] Intervention refusée, aucun employe disponible");
            //refuser l'intervention, notifier le client !
        }
    }
        
    //Envoie pointernull si Personne non trouvée
    public static Personne authentification(String mail, String mdp) {
        Personne personne = null;
        JpaUtil.creerEntityManager();
        try {
            personne = PersonneDAO.authentification(mail, mdp);
            System.out.println("[Service:Log] "+personne.getNom()+" connecté");
        } catch(Exception e) {
            DebugLogger.log("[Service:Log] echec de connexion de "+mail);
            //authentification non complete
        }
        JpaUtil.fermerEntityManager();
        return personne;
    }
    

    //récupère l'historique des intervention d'un client, renvoie une 
    //une liste vide ou pointeur null si aucune intervention
    public static List<Intervention> historiqueClient(Client c) {
        //System.out.println("[Service:Log] demande historique de "+c.getNom());
        List<Intervention> historique = null;
        JpaUtil.creerEntityManager();
        try{
            historique = ClientDAO.historique(c);
        }catch(Exception e){
            DebugLogger.log("[Service:Log] Erreur en cherchant l'historique du client : ",e);
        }
        JpaUtil.fermerEntityManager();
        return historique;
    }
    
    //Renvoie l'intervention que l'employe a à effectuer,
    //Renvoie null si pas d'intervention à faire
    public static Intervention interventionActuelle(Employe emp){
        Intervention intervention = null;
        JpaUtil.creerEntityManager();
        
        //récupération de l'intervention
        try {
            intervention = InterventionDAO.interventionCourante(emp);
            //System.out.println("[Service:Log] Cloture de l'intervention");
        } catch(Exception e) {
            DebugLogger.log("[Service:Log] Erreur en trouvant l'intervention de l'employe : ",e);
        }
        JpaUtil.fermerEntityManager();
        return intervention;
    }
    
    //cloture l'intervention actuelle d'un employe
    public static void cloturerIntervention(Employe emp, String commentaire, String statut){
        Intervention intervention = interventionActuelle(emp);
        
        //mise à jour de l'intervention et du statut de l'employe
        if(intervention != null){
            intervention.setStatut(statut);
            intervention.setCommentaire(commentaire);
            intervention.setDateFin(new Date());
            emp.setStatut(true);

            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction(); 
            try{
                InterventionDAO.cloturerIntervention(intervention);
                EmployeDAO.miseAjourEmploye(emp);
                //System.out.println("[Service:Log] Intervention cloturée");
                JpaUtil.validerTransaction();
                
                //notification du client
                StringWriter message = new StringWriter();
                PrintWriter notificationWriter = new PrintWriter(message);
                notificationWriter.println("Bonjour, "+intervention.getClient().getPrenom());
                notificationWriter.println("Votre intervention a bien été traitée,");
                notificationWriter.println("Commentaire de l'employé : "+intervention.getCommentaire());
                Message.envoyerNotification(
                    intervention.getClient().getNumTel(),
                    message.toString()
                );
            }catch(Exception e){
                JpaUtil.annulerTransaction();
                DebugLogger.log("[Service:Log] Erreur de cloture d'intervention",e);
            }
            JpaUtil.fermerEntityManager();
        }else{
            System.out.println("[Service:Log] Employe n'a pas d'intervention courante");
        }      
    }
    
    
    public static List<Intervention> interventionsJournee(){
        List<Intervention> historique = null;
        JpaUtil.creerEntityManager();
        try{
            historique = InterventionDAO.historiqueJournee();
        }catch(Exception e){
            DebugLogger.log("[Service:Log] Erreur en cherchant l'historique du client : ",e);
        }
        JpaUtil.fermerEntityManager();
        return historique;
    }
}
