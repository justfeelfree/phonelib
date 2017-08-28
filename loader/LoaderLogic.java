/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonelib.loader;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import phonelib.facade.*;
import phonelib.jpa.*;

/** Класс используется для логики отображения данных из списка объектов RowRes в базу данных <br>
 * <br>saveData</b> - основной метод
 *
 * @author Kirill Nigmatullin
 */

@Model
public class LoaderLogic {
    
    @EJB 
    private phonelib.facade.FilialFacade ejbFilial;
    
    @EJB 
    private phonelib.facade.DepartmentFacade ejbDepartment;
    
    @EJB
    private phonelib.facade.RoomFacade ejbRoom;
    
    @EJB
    private phonelib.facade.SpecialtyFacade ejbSpecialty;
    
    @EJB
    private phonelib.facade.EmployeFacade ejbEmploye;
    
    @EJB
    private phonelib.facade.EmploymentFacade ejbEmployment;
    
    @EJB
    private phonelib.facade.EmpPhoneFacade ejbEmpPhone;
    
    // лог
    private String logMessage = "";
    
    
    public FilialFacade getEjbFilial() {
        return ejbFilial;
    }

    public void setEjbFilial(FilialFacade ejbFilial) {
        this.ejbFilial = ejbFilial;
    }

    public DepartmentFacade getEjbDepartment() {
        return ejbDepartment;
    }

    public void setEjbDepartment(DepartmentFacade ejbDepartment) {
        this.ejbDepartment = ejbDepartment;
    }

    public RoomFacade getEjbRoom() {
        return ejbRoom;
    }

    public void setEjbRoom(RoomFacade ejbRoom) {
        this.ejbRoom = ejbRoom;
    }

    public SpecialtyFacade getEjbSpecialty() {
        return ejbSpecialty;
    }

    public void setEjbSpecialty(SpecialtyFacade ejbSpecialty) {
        this.ejbSpecialty = ejbSpecialty;
    }

    public EmployeFacade getEjbEmploye() {
        return ejbEmploye;
    }

    public void setEjbEmploye(EmployeFacade ejbEmploye) {
        this.ejbEmploye = ejbEmploye;
    }

    public EmpPhoneFacade getEjbEmpPhone() {
        return ejbEmpPhone;
    }

