package deamons;

import ConnectionDataBase.LecturersAvailableHelper;
import ConnectionDataBase.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import utilities.Lecturer;
import utilities.LecturersManager;

public class LecturersPopulator extends Thread {

    private static final int SLEEP_TIME = 2;

    public LecturersPopulator() {
        setDaemon(true);
    }

    @Override
    public void run() {
        /* Inicialization code. */

        System.out.println("Lecturers thread " + getName() + " started");

        //boolean stop = false;
        while (true) {
            try {
                // Initial run for the purpose of presentation after 30 sec.
                sleep(1000*30);

                System.out.println(this.getName()
                        + " is repopulating available lecturers");

                // Updating available lecturers for all players
                        // Setting up all required helpers
                LecturersAvailableHelper avHelper = new LecturersAvailableHelper();
                LecturersHelper lecHelper = new LecturersHelper();
                LecturersSpecializationsHelper specHelper = new LecturersSpecializationsHelper();

                DepartmentinfoHelper deptInfoHelper = new DepartmentinfoHelper();
                PlayerHelper playersHelper = new PlayerHelper();
                List<Players> allPlayers = playersHelper.getPlayers();
                if (allPlayers == null) {
                    continue;
                }
                Iterator<Players> it = allPlayers.iterator();

                while (it.hasNext()) {
                    Players player = it.next();
                    String idname = player.getIdname();

                    if (idname != null) {
                        // Checking whether a given player has a department
                        boolean hasDept = deptInfoHelper.hasDepartment(idname);
                        if (!hasDept) {
                            // If not continue to another player.
                            continue;
                        }

                        // Removing all prevous records.
                        LecturersManager manager = new LecturersManager(idname);
                        ArrayList<Lecturer> av_list = manager.getAvailabeLecturers();
                        Iterator<Lecturer> lec_it = av_list.iterator();
                        while (lec_it.hasNext()) {
                            Lecturer cur = lec_it.next();
                            String lecName = cur.getName();
                            lecHelper.removeLecturer(lecName);
                            specHelper.removeSpecializationsRecords(lecName);
                        }

                        avHelper.removeAvailableLecturers(idname);
                        // Repopulating available lecturers.
                        manager.repopulateAvailableLec();
                    }
                    /* Sleep for three minutes. */
                }
                System.out.println(this.getName() + " is going to sleep.");
                sleep(1000 * 60 * SLEEP_TIME);

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(this.getName() + " Lecturers thread broke!!!!!!!!!!!!!!!!");
                //stop = true;
            }
        }
    }
}
