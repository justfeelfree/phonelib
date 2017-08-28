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
import phonelib.jpa.Filial;

/**
 *
 * @author user
 */
@Stateless
public class DepartmentFacade extends AbstractFacade<Department> {

    @PersistenceContext(unitName = "PhoneLibAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartmentFacade() {
        super(Department.class);
    }
    
    public List<Department> findDepartmentsByName(String departmentName, Filial filial){
        TypedQuery<Department> namedQuery = em.createNamedQuery("Department.findByNameAndFilial", Department.class);
        namedQuery.setParameter("departmentName", departmentName);
        namedQuery.setParameter("filial", filial);
        return namedQuery.getResultList();
    }
}
