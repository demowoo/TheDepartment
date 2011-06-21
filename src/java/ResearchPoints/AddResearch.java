/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ResearchPoints;

import ConnectionDataBase.LecturersHelper;
import ConnectionDataBase.Research;
import Connections.ConnectionSingleton;
import Connections.UserManager;
import UserBeans.Auth;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.TreeNode;
import specializationsGenerator.SpecializationsGenerator;
import utilities.BasicUtils;
import utilities.BoostUtils;
import utilities.Lecturer;
import utilities.LecturerBenefits;
import utilities.LecturersManager;

/**
 *
 * @author karol
 */
public class AddResearch implements Serializable {

    private static List<SelectItem> subjects = new ArrayList<SelectItem>();

    static {
        // Adding subjects names.
        for (int i = 0; i < getSubjectList().length; i++) {
            subjects.add(new SelectItem(new Integer(i), getSubjectList()[i]));
        }
    }
    /* The title of the research */
    private String name;

    /* Research points awarded to the player for successful completion */
    private int ResearchPoints;

    /* A vector of choosen lecturers. */
    private List<String> chosenLecturers = new ArrayList<String>();
    private List<SelectItem> lecturers;
    private List<SelectItem> researchesList;
    private List<ResearchTreeNode> availableResearches = new ArrayList<ResearchTreeNode>();
    private List<Lecturer> owned_lecturers;
    private Integer moneyAmount;
    private Integer undergraduates;
    private Integer phds;
    private Integer chosenResearch;
    private ResearchBag researchBag;
    private Auth auth;
    private Integer subject;  // The index of the selected item
    // can be given as String/Integer/int ...
    private boolean initialized = false;
    private boolean confirmation;
    

    public void initialize(HttpSession session) {
        researchBag = (ResearchBag) session.getAttribute(Connections.ConnectionSingleton.researchBag);
        auth = (Auth) session.getAttribute(ConnectionSingleton.auth);

        moneyAmount = 0;
        undergraduates = 0;
        phds = 0;
        chosenResearch = 0;
        subject = 0;

        initialized = true;
    }

    public int getPhds() {
        return phds;
    }

    public void setPhds(int phds) {
        this.phds = phds;
    }

    public int getUndergraduates() {
        return undergraduates;
    }

