package ConnectionDataBase;
// Generated 09-Jun-2011 22:33:22 by Hibernate Tools 3.2.1.GA



/**
 * Lecturerssepciatlizations generated by hbm2java
 */
public class Lecturerssepciatlizations  implements java.io.Serializable {


     private LecturerssepciatlizationsId id;
     private Integer boost;

    public Lecturerssepciatlizations() {
    }

	
    public Lecturerssepciatlizations(LecturerssepciatlizationsId id) {
        this.id = id;
    }
    public Lecturerssepciatlizations(LecturerssepciatlizationsId id, Integer boost) {
       this.id = id;
       this.boost = boost;
    }
   
    public LecturerssepciatlizationsId getId() {
        return this.id;
    }
    
    public void setId(LecturerssepciatlizationsId id) {
        this.id = id;
    }
    public Integer getBoost() {
        return this.boost;
    }
    
    public void setBoost(Integer boost) {
        this.boost = boost;
    }




}

