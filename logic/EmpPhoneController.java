package phonelib.logic;

import phonelib.jpa.util.JsfUtil;
import phonelib.jpa.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import phonelib.jpa.EmpPhone;
import phonelib.facade.EmpPhoneFacade;

@Named("empPhoneController")
@SessionScoped
public class EmpPhoneController implements Serializable {

    @EJB
    private phonelib.facade.EmpPhoneFacade ejbFacade;
    private List<EmpPhone> items = null;
    private EmpPhone selected;

    public EmpPhoneController() {
    }

    public EmpPhone getSelected() {
        return selected;
    }

    public void setSelected(EmpPhone selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmpPhoneFacade getFacade() {
        return ejbFacade;
    }

    public EmpPhone prepareCreate() {
        selected = new EmpPhone();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EmpPhoneCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EmpPhoneUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EmpPhoneDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EmpPhone> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public EmpPhone getEmpPhone(Long id) {
        return getFacade().find(id);
    }

    public List<EmpPhone> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EmpPhone> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EmpPhone.class)
    public static class EmpPhoneControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmpPhoneController controller = (EmpPhoneController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "empPhoneController");
            return controller.getEmpPhone(getKey(value));
        }

        Long getKey(String value) {
            Long key;
            key = new Long(value);
            return key;
        }

        String getStringKey(Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EmpPhone) {
                EmpPhone o = (EmpPhone) object;
                return o.getEmploymentPhoneId().toString();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EmpPhone.class.getName()});
                return null;
            }
        }

    }

}
