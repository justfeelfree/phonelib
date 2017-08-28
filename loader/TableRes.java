/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonelib.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import phonelib.exceptions.HeadersNotFoundException;

/** Класс служит для обработки файла .xlsx, 
 * поиска в нем данных и создания списка объектов RowRes на основе этих данных <br>
 * <b>getRes()</b> - основной методо класса
 * 
 * @author Kirill Nigmatullin
 */
public class TableRes  {
    
    /**
     * 
     * @param path - путь к файлу .xlsx
     * @return список объектов RowRes с данными из таблицы
     * @see phonelib.loader.RowRes
     */
    private final int ERROR = 999;
    private String errorMessage = "Ошибка нахождения заголовков.";
    
    public List<RowRes> getRes(String path) throws HeadersNotFoundException, FileNotFoundException, IOException {
        List<RowRes> allRows = new ArrayList<RowRes>();
        
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path));
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        if (headersVerification(rows) == false) {
            throw new HeadersNotFoundException(errorMessage);
        }

        // получаем строку с ресурсами и добавляем в список строк ресурсов
        while (rows.hasNext()) {
            Row row = rows.next();
            RowRes rowRes = rowVerification(row);
            allRows.add(rowRes);
        }
        
        return allRows;
    }
    
    private boolean headersVerification(Iterator<Row> rows) throws HeadersNotFoundException {
        boolean isFilialHeaderCorrect = false, 
                isDepartmentHeaderCorrect = false, 
                isRoomHeaderCorrect = false, 
                isFioHeaderCorrect = false,
                isEmploymentHeaderCorrect = false, 
                isSpecialtyHeaderCorrect = false,
                isManagerHeaderCorrect = false, 
                isPnoneNumHeaderCorrect = false,
                isMobileHeaderCorrect = false, 
                isPersonalHeaderCorrect = false;
        
        if (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.iterator();
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("филиал")) isFilialHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("отдел")) isDepartmentHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("комната")) isRoomHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("фио")) isFioHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("должность")) isEmploymentHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("специальность")) isSpecialtyHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("руководитель (фио)")) isManagerHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("телефон")) isPnoneNumHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("мобильный?")) isMobileHeaderCorrect = true;
                }
            }
            
            if (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                    String header = cell.getStringCellValue().trim().toLowerCase();
                    if (header.equals("Личный?")) isPersonalHeaderCorrect = true;
                }
            }
                    
        } else {
            return false;
        }
        
        if (isFilialHeaderCorrect == false) errorMessage += " Не найден заголовок 'Филиал'.";
        if (isDepartmentHeaderCorrect == false) errorMessage += " Не найден заголовок 'Отдел'.";
        if (isRoomHeaderCorrect == false) errorMessage += " Не найден заголовок 'Комната'.";
        if (isFioHeaderCorrect == false) errorMessage += " Не найден заголовок 'ФИО'.";
        if (isEmploymentHeaderCorrect == false) errorMessage += " Не найден заголовок 'Должность'.";
        if (isSpecialtyHeaderCorrect == false) errorMessage += " Не найден заголовок 'Специальность'.";
        if (isManagerHeaderCorrect == false) errorMessage += " Не найден заголовок 'Руководитель (ФИО)'.";
        if (isPnoneNumHeaderCorrect == false) errorMessage += " Не найден заголовок 'Телефон'.";
        if (isMobileHeaderCorrect == false) errorMessage += " Не найден заголовок 'Мобильный?'.";
        if (isPersonalHeaderCorrect == false) errorMessage += " Не найден заголовок 'Личный?'.";
        
        if (isFilialHeaderCorrect == true &&
                isDepartmentHeaderCorrect == true &&
                isRoomHeaderCorrect == true &&
                isFioHeaderCorrect == true &&
                isEmploymentHeaderCorrect == true &&
                isSpecialtyHeaderCorrect == true &&
                isManagerHeaderCorrect == true &&
                isPnoneNumHeaderCorrect == true &&
                isMobileHeaderCorrect == true &&
                isPersonalHeaderCorrect == true) {
            return true;
        } else {
            return false;
        }
    }
    
    private RowRes rowVerification(Row row){        
        boolean isFilialCorrect, 
                isDepartmentCorrect, 
                isRoomCorrect, 
                isFioCorrect,
                isEmploymentCorrect, 
                isSpecialtyCorrect,
                isManagerCorrect, 
                isPnoneNumCorrect,
                isMobileCorrect, 
                isPersonalCorrect;
                
        RowRes rowRes = new RowRes();
        Iterator<Cell> cells = row.iterator();

        // устанавливаем значения из ячеек таблицы в объект RowRes, возвращаем true если значение корректно
        isFilialCorrect = setRowFilial(cells, rowRes);
        isDepartmentCorrect = setRowDepartment(cells, rowRes);
        isRoomCorrect = setRowRoom(cells, rowRes);
        isFioCorrect = setRowFio(cells, rowRes);
        isEmploymentCorrect = setRowEmployment(cells, rowRes);
        isSpecialtyCorrect = setRowSpecialty(cells, rowRes);
        isManagerCorrect = setRowManager(cells, rowRes);
        isPnoneNumCorrect = setRowNum(cells, rowRes);
        isMobileCorrect = setRowMobile(cells, rowRes);
        isPersonalCorrect = setRowPersonal(cells, rowRes);
        
        // если все значения корректны, объект RowRes полностью корректен
        if (isFilialCorrect && 
                isDepartmentCorrect &&
                isRoomCorrect && 
                isFioCorrect &&
                isEmploymentCorrect && 
                isSpecialtyCorrect &&
                isManagerCorrect && 
                isPnoneNumCorrect &&
                isMobileCorrect && 
                isPersonalCorrect
                ) {
            rowRes.setIsCorrect(true);
        }  
        
        return rowRes;
    }
    
    private boolean setRowFilial(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        if (cells.hasNext()){
            Cell cell = cells.next();
            if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                String filial = cell.getStringCellValue();
                if (filial.length() <= 255) {
                    correct = true;
                    rowRes.setFilialName(filial);
                } else {
                    rowRes.setFilialName("некорректная строка(слишком длинная)");
                }
            } else {
                rowRes.setFilialName("некорректный тип данных");
            }
        }
        
        return correct;
    }

    private boolean setRowDepartment(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        if (cells.hasNext()){
            Cell cell = cells.next();
            if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                String department = cell.getStringCellValue();
                rowRes.setDepartmentName(department);
                if (department.length() <= 255) {
                    correct = true;
                    rowRes.setDepartmentName(department);
                } else {
                    rowRes.setDepartmentName("некорректная строка(слишком длинная)");
                }
            } else {
                rowRes.setDepartmentName("некорректный тип данных");
            }
        }
        
        return correct;
    }

    private boolean setRowRoom(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        String room = "";
        if (cells.hasNext()){
            Cell cell = cells.next();
            switch (cell.getCellType()) {
                case(XSSFCell.CELL_TYPE_NUMERIC):
                    room = Integer.toString((int)cell.getNumericCellValue());
                    break;
                case(XSSFCell.CELL_TYPE_STRING):
                    room = cell.getStringCellValue();
                    break;
                default:
                    room = "error";
                    break;
            }
        }
        
        if (room.equals("error") || room.length() > 10) {
            rowRes.setRoomNum("некорректное значение");
        } else {
            correct = true;
            rowRes.setRoomNum(room);
        }
        
        return correct;
    }
        
    private boolean setRowFio(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        String fio = "";
        if (cells.hasNext()){
            Cell cell = cells.next();
            if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                fio = cell.getStringCellValue();
            } else fio = "error";
        }
        
        if (fio.equals("error") || fio.length() > 255) {
            rowRes.setFullName("некорректное значение");
        } else {

            // добавить проверку с помощью REGEXP
            correct = true;
            rowRes.setFullName(fio);
        }
        
        return correct;
    }
    
    private boolean setRowEmployment(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        if (cells.hasNext()){
            Cell cell = cells.next();
            if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                String employment = cell.getStringCellValue();
                if (employment.length() <= 50) { 
                    correct = true;
                    rowRes.setEmploymentName(employment);
                } else {
                    rowRes.setEmploymentName("некорректная строка(слишком длинная)");
                }
            } else {
                rowRes.setEmploymentName("некорректный тип данных");
            }
        }
        
        return correct;
    }
    
    private boolean setRowSpecialty(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        if (cells.hasNext()){
            Cell cell = cells.next();
            if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                String specialty = cell.getStringCellValue();
                if (specialty.length() <= 100) { 
                    correct = true;
                    rowRes.setSpecialtyName(specialty);
                } else {
                    rowRes.setSpecialtyName("некорректная строка(слишком длинная)");
                }
            } else {
                rowRes.setSpecialtyName("некорректный тип данных");
            }
        }
        
        return correct;
    }
    
    private boolean setRowManager(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        String managerFio = "";
        if (cells.hasNext()){
            Cell cell = cells.next();
            if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                managerFio = cell.getStringCellValue();
            }  else managerFio = "error";
        }
        
        if (managerFio.equals("error") || managerFio.length() > 255) {
            rowRes.setFullName("некорректное значение");
        } else {

            // добавить проверку с помощью REGEXP
            correct = true;
            rowRes.setManagerFullName(managerFio);
        }
        
        return correct;
    }

    private boolean setRowNum(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        String phoneNum = "";
        if (cells.hasNext()){
            Cell cell = cells.next();
            switch(cell.getCellType()) {
                case (XSSFCell.CELL_TYPE_NUMERIC) :
                    phoneNum = Integer.toString((int)cell.getNumericCellValue());
                    break;
                case (XSSFCell.CELL_TYPE_STRING) :
                    phoneNum = cell.getStringCellValue();
                    break;
                default:
                    phoneNum = "error";
                    break;            
            }
        }
        
        if (phoneNum.equals("error") || phoneNum.length() > 100) {
            rowRes.setPhoneNumber("некорректное значение");
        } else {
            correct = true;
            rowRes.setPhoneNumber(phoneNum);
        }
        
        return correct;
    }
    
    private boolean setRowMobile(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        int mobile = -1;
        if (cells.hasNext()){
            Cell cell = cells.next();
            if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
                mobile = (int)cell.getNumericCellValue();
            } else {
                mobile = ERROR;
            }
        }
        
        if (mobile == 0 || mobile == 1) {
            correct = true;
            rowRes.setIsMobile(mobile);
        } else {
            rowRes.setIsMobile(ERROR);
        }
        
        return correct;
    }
    
    private boolean setRowPersonal(Iterator<Cell> cells, RowRes rowRes) {
        boolean correct = false;
        
        int personal = -1;
        if (cells.hasNext()){
            Cell cell = cells.next();
            if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
                personal = (int)cell.getNumericCellValue();
            } else {
                personal = ERROR;
            }
        }
        
        if (personal == 0 || personal == 1) {
            correct = true;
            rowRes.setIsPersonal(personal);
        } else {
            rowRes.setIsPersonal(ERROR);
        }
         
        return correct;
    }
}


