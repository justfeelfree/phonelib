
package phonelib.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import phonelib.exceptions.HeadersNotFoundException;
import phonelib.exceptions.RowsNotFoundException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** Класс служит для взаимодействия со страницами Load.xhtml и success.xhtml,
 * содержит основную логику модуля loader в методе save.
 * Свойства: <br>
 * <b>file</b> - файл, загружаемый через пользовательский интерфейс <br>
 * <b>msg</b> - лог <br>
 * Методы: <br>
 * <b>save()</b> - обрабатывает загружаемый файл, сохраняет его на сервер, извлекает
 * из него ресурсы, запускает логику отображения в базу данных.<br>
 * 
 * @see phonelib.loader.LoaderLogic
 * @author Кирилл
 */

@ManagedBean
@SessionScoped
public class LoaderBean {
    
    @Inject
    private LoaderLogic logic;
    private String logMessage = "";
    private Part file;

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
     
    /** Метод содержит основную логику модуля loader
     * 
     * @return возвращает страницу success.xhtml
     * 
     */
    public String save() {
        
        String fullPath = getFullPath();
        try {
            saveFile(fullPath);
        } catch (IOException ex) {
            logMessage += "не удалось сохранить файл\n" + ex + "\n";
            return "success";
        }
        
        List<RowRes> rowReses;
        try {
            rowReses = getResources(fullPath);
        } catch (IOException ex) {
            logMessage += "файл не найден\n" + ex + "\n";
            return "success";
        } catch (HeadersNotFoundException ex) {
            logMessage += "в таблице не найдены корректные заголовки\n" + ex + "\n";
            return "success";
        } 
        
        logMessage += logic.saveData(rowReses);
        
        return "success";
    }
    
    
    private String getFullPath() {
        ServletContext servletContext = (ServletContext)FacesContext
                                               .getCurrentInstance()
                                               .getExternalContext()
                                               .getContext();
        String path = servletContext.getRealPath("/");
        File uploadFolder = new File(path + "/upload");
        
        if (uploadFolder.exists() == false) {
            uploadFolder.mkdir();
            logMessage += "Папка upload не найдена (СОЗДАЮ)\n";
        } else {
            logMessage += "Папка upload найдена\n";
        }
        
        String fullPath = uploadFolder.getPath() + File.separator + file.getSubmittedFileName();
        return fullPath;
    }
    
    private void saveFile(String fullPath) throws IOException {
        InputStream input = file.getInputStream();
        FileOutputStream output = new FileOutputStream(fullPath);
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while (true) {
            bytesRead = input.read(buffer);
            if (bytesRead > 0) {
                output.write(buffer, 0, bytesRead);
            } else {
                break;
            }
        }
        output.close();
        input.close();
        
        logMessage += String.format("Файл %s сохранен\n", file.getSubmittedFileName());
    }
    
    private List<RowRes> getResources(String path) throws HeadersNotFoundException, IOException {
        TableRes tableRes = new TableRes();
        List<RowRes> rowReses = tableRes.getRes(path);
        
        int countCorrect = 0, countAll = 0;
        for (RowRes rowRes : rowReses) {
            if (rowRes.isIsCorrect()) {
                countCorrect++;
            }
            countAll++;
            
        }
        
        // сделать обработку двух ошибок - заголовки не найдены, или заголовки найдены но ни одной строки не найдено
        /*
        if (countAll == 0) {
            throw new RowsNotFoundException("В таблице не удалось найти все заголовки");
        }
        */
        
        logMessage += "Найдено строк: " + Integer.toString(countAll) + "\n";
        logMessage += "Количество корректных строк: " + Integer.toString(countCorrect) + "\n";
                
        return rowReses;
    }
        
    /** Валидатор для загрузки файла в Load.xhtml
     * 
     */
    public void validate(FacesContext context, UIComponent component, Object value) {          
        if (value == null) {
            throw new ValidatorException(new FacesMessage("Choose file..."));
        }
        
        Part file = (Part) value;
        if (file.getSize() > 1_000_000) {
            throw new ValidatorException(new FacesMessage("File is too large"));
        }
        
        // константа XLSX соответствует content-type файла .xlsx
        final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        // константа XLS соответствует content-type файла .xls
        final String XLS = "application/vnd.ms-excel";
        
        if (file.getContentType().equals(XLS)) {
            throw new ValidatorException(new FacesMessage("xls files doesn't supported"));
        }
        
        if (!file.getContentType().equals(XLSX)) {
            throw new ValidatorException(new FacesMessage("File is not a xlsx file"));
        }
    }
}
