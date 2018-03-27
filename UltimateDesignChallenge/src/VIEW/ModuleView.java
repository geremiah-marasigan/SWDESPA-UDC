/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ianona
 */
public class ModuleView extends JFrame {
    private Container mainPane;
    
    /**** Calendar Table Components ***/
    public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;
    private JScrollPane scrollCalendarTable;
    public JLabel monthLabel, yearLabel;
    public JButton btnPrev, btnNext,btnPrevYear, btnNextYear;
    private JPanel calendarPanel;
    private int yearBound, monthBound, dayBound, yearToday, monthToday;
    private String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    
    public ModuleView() {
        this.setSize(900, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPane = this.getContentPane();
        mainPane.setBackground(Color.WHITE);
        mainPane.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        
        initCalendar();
    }
    
    public void initCalendar() {
        modelCalendarTable = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++) {
            modelCalendarTable.addColumn(headers[i]);
        }
        
        modelCalendarTable.setColumnCount(7);
        modelCalendarTable.setRowCount(6);
        
        calendarTable = new JTable(modelCalendarTable);
        calendarTable.addMouseListener(new calListener());
        calendarPanel = new JPanel(null);
        
        scrollCalendarTable = new JScrollPane(calendarTable);
        scrollCalendarTable.setBounds(0, 0, 200, 200);
        
        monthLabel = new JLabel ("January");
        monthLabel.setBounds(80, 200, 90, 30);
        yearLabel = new JLabel ("2018");
        yearLabel.setBounds(80, 230, 50, 30);

        btnPrev = new JButton ("<");
        btnPrev.setBounds(0, 200, 50, 30);
        btnNext = new JButton (">");
        btnNext.setBounds(150, 200, 50, 30);
        btnPrevYear = new JButton ("<<");
        btnPrevYear.setBounds(0, 230, 50, 30);
        btnNextYear = new JButton (">>");
        btnNextYear.setBounds(150, 230, 50, 30);
        
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        btnPrevYear.addActionListener(new btnPrevYear_Action());
        btnNextYear.addActionListener(new btnNextYear_Action());
        
        // SETS CURRENT DATE
        GregorianCalendar cal = new GregorianCalendar();
        dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        monthBound = cal.get(GregorianCalendar.MONTH);
        yearBound = cal.get(GregorianCalendar.YEAR);
        monthToday = monthBound;
        yearToday = yearBound;
        
        refreshCalendar(monthToday, yearToday);
        
        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);
        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        calendarTable.setRowHeight(30);
        
        calendarPanel.setBounds(10,120,200,260);
        calendarPanel.setBackground(Color.LIGHT_GRAY);
        calendarPanel.add(scrollCalendarTable);
        calendarPanel.add(monthLabel);
        calendarPanel.add(yearLabel);
        calendarPanel.add(btnPrev);
        calendarPanel.add(btnNext);
        calendarPanel.add(btnPrevYear);
        calendarPanel.add(btnNextYear);
        mainPane.add(calendarPanel);
    }
    
    public void refreshCalendar(int month, int year) {
        int nod, som, i, j;
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);

        if (month == 0 && year <= yearBound-10)
            btnPrev.setEnabled(false);
        if (month == 11 && year >= yearBound+100)
            btnNext.setEnabled(false);

        monthLabel.setText(months[month]);
        yearLabel.setText(String.valueOf(year));

        for (i = 0; i < 6; i++)
            for (j = 0; j < 7; j++)
                modelCalendarTable.setValueAt(null, i, j);

        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        /*
        eDay.setSelectedItem(""+1);
        eYear.setSelectedItem(""+year);
            
        eDay2.setSelectedItem(""+1);
        eYear2.setSelectedItem(""+year);

        curYear = year;
        curMonth = month + 1;
        curDay = 1;

        eMonth.setSelectedItem(months[month]);
        eMonth2.setSelectedItem(months[month]);
        
        eDay.removeAllItems();
        eDay2.removeAllItems();
        */

        // SET THE CALENDAR NUMBERS
        for (i = 1; i <= nod; i++) {
            int row = (i+som-2)/7;
            int column  =  (i+som-2)%7;
            modelCalendarTable.setValueAt(i, row, column);
                
            // eDay.addItem(String.valueOf(i));
            // eDay2.addItem(String.valueOf(i));
        }

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
    }
    
    class calListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
                /*
                curCol = calendarTable.getSelectedColumn();
                curRow = calendarTable.getSelectedRow();

                String val = calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()).toString();
                val = val.replaceAll("\\D+","");
                eDay.setSelectedItem(val);
                eMonth.setSelectedIndex(curMonth - 1);
                curDay = Integer.valueOf(val.trim());
                eYear.setSelectedItem(""+curYear);
                    
                eDay2.setSelectedItem(val);
                eMonth2.setSelectedIndex(curMonth - 1);
                eYear2.setSelectedItem(""+curYear);
                    
                controller.updateViews(curYear, curMonth, curDay);
                */

        }
    }
    
    class btnPrev_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (monthToday == 0) {
                monthToday = 11;
                yearToday -= 1;
            } else {
                monthToday -= 1;
            }
            refreshCalendar(monthToday, yearToday);
        }   
    }
    class btnNext_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (monthToday == 11) {
                monthToday = 0;
                yearToday += 1;
            } else {
                monthToday += 1;
            }
            refreshCalendar(monthToday, yearToday);
        }
    }
    class btnPrevYear_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            yearToday -= 1;
            refreshCalendar(monthToday, yearToday);
        }
    }
    class btnNextYear_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            yearToday += 1;
            refreshCalendar(monthToday, yearToday);
        }
    }
}
