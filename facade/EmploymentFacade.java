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
import phonelib.jpa.Employe;
import phonelib.jpa.Employment;

/**
 *
 * @author user
 */
@Stateless
public class EmploymentFacade extends AbstractFacade<Employment> {

    @PersistenceContext(unitName = "PhoneLibAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmploymentFacade() {
        super(Employment.class);
    }
    
    public List<Employment> findEmploymentsByUserAndName(Employe user, String employmentName){
        TypedQuery<Employment> namedQuery = em.createNamedQuery("Employment.findByEmployeAndName", Employment.class);
        namedQuery.setParameter("employe", user);
        namedQuery.setParameter("name", employmentName);
        return namedQuery.getResultList();
    }
    
}
