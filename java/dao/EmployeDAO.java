/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Employe;
import metier.modele.Intervention;

/**
 *
 * @author ffonteneau
 */
public class EmployeDAO {
    
    public static void initBase() {
        Date d = new Date(1998, 9, 2);
        Employe employe = new Employe("Quentin", "Ferro", d, "homme", "0668629985", "vladimir.poutine@insa-lyon.fr", "20 Avenue Albert Einstein, Lyon", true, 9, 22, "mdp");
        Employe employe2 = new Employe("Zinedine", "Zidane", d, "homme", "0606363682", "zinedine.superstar@insa-lyon.fr", "Avenue de Valescure, Saint-Raphaël", true, 9, 22, "mdp");
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try{
            EntityManager em = JpaUtil.obtenirEntityManager();
            em.persist(employe);
            em.persist(employe2);
            JpaUtil.validerTransaction();
        } catch(Exception ex){
            JpaUtil.annulerTransaction();
            ex.printStackTrace();  // ajouter l'envoi du mail
        }

        JpaUtil.fermerEntityManager();
    }
    
    public static List<Employe> employeDispo(){
        String jpql = "select e from Employe e where e.statut = 1";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery(jpql);
        List<Employe> res = (List<Employe>) q.getResultList();
        return res;
    }
    
    public static void miseAjourEmploye(Employe emp){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(emp);
    }
   
}
