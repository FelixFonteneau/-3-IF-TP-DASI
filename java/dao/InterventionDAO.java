/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
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
}