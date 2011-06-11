/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ResearchPoints;

import ConnectionDataBase.LecturersHelper;
import ConnectionDataBase.Research;
import Connections.ConnectionSingleton;
import UserBeans.Auth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.TreeNode;
import specializationsGenerator.SpecializationsGenerator;
import utilities.Lecturer;
import utilities.LecturerBenefits;
import utilities.LecturersManager;

/**
 *
 * @author karol
 */
public class AddResearch {

    /* The title of the research */
    private String name;

    /* Research points awarded to the player for successful completion */
    private int ResearchPoints;

    /* A vector of choosen lecturers. */
    private List<String> chosenLecturers;
    private List<SelectItem> lecturers;
    private List<SelectItem> subjects;
    private List<SelectItem> researchesList;
    private Integer moneyAmount;
    private Integer chosenResearch;
    private List<ResearchTreeNode> availableResearches = new ArrayList<ResearchTreeNode>();
    private List<Lecturer> owned_lecturers = new ArrayList<Lecturer>();
    private ResearchBag researchBag;
    private Auth auth;

    public AddResearch() {
        chosenLecturers = new ArrayList<String>();
        subjects = new ArrayList<SelectItem>();
        lecturers = new ArrayList<SelectItem>();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        /* Adding research thread to the list of researches of the given user */
        researchBag = (ResearchBag) session.getAttribute(Connections.ConnectionSingleton.researchBag);
        auth = (Auth) session.getAttribute(ConnectionSingleton.Auth);

        moneyAmount = 50;
        chosenResearch = 0;

        // Adding subjects names.
        for (int i = 0; i < getSubjectList().length; i++) {
            subjects.add(new SelectItem(new Integer(i), getSubjectList()[i]));
        }
    }
    private Integer subject;  // The index of the selected item
    // can be given as String/Integer/int ...

    public List getSubjects() {
        return subjects;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer nextSWVersion) {
        subject = nextSWVersion;
    }

    public void setLecturers(List chosenLects) {
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
        researchesList = new ArrayList<SelectItem>();
        availableResearches = new ArrayList<ResearchTreeNode>();

        List<ResearchTreeNode> subjectList =
                ResearchDevelopment.getFirstResearch(getSubject());

        List<Integer> integerList = researchBag.getAvailableResearch();

        int i = 0;
        Iterator<Integer> iterator = integerList.iterator();
        while(iterator.hasNext()) {
            Integer researchId = iterator.next();
            ResearchTreeNode researchNode = ResearchDevelopment.getResearchTreeNode(researchId);

            if(researchNode.getResearchInstance().getSubjectid() == getSubject()) {
                availableResearches.add(researchNode);
                researchesList.add(new SelectItem(new Integer(i), researchNode.toString()));
            }
        }

        return researchesList;
    }

    public List getLecturers() {
        return new ArrayList();
    }

    public List getLecturerList() {
        String playerName = utilities.BasicUtils.getUserName();
        LecturersManager mgr = new LecturersManager(playerName);
        List<Lecturer> owned = mgr.getOwnedLecturers();

        lecturers = new ArrayList<SelectItem>();
        owned_lecturers = new ArrayList<Lecturer>();

        Iterator<Lecturer> iterator = owned.iterator();
        int i = 0;
        LecturerBenefits lecturerBenefits =
                new LecturerBenefits(SpecializationsGenerator.subjectList[getSubject()]);

        while (iterator.hasNext()) {

            Lecturer lec = iterator.next();
            List<LecturerBenefits> list = lec.getSpecializations();

            System.out.println("Lecturer's name is: " + lec.getName());
            System.out.println("Current lecturer's spec is: " + lecturerBenefits.getField());
            
            if (lec.getUsable() && list.contains(lecturerBenefits) ) {

                lecturers.add(new SelectItem(new Integer(i++), lec.getName()));
                owned_lecturers.add(lec);

            }
        }

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

    public String startResearch() {

        Research research = new Research(auth,availableResearches.get(getChosenResearch()).
                getResearchInstance().getResearchid());

        /* Reading lecturers from the database. */
        LecturersManager mgr = new LecturersManager(utilities.BasicUtils.getUserName());
        ArrayList<Lecturer> owned_lecturers = mgr.getOwnedLecturers();

        /* Getting neccessary hibernate helpers. */
        LecturersHelper helper = new LecturersHelper();

        // Calculacting the boost a given research would give.
        int boost_value = 0;
        for (int i = 0; i < chosenLecturers.size(); i++) {
            String lecturerName = chosenLecturers.get(i);
            System.out.println(lecturerName);
            Lecturer lecturer = mgr.lookUpLecturer(lecturerName, owned_lecturers);
            System.out.println("-----------------------------------------");
            System.out.println("LECTURERS OBJECT: " + lecturer.getName());

            /* Making the researcher unusable. */
            helper.setUsable(lecturerName, false);

            /* Setting the research object. */
            research.addResearcher(lecturer);

            // Checking whether a given lecturer is has a given research as
            // its attribtues
            ArrayList<LecturerBenefits> benefits = lecturer.getSpecializations();
            String research_area = getSubjectList()[subject];
            Iterator<LecturerBenefits> it = benefits.iterator();
            while (it.hasNext()) {
                LecturerBenefits benefit = it.next();
                if (benefit.getField().equals(research_area)) {
                    boost_value += benefit.getBoost();
                    break;
                }
            }
        }

        /* Creating new object research */
        research.addAvailableResearchList(researchBag.getAvailableResearch());
        research.addResearchList(researchBag.getResearches());
        research.setMoney(getMoneyAmount());
        research.setUserId(auth.getUsername());
        research.setResearchBoost(boost_value);

        List<Research> ongoingResearch = researchBag.getResearches();
       // List<Integer> finishedResearch = researchBag.getFinishedResearches();

        ongoingResearch.add(research);
        research.addResearchList(ongoingResearch);
       // research.addFinishedResearchList(finishedResearch);

        Thread thread = new Thread(research);

        thread.start();

        return "success";
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public void clean() {
    }

    private String[] getSubjectList() {
        return SpecializationsGenerator.subjectList;
    }

    public TreeNode getRoot() {
       return ResearchTreeShowcase.getRoot();
    }
}
