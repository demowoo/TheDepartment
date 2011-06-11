
package buildings;

import ConnectionDataBase.Buildings;
import ConnectionDataBase.BuildingsHelper;
import ConnectionDataBase.BuildingsPositionHelper;
import utilities.BuildingInfo;

public class Tresco extends Building {

    public Tresco (int cost) {
        this.cost = cost;
    }

    
    @Override
    public boolean build(String playerName, int position) {

        BuildingsHelper buildingsHelper
                = new BuildingsHelper();

        BuildingsPositionHelper posHelper
                = new BuildingsPositionHelper();

        // Checking money and position.
        boolean result = checkMoneyAndPosition(playerName, position);
        if (!result) {
            return false;
        }

        // Checkng whether labs were not already built.
        Buildings building_record
                = buildingsHelper.getBuildings(playerName);
        if (building_record == null) {
            return false;
        }

        int tresco_level = building_record.getTresco();
        if (tresco_level != Building.NOT_BUILT_LEVEL) {
            return false;
        }


        /* Updating Buildings table. */
        buildingsHelper.updateTresco(playerName, Building.BASIC_LEVEL);

        /* Updating Position table. */
        posHelper.createBuildingPosition(playerName, position,
                Building.CODE_TRESCO);

        return true;
    }


    @Override
    public boolean remove(String playerName, int position) {

        /* Removing from Buildings table. */
        BuildingsHelper buildingsHelper
                = new BuildingsHelper();
        buildingsHelper.updateBrain(playerName, Building.NOT_BUILT_LEVEL);

        /* Removing from Position table. */
        BuildingsPositionHelper posHelper
                = new BuildingsPositionHelper();
        posHelper.updateBuildingPosition(playerName, position, null);


        return true;
    }

    @Override
    public BuildingInfo isAllowedToBuild(String playerName, int position) {
        BuildingsHelper buildingsHelper
                = new BuildingsHelper();

        // Checking money and position.
        BuildingInfo info = checkMoneyAndPositionInfo(playerName, position);
        if (!info.getResult()) {
            return info;
        }

        // Checkng whether labs were not already built.
        Buildings building_record
                = buildingsHelper.getBuildings(playerName);
        if (building_record == null) {
            return new BuildingInfo(false, "Player does not exist");
        }

        int tresco_level = building_record.getTresco();
        if (tresco_level != Building.NOT_BUILT_LEVEL) {
            return new BuildingInfo(false, "Tresco is already built");
        }

        return new BuildingInfo(true, "Build me!");
    }

}
