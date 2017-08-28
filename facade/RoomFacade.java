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
import phonelib.jpa.Room;

/**
 *
 * @author user
 */
@Stateless
public class RoomFacade extends AbstractFacade<Room> {

    @PersistenceContext(unitName = "PhoneLibAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoomFacade() {
        super(Room.class);
    }
 
    public List<Room> findRoomsByNumAndFilial(String roomNumber, Filial filial){
        TypedQuery<Room> namedQuery = em.createNamedQuery("Room.findByNumAndFilial", Room.class);
        namedQuery.setParameter("roomNumber", roomNumber);
        namedQuery.setParameter("filial", filial);
        return namedQuery.getResultList();
    }
}
