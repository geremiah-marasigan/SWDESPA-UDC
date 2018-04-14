/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import CONTROLLER.ClientController;
import CONTROLLER.SecretaryController;
import java.util.List;

/**
 *
 * @author ianona
 */
public class AppointmentBuilder extends TimeslotBuilder {

    @Override
    public boolean isAvailable(Appointment app) {
        boolean free = false;
        if (controller instanceof ClientController) {
            List<Appointment> apps = ((ClientController) controller).getAllAppointments();
            List<User> users = ((ClientController) controller).getAllUsers();
            if (app.getStartTime() >= app.getEndTime()) {
                return false;
            }
            if (app.getStartDay().equals(app.getEndDay())) {
                for (Appointment App : apps) {
                    for (User user : users) {
                        if (!App.getStartDay().equals(app.getStartDay())) {
                            free = true;
                            break;
                        } else {
                            if (App.getName().equals(user.getLastname()) && user.getType().equals("DOCTOR")) {
                                System.out.println(App.getName() + " HERE IS FUCKING NAME");
                                if (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) {
                                    System.out.println(App.getStartTime() + " OVER HERE " + app.getStartTime());
                                    free = true;
                                }
                            }
                            if (App.getName().equals(user.getLastname()) && user.getType().equals("CLIENT")) {
                                if ((app.getStartTime() <= App.getStartTime() && app.getEndTime() >= App.getEndTime()) || (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) || (app.getEndTime() <= App.getEndTime() && app.getEndTime() >= App.getEndTime()) || (app.getStartTime() <= App.getEndTime() && app.getStartTime() >= App.getEndTime())) {
                                    System.out.println(App.getStartTime() + " FUCK " + app.getStartTime() + " THIS " + App.getEndTime() + " SHIT " + app.getEndTime());
                                    return false;
                                }
                            }
                        }
                    }
                }
            } else {
                for (Appointment App : apps) {
                    for (User user : users) {
                        if (App.getName().equals(user.getLastname()) && user.getType().equals("DOCTOR")) {
                            if (Integer.parseInt(app.getStartDay().split("/")[0]) >= Integer.parseInt(App.getStartDay().split("/")[0]) && Integer.parseInt(app.getStartDay().split("/")[1]) >= Integer.parseInt(App.getStartDay().split("/")[1]) && Integer.parseInt(app.getStartDay().split("/")[2]) >= Integer.parseInt(App.getStartDay().split("/")[2])) {
                                if (Integer.parseInt(app.getEndDay().split("/")[0]) <= Integer.parseInt(App.getEndDay().split("/")[0]) && Integer.parseInt(app.getEndDay().split("/")[1]) <= Integer.parseInt(App.getEndDay().split("/")[1]) && Integer.parseInt(app.getEndDay().split("/")[2]) <= Integer.parseInt(App.getEndDay().split("/")[2])) {
                                    if (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) {
                                        free = true;
                                    }
                                }
                            }
                        }
                        if (App.getName().equals(user.getLastname()) && user.getType().equals("CLIENT")) {
                            if (Integer.parseInt(app.getStartDay().split("/")[0]) >= Integer.parseInt(App.getStartDay().split("/")[0]) && Integer.parseInt(app.getStartDay().split("/")[1]) >= Integer.parseInt(App.getStartDay().split("/")[1]) && Integer.parseInt(app.getStartDay().split("/")[2]) >= Integer.parseInt(App.getStartDay().split("/")[2])) {
                                if (Integer.parseInt(app.getEndDay().split("/")[0]) <= Integer.parseInt(App.getEndDay().split("/")[0]) && Integer.parseInt(app.getEndDay().split("/")[1]) <= Integer.parseInt(App.getEndDay().split("/")[1]) && Integer.parseInt(app.getEndDay().split("/")[2]) <= Integer.parseInt(App.getEndDay().split("/")[2])) {
                                    if ((app.getStartTime() <= App.getStartTime() && app.getEndTime() >= App.getEndTime()) || (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) || (app.getEndTime() <= App.getEndTime() && app.getEndTime() >= App.getEndTime()) || (app.getStartTime() <= App.getEndTime() && app.getStartTime() >= App.getEndTime())) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (controller instanceof SecretaryController) {
            List<Appointment> apps = ((SecretaryController) controller).getAllAppointments();
            List<User> users = ((SecretaryController) controller).getAllUsers();
            if (app.getStartTime() >= app.getEndTime()) {
                return false;
            }
            if (app.getStartDay().equals(app.getEndDay())) {
                for (Appointment App : apps) {
                    for (User user : users) {
                        if (App.getName().equals(user.getFirstname()) && user.getType().equals("DOCTOR")) {
                            if (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) {
                                free = true;
                            }
                        }
                        if (App.getName().equals(user.getFirstname()) && user.getType().equals("CLIENT")) {
                            if (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) {
                                return false;
                            }
                        }

                    }
                }
            } else {
                for (Appointment App : apps) {
                    for (User user : users) {
                        if (App.getName().equals(user.getFirstname()) && user.getType().equals("DOCTOR")) {
                            if (Integer.parseInt(app.getStartDay().split("/")[0]) >= Integer.parseInt(App.getStartDay().split("/")[0]) && Integer.parseInt(app.getStartDay().split("/")[1]) >= Integer.parseInt(App.getStartDay().split("/")[1]) && Integer.parseInt(app.getStartDay().split("/")[2]) >= Integer.parseInt(App.getStartDay().split("/")[2])) {
                                if (Integer.parseInt(app.getEndDay().split("/")[0]) <= Integer.parseInt(App.getEndDay().split("/")[0]) && Integer.parseInt(app.getEndDay().split("/")[1]) <= Integer.parseInt(App.getEndDay().split("/")[1]) && Integer.parseInt(app.getEndDay().split("/")[2]) <= Integer.parseInt(App.getEndDay().split("/")[2])) {
                                    if (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) {
                                        free = true;
                                    }
                                }
                            }
                        }
                        if (App.getName().equals(user.getFirstname()) && user.getType().equals("CLIENT")) {
                            if (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return free;
    }

    public void addAppSlot(Appointment app) {
        if (controller instanceof ClientController) {
            ((ClientController) controller).addAppointment(app);
        } else if (controller instanceof SecretaryController) {
            ((SecretaryController) controller).addAppointment(app);
        }
    }
}
