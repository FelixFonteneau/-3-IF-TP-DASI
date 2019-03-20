/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import com.google.maps.model.LatLng;
import util.GeoTest;
import dao.EmployeDAO;
import dao.JpaUtil;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import metier.modele.*;
import metier.service.*;
import static util.GeoTest.getFlightDistanceInKm;
import static util.GeoTest.getLatLng;
import static util.GeoTest.getTripDistanceByCarInKm;
import static util.GeoTest.getTripDurationByBicycleInMinute;

/**
 *
 * @author Quentin Ferro
 */
public class Main {
    
    public static void main(String[] args) {
        JpaUtil.init();
        EmployeDAO.initBase();
        Date d = new Date(1998, 9, 2);
        Client salut = new Client("Ferro", "BG", d, "CHOSE", "0668629985", "mao.zedong@insa-lyon.fr", "221 Boulevard Adrian, Saint-Raphael", "mdp");
        Client salut2 = new Client("Lechat", "Felix", d, "CHOSE", "0609837263", "lenine@insa-lyon.fr", "Rue de la République, Lyon", "mdp");
        Service.inscription(salut);
        Service.inscription(salut2);
        Service.authentification("mao.zedong@insa-lyon.fr", "mdp");
        Personne p = Service.authentification("mao.zedong@insa-lyon.fr", "hmmm");
        Service.authentification("bah", "mdp");   
        
        Date date = new Date(2019, 3, 18, 15, 1);
        Employe emp = (Employe) Service.authentification( "vladimir.poutine@insa-lyon.fr",  "mdp");
        InterventionAnimale interMilan = new InterventionAnimale(salut, date, "blabla", "Chat" ,"test");
        InterventionAnimale inter2 = new InterventionAnimale(salut2, date, "miaou", "Chat" ,"test");

        Service.demandeInter(interMilan);
        Service.demandeInter(inter2);
        List<Intervention> historique = Service.historiqueClient(salut);
        for(Intervention i : historique){
            System.out.println("   "+i.getDescription());
        }
        // ========
        
       /* 
        String adresse1 = "7 Avenue Jean Capelle Ouest, Villeurbanne";
        LatLng coords1 = getLatLng(adresse1);
        System.out.println("Lat/Lng de Adresse #1: " + coords1);

        String adresse2 = "37 Avenue Jean Capelle Est, Villeurbanne";
        LatLng coords2 = getLatLng(adresse2);
        System.out.println("Lat/Lng de Adresse #2: " + coords2);

        String adresse3 = "61 Avenue Roger Salengro, Villeurbanne";
        LatLng coords3 = getLatLng(adresse3);
        System.out.println("Lat/Lng de Adresse #3: " + coords3);
        
        // Coordonnées directes: Rond-Point du Totem, Cours Tolstoï, Villeurbanne
        LatLng coords4 = new LatLng(45.763781,4.8735128);
        System.out.println("Lat/Lng de Coords #4: ( " + coords4.lat + "; " + coords4.lng + " )");


        Double duree = getTripDurationByBicycleInMinute(coords1, coords3);
        System.out.println("Durée de Trajet à Vélo de Adresse #1 à Adresse #3 (trajet direct): " + duree + " min");

        Double distance = getTripDistanceByCarInKm(coords1, coords3, coords2);
        System.out.println("Distance en Voiture de Adresse #1 à Adresse #3 en passant par Adresse #2 (distance par la route): " + distance + " km");

        Double distanceVolDOiseau = getFlightDistanceInKm(coords1, coords3);
        System.out.println("Distance à Vol d'Oiseau de Adresse #1 à Adresse #3 (distance géographique): " + distanceVolDOiseau + " km");
        
        Double autreDistanceVolDOiseau = getFlightDistanceInKm(coords1, coords4);
        System.out.println("Distance à Vol d'Oiseau de Adresse #1 à Coords #4 (distance géographique): " + autreDistanceVolDOiseau + " km");
        
        */
        
        
        
        JpaUtil.destroy();
    }
}
