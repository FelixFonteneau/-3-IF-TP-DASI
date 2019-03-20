/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;
import metier.modele.Intervention;

/**
 *
 * @author ffonteneau
 */
public class ClientDAO {
    
    public static void inscrire(Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }
    
    public static List<Intervention> historique(Client c){
        String jpql = "select i from Intervention i where i.client.id = :id";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery(jpql);
        q.setParameter("id", c.getId());
        List<Intervention> res = (List<Intervention>) q.getResultList();
        return res;
    }
   
}
