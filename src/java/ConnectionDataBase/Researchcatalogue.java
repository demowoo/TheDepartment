package ConnectionDataBase;
// Generated 09-Jun-2011 16:37:51 by Hibernate Tools 3.2.1.GA



/**
 * Researchcatalogue generated by hbm2java
 */
public class Researchcatalogue  implements java.io.Serializable {


     private String researchname;
     private String researchsubject;
     private String researchdescription;
     private int researchid;

    public Researchcatalogue() {
    }

    public Researchcatalogue(String researchname, String researchsubject, String researchdescription, int researchid) {
       this.researchname = researchname;
       this.researchsubject = researchsubject;
       this.researchdescription = researchdescription;
       this.researchid = researchid;
    }
   
    public String getResearchname() {
        return this.researchname;
    }
    
    public void setResearchname(String researchname) {
        this.researchname = researchname;
    }
    public String getResearchsubject() {
        return this.researchsubject;
    }
    
    public void setResearchsubject(String researchsubject) {
        this.researchsubject = researchsubject;
    }
    public String getResearchdescription() {
        return this.researchdescription;
    }
    
    public void setResearchdescription(String researchdescription) {
        this.researchdescription = researchdescription;
    }
    public int getResearchid() {
        return this.researchid;
    }
    
    public void setResearchid(int researchid) {
        this.researchid = researchid;
    }




}

