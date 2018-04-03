/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ModuleController;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class ScheduleView extends JPanel{
    private ModuleController controller;
    List<ScheduleItem> items;
    
    public ScheduleView (ModuleController controller) {
        this.controller = controller;
        items = new ArrayList<ScheduleItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT,VerticalFlowLayout.TOP, 0, 0));
    }
    
    public void setItems () {
        for (int i = 0; i < items.size(); i++) {
                remove(items.get(i));
        }
			
        items.clear();
        
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                String hourString = String.valueOf(hour);
                if (hourString.length() == 1)
                    hourString = "0"+hourString;
                String minString = String.valueOf(min);
                if (minString.length() == 1)
                    minString = "0"+minString;
                String time = hourString + ":" + minString;
                items.add(new ScheduleItem(controller, time));
            }
        }
        
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }
        
        setPreferredSize(new Dimension(260, items.size() * 45 + 30));
        
        revalidate();
        repaint();
    }
}
