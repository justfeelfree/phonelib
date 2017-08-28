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
@Table(name = "department")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d")
    , @NamedQuery(name = "Department.findByDepartmentId", query = "SELECT d FROM Department d WHERE d.departmentId = :departmentId")
    , @NamedQuery(name = "Department.findByDepName", query = "SELECT d FROM Department d WHERE d.departmentName = :departmentName")
    , @NamedQuery(name = "Department.findByNameAndFilial", 
            query = "SELECT d FROM Department d WHERE d.departmentName = :departmentName AND d.filial = :filial")})
public class Department implements Serializable {
    private static final Long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqdep")
    @SequenceGenerator(name = "seqdep", sequenceName = "seqdep")
    @Column(name = "dep_id")
    @Basic(optional = false)
    @NotNull
    private Long departmentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "dep_name")
    private String departmentName;
    @OneToMany(mappedBy = "department")
    private Collection<Employment> employmentCollection;
    @JoinColumn(name = "filial_id", referencedColumnName = "fil_id")
    @ManyToOne
    private Filial filial;

    public Department() {
    }

    public Department(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Department(Long departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.departmentId);
        hash = 89 * hash + Objects.hashCode(this.departmentName);
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
        final Department other = (Department) obj;
        if (!Objects.equals(this.departmentName, other.departmentName)) {
            return false;
        }
        if (!Objects.equals(this.departmentId, other.departmentId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return departmentName;
    }

}
