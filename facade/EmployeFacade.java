/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonelib.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import phonelib.jpa.AbstractFacade;
import phonelib.jpa.Department;
import phonelib.jpa.Employe;
import phonelib.jpa.Filial;

/**
 *
 * @author user
 */
@Stateless
public class EmployeFacade extends AbstractFacade<Employe> {

    @PersistenceContext(unitName = "PhoneLibAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeFacade() {
        super(Employe.class);
    }
    
    public List<Employe> findEmployesByFullname(String firstName, String secondName, String middleName){
        TypedQuery<Employe> namedQuery = em.createNamedQuery("Employe.findByFullname", Employe.class);
        namedQuery.setParameter("firstName", firstName);
        namedQuery.setParameter("secondName", secondName);
        namedQuery.setParameter("middleName", middleName);
        return namedQuery.getResultList();
    }
}
