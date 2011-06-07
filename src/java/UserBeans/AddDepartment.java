/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserBeans;

import ConnectionDataBase.DepartmentinfoHelper;
import ConnectionDataBase.PlayerresourcesHelper;
import Connections.ConnectionSingleton;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author karol
 */
public class AddDepartment {

    /** Creates a new instance of AddDepartment */
    public AddDepartment() {
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String addDepartment() throws SQLException {

        if(getName() == null || getName().trim().length() == 0)
            return "failure";

        setName(getName().trim());

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        String playerName = session.getAttribute(ConnectionSingleton.idname).toString();

        PlayerresourcesHelper playerResources = new PlayerresourcesHelper();
        playerResources.createPlayerResources(playerName);

        /* Populating DepartmentInfo table. */
        DepartmentinfoHelper departmentInfo = new DepartmentinfoHelper();
        departmentInfo.createDepartment(playerName,getName());     

        return "success";
    }

    public String go() {
        return "go";
    }
}