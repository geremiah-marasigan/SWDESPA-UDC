/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.SecretaryController;
import MODEL.Appointment;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Arturo III
 */
public class TableRenderer extends DefaultTableCellRenderer
{
    private SecretaryController controller;
    private int monthSelected, yearSelected;
    public TableRenderer(){

    }
    public TableRenderer(SecretaryController ctrl, int month, int year){
    controller = ctrl;
    monthSelected = month;
    yearSelected = year;
    }
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6)
                if (value != null)
                    setBackground(new Color(118,148,91));
                else
                    setBackground(Color.WHITE);
                    
            else
                if (value != null)
                    setBackground(new Color(75,122,145));
                else
                    setBackground(Color.WHITE);
            
            if(value != null && controller != null){
                List<Appointment> apps = controller.getAllAppointments();
                for(Appointment a: apps){

                    int dayMonth = Integer.valueOf(a.getDate().split("/")[0]) - 1;
                    int dayYear = Integer.valueOf(a.getDate().split("/")[2]);
                    int dayDay = Integer.valueOf(a.getDate().split("/")[1]);
                    if(Integer.valueOf(value.toString()) == dayDay &&
                       monthSelected == dayMonth &&
                       yearSelected == dayYear &&
                       a.getTaken().equals("NOT_TAKEN")){
                        setBackground(new Color(178, 34, 34));
                    }
                }
            }
            
            if (selected) {
            Color c = this.getBackground();
            setBackground(c.darker());
            }
            setBorder(null);
            setForeground(Color.black);
            return this;  
    }
}
