/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.*;
import MODEL.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ianona
 */
public class SecretaryView extends JFrame implements ModuleView {
    private Container mainPane;
    public ModuleController controller;
    private User user;
    private JLabel icon;
    
    /**** Calendar Table Components ***/
    public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;
    private JScrollPane scrollCalendarTable;
    public JLabel monthLabel, yearLabel;
    public JButton btnPrev, btnNext,btnPrevYear, btnNextYear;
    private JPanel calendarPanel;
    private int yearBound, monthBound, dayBound, yearToday, monthToday;
    private String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    
    /**** Agenda and Schedule View Components ****/
    private JScrollPane agendaScroll;
    private JScrollPane scheduleScroll;
    private AgendaView av;
    private ScheduleView sv;
    private JLabel agendaLbl, schedLbl;
    private String curDate;
    
    public SecretaryView() {
        this.setSize(900, 660);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPane = this.getContentPane();
        mainPane.setBackground(Color.WHITE);
        mainPane.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
        buildIcon();
        initCalendar();
    }
    
    @Override
    public void buildIcon() {
        try {
            ImageIcon imgicon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/secretary.png")));
            icon = new JLabel (new ImageIcon(imgicon.getImage().getScaledInstance(120,120, Image.SCALE_DEFAULT)));
        } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        mainPane.add(icon);
        icon.setBounds(10,10,100,100);
    }

    @Override
    public void setController(ModuleController controller) {
        this.controller = controller;
        
        this.av = new AgendaView(controller);
        av.setUser(user);
        agendaScroll = new JScrollPane(av);
        agendaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        agendaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        agendaScroll.setBounds(220, 40, 390,450);
        agendaLbl = new JLabel("Agenda for Today");
        agendaLbl.setBounds(220, 10, 390, 30);
        
        mainPane.add(agendaScroll);
        mainPane.add(agendaLbl);

        this.sv = new ScheduleView(controller);
        scheduleScroll = new JScrollPane(sv);
        scheduleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scheduleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scheduleScroll.getVerticalScrollBar().setUnitIncrement(10);
        scheduleScroll.setBounds(620, 40, 260,580);
        schedLbl = new JLabel("Schedule for Today");
        schedLbl.setBounds(620, 10, 260, 30);
        
        mainPane.add(scheduleScroll);
        mainPane.add(schedLbl);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        setTitle("Secretary Module - Secretary " + user.getFirstname());
    }

    @Override
    public void setScheduleItems(List<Appointment> apps) {
        sv.setItems();
    }

    @Override
    public void setAgendaItems(List<Appointment> apps, String date) {
        av.setItems(apps, date);
    }

    @Override
    public void updateViews(List<Appointment> apps) {
        av.setItems(apps, curDate);
    }

    @Override
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

    @Override
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

        // SET THE CALENDAR NUMBERS
        for (i = 1; i <= nod; i++) {
            int row = (i+som-2)/7;
            int column  =  (i+som-2)%7;
            modelCalendarTable.setValueAt(i, row, column);
        }

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
    }
    
    class calListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
                int day = Integer.valueOf(calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()).toString().trim());
                curDate = (monthToday + 1) + "/" + day + "/" + yearToday;
                agendaLbl.setText("Agenda for " + curDate);
                schedLbl.setText("Schedule for " + curDate);
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
