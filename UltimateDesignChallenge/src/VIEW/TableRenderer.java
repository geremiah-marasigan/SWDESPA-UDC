/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Arturo III
 */
public class TableRenderer extends DefaultTableCellRenderer
{
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
            
            if (selected) {
            Color c = this.getBackground();
            setBackground(c.darker());
            }
            setBorder(null);
            setForeground(Color.black);
            return this;  
    }
}
