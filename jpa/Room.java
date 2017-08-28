/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonelib.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "room")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r")
    , @NamedQuery(name = "Room.findByRoomId", query = "SELECT r FROM Room r WHERE r.roomId = :roomId")
    , @NamedQuery(name = "Room.findByRoomName", query = "SELECT r FROM Room r WHERE r.roomName = :roomName")
    , @NamedQuery(name = "Room.findByNumAndFilial", 
            query = "SELECT r FROM Room r WHERE r.roomNumber = :roomNumber AND r.filial = :filial")})
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_room")
    @SequenceGenerator(name = "seq_room", sequenceName = "seq_room")
    @NotNull
    @Basic(optional = false)
    @Column(name = "room_id")
    private Long roomId;
    @Size(max = 150)
    @Column(name = "room_name")
    private String roomName;
    @Size(max = 10)
    @Column(name = "room_number")
    private String roomNumber;
    @OneToMany(mappedBy = "room")
    private Collection<Employment> employmentCollection;
    @JoinColumn(name = "filial_id", referencedColumnName = "fil_id")
    @ManyToOne
    private Filial filial;

    public Room() {
    }

    public Room(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        if (roomName == null){
            return "";
        }
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @XmlTransient
    public Collection<Employment> getEmploymentCollection() {
        return employmentCollection;
    }

    public void setEmploymentCollection(Collection<Employment> employmentCollection) {
        this.employmentCollection = employmentCollection;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.roomId);
        hash = 13 * hash + Objects.hashCode(this.roomName);
        hash = 13 * hash + Objects.hashCode(this.roomNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (!Objects.equals(this.roomName, other.roomName)) {
            return false;
        }
        if (!Objects.equals(this.roomNumber, other.roomNumber)) {
            return false;
        }
        if (!Objects.equals(this.roomId, other.roomId)) {
            return false;
        }
        return true;
    }

   

    public String getRoomNumber() {
        if (roomNumber == null){
            return "";
        }
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomDescription() {
        return getRoomNumber() + " " + getRoomName();
    }

    @Override
    public String toString() {
        return getRoomDescription();
    }

}
