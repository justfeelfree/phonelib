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
@Table(name = "employe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employe.findAll", query = "SELECT e FROM Employe e")
    , @NamedQuery(name = "Employe.findByUserId", query = "SELECT e FROM Employe e WHERE e.userId = :userId")
    , @NamedQuery(name = "Employe.findByFirstName", query = "SELECT e FROM Employe e WHERE e.firstName = :firstName")
    , @NamedQuery(name = "Employe.findBySecondName", query = "SELECT e FROM Employe e WHERE e.secondName = :secondName")
    , @NamedQuery(name = "Employe.findByMiddleName", query = "SELECT e FROM Employe e WHERE e.middleName = :middleName")
    , @NamedQuery(name = "Employe.findByFullname", 
            query = "SELECT e FROM Employe e WHERE e.firstName = :firstName AND e.secondName = :secondName AND e.middleName = :middleName")})
public class Employe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_employe")
    @SequenceGenerator(name = "seq_employe", sequenceName = "seq_employe")
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private Long userId;
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 100)
    @Column(name = "second_name")
    private String secondName;
    @Size(max = 100)
    @Column(name = "middle_name")
    private String middleName;
    @OneToMany(mappedBy = "manager")
    private Collection<Employment> employmentCollection;

    public Employe() {
    }

    public Employe(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        if (firstName == null) {
            return "";
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        if (secondName == null) {
            return "";
        }
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        if (middleName == null) {
            return "";
        }
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @XmlTransient
    public Collection<Employment> getEmploymentCollection() {
        return employmentCollection;
    }

    public void setEmploymentCollection(Collection<Employment> employmentCollection) {
        this.employmentCollection = employmentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.userId);
        hash = 41 * hash + Objects.hashCode(this.firstName);
        hash = 41 * hash + Objects.hashCode(this.secondName);
        hash = 41 * hash + Objects.hashCode(this.middleName);
        hash = 41 * hash + Objects.hashCode(this.employmentCollection);
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
        final Employe other = (Employe) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.secondName, other.secondName)) {
            return false;
        }
        if (!Objects.equals(this.middleName, other.middleName)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.employmentCollection, other.employmentCollection)) {
            return false;
        }
        return true;
    }


    public String getFullName() {
        return getSecondName() + " " + getFirstName() + " " + getMiddleName();
    }

    @Override
    public String toString() {
        return getFullName();
    }

}
