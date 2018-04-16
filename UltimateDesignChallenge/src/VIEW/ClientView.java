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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ianona
 */
public class ClientView extends JFrame implements ModuleView {

    private Container mainPane;
    public ModuleController controller;
    private User user;
    private JLabel icon;
    private Director director;

    /**
     * ** Calendar Table Components **
     */
    public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;
    private JScrollPane scrollCalendarTable;
    public JLabel monthLabel, yearLabel;
    public JButton btnPrev, btnNext, btnPrevYear, btnNextYear;
    private JPanel calendarPanel;
    private int yearBound, monthBound, dayBound, yearToday, monthToday;
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    
    /**
     * ** Agenda and Schedule View Components ***
     */
    private JScrollPane agendaScroll;
    private JScrollPane scheduleScroll;
    private AgendaView av;
    private ScheduleView sv;
    private JLabel agendaLbl, schedLbl;
    private String curDate, curDoctor;
    
    /**
     * ** Tools taken from Ian ***
     */
    private JButton btnApp, btnFltr;
    private JPanel pnlApp;
    private JLabel sDayLbl, eDayLbl, sTimeLbl, eTimeLbl, repeatLbl, errorMsg, doctorLbl;
    private JTextField sDay, eDay;
    private JComboBox sTime, eTime, repeat, doctors, filter;
    private JButton btnAdd, btnCancel, btnClear;

    private JPanel pnlEdit;
    private JLabel oldTimeLbl, newTimeLbl;
    private JComboBox newTimeCmb;
    private JButton editBtn;
    
    public ClientView() {
        this.setSize(900, 660);
        mainPane = this.getContentPane();
        mainPane.setBackground(Color.WHITE);
        mainPane.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        buildIcon();
        initCalendar();

        initTools();
        initTools2();
        director = new Director();
    }

