package events;

import ConnectionDataBase.*;
import buildings.Building;
import buildings.BuildingFactory;
import buildings.Laboratories;

public class LabsInFire extends Event {

    @Override
    public boolean invoke(String playerName) {

        // Checking whether labs are present
        BuildingsHelper buildingsHelper
                = new BuildingsHelper();
        Buildings building_record = buildingsHelper.getBuildings(playerName);
        if (building_record == null) {
            return false;
        }
        int labs_level = building_record.getLabolatories();
        if (labs_level == Building.NOT_BUILT_LEVEL) {
            return false;
        }

        // Destorying labs.
        BuildingsPositionHelper posHelper
                = new BuildingsPositionHelper();
        int labs_position = 
                posHelper.getPosition(playerName, Building.CODE_LABS);

        if (labs_position == 0) {
            // Checking for superlabs
            labs_position
               = posHelper.getPosition(playerName, Building.CODE_SUPERLABS);
        }

        if (labs_position == 0) {
            // Inconsistency in the database, given that labs are built this
            // should not happen.
            System.err.println("Incosistency in the databse, could not find" +
                    " labs posotion given that labs exists.");
            return false;
        }

        BuildingFactory factory = new BuildingFactory();
        Laboratories labs = factory.getLabs();
        labs.remove(playerName, labs_position);

        // Increasing students alcoholism.
        increaseAlcoholizm(playerName, LotteryManager.HIGH);

        return true;
    }

    @Override
    public String getInfo() {
        return "Very bad news! Some drunk students have set your laboratories " +
                "in fire. Your labs are still standing, but it is very likely " +
                "that they will have to be rebuilt soon. You would think that " +
                "the event would cause students to be generally more happy. " +
                "On the contrary, as labs became unusable, some of your " +
                "students lost their purpose in life. Consequently, the level " +
                "of alcoholism of your students increases.";
    }

    @Override
    public boolean isEqual(String name) {
        return (name.equals(LotteryManager.LABS_FIRE));
    }

    @Override
    public String getName() {
        return ("Fire in the labs!");
    }

    @Override
    public void writeToDb(String playerName) {
        EventsHelper eventHelper
                = new EventsHelper();
        eventHelper.setLabsInFire(playerName, getNumOfTickets());
    }

}
