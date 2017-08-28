/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonelib.jpa;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "emp_phone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpPhone.findAll", query = "SELECT e FROM EmpPhone e")
    , @NamedQuery(name = "EmpPhone.findByEpId", query = "SELECT e FROM EmpPhone e WHERE e.employmentPhoneId = :employmentPhoneId")
    , @NamedQuery(name = "EmpPhone.findByPhoneNumber", query = "SELECT e FROM EmpPhone e WHERE e.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "EmpPhone.findByMobile", query = "SELECT e FROM EmpPhone e WHERE e.isMobile = :isMobile")})
public class EmpPhone implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_emp_phone")
    @SequenceGenerator(name = "seq_emp_phone", sequenceName = "seq_emp_phone")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ep_id")
    private Long employmentPhoneId;
    @Size(max = 100)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "mobile")
    private Boolean isMobile;
    @Column(name = "work_phone")
    private Boolean isWorkPhone;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne
    private Employment employment;

    public EmpPhone() {
    }

    public EmpPhone(Long epId) {
        this.employmentPhoneId = epId;
    }

    public Long getEmploymentPhoneId() {
        return employmentPhoneId;
    }

    public void setEmploymentPhoneId(Long employmentPhoneId) {
        this.employmentPhoneId = employmentPhoneId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public Boolean getIsWorkPhone() {
        return isWorkPhone;
    }

    public void setIsWorkPhone(Boolean isWorkPhone) {
        this.isWorkPhone = isWorkPhone;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.employmentPhoneId);
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
        final EmpPhone other = (EmpPhone) obj;
        if (!Objects.equals(this.employmentPhoneId, other.employmentPhoneId)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return phoneNumber;
    }

}