    @Override
    public void buildIcon() {
        try {
            ImageIcon imgicon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/client.png")));
            icon = new JLabel(new ImageIcon(imgicon.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        mainPane.add(icon);
        icon.setBounds(10, 10, 100, 100);
    }

    @Override
    public void setController(ModuleController controller) {
        this.controller = controller;

        this.av = new AgendaView(controller);
        av.setUser(user);
        agendaScroll = new JScrollPane(av);
        agendaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        agendaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        agendaScroll.setBounds(220, 40, 390, 450);
        agendaLbl = new JLabel("Agenda for Today");
        agendaLbl.setBounds(220, 10, 390, 30);

        mainPane.add(agendaScroll);
        mainPane.add(agendaLbl);

        this.sv = new ScheduleView(controller);
        scheduleScroll = new JScrollPane(sv);
        scheduleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scheduleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scheduleScroll.getVerticalScrollBar().setUnitIncrement(10);
        scheduleScroll.setBounds(620, 40, 260, 580);
        schedLbl = new JLabel("Schedule for Today");
        schedLbl.setBounds(620, 10, 260, 30);

        mainPane.add(scheduleScroll);
        mainPane.add(schedLbl);

        for (User user : ((ClientController) controller).getAllUsers()) {
            if (user.getType().equals("DOCTOR")) {
                doctors.addItem("Dr. " + user.getLastname());
                filter.addItem("Dr. " + user.getLastname());
                curDoctor = user.getLastname();
            }
        }

        Timer timer = new Timer(5000, new timer_Action());
        timer.start();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        this.setTitle("Client Module - Mr./Ms. " + user.getFirstname());
    }

    @Override
    public void setScheduleItems(List<Appointment> apps) {
        //sv.setItems(apps, ((ClientController)controller).getAllUsers(),((ClientController)controller).getAllDeleted(), curDate);
    }

    @Override
    public void setAgendaItems(List<Appointment> apps, String date) {
        av.setItems(apps);
    }

    @Override
    public void updateViews(List<Appointment> apps) {
        av.setItems(apps);
        sv.setItems(apps, user);
    }

    @Override
    public void initCalendar() {
        modelCalendarTable = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i = 0; i < 7; i++) {
            modelCalendarTable.addColumn(headers[i]);
        }

        modelCalendarTable.setColumnCount(7);
        modelCalendarTable.setRowCount(6);

        calendarTable = new JTable(modelCalendarTable);
        calendarTable.addMouseListener(new calListener());
        calendarPanel = new JPanel(null);

        scrollCalendarTable = new JScrollPane(calendarTable);
        scrollCalendarTable.setBounds(0, 0, 200, 200);

        monthLabel = new JLabel("January");
        monthLabel.setBounds(80, 200, 90, 30);
        yearLabel = new JLabel("2018");
        yearLabel.setBounds(80, 230, 50, 30);

        btnPrev = new JButton("<");
        btnPrev.setBounds(0, 200, 50, 30);
        btnNext = new JButton(">");
        btnNext.setBounds(150, 200, 50, 30);
        btnPrevYear = new JButton("<<");
        btnPrevYear.setBounds(0, 230, 50, 30);
        btnNextYear = new JButton(">>");
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

        calendarPanel.setBounds(10, 120, 200, 260);
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

    public void initTools() {
        doctors = new JComboBox();
        filter = new JComboBox();
        filter.addItem("All Doctors");
        filter.setBounds(60, 380, 150, 50);
        filter.addActionListener(new btnFltr_Action());
        btnClear = new JButton("Clear");
        btnClear.setBounds(10,430,100,50);
        btnClear.addActionListener(new btnClear_Action());
        btnApp = new JButton();
        btnApp.setBounds(10, 380, 50, 50);
        btnApp.addActionListener(new btnApp_Action());
        mainPane.add(btnClear);
        mainPane.add(btnApp);
        mainPane.add(filter);
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/btnApp.png")));
            btnApp.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
            btnApp.setToolTipText("Make an Appointment");
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }

        pnlApp = new JPanel(null);
        pnlApp.setBounds(220, 495, 390, 125);
        pnlApp.setBackground(Color.LIGHT_GRAY);
        mainPane.add(pnlApp);
        pnlApp.setVisible(false);

        btnAdd = new JButton("Set Slots");
        btnAdd.addActionListener(new btnAdd_Action());
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new btnCancel_Action());
        sDay = new JTextField(10);
        eDay = new JTextField(10);
        eDay.setVisible(false);
        sTime = new JComboBox();
        eTime = new JComboBox();
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                String hourString = String.valueOf(hour);
                if (hourString.length() == 1) {
                    hourString = "0" + hourString;
                }
                String minString = String.valueOf(min);
                if (minString.length() == 1) {
                    minString = "0" + minString;
                }
                String time = hourString + ":" + minString;
                sTime.addItem(time);
                eTime.addItem(time);
            }
        }
        String[] repeatOptions = {"None", "Daily"};
        repeat = new JComboBox(repeatOptions);
        repeat.setSelectedIndex(0);
        repeat.addActionListener(new repeat_Action());

        sDayLbl = new JLabel("Start Day: ");
        eDayLbl = new JLabel("End Day: ");
        eDayLbl.setVisible(false);
        doctorLbl = new JLabel("Doctor: ");
        sTimeLbl = new JLabel("Start Time: ");
        eTimeLbl = new JLabel("End Time: ");
        repeatLbl = new JLabel("Repeat: ");
        errorMsg = new JLabel();
        errorMsg.setForeground(Color.red);
        errorMsg.setVisible(false);
        
        GregorianCalendar cal = new GregorianCalendar();
        int dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int monthBound = cal.get(GregorianCalendar.MONTH) + 1;
        int yearBound = cal.get(GregorianCalendar.YEAR);
        
        curDate = monthBound + "/" + dayBound + "/" + yearBound;        
        sDay.setText(curDate);
        eDay.setText(curDate);
        
        sDayLbl.setBounds(5, 5, 100, 25);
        sTimeLbl.setBounds(5, 55, 100, 25);
        eTimeLbl.setBounds(5, 80, 100, 25);
        repeatLbl.setBounds(210, 5, 70, 25);
        eDayLbl.setBounds(5, 30, 100, 25);
        errorMsg.setBounds(5, 90, 200, 25);
        doctorLbl.setBounds(210, 30, 70, 25);
        
