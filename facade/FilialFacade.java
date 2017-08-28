/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonelib.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import phonelib.jpa.AbstractFacade;
import phonelib.jpa.Filial;

/**
 *
 * @author user
 */
@Stateless
public class FilialFacade extends AbstractFacade<Filial> {

    @PersistenceContext(unitName = "PhoneLibAppPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public FilialFacade() {
        super(Filial.class);
    }
    
    public List<Filial> findFilialsByName(String filialName){
        TypedQuery<Filial> namedQuery = em.createNamedQuery("Filial.findByFilialName", Filial.class);
        namedQuery.setParameter("filialName", filialName);
        return namedQuery.getResultList();
    }
}
