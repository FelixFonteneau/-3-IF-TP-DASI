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
public class InterventionDAO {
    
    public static void demandeInterv(Intervention i) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(i);
    }
    
    public static void cloturerIntervention(Intervention intervention){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(intervention);
    }
    
     public static Intervention interventionCourante(Employe emp){
        String jpql = "select i from Intervention i where i.employe = :emp and i.statut = :encours";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery(jpql);
        q.setParameter("emp", emp);
        q.setParameter("encours", "EN COURS");
        List<Intervention> res = (List<Intervention>) q.getResultList();
        return res.get(0);
    }
     
    public static List<Intervention> historiqueJournee(){
        String jpql = "select i from Intervention i where i.statut = :encours or i.dateFin >= :date ";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery(jpql);
        Date d = new Date();
        d.setMinutes(0);
        d.setHours(0);
        q.setParameter("date", d);
        q.setParameter("encours", "EN COURS");
        List<Intervention> res = (List<Intervention>) q.getResultList();
        return res;
    }
}