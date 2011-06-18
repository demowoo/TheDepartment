package UserBeans;

import ConnectionDataBase.CapacityHelper;
import ConnectionDataBase.Playerresources;
import Connections.ConnectionSingleton;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import utilities.BasicUtils;
import utilities.Lecturer;
import utilities.LecturerBenefits;
import utilities.LecturersManager;


public class BuyLecturers  {

    private Lecturer selected_lecturer;
    private List<Lecturer> lecturers;

    private String username;

    /** Creates a new instance of BuyLecturers */
    public BuyLecturers() {}

    public String getUsername() {
        return username;
    }
    
    public void initialize(String username) {

        this.username = username;

        LecturersManager mgr
                = new LecturersManager(username);
        lecturers = mgr.getAvailabeLecturers();
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> input) {
        lecturers = input;
    }

    public Lecturer getSelected () {
        return selected_lecturer;
    }

    public void setSelected(Lecturer lec) {
        selected_lecturer = lec;
    }

    public List<LecturerBenefits> getBenefitsList() {
        return selected_lecturer.getSpecializations();
    }

    public String buy() {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Auth auth = (Auth) session.getAttribute(ConnectionSingleton.auth);

        LecturersManager mgr
                = new LecturersManager(utilities.BasicUtils.getUserName());


        Playerresources resources = auth.getResources();
        CapacityHelper capacityhelper = new CapacityHelper();
        if( capacityhelper.getCapacity(auth.getRemember()).getProfessorscapacity() <=  mgr.getOwnedLecturers().size() ){

                FacesContext.getCurrentInstance().addMessage(
                BasicUtils.findComponent(facesContext.getViewRoot(),"submitProf").getClientId(facesContext),
                new FacesMessage(FacesMessage.SEVERITY_ERROR," Me ", "Not enought space for new Prof!!!"));
                return "fail";

        } else if ( resources.getMoney() < selected_lecturer.getPrice()){

             FacesContext.getCurrentInstance().addMessage(
             BasicUtils.findComponent(facesContext.getViewRoot(),"submitProf").getClientId(facesContext),
             new FacesMessage(FacesMessage.SEVERITY_ERROR," Me ", "This guy is too expensive for your DoC!!!"));
             return "fail";
        }

        mgr.purchaseLecturer(selected_lecturer.getName());

        return "success";
    }




}