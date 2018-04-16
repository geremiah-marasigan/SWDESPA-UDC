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
import java.util.TimerTask;
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
    private Director director;
    
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
    private String curDate, curDoctor;
    private JButton btnWeeklyAgenda, btnWeeklySchedule;
    private WeeklyView wv;
    private WeeklyAgendaView wav;
    
    /**** Notification ****/
    private JButton bttnNotify, bttnNotifyAll;
    private JComboBox cmbNotify;
    private JLabel lblNotifyMessage;
    private JTextField txtNotifyTextField;
    
    /**** Timer ****/
    public java.util.Timer timer;
    public TimerTask timerTask;
    
    /**** DocList ****/
    List<User> docList;
    public JComboBox cmbAgenda;
    
        /****Walk-In Tools****/
    private JPanel pnlApp;
    private JButton btnWalkIn, btnAdd, btnCancel;
    private JLabel nameLbl, docLbl, sDayLbl, eDayLbl, sTimeLbl, eTimeLbl, repeatLbl, errorMsg;
    private JTextField name, sDay, eDay;
    private JComboBox doc, sTime, eTime, repeat;
    
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
        
        director = new Director();
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
        
        bttnNotifyAll = new JButton("Notify All");
        bttnNotifyAll.setBounds(10,400, 100, 25);
        mainPane.add(bttnNotifyAll);
        
        bttnNotify = new JButton("Notify");
        bttnNotify.setBounds(10,450, 100, 25);
        mainPane.add(bttnNotify);
        
        this.docList = ((SecretaryController)controller).getDoctors();
        User[] docArray = new User[docList.size()];
        docList.toArray(docArray);
        String[] docNames = new String[docList.size()];
        String[] docAgenda = new String[docList.size() + 1]; //names + "All"
        docAgenda[0] = "All";
        for(int i = 0; i < docList.size(); i++){
            docNames[i] = "Dr. " + docArray[i].getFirstname() + " " + docArray[i].getLastname();
            //docAgenda[i+1] = docArray[i].getFirstname() + " " + docArray[i].getLastname();
            docAgenda[i+1] = docArray[i].getLastname();
        }
        cmbNotify = new JComboBox(docNames);
        cmbNotify.setBounds(115,450, 100, 25);
        mainPane.add(cmbNotify);
        
         
        lblNotifyMessage = new JLabel("Message: ");
        lblNotifyMessage.setBounds(10, 475, 100, 25);
        mainPane.add(lblNotifyMessage);
        
        txtNotifyTextField = new JTextField();
        txtNotifyTextField.setBounds(10, 500, 205, 100);
        mainPane.add(txtNotifyTextField);
        
        
        cmbAgenda = new JComboBox(docAgenda);
        cmbAgenda.setBounds(510, 10, 100, 25);
        mainPane.add(cmbAgenda);
        
        bttnNotify.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        ((SecretaryController)controller).notifyDoctor(docArray[cmbNotify.getSelectedIndex()], txtNotifyTextField.getText());
                        txtNotifyTextField.setText("");
                    }
        });
        
        bttnNotifyAll.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        String text = txtNotifyTextField.getText();
                        for(int i = 0; i < docArray.length; i++)
                        {
                            ((SecretaryController)controller).notifyDoctor(docArray[i], text);
                            txtNotifyTextField.setText("");
                        }
                    }        
        });
        
        cmbAgenda.addActionListener(new cmbAgenda_Action());
        
        btnWeeklyAgenda = new JButton("Weekly [A]");
        btnWeeklyAgenda.setBounds(400, 10, 100, 25);
        mainPane.add(btnWeeklyAgenda);

        btnWeeklySchedule = new JButton("Weekly [S]");
        btnWeeklySchedule.setBounds(770, 10, 100, 25);
        mainPane.add(btnWeeklySchedule);
        
        btnWeeklySchedule.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                wv = new WeeklyView(controller, getWeekInfo(), user);
            }
        });

        btnWeeklyAgenda.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                wav = new WeeklyAgendaView(controller, getWeekInfo(), user);
            }
        });
        
        btnWalkIn = new JButton("Add Walk-in Appointment");
        btnWalkIn.setBounds(310, 495, 200, 25);
        mainPane.add(btnWalkIn);
        btnWalkIn.addActionListener(new btnWalkIn_Action());

        walkInTools();
        runTimer();
    }

    public void runTimer(){
        timerTask = new TimerTask(){
            @Override
            public void run(){
                ((SecretaryController) controller).updateViews(curDate);
                refreshCalendar(monthToday, yearToday);
            }
        };
        timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(timerTask, 5000, 5000);
    }
    
    @Override
    public void setUser(User user) {
        this.user = user;
        setTitle("Secretary Module - Secretary " + user.getFirstname());
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
    
    public void walkInTools(){
        pnlApp = new JPanel(null);
        pnlApp.setBounds(220, 495, 390, 125);
        pnlApp.setBackground(Color.LIGHT_GRAY);
        mainPane.add(pnlApp);
        pnlApp.setVisible(false);
        
        btnAdd = new JButton("Set Slots");
        btnAdd.addActionListener(new btnAdd_Action());
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                pnlApp.setVisible(false);
                btnWalkIn.setVisible(true);
            }
        });
        
        name = new JTextField(10);
        sDay = new JTextField(10);
        eDay = new JTextField(10);
        eDay.setVisible(false);
        sTime = new JComboBox();
        eTime = new JComboBox();
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                String hourString = String.valueOf(hour);
                if (hourString.length() == 1)
                    hourString = "0"+hourString;
                String minString = String.valueOf(min);
                if (minString.length() == 1)
                    minString = "0"+minString;
                String time = hourString + ":" + minString;
                sTime.addItem(time);
                eTime.addItem(time);
            }
        }
        
        User[] docArray = new User[docList.size()];
        docList.toArray(docArray);
        String[] docNames = new String[docList.size()];
        for(int i = 0; i < docList.size(); i++){
            docNames[i] = docArray[i].getFirstname() + " " + docArray[i].getLastname();
        }
        doc = new JComboBox(docNames);
        
        String[] repeatOptions = {"None", "Daily", "Monthly"};
        repeat = new JComboBox(repeatOptions);
        repeat.setSelectedIndex(0);
        repeat.addActionListener(new repeat_Action());
        
        nameLbl = new JLabel("Name: ");
        docLbl = new JLabel("Doctor: ");
        sDayLbl = new JLabel("Start Day: ");
        eDayLbl = new JLabel("End Day: ");
        eDayLbl.setVisible(false);
        sTimeLbl = new JLabel("Start Time: ");
        eTimeLbl = new JLabel("End Time: ");
        repeatLbl = new JLabel("Repeat: ");
        errorMsg = new JLabel("ERROR");
        errorMsg.setForeground(Color.red);
        errorMsg.setVisible(false);
        
        GregorianCalendar cal = new GregorianCalendar();
        int dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int monthBound = cal.get(GregorianCalendar.MONTH) + 1;
        int yearBound = cal.get(GregorianCalendar.YEAR);
        curDate = monthBound + "/" + dayBound + "/" + yearBound;
        sDay.setText(curDate);
        eDay.setText(curDate);
        
        nameLbl.setBounds(5, 3, 100, 25);
        docLbl.setBounds(5, 27, 100, 25);
        sDayLbl.setBounds(5, 52, 100, 25);
        sTimeLbl.setBounds(5, 75, 100, 25);
        eTimeLbl.setBounds(140, 75, 100, 25);
        repeatLbl.setBounds(210, 5, 70, 25);
        eDayLbl.setBounds(210, 30, 70, 25);
        errorMsg.setBounds(105, 100, 70, 25);
        
        name.setBounds(72, 3, 100, 25);
        doc.setBounds(72, 27, 100, 25);
        sDay.setBounds(72, 52, 100, 25);
        sTime.setBounds(72, 75, 65, 25);
        eTime.setBounds(200, 75, 65, 25);
        repeat.setBounds(280, 5, 100, 25);
        eDay.setBounds(280, 30, 100, 25);
        btnAdd.setBounds(280, 60, 100,30);
        btnCancel.setBounds(280, 90, 100,30);
        
        pnlApp.add(name);
        pnlApp.add(doc);
        pnlApp.add(btnAdd);
        pnlApp.add(btnCancel);
        pnlApp.add(sDay);
        pnlApp.add(eDay);
        pnlApp.add(sTime);
        pnlApp.add(eTime);
        pnlApp.add(repeat);
        
        pnlApp.add(nameLbl);
        pnlApp.add(docLbl);
        pnlApp.add(sDayLbl);
        pnlApp.add(eDayLbl);
        pnlApp.add(sTimeLbl);
        pnlApp.add(eTimeLbl);
        pnlApp.add(repeatLbl);
        pnlApp.add(errorMsg);
        
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

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer((SecretaryController)controller, month, year));
    }

    @Override
    public void setScheduleItems(List<Appointment> apps) {
        sv.setItems(apps, docList.get(1));
    }

    @Override
    public void setAgendaItems(List<Appointment> apps, String date) {
        av.setItems(apps);
    }

    @Override
    public void updateViews(List<Appointment> apps) {
        sv.setItems(apps, docList.get(1));
        av.setItems(apps);
    }
    
    public void filterViews(List<Appointment> apps, String name) {
        
        sv.filterItems(apps, docList, curDate, name);
        av.filterItems(apps, curDate, name);
    }
    
    public List<String[]> getWeekInfo() {
        GregorianCalendar cal = new GregorianCalendar(Integer.valueOf(curDate.split("/")[2]), Integer.valueOf(curDate.split("/")[0]) - 1, Integer.valueOf(curDate.split("/")[1]));
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        SimpleDateFormat sdf = new SimpleDateFormat("M d yyyy");

        List<String[]> weekInfo;
        weekInfo = new ArrayList<String[]>();

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        weekInfo.add(sdf.format(cal.getTime()).split(" "));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        weekInfo.add(sdf.format(cal.getTime()).split(" "));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        weekInfo.add(sdf.format(cal.getTime()).split(" "));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        weekInfo.add(sdf.format(cal.getTime()).split(" "));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        weekInfo.add(sdf.format(cal.getTime()).split(" "));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        weekInfo.add(sdf.format(cal.getTime()).split(" "));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        weekInfo.add(sdf.format(cal.getTime()).split(" "));

        return weekInfo;
    }
    
    class calListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
                int day = Integer.valueOf(calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()).toString().trim());
                curDate = (monthToday + 1) + "/" + day + "/" + yearToday;
                agendaLbl.setText("Agenda for " + curDate);
                schedLbl.setText("Schedule for " + curDate);
                cmbAgenda.setSelectedIndex(0);
                ((SecretaryController)controller).updateViews(curDate);
        }
    }
    
    class cmbAgenda_Action implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            String selectedDoc = "";
            selectedDoc = cmbAgenda.getSelectedItem().toString();
            ((SecretaryController)controller).filterViews(selectedDoc);
        }
    }
    
    class btnWalkIn_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            btnWalkIn.setVisible(false);
            pnlApp.setVisible(true);
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
    
    class btnAdd_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
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
            curDoctor = ((String)doc.getSelectedItem()).split(" ")[1];
            
            System.out.println("Current Doc: " + curDoctor);
            
            for (int i = 0; i < daysBetween(startD, endD) + 1; i++) {
                int time = startT;
                while (time != endT) {
                    appsToAdd.add(new Appointment(curDoctor,date,time,name.getText()));
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
            
            for(int j = 0; j < appsToAdd.size(); j++)
            {
                System.out.println("APPS TO ADD: ");
                System.out.println("Name: " + appsToAdd.get(j).getName());
                System.out.println("Name: " + appsToAdd.get(j).getDate());
                System.out.println("Name: " + appsToAdd.get(j).getTime());
                System.out.println("Name: " + appsToAdd.get(j).getTaken());
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
            
            if(errorMsg.isVisible() == false)
            {
                pnlApp.setVisible(false);
                btnWalkIn.setVisible(true);
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
        public void actionPerformed (ActionEvent e) {
            String choice = String.valueOf(repeat.getSelectedItem());
            if (choice.equalsIgnoreCase("None")) {
                eDay.setVisible(false);
                eDayLbl.setVisible(false);
            }
            else {
                eDay.setVisible(true);
                eDayLbl.setVisible(true);
            }
                
        }
    }
    
}
