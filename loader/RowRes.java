/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonelib.loader;

/** Класс служит для хранения значений из строки таблицы со свойствами: <br>
 * <b>filialName</b> - филиал <br>
 * <b>departmentName</b> - отдел <br>
 * <b>roomNum</b> - номер комнаты <br>
 * <b>fullName</b> - полное имя пользователя <br>
 * <b>employmentName</b> - название должности <br>
 * <b>specialtyName</b> - специальность <br>
 * <b>managerFullName</b> - полное имя менеджера <br>
 * <b>phoneNumber</b> - номер телефона <br>
 * <b>isMobile</b> - является ли номер мобильным телефоном <br>
 * <b>isPersonal</b> - является ли номер рабочим телефоном <br><br>
 * 
 * <b>isCorrect</b> - отображает корректность всех остальных свойств, <br>
 * для этого достаточно чтобы они просто были заполнены <br><br>
 * 
 * @author Kirill Nigmatullin
 */
public class RowRes {    
    private String filialName;
    
    private String departmentName;
    
    private String roomNum;
    
    private String fullName;
    
    private String employmentName;
    
    private String specialtyName;
    
    private String managerFullName;
    
    private String phoneNumber;
    
    private int isMobile;
    
    private int isPersonal;
    
    private boolean isCorrect = false;  

    public String getFilialName() {
        return filialName;
    }

    public void setFilialName(String filialName) {
        this.filialName = filialName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmploymentName() {
        return employmentName;
    }

    public void setEmploymentName(String employmentName) {
        this.employmentName = employmentName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getManagerFullName() {
        return managerFullName;
    }

    public void setManagerFullName(String managerFullName) {
        this.managerFullName = managerFullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(int isMobile) {
        this.isMobile = isMobile;
    }

    public int getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(int isPersonal) {
        this.isPersonal = isPersonal;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}