    public void setEjbEmpPhone(EmpPhoneFacade ejbEmpPhone) {
        this.ejbEmpPhone = ejbEmpPhone;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public EmploymentFacade getEjbEmployment() {
        return ejbEmployment;
    }

    public void setEjbEmployment(EmploymentFacade ejbEmployment) {
        this.ejbEmployment = ejbEmployment;
    }
  
    /** Метод ищет данные из объектов RowRes в БД, если не находит, то создает.
     * 
     * @param rowReses - список объектов RowRes с данными из таблицы
     * @return возвращает строку с логом.
     * @see phonelib.loader.RowRes
     */
    public String saveData(List<RowRes> rowReses) {
        
        int rowsCount = 0;
        for (RowRes rowRes : rowReses) {
            rowsCount++;
            logMessage += Integer.toString(rowsCount) + ") ";
            
            if (rowRes.isIsCorrect()) {
                Filial currentFilial = saveFilial(rowRes.getFilialName());
                Department currentDepartment = saveDepartment(rowRes.getDepartmentName(), currentFilial);
                Room currentRoom = saveRoom(rowRes.getRoomNum(), currentFilial);
                Specialty currentSpecialty = saveSpecialty(rowRes.getSpecialtyName());
                Employe currentManager = saveEmploye(rowRes.getManagerFullName(), true);
                Employe currentUser = saveEmploye(rowRes.getFullName(), false);
                Employment currentEmployment = saveEmployment(
                        rowRes.getEmploymentName(),
                        currentDepartment, 
                        currentRoom, 
                        currentSpecialty, 
                        currentManager, 
                        currentUser);
                saveEmpPhone(
                        rowRes.getPhoneNumber(), 
                        rowRes.getIsMobile(), 
                        rowRes.getIsPersonal(), 
                        currentEmployment);
            } else {
                String incorrectRow = "Некорретная строка(одно из полей некорректно): " + 
                        "филиал - " +
                        rowRes.getFilialName() + ", " +
                        "отдел - " + 
                        rowRes.getDepartmentName() + ", " +
                        "комната - " + 
                        rowRes.getRoomNum() + ", " +
                        "специальность - " + 
                        rowRes.getSpecialtyName() + ", " +
                        "менеджер - " + 
                        rowRes.getManagerFullName() + ", " + 
                        "пользователь - " + 
                        rowRes.getFullName() + ", " +
                        "должность - " + 
                        rowRes.getEmploymentName() + ", " +
                        "номер телефона - " + 
                        rowRes.getPhoneNumber() + ", " +
                        "мобильный? - " + 
                        rowRes.getIsMobile() + ", " +
                        "личный? - " + 
                        rowRes.getIsPersonal() + "\n";
                if (rowRes.getIsMobile() == 999 || rowRes.getIsPersonal() == 999) {
                    incorrectRow += "(код 999: значит указано некорректное значение";
                }
                logMessage += incorrectRow;
            }
        }
        
        return logMessage;
    }
    
    private Filial saveFilial(String filialName) {
        List<Filial> filials = getEjbFilial().findFilialsByName(filialName);
        Filial filial = new Filial();
        Iterator<Filial> searchIt = filials.iterator();
        
        String message;
        if(searchIt.hasNext()) {
            message = "Филиал " + filialName + " найден. ";
            filial = searchIt.next();
        } else {
            message = "Филиал " + filialName + " не найден (ДОБАВЛЯЮ) ";
            filial.setFilialName(filialName);
            getEjbFilial().create(filial);
        }
        
        logMessage += message;
        
        return filial;
    }
    
    private Department saveDepartment(String departmentName, Filial filial) {
        List<Department> departments = getEjbDepartment().findDepartmentsByName(departmentName, filial);
        Department department = new Department();
        Iterator<Department> searchIt = departments.iterator();
        
        String message;
        if(searchIt.hasNext()) {
            message = "Отдел " + departmentName + " в этом филиале найден. ";
            department = searchIt.next();
        } else {
            message = "Отдел " + departmentName + " в этом филиале не найден (ДОБАВЛЯЮ) ";
            department.setDepartmentName(departmentName);
            department.setFilial(filial);
            getEjbDepartment().create(department);
        }
        
        logMessage += message;
        
        return department;
    }
    
    private Room saveRoom(String roomNumber, Filial filial) {
        List<Room> rooms = getEjbRoom().findRoomsByNumAndFilial(roomNumber, filial);
        Room room = new Room();
        Iterator<Room> searchIt = rooms.iterator();
        
        String message;
        if(searchIt.hasNext()) {
            message = "Комната " + roomNumber + " в этом филиале найдена. ";
            room = searchIt.next();
        } else {
            message = "Комната " + roomNumber + " в этом филиале не найдена (ДОБАВЛЯЮ). ";
            room.setRoomNumber(roomNumber);
            room.setFilial(filial);
            getEjbRoom().create(room);
        }
        
        logMessage += message;
        
        return room;
    }
    
    private Specialty saveSpecialty(String specialtyName) {
        List<Specialty> specialties = getEjbSpecialty().findSpecialtiesByName(specialtyName);
        Specialty specialty = new Specialty();
        Iterator<Specialty> searchIt = specialties.iterator();
        
        String message;
        if(searchIt.hasNext()) {
            message = "Специальность " + specialtyName + " найдена. ";
            specialty = searchIt.next();
        } else {
            message = "Специальность " + specialtyName + " не найдена (ДОБАВЛЯЮ). ";
            specialty.setName(specialtyName);
            getEjbSpecialty().create(specialty);
        }
        
        logMessage += message;
        
        return specialty;
    }
    
    private Employe saveEmploye(String fullname, boolean isManager) {
        Pattern p = Pattern.compile("([A-zА-яё-]+)\\s*([A-zА-яё-]+)\\s*([A-zА-яё-]+)");
        Matcher m = p.matcher(fullname);
        String secondName = null, firstName = null, middleName = null;
        if(m.find()) {
            secondName = m.group(1);
            firstName = m.group(2);
            middleName = m.group(3);
        } else {
            // сделать ошибку, добавить проверку данных при считывании с таблицы, м.б перенести часть метода в if
        }
        
        List<Employe> employes = getEjbEmploye().findEmployesByFullname(firstName, secondName, middleName);
        Employe employe = new Employe();
        Iterator<Employe> searchIt = employes.iterator();
        
        String message = isManager == true ? "Менеджер " : "Сотрудник " ;
        if(searchIt.hasNext()) {
            message += fullname + " найден. ";
            employe = searchIt.next();
        } else {
            message += fullname + " не найден (ДОБАВЛЯЮ). ";
            employe.setFirstName(firstName);
            employe.setSecondName(secondName);
            employe.setMiddleName(middleName);
            getEjbEmploye().create(employe);
        }
        
        logMessage += message;
        
        return employe;
    }
    
    private Employment saveEmployment(
            String employmentName,
            Department department, 
            Room room, 
            Specialty specialty, 
            Employe manager, 
            Employe user
    ) {
        
        List<Employment> employments = getEjbEmployment().findEmploymentsByUserAndName(user, employmentName);
        Employment employment = new Employment();
        Iterator<Employment> searchIt = employments.iterator();
        
        String message;
        if(searchIt.hasNext()) {
            message = "Должность " + employmentName + " найдена. ";
            employment = searchIt.next();
        } else {
            message = "Должность " + employmentName + " не найдена (ДОБАВЛЯЮ). ";
            employment.setDepartment(department);
            employment.setRoom(room);
            employment.setSpeciality(specialty);
            employment.setManager(manager);
            employment.setEmploye(user);
            employment.setName(employmentName);
            getEjbEmployment().create(employment);
        }
        
        logMessage += message;
        
        return employment;
    }
    
    private EmpPhone saveEmpPhone(
            String phoneNumber, 
            int mobile, 
            int workphone, 
            Employment employment
    ) {
        boolean isMobile = mobile == 1 ? true : false;
        boolean isWorkPhone = workphone == 1 ? true : false;
        
        List<EmpPhone> empPhones = getEjbEmpPhone().findEmpPhonesByNum(phoneNumber);
        EmpPhone empPhone = new EmpPhone();
        Iterator<EmpPhone> searchIt = empPhones.iterator();
        
        String message;
        if(searchIt.hasNext()) {
            message = "Телефон " + phoneNumber + " найден. \n";
            empPhone = searchIt.next();
        } else {
            message = "Телефон " + phoneNumber + " не найден (ДОБАВЛЯЮ). \n";
            empPhone.setPhoneNumber(phoneNumber);
            empPhone.setEmployment(employment);
            empPhone.setIsMobile(isMobile);
            empPhone.setIsWorkPhone(isWorkPhone);
            getEjbEmpPhone().create(empPhone);
        }
        
        logMessage += message;
        
        return empPhone;
    }
}
