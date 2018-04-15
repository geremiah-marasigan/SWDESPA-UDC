/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.*;
import MODEL.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class NotificationView extends JPanel{
    private ModuleController controller;
    List<NotificationItem> items;

    public NotificationView(ModuleController controller) {
        this.controller = controller;
        items = new ArrayList<NotificationItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP, 0, 0));
    }

    // DOCTOR
    public void setItems(List<Notification> notifs) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        items.clear();
        
        for (int i = 0; i < notifs.size(); i++) {
            items.add(new NotificationItem(controller, notifs.get(i)));
        }

        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(200, items.size() * 45 + 30));

        revalidate();
        repaint();
    }
}
