package ConnectionDataBase;
// Generated 09-Jun-2011 11:47:03 by Hibernate Tools 3.2.1.GA



/**
 * Buildingsposition generated by hbm2java
 */
public class Buildingsposition  implements java.io.Serializable {


     private String idname;
     private String pos1;
     private String pos2;
     private String pos3;
     private String pos4;
     private String pos5;
     private String pos6;
     private String pos7;
     private String pos8;
     private String pos9;
     private String pos10;
     private String pos11;
     private String pos12;
     private String pos13;
     private String pos14;
     private String pos15;
     private String pos16;

    public Buildingsposition() {
    }


    public Buildingsposition(String idname) {
        this.idname = idname;
    }
    public Buildingsposition(String idname, String pos1, String pos2, 
            String pos3, String pos4, String pos5, String pos6, String pos7,
            String pos8, String pos9, String pos10, String pos11, String pos12,
            String pos13, String pos14, String pos15, String pos16) {
       this.idname = idname;
       this.pos1 = pos1;
       this.pos2 = pos2;
       this.pos3 = pos3;
       this.pos4 = pos4;
       this.pos5 = pos5;
       this.pos6 = pos6;
       this.pos7 = pos7;
       this.pos8 = pos8;
       this.pos9 = pos9;
       this.pos10 = pos10;
       this.pos11 = pos11;
       this.pos12 = pos12;
       this.pos13 = pos13;
       this.pos14 = pos14;
       this.pos15 = pos15;
       this.pos16 = pos16;
    }

    public String getIdname() {
        return this.idname;
    }

    public void setIdname(String idname) {
        this.idname = idname;
    }

    public void setPos(int n, String s){
        switch (n){
            case 1: setPos1(s);
            case 2: setPos2(s);
            case 3: setPos3(s);
            case 4: setPos4(s);
            case 5: setPos5(s);
            case 6: setPos6(s);
            case 7: setPos7(s);
            case 8: setPos8(s);
            case 9: setPos9(s);
            case 10: setPos10(s);
            case 11: setPos11(s);
            case 12: setPos12(s);
            case 13: setPos13(s);
            case 14: setPos14(s);
            case 15: setPos15(s);
            case 16: setPos16(s);
        }
    }

        public String getPos(int n){
        switch (n){
            case 1:return getPos1();
            case 2:return getPos2();
            case 3:return getPos3();
            case 4:return getPos4();
            case 5:return getPos5();
            case 6:return getPos6();
            case 7:return getPos7();
            case 8:return getPos8();
            case 9:return getPos9();
            case 10:return getPos10();
            case 11:return getPos11();
            case 12:return getPos12();
            case 13:return getPos13();
            case 14:return getPos14();
            case 15:return getPos15();
            case 16:return getPos16();
        }
        return "";
    }
    public String getPos1() {
        return this.pos1;
    }

    public void setPos1(String pos1) {
        this.pos1 = pos1;
    }
    public String getPos2() {
        return this.pos2;
    }

    public void setPos2(String pos2) {
        this.pos2 = pos2;
    }
    public String getPos3() {
        return this.pos3;
    }

    public void setPos3(String pos3) {
        this.pos3 = pos3;
    }
    public String getPos4() {
        return this.pos4;
    }

    public void setPos4(String pos4) {
        this.pos4 = pos4;
    }
    public String getPos5() {
        return this.pos5;
    }

    public void setPos5(String pos5) {
        this.pos5 = pos5;
    }
    public String getPos6() {
        return this.pos6;
    }

    public void setPos6(String pos6) {
        this.pos6 = pos6;
    }
    public String getPos7() {
        return this.pos7;
    }

    public void setPos7(String pos7) {
        this.pos7 = pos7;
    }
    public String getPos8() {
        return this.pos8;
    }

    public void setPos8(String pos8) {
        this.pos8 = pos8;
    }
    public String getPos9() {
        return this.pos9;
    }

    public void setPos9(String pos9) {
        this.pos9 = pos9;
    }
    public String getPos10() {
        return this.pos10;
    }

    public void setPos10(String pos10) {
        this.pos10 = pos10;
    }
    public String getPos11() {
        return this.pos11;
    }

    public void setPos11(String pos11) {
        this.pos11 = pos11;
    }
    public String getPos12() {
        return this.pos12;
    }

    public void setPos12(String pos12) {
        this.pos12 = pos12;
    }
    public String getPos13() {
        return this.pos13;
    }

    public void setPos13(String pos13) {
        this.pos13 = pos13;
    }
    public String getPos14() {
        return this.pos14;
    }

    public void setPos14(String pos14) {
        this.pos14 = pos14;
    }
    public String getPos15() {
        return this.pos15;
    }

    public void setPos15(String pos15) {
        this.pos15 = pos15;
    }
    public String getPos16() {
        return this.pos16;
    }

    public void setPos16(String pos16) {
        this.pos16 = pos16;
    }




}

