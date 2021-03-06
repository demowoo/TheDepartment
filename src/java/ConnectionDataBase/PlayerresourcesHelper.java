package ConnectionDataBase;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PlayerresourcesHelper extends AbstractHelper {

    public void createPlayerResources(String idname) {
        Playerresources playerResources = new Playerresources();
        playerResources.setIdname(idname);
        playerResources.setMoney(100000);
        playerResources.setPhdsnumber(0);
        playerResources.setUndergraduatesnumber(0);
        playerResources.setResearchpoints(0);

        Session session = createNewSessionAndTransaction();
        session.save(playerResources);
        commitTransaction(session);


    }

    public Playerresources getResources(String idname) {
        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Playerresources where idname='"
                + idname + "'");
        Playerresources ret = (Playerresources) q.uniqueResult();
        session.close();
        return ret;
    }

    public int getMoney(String idname) {

        int money = 0;
        Session session = createNewSessionAndTransaction();

        Query q = session.createQuery("from Playerresources where idname = '"
                + idname + "'");
        Playerresources resources = (Playerresources) q.uniqueResult();
        if (resources != null) {
            money = resources.getMoney();
        }

        session.close();
        return money;
    }

    public int getResearchpoints(String idname) {

        int points = 0;

        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Playerresources where idname='"
                + idname + "'");
        Playerresources resources = (Playerresources) q.uniqueResult();

        if (resources != null) {
            points = resources.getResearchpoints();
        }

        session.close();

        return points;
    }

    public void updateMoney(String idname, int newValue) {
        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Playerresources where idname='"
                + idname + "'");
        Playerresources resource = (Playerresources) q.uniqueResult();
        if (resource != null) {
            resource.setMoney(newValue);
            
        }
        commitTransaction(session);
    }

    public void deleteResources(String idname) {

        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Playerresources where idname='"
                + idname + "'");
        Playerresources resources = (Playerresources) q.uniqueResult();
        if (resources != null) {
            session.delete(resources);
            
        }
        commitTransaction(session);

    }

    void addResearchPoints(String userId, Integer researchpoints) {

        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Playerresources where idname='"
                + userId + "'");

        Playerresources resources = (Playerresources) q.uniqueResult();
        if (resources != null) {
            resources.setResearchpoints(resources.getResearchpoints() + researchpoints);
            
        }
        commitTransaction(session);
    }

    public void saveOrUpdate(Playerresources resources) {
        Session session = createNewSessionAndTransaction();
        session.saveOrUpdate(resources);

        commitTransaction(session);
    }

    public void updatePhdsNumber(String idname, int phds) {
        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Playerresources where idname='"
                + idname + "'");
        Playerresources resource = (Playerresources) q.uniqueResult();
        if (resource != null) {
            resource.setPhdsnumber(phds);
            
        }
        commitTransaction(session);
    }

    public void updateUndergraduatesnumber(String idname, int students) {
        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Playerresources where idname='"
                + idname + "'");
        Playerresources resource = (Playerresources) q.uniqueResult();
        if (resource != null) {
            resource.setUndergraduatesnumber(students);
            
        }
        commitTransaction(session);
    }

    public void updateResources(Playerresources resources) {
        Session session = createNewSessionAndTransaction();
        session.saveOrUpdate(resources);
        commitTransaction(session);
    }

    public List<Playerresources> getRanking(String resource){
        List<Playerresources> output = new ArrayList<Playerresources>();
        Session session = createNewSessionAndTransaction();
        Query q = (Query) session.createQuery("from Playerresources order by "
                + resource + " DESC");
        output = q.list();
        session.close();
        return output;
    }
}
