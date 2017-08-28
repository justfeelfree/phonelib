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
@Table(name = "filial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filial.findAll", query = "SELECT f FROM Filial f")
    , @NamedQuery(name = "Filial.findByFilId", query = "SELECT f FROM Filial f WHERE f.filialId = :filialId")
    , @NamedQuery(name = "Filial.findByFilialName", query = "SELECT f FROM Filial f WHERE f.filialName = :filialName")
    , @NamedQuery(name = "Filial.findByAddress", query = "SELECT f FROM Filial f WHERE f.address = :address")})
public class Filial implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_filial")
    @SequenceGenerator(name = "seq_filial", sequenceName = "seq_filial")
    @NotNull
    @Basic(optional = false)
    @Column(name = "fil_id")
    private Long filialId;
    @Size(max = 150)
    @Column(name = "filial_name")
    private String filialName;
    @Size(max = 200)
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "filial")
    private Collection<Department> departmentCollection;
    @OneToMany(mappedBy = "filial")
    private Collection<Room> roomCollection;

    public Filial() {
    }

    public Filial(Long filId) {
        this.filialId = filId;
    }

    public Long getFilialId() {
        return filialId;
    }

    public void setFilialId(Long filialId) {
        this.filialId = filialId;
    }

    public String getFilialName() {
        return filialName;
    }

    public void setFilialName(String filialName) {
        this.filialName = filialName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public Collection<Department> getDepartmentCollection() {
        return departmentCollection;
    }

    public void setDepartmentCollection(Collection<Department> departmentCollection) {
        this.departmentCollection = departmentCollection;
    }

    @XmlTransient
    public Collection<Room> getRoomCollection() {
        return roomCollection;
    }

    public void setRoomCollection(Collection<Room> roomCollection) {
        this.roomCollection = roomCollection;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.filialId);
        hash = 41 * hash + Objects.hashCode(this.filialName);
        hash = 41 * hash + Objects.hashCode(this.address);
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
        final Filial other = (Filial) obj;
        if (!Objects.equals(this.filialName, other.filialName)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.filialId, other.filialId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return filialName;
    }

}