        sDay.setBounds(105, 5, 100, 25);
        sTime.setBounds(105, 55, 100, 25);
        eTime.setBounds(105, 80, 100, 25);
        repeat.setBounds(280, 5, 100, 25);
        doctors.setBounds(280, 30, 100, 25);
        eDay.setBounds(105, 30, 100, 25);
        btnAdd.setBounds(280, 60, 100, 30);
        btnCancel.setBounds(280, 90, 100, 30);
        
        pnlApp.add(btnCancel);
        pnlApp.add(btnAdd);
        pnlApp.add(sDay);
        pnlApp.add(eDay);
        pnlApp.add(sTime);
        pnlApp.add(eTime);
        pnlApp.add(repeat);
        pnlApp.add(doctors);
        
        pnlApp.add(sDayLbl);
        pnlApp.add(eDayLbl);
        pnlApp.add(sTimeLbl);
        pnlApp.add(eTimeLbl);
        pnlApp.add(repeatLbl);
        pnlApp.add(errorMsg);
        pnlApp.add(doctorLbl);
        
        sDay.setToolTipText("M/D/Y");
        eDay.setToolTipText("M/D/Y");
        sTime.setToolTipText("Enter Available Time");
        eTime.setToolTipText("Enter Available Time");
        repeat.setToolTipText("Choose Frequency");
        doctors.setToolTipText("Select Available Doctor");
        filter.setToolTipText("View Schedule Of...");
        btnAdd.setToolTipText("Make Appointment");
        btnCancel.setToolTipText("Cancel Appointment");
        btnClear.setToolTipText("Delete All Scheduled Appointments");
    }

    @Override
    public void refreshCalendar(int month, int year) {
        int nod, som, i, j;
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);

        if (month == 0 && year <= yearBound - 10) {
            btnPrev.setEnabled(false);
        }
        if (month == 11 && year >= yearBound + 100) {
            btnNext.setEnabled(false);
        }

        monthLabel.setText(months[month]);
        yearLabel.setText(String.valueOf(year));

        for (i = 0; i < 6; i++) {
            for (j = 0; j < 7; j++) {
                modelCalendarTable.setValueAt(null, i, j);
            }
        }

        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);

        // SET THE CALENDAR NUMBERS
        for (i = 1; i <= nod; i++) {
            int row = (i + som - 2) / 7;
            int column = (i + som - 2) % 7;
            modelCalendarTable.setValueAt(i, row, column);
        }

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
    }

    public void initTools2() {
        pnlEdit = new JPanel(null);
        pnlEdit.setBounds(220, 495, 390, 125);
        pnlEdit.setBackground(Color.LIGHT_GRAY);
        mainPane.add(pnlEdit);
        pnlEdit.setVisible(false);

        editBtn = new JButton("Edit");
        editBtn.addActionListener(new btnEdit_Action());
        newTimeCmb = new JComboBox();
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                String hourString = String.valueOf(hour);
                if (hourString.length() == 1) {
                    hourString = "0" + hourString;
                }
                String minString = String.valueOf(min);
                if (minString.length() == 1) {
                    minString = "0" + minString;
                }
                String time = hourString + ":" + minString;
                newTimeCmb.addItem(time);
            }
        }

        oldTimeLbl = new JLabel("Old Slot: ");
        newTimeLbl = new JLabel("New Slot: ");

        oldTimeLbl.setBounds(5, 5, 150, 25);
        newTimeLbl.setBounds(5, 30, 100, 25);

        editBtn.setBounds(280, 60, 100, 30);
        newTimeCmb.setBounds(105, 30, 100, 25);

        pnlEdit.add(editBtn);
        pnlEdit.add(btnCancel);
        pnlEdit.add(oldTimeLbl);
        pnlEdit.add(newTimeLbl);
        pnlEdit.add(newTimeCmb);
        editBtn.setToolTipText("Edit Appointment");
    }

    public void edit(String time) {
        pnlEdit.add(btnCancel);
        oldTimeLbl.setText("Old Time: " + time);
        pnlEdit.setVisible(true);
        pnlApp.setVisible(false);
    }

    class calListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            int day = Integer.valueOf(calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()).toString().trim());
            curDate = (monthToday + 1) + "/" + day + "/" + yearToday;
            agendaLbl.setText("Agenda for " + curDate);
            schedLbl.setText("Schedule for " + curDate);

            ((ClientController) controller).updateViews(curDate);
            sDay.setText(curDate);
            eDay.setText(curDate);
        }
    }

    class btnClear_Action implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ((ClientController)controller).deleteDay(curDate,"Marasigan");
            ((ClientController)controller).deleteDay(curDate,"Reyes");
        }
    }
    
    class btnFltr_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            av.setItems(((ClientController)controller).getAllFilter((String)filter.getSelectedItem(),curDate));
        }
    }

    class btnApp_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            pnlApp.setVisible(true);
        }
    }

    class timer_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent tc) {
            ((ClientController) controller).updateViews(curDate);
        }
    }

    class btnEdit_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Appointment> appsToAdd = new ArrayList<>();
            System.out.println(oldTimeLbl.getText().split(" ")[1].replaceAll(":", ""));
            int oldTime = Integer.valueOf(oldTimeLbl.getText().split(" ")[2].replaceAll(":", ""));
            int newTime = Integer.valueOf(((String)newTimeCmb.getSelectedItem()).replaceAll(":",""));
            appsToAdd.add(new Appointment(curDoctor,curDate,newTime,user.getLastname()));
                    
            director.setTimeslotBuilder(new AppointmentBuilder(),controller);
            if (director.addApp(appsToAdd)) {
                ((ClientController)controller).deleteAppointment(new Appointment(curDoctor,curDate,oldTime,user.getLastname()));
                pnlApp.setVisible(false);
                errorMsg.setVisible(false);
            } else {
                pnlApp.add(errorMsg);
                errorMsg.setText("<html>ERROR! Conflicting<br/>Appointment Slots</html>");
                errorMsg.setVisible(true);
            }
        }
    }

    class btnAdd_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String choice = String.valueOf(repeat.getSelectedItem());
            String startD = sDay.getText();
            String endD = null;
            if (choice.equalsIgnoreCase("None")) {
                endD = startD;
            } else {
                endD = eDay.getText();
            }
            int startT = Integer.parseInt(sTime.getSelectedItem().toString().replace(":", ""));
            int endT = Integer.parseInt(eTime.getSelectedItem().toString().replace(":", ""));
            
            List<Appointment> appsToAdd = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
            Calendar c = Calendar.getInstance();
            String date = startD;
            curDoctor = ((String)doctors.getSelectedItem()).split(" ")[1];
            for (int i = 0; i < daysBetween(startD, endD) + 1; i++) {
                int time = startT;
                while (time != endT) {
                    appsToAdd.add(new Appointment(curDoctor,date,time,user.getLastname()));
                    time += 30;
                    if (time % 100 >= 60) {
                        time = ((time / 100) + 1) * 100;
                    }
                    if (time / 100 >= 24) {
                        time = 0;
                    }
                }
                try {
                    c.setTime(sdf.parse(date));
                    c.add(Calendar.DATE, 1);
                    date = sdf.format(c.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(DoctorView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            director.setTimeslotBuilder(new AppointmentBuilder(),controller);
            if (director.addApp(appsToAdd)) {
                pnlApp.setVisible(false);
                errorMsg.setVisible(false);
            } else {
                pnlApp.add(errorMsg);
                errorMsg.setText("<html>ERROR! Conflicting<br/>Appointment Slots</html>");
                errorMsg.setVisible(true);
            }
        }
    }
    
    public long daysBetween(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate firstDate = LocalDate.parse(date1, formatter);
        LocalDate secondDate = LocalDate.parse(date2, formatter);
        long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        return days;
    }
    
    class repeat_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String choice = String.valueOf(repeat.getSelectedItem());
            if (choice.equalsIgnoreCase("None")) {
                eDay.setVisible(false);
                eDayLbl.setVisible(false);
            } else {
                eDay.setVisible(true);
                eDayLbl.setVisible(true);
            }

        }
    }

    class btnPrev_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
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
        public void actionPerformed(ActionEvent e) {
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
        public void actionPerformed(ActionEvent e) {
            yearToday -= 1;
            refreshCalendar(monthToday, yearToday);
        }
    }

    class btnNextYear_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            yearToday += 1;
            refreshCalendar(monthToday, yearToday);
        }
    }
    
    class btnCancel_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            pnlApp.setVisible(false);
            pnlEdit.setVisible(false);
        }
    }
}
