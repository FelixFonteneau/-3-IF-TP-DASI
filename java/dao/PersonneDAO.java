/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Personne;

/**
 *
 * @author ffonteneau
 */
public class PersonneDAO {
    public static Personne authentification(String mail, String mdp) {
        String jpql = "select p from Personne p where p.mail = :email and p.mdp = :motdepasse";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery(jpql);
        q.setParameter("email", mail);
        q.setParameter("motdepasse", mdp);
        List<Personne> res = (List<Personne>) q.getResultList();
        return res.get(0);
    }
}
