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
@Table(name = "specialty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Specialty.findAll", query = "SELECT s FROM Specialty s")
    , @NamedQuery(name = "Specialty.findBySpecId", query = "SELECT s FROM Specialty s WHERE s.specialityId = :specialityId")
    , @NamedQuery(name = "Specialty.findByName", query = "SELECT s FROM Specialty s WHERE s.name = :name")})
public class Specialty implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_speciality")
    @SequenceGenerator(name = "seq_speciality", sequenceName = "seq_speciality")
    @NotNull
    @Basic(optional = false)
    @Column(name = "spec_id")
    private Long specialityId;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "speciality")
    private Collection<Employment> employmentCollection;

    public Specialty() {
    }

    public Specialty(Long specId) {
        this.specialityId = specId;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash = 47 * hash + Objects.hashCode(this.specialityId);
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.employmentCollection);
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
        final Specialty other = (Specialty) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.specialityId, other.specialityId)) {
            return false;
        }
        if (!Objects.equals(this.employmentCollection, other.employmentCollection)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return name;
    }
    
}
