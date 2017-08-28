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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "employment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employment.findAll", query = "SELECT e FROM Employment e")
    , @NamedQuery(name = "Employment.findByEmpId", query = "SELECT e FROM Employment e WHERE e.employmentId = :employmentId")
    , @NamedQuery(name = "Employment.findByEmployeAndName", 
            query = "SELECT e FROM Employment e WHERE e.employe = :employe AND e.name = :name")})
public class Employment implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_employment")
    @SequenceGenerator(name = "seq_employment", sequenceName = "seq_employment")
    @NotNull
    @Basic(optional = false)
    @Column(name = "emp_id")
    private Long employmentId;
    @OneToMany(mappedBy = "employment")
    private Collection<EmpPhone> empPhoneCollection;
    @JoinColumn(name = "dep_id", referencedColumnName = "dep_id")
    @ManyToOne
    private Department department;
    @JoinColumn(name = "manager_id", referencedColumnName = "user_id")
    @ManyToOne
    private Employe manager;
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    @ManyToOne
    private Room room;
    @JoinColumn(name = "speciality_id", referencedColumnName = "spec_id")
    @ManyToOne
    private Specialty speciality;
    @JoinColumn(name = "employe_id", referencedColumnName = "user_id")
    @ManyToOne
    private Employe employe;
    private String name;

    public Employment() {
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    
    public Employment(Long empId) {
        this.employmentId = empId;
    }

    public Long getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(Long employmentId) {
        this.employmentId = employmentId;
    }

    @XmlTransient
    public Collection<EmpPhone> getEmpPhoneCollection() {
        return empPhoneCollection;
    }

    public void setEmpPhoneCollection(Collection<EmpPhone> empPhoneCollection) {
        this.empPhoneCollection = empPhoneCollection;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employe getManager() {
        return manager;
    }

    public void setManager(Employe manager) {
        this.manager = manager;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Specialty getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Specialty speciality) {
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.employmentId);
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
        final Employment other = (Employment) obj;
        return true;
    }

    

   

    @Override
    public String toString() {
        return "специальность: " + speciality.getName() + " отдел: " + department.getDepartmentName();
    }

}
