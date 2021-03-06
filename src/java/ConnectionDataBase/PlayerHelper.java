/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionDataBase;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author karol
 */
public class PlayerHelper extends AbstractHelper {

    public List<Players> getPlayers() {

        List<Players> list = null;
        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Players");
        list = (List<Players>) q.list();

        session.close();
        
        return list;

    }

    public Players getPlayer(String idname) {
        Players player = null;

        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Players where idname='"
                + idname + "'");
        player = (Players) q.uniqueResult();

        session.close();

        return player;
    }

    public void updateLoggedStatus(String idname, boolean logged) {
        Session session = createNewSessionAndTransaction();

        Query q = session.createQuery("from Players where idname='"
                + idname + "'");
        Players player = (Players) q.uniqueResult();
        if (player != null) {
            player.setLoggedin(logged);
            session.saveOrUpdate(player);
            
        }
        commitTransaction(session);

    }

    public void addPlayer(String idname, String encodedPassword, String email) {
        Session session = createNewSessionAndTransaction();
        Players player = new Players(idname,encodedPassword,email,false);

        session.save(player);
        commitTransaction(session);
    }

    public void deletePlayer(String idname) {
        Session session = createNewSessionAndTransaction();
        Query q = session.createQuery("from Players where idname='" + idname + "'");
        Players player = (Players) q.uniqueResult();

        if(player != null) {
            session.delete(player);
            
        }
        commitTransaction(session);
    }

    public boolean existsPlayer(String username) {
        return getPlayer(username) != null;
    }


}
