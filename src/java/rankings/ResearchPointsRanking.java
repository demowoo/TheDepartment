/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rankings;

import ConnectionDataBase.Playerresources;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author pk2109
 */
public class ResearchPointsRanking extends AbstractRanking implements Serializable  {

    public ResearchPointsRanking() {
        super();
    }

    @Override
    public List<Playerresources> getList() {
        setPlayerPosition(1);
        setList(helper.getRanking("researchpoints"));
        return helper.getRanking("researchpoints");
    }

    @Override
    public List<Playerresources> produceRanking() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}