    public void setUndergraduates(int undergraduates) {
        this.undergraduates = undergraduates;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public List<SelectItem> getSubjects() {
        return subjects;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {

        if(confirmation)
            return;


        this.subject = subject;

        System.out.println("Current subject is: " + getSubject());
        System.out.println("Current subject name is: " + getSubjectName());

        researchesList = new ArrayList<SelectItem>();
        availableResearches = new ArrayList<ResearchTreeNode>();

        List<ResearchTreeNode> subjectList =
                ResearchDevelopment.getFirstResearch(subject);

        List<Integer> integerList = researchBag.getAvailableResearch();

        int i = 0;
        Iterator<Integer> iterator = integerList.iterator();
        while (iterator.hasNext()) {
            Integer researchId = iterator.next();
            ResearchTreeNode researchNode = ResearchDevelopment.getResearchTreeNode(researchId);

            if (researchNode.getResearchInstance().getSubjectid() == subject) {
                availableResearches.add(researchNode);
                researchesList.add(new SelectItem(new Integer(i), researchNode.toString()));
            }
        }

        String playerName = utilities.BasicUtils.getUserName();
        LecturersManager mgr = new LecturersManager(playerName);
        List<Lecturer> owned = mgr.getAssossiatedLecturers(SpecializationsGenerator.subjectList[getSubject()]);

        lecturers = new ArrayList<SelectItem>();
        owned_lecturers = new ArrayList<Lecturer>();

        Iterator<Lecturer> lecturerIterator = owned.iterator();
        i = 0;

        while (lecturerIterator.hasNext()) {
            Lecturer lec = lecturerIterator.next();
            List<LecturerBenefits> list = lec.getSpecializations();

            lecturers.add(new SelectItem(new Integer(i++), lec.getName()));
            owned_lecturers.add(lec);
        }


    }

    public void setLecturers(List chosenLects) {

        chosenLecturers = new ArrayList<String>();

        for (int i = 0; i < chosenLects.size(); i++) {
            String selected_item = chosenLects.get(i).toString();
            SelectItem item = lecturers.get(Integer.parseInt(selected_item));
            chosenLecturers.add(item.getLabel());
        }
    }

    public Integer getChosenResearch() {
        return chosenResearch;
    }

    public void setChosenResearch(Integer chosenResearch) {
        this.chosenResearch = chosenResearch;
    }

    public String getResearchName() {
        ResearchTreeNode selectedNode = availableResearches.get(getChosenResearch());

        return selectedNode.toString();
    }

    public List<SelectItem> getAvailableResearches() {
        return researchesList;
    }

    public List getLecturers() {
        return new ArrayList();
    }

    public List getLecturerList() {

        return lecturers;
    }

    public void setResearchPoints(int rp) {
        ResearchPoints = rp;
    }

    public int getResearchPoints() {
        return ResearchPoints;
    }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Integer moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Integer getMinimumMoney() {
        return new Integer(0);
    }

    public Integer getMaximumMoney() {
        return new Integer(100);
    }

    public String getSubjectName() {
        return getSubjectList()[getSubject()];
    }

    public void startResearch() {

        confirmation = false;

        if(!validateSubject()) {
            return;
        } else if(chosenLecturers.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(BasicUtils.findComponent(facesContext.getViewRoot(),
                    "lecturersList").getClientId(facesContext),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No lecturer chosen",
                    "You must chose at least one lecturer for your research!"));
        }
        
        System.out.println("Chosen integer: " + getChosenResearch());
        System.out.println("Name: " + getResearchName());
        
        System.out.println("starting research " + availableResearches.get(getChosenResearch()).getResearchInstance().getResearchname()
                + " at " + availableResearches.get(getChosenResearch()).getResearchInstance().getSubjectid());

        Research research = new Research(auth, availableResearches.get(getChosenResearch()).
                getResearchInstance().getResearchid());

        /* Reading lecturers from the database. */
        LecturersManager mgr = new LecturersManager(utilities.BasicUtils.getUserName());
        ArrayList<Lecturer> owned_lecturers = mgr.getOwnedLecturers();

        /* Getting neccessary hibernate helpers. */
        LecturersHelper helper = new LecturersHelper();

        // Calculacting the boost a given research would give.
        int boost_value = 0;
        BoostUtils boostHelper = new BoostUtils();

        for (int i = 0; i < chosenLecturers.size(); i++) {
            String lecturerName = chosenLecturers.get(i);

            Lecturer lecturer = mgr.lookUpLecturer(lecturerName, owned_lecturers);
            String research_area = getSubjectList()[subject];
            boost_value += boostHelper.calculateLecturerBoost(lecturer, research_area);

            /* Making the researcher unusable. */
            helper.setUsable(lecturerName, false);

            /* Setting the research object. */
            research.addResearcher(lecturer);

        }

        // Calculating additionaly research boost.
        double building_rate = boostHelper.calculateBuildingBoostMultiplier(mgr.getUserName());
        double students_boost = boostHelper.calculateStudentsBoost(mgr.getUserName(), getUndergraduates(), getPhds());
        double money_boost = boostHelper.calculateMoneyBoost(moneyAmount);

        double temp = (boost_value + students_boost + money_boost) * building_rate;
        boost_value = (int) Math.round(temp);

        /* Creating new object research */
        research.addAvailableResearchList(researchBag.getAvailableResearch());
        research.addResearchList(researchBag.getResearches());
        research.setMoney(getMoneyAmount());
        research.setUserId(auth.getUsername());

        // Addidng research boost
        if (boost_value > 100) {
            boost_value = 100;
        }
        else if (boost_value < 1) {
            boost_value = 1;
        }
        research.setResearchBoost(boost_value);

        List<Research> ongoingResearch = researchBag.getResearches();
        // List<Integer> finishedResearch = researchBag.getFinishedResearches();

        ongoingResearch.add(research);
        research.addResearchList(ongoingResearch);
        // research.addFinishedResearchList(finishedResearch);

        Thread thread = new Thread(research);

        thread.start();

        clean();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        facesContext.addMessage(BasicUtils.findComponent(facesContext.getViewRoot(),
                    "subjectList").getClientId(facesContext),
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "New Research", "You added new research!"));

    }


    public void clean() {

        /* The title of the research */
    String name = new String();
    ResearchPoints = 0;
    chosenLecturers = new ArrayList<String>();
    lecturers = new ArrayList<SelectItem>();
    researchesList = new ArrayList<SelectItem>();
    availableResearches = new ArrayList<ResearchTreeNode>();
    owned_lecturers = new ArrayList<Lecturer>();
    moneyAmount = 0;
    undergraduates = 0;
    phds = 0;
    chosenResearch = 0;
    subject = 0;
    initialized = false;
    confirmation = false;


    }

    private static String[] getSubjectList() {
        return SpecializationsGenerator.subjectList;
    }

    public TreeNode getRoot() {
        return ResearchTreeShowcase.getRoot();
    }

    public boolean validateSubject() {

        boolean result = true;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        if(getLecturerList().isEmpty()) {
            
            facesContext.addMessage(BasicUtils.findComponent(facesContext.getViewRoot(),
                    "subjectList").getClientId(facesContext),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No lecturers available",
                    "You must have at least one lecturer in your chosen subject!"));
             
            result = false;
        } else if(availableResearches.isEmpty()) {

            
            facesContext.addMessage(BasicUtils.findComponent(facesContext.getViewRoot(),
                    "subjectList").getClientId(facesContext),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No research available",
                    "You have no available research at given subject!"));
            
            result = false;
        } else if(!facesContext.getMessageList("subjectList").isEmpty()) {

            facesContext.addMessage(BasicUtils.findComponent(facesContext.getViewRoot(),
                    "subjectList").getClientId(facesContext),
                    new FacesMessage(FacesMessage.SEVERITY_INFO, null, null));

        }

        return result;
    }
}
