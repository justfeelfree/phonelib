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
import phonelib.jpa.EmpPhone;

/**
 *
 * @author user
 */
@Stateless
public class EmpPhoneFacade extends AbstractFacade<EmpPhone> {

    @PersistenceContext(unitName = "PhoneLibAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpPhoneFacade() {
        super(EmpPhone.class);
    }
    
    public List<EmpPhone> findEmpPhonesByNum(String phoneNumber) {
        TypedQuery<EmpPhone> namedQuery = em.createNamedQuery("EmpPhone.findByPhoneNumber", EmpPhone.class);
        namedQuery.setParameter("phoneNumber", phoneNumber);
        return namedQuery.getResultList();
    }
}
