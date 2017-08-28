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
import phonelib.jpa.Filial;
import phonelib.jpa.Specialty;

/**
 *
 * @author user
 */
@Stateless
public class SpecialtyFacade extends AbstractFacade<Specialty> {

    @PersistenceContext(unitName = "PhoneLibAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpecialtyFacade() {
        super(Specialty.class);
    }
    
    public List<Specialty> findSpecialtiesByName(String specialtyName){
        TypedQuery<Specialty> namedQuery = em.createNamedQuery("Specialty.findByName", Specialty.class);
        namedQuery.setParameter("name", specialtyName);
        return namedQuery.getResultList();
    }
    
}
