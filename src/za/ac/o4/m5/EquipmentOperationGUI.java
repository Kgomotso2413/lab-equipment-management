package za.ac.o4.m5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class EquipmentOperationGUI extends JFrame {

    private final JLabel lblUsername;
    private final JTextField tfUsername;
    private final JLabel lblPassword;
    private final JPasswordField pfPassword;
    private final JButton btnConnect;
    private final JLabel lblStatusCaption;
    private final JLabel lblConnectionStatus;

    private final JLabel lblEquipmentName;
    private final JTextField tfEquipmentName;
    private final JLabel lblDepartment;
    private final JTextField tfDepartment;
    private final JLabel lblPrice;
    private final JTextField tfPrice;
    private final JCheckBox ckbInUse;
    private final JButton btnInsertEquipment;

    private final JButton btnLoadDepartments;
    private final JComboBox<String> cmbDepartment;
    private final JButton btnFilterDepartment;
    private final JLabel lblEquipmentSearch;
    private final JTextField tfEquipmentSearch;
    private final JButton btnFilterExistingResultSet;
    private final JButton btnViewAll;

    private final JTable tblEquipmentRecords;
    private final DefaultTableModel tableModel;
    private final JButton btnClearResults;
    private final JLabel lblRecordsDisplayed;

    private final JLabel lblTotalRecordsValue;
    private final JLabel lblTotalExpenditureValue;
    private final JLabel lblTotalDepartmentsValue;
    private final JLabel lblCheapestEquipmentValue;
    private final JLabel lblMostExpensiveValue;
    private final JButton btnLoadDashboard;

    private JComboBox<String> cmbPurchaseMonth;
    private final JButton btnUpdateMultimedia;
    private final JButton btnDeleteDamagedNotInUse;
    private final JButton btnExit;
    private final JLabel lblOperationStatus;
    
    private Connection connection;
    private Statement baselineStatement;
    private ResultSet baselineResultSet;

    public EquipmentOperationGUI() {
        setTitle("Equipment Database Operations Console");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(880, 800));
        setLocationRelativeTo(null);
        
        Font headingFont = new Font("Arial", Font.BOLD, 15);
        Font normalFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        /*
         * -------------------------------------------------------------
         * DATABASE LOGIN
         * -------------------------------------------------------------
         */
        JPanel pnlDatabaseLogin = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        pnlDatabaseLogin.setBorder(BorderFactory.createTitledBorder(
                                   BorderFactory.createLineBorder(Color.decode("#8ac2a1"), 3),
                                   "Database Login"));

        lblUsername = new JLabel("Username:");
        lblUsername.setFont(normalFont);

        tfUsername = new JTextField("app", 10);
        tfUsername.setFont(normalFont);

        lblPassword = new JLabel("Password:");
        lblPassword.setFont(normalFont);

        pfPassword = new JPasswordField("app", 10);
        pfPassword.setFont(normalFont);

        btnConnect = new JButton("Connect");
        btnConnect.setFont(buttonFont);
        btnConnect.setBackground(Color.decode("#b1fbd0"));

        lblStatusCaption = new JLabel("Status:");
        lblStatusCaption.setFont(normalFont);

        lblConnectionStatus = new JLabel("Not connected");
        lblConnectionStatus.setFont(normalFont);

        pnlDatabaseLogin.add(lblUsername); pnlDatabaseLogin.add(tfUsername);
        pnlDatabaseLogin.add(lblPassword); pnlDatabaseLogin.add(pfPassword);
        pnlDatabaseLogin.add(btnConnect); 
        pnlDatabaseLogin.add(lblStatusCaption); pnlDatabaseLogin.add(lblConnectionStatus);
       /*
        * -------------------------------------------------------------
        * INSERT EQUIPMENT
        * -------------------------------------------------------------
        */
        JPanel pnlInsertEquipment = new JPanel(new GridLayout(2, 1, 2, 2));
        pnlInsertEquipment.setBorder(BorderFactory.createTitledBorder(
                                     BorderFactory.createLineBorder(Color.decode("#b28ac2"), 3),
                                     "Insert Equipment"));

        JPanel pnlInsertFields = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));

        lblEquipmentName = new JLabel("Name:");
        lblEquipmentName.setFont(normalFont);

        tfEquipmentName = new JTextField("Equipment name", 12);
        tfEquipmentName.setFont(normalFont);

        lblDepartment = new JLabel("Department:");
        lblDepartment.setFont(normalFont);

        tfDepartment = new JTextField("Department", 12);
        tfDepartment.setFont(normalFont);

        lblPrice = new JLabel("Price:");
        lblPrice.setFont(normalFont);

        tfPrice = new JTextField("0.00", 8);
        tfPrice.setFont(normalFont);

        ckbInUse = new JCheckBox("In Use");
        ckbInUse.setFont(normalFont);

        btnInsertEquipment = new JButton("Insert Equipment");
        btnInsertEquipment.setFont(buttonFont);
        btnInsertEquipment.setBackground(Color.decode("#dcaaf0"));

        pnlInsertFields.add(lblEquipmentName); pnlInsertFields.add(tfEquipmentName);
        pnlInsertFields.add(lblDepartment); pnlInsertFields.add(tfDepartment);
        pnlInsertFields.add(lblPrice); pnlInsertFields.add(tfPrice);
        pnlInsertFields.add(ckbInUse);
        pnlInsertFields.add(btnInsertEquipment);

        JPanel pnlInsertNote = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));

        JLabel lblInsertNote = new JLabel(
                "Purchase Date = current date \u2022 Condition = New \u2022 ID = generated by Derby");
        lblInsertNote.setFont(new Font("Arial", Font.PLAIN, 10));

        pnlInsertNote.add(lblInsertNote);

        pnlInsertEquipment.add(pnlInsertFields);
        pnlInsertEquipment.add(pnlInsertNote);

        /*
         * -------------------------------------------------------------
         * SEARCH AND FILTER
         * -------------------------------------------------------------
         */
        JPanel pnlSearchFilter = new JPanel(new GridLayout(3, 1, 4, 4));
        pnlSearchFilter.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Color.decode("#6abac4"), 3),
                                   "Search and Filter"));
        
        btnLoadDepartments = new JButton("Load Departments");
        btnLoadDepartments.setFont(buttonFont);
        btnLoadDepartments.setBackground(Color.decode("#b8dce0"));
        btnLoadDepartments.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        cmbDepartment = new JComboBox<>();
        cmbDepartment.addItem("Select Department");
        cmbDepartment.setFont(normalFont);

        btnFilterDepartment = new JButton("Filter Department");
        btnFilterDepartment.setFont(buttonFont);
        btnFilterDepartment.setBackground(Color.decode("#b8dce0"));
        
        JPanel pnlEquipmentNameFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));

        lblEquipmentSearch = new JLabel("Equipment Name:");
        lblEquipmentSearch.setFont(normalFont);

        tfEquipmentSearch = new JTextField("Partial name", 14);
        tfEquipmentSearch.setFont(normalFont);

        btnFilterExistingResultSet = new JButton("Filter Existing ResultSet");
        btnFilterExistingResultSet.setFont(buttonFont);
        btnFilterExistingResultSet.setBackground(Color.decode("#b8dce0"));

        pnlEquipmentNameFilter.add(cmbDepartment); 
        pnlEquipmentNameFilter.add(btnFilterDepartment);
        pnlEquipmentNameFilter.add(lblEquipmentSearch);
        pnlEquipmentNameFilter.add(tfEquipmentSearch);
        pnlEquipmentNameFilter.add(btnFilterExistingResultSet);
        

        btnViewAll = new JButton("View All");
        btnViewAll.setFont(buttonFont);
        btnViewAll.setBackground(Color.decode("#b8dce0"));

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        pnlButton.add(btnViewAll);
        
        pnlSearchFilter.add(btnLoadDepartments);
        pnlSearchFilter.add(pnlEquipmentNameFilter);
        pnlSearchFilter.add(pnlButton);

        /*
         * -------------------------------------------------------------
         * EQUIPMENT RECORDS TABLE
         * -------------------------------------------------------------
         */
        JPanel pnlEquipmentRecords = new JPanel(new BorderLayout(5, 5));
        pnlEquipmentRecords.setBorder(BorderFactory.createTitledBorder(
                                       BorderFactory.createLineBorder(Color.decode("#a4b842"), 4),
                                        "Equipment Records"));

        String[] columnNames = { "ID", "Name", "Department", "Purchase Date",
                                 "Condition", "In Use", "Price" };

        tableModel = new DefaultTableModel(columnNames, 0);

        tblEquipmentRecords = new JTable(tableModel);
        tblEquipmentRecords.setFont(new Font("Arial", Font.PLAIN, 11));
        tblEquipmentRecords.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
        tblEquipmentRecords.getTableHeader().setBackground(Color.decode("#f6facf"));
        tblEquipmentRecords.setPreferredSize(new Dimension(400, 270));
        /*
         * Make the JTable non-editable without introducing another class.
         */
        tblEquipmentRecords.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(tblEquipmentRecords);

        JPanel pnlRecordsBottom = new JPanel(new BorderLayout(8, 4));

        lblRecordsDisplayed = new JLabel("Records displayed: 0");
        lblRecordsDisplayed.setFont(headingFont);

        btnClearResults = new JButton("Clear Results");
        btnClearResults.setFont(buttonFont);
        btnClearResults.setBackground(Color.decode("#f6facf"));

        pnlRecordsBottom.add(lblRecordsDisplayed, BorderLayout.WEST);
        pnlRecordsBottom.add(btnClearResults, BorderLayout.EAST);

        pnlEquipmentRecords.add(scrollPane, BorderLayout.CENTER);
        pnlEquipmentRecords.add(pnlRecordsBottom, BorderLayout.SOUTH);

        /*
         * -------------------------------------------------------------
         * DATABASE DASHBOARD
         * -------------------------------------------------------------
         */
        JPanel pnlDashboard = new JPanel(new BorderLayout(5, 5));
        pnlDashboard.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Color.decode("#d19a5e"), 3),
                                   "Database Dashboard"));

        JPanel pnlDashboardCards = new JPanel(new GridLayout(5, 1, 5, 5));

        JPanel pnlTotalRecords = new JPanel(new BorderLayout(3, 3));
        pnlTotalRecords.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Color.decode("#F28500"), 2),
                                   "Total Records"));

        lblTotalRecordsValue = new JLabel("0");
        lblTotalRecordsValue.setFont(headingFont);
        pnlTotalRecords.add(lblTotalRecordsValue, BorderLayout.CENTER);

        JPanel pnlTotalExpenditure = new JPanel(new BorderLayout(3, 3));
        pnlTotalExpenditure.setBorder(BorderFactory.createTitledBorder(
                                      BorderFactory.createLineBorder(Color.decode("#F28500"), 2),
                                      "Total Expenditure"));

        lblTotalExpenditureValue = new JLabel("R0.00");
        lblTotalExpenditureValue.setFont(headingFont);
        pnlTotalExpenditure.add(lblTotalExpenditureValue, BorderLayout.CENTER);

        JPanel pnlTotalDepartments = new JPanel(new BorderLayout(3, 3));
        pnlTotalDepartments.setBorder(BorderFactory.createTitledBorder(
                                      BorderFactory.createLineBorder(Color.decode("#F28500"), 2),
                                      "Total Departments"));

        lblTotalDepartmentsValue = new JLabel("0");
        lblTotalDepartmentsValue.setFont(headingFont);
        pnlTotalDepartments.add(lblTotalDepartmentsValue, BorderLayout.CENTER);

        JPanel pnlCheapestEquipment = new JPanel(new BorderLayout(3, 3));
        pnlCheapestEquipment.setBorder(BorderFactory.createTitledBorder(
                                       BorderFactory.createLineBorder(Color.decode("#F28500"), 2),
                                       "Cheapest Equipment"));

        lblCheapestEquipmentValue = new JLabel("Not loaded");
        lblCheapestEquipmentValue.setFont(normalFont);
        pnlCheapestEquipment.add(lblCheapestEquipmentValue, BorderLayout.CENTER);

        JPanel pnlMostExpensive = new JPanel(new BorderLayout(3, 3));
        pnlMostExpensive.setBorder(BorderFactory.createTitledBorder(
                                   BorderFactory.createLineBorder(Color.decode("#F28500"), 2),
                                   "Most Expensive Equipment"));

        lblMostExpensiveValue = new JLabel("Not loaded");
        lblMostExpensiveValue.setFont(normalFont);
        pnlMostExpensive.add(lblMostExpensiveValue, BorderLayout.CENTER);

        pnlDashboardCards.add(pnlTotalRecords);
        pnlDashboardCards.add(pnlTotalExpenditure);
        pnlDashboardCards.add(pnlTotalDepartments);
        pnlDashboardCards.add(pnlCheapestEquipment);
        pnlDashboardCards.add(pnlMostExpensive);

        btnLoadDashboard = new JButton("Load Dashboard");
        btnLoadDashboard.setFont(buttonFont);
        btnLoadDashboard.setBackground(Color.decode("#FF9966"));

        JPanel pnlDashboardButton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        pnlDashboardButton.add(btnLoadDashboard);

        pnlDashboard.add(pnlDashboardCards, BorderLayout.CENTER);
        pnlDashboard.add(pnlDashboardButton, BorderLayout.SOUTH);

        /*
         * -------------------------------------------------------------
         * RECORDS + DASHBOARD CONTAINER
         * -------------------------------------------------------------
         */
        JPanel pnlRecordsDashboard = new JPanel(new BorderLayout(8, 0));
        pnlRecordsDashboard.add(pnlEquipmentRecords, BorderLayout.CENTER);
        pnlRecordsDashboard.add(pnlDashboard, BorderLayout.EAST);

        /*
         * -------------------------------------------------------------
         * DATABASE OPERATIONS
         * -------------------------------------------------------------
         */
        JPanel pnlDatabaseOperations = new JPanel(new BorderLayout(5, 5));
        pnlDatabaseOperations.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Color.decode("#8b95c9"), 3),
                                   "Database Operation"));

        JPanel pnlOperationsControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));

        JLabel lblPurchaseMonth = new JLabel("Purchase Month:");
        lblPurchaseMonth.setFont(normalFont);

        cmbPurchaseMonth = new JComboBox<>();
        cmbPurchaseMonth.addItem("January"); cmbPurchaseMonth.addItem("February");
        cmbPurchaseMonth.addItem("March"); cmbPurchaseMonth.addItem("April");
        cmbPurchaseMonth.addItem("May"); cmbPurchaseMonth.addItem("June");
        cmbPurchaseMonth.addItem("July"); cmbPurchaseMonth.addItem("August");
        cmbPurchaseMonth.addItem("September"); cmbPurchaseMonth.addItem("October");
        cmbPurchaseMonth.addItem("November"); cmbPurchaseMonth.addItem("December");
        cmbPurchaseMonth.setFont(normalFont);

        btnUpdateMultimedia = new JButton("Update Multimedia Dept");
        btnUpdateMultimedia.setFont(buttonFont);
        btnUpdateMultimedia.setBackground(Color.decode("#d2d8f7"));

        btnDeleteDamagedNotInUse = new JButton("Delete Damaged Not In Use");
        btnDeleteDamagedNotInUse.setFont(buttonFont);
        btnDeleteDamagedNotInUse.setBackground(Color.decode("#d2d8f7"));

        btnExit = new JButton("Exit");
        btnExit.setFont(buttonFont);
        btnExit.setBackground(Color.decode("#faacac"));

        pnlOperationsControls.add(lblPurchaseMonth);
        pnlOperationsControls.add(cmbPurchaseMonth);
        pnlOperationsControls.add(btnUpdateMultimedia);
        pnlOperationsControls.add(btnDeleteDamagedNotInUse);

        JPanel pnlExit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        pnlExit.add(btnExit);

        JPanel pnlOperationStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));

        JLabel lblOperationStatusCaption = new JLabel("Operation Status:");
        lblOperationStatusCaption.setFont(headingFont);

        lblOperationStatus = new JLabel("Ready");
        lblOperationStatus.setFont(normalFont);

        pnlOperationStatus.add(lblOperationStatusCaption);
        pnlOperationStatus.add(lblOperationStatus);

        pnlDatabaseOperations.add(pnlOperationsControls, BorderLayout.NORTH);
        pnlDatabaseOperations.add(pnlOperationStatus, BorderLayout.CENTER);
        pnlDatabaseOperations.add(pnlExit, BorderLayout.EAST);

        /*
         * -------------------------------------------------------------
         * MAIN LAYOUT
         * -------------------------------------------------------------
         */
        JPanel pnlTopSections = new JPanel(new BorderLayout());
        pnlTopSections.add(pnlDatabaseLogin, BorderLayout.NORTH);
        pnlTopSections.add(pnlInsertEquipment, BorderLayout.CENTER);
        pnlTopSections.add(pnlSearchFilter, BorderLayout.SOUTH);

        mainPanel.add(pnlTopSections, BorderLayout.NORTH);
        mainPanel.add(pnlRecordsDashboard, BorderLayout.CENTER);
        mainPanel.add(pnlDatabaseOperations, BorderLayout.SOUTH);

        add(mainPanel);
        /*
         * -------------------------------------------------------------
         * LISTENER REGISTRATION
         * -------------------------------------------------------------
         */
        btnConnect.addActionListener(new BtnConnectListener());

        btnInsertEquipment.addActionListener(new BtnInsertEquipmentListener());
        btnLoadDepartments.addActionListener(new BtnLoadDepartmentsClickListener());
        btnFilterDepartment.addActionListener(new BtnFilterDepartmentClickListener());

        btnFilterExistingResultSet.addActionListener(new BtnFilterExistingResultSetListener());
        btnViewAll.addActionListener(new BtnViewAllListener());
        btnClearResults.addActionListener(new BtnClearResultsListener());
        btnLoadDashboard.addActionListener(new BtnLoadDashboardListener());
        btnUpdateMultimedia.addActionListener(new BtnUpdateMultimediaListener());
        btnDeleteDamagedNotInUse.addActionListener(new BtnDeleteDamagedNotInUseListener());
        btnExit.addActionListener(new BtnExitListener());
    }

    /*
     * -------------------------------------------------------------
     * CONNECT LISTENER
     * -------------------------------------------------------------
     */
   private class BtnConnectListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String url="jdbc:derby://localhost:1527/labDB";
        String user= "app";
        String pwd= "app";
        try{
            
            connection= DriverManager.getConnection(url, user, pwd);
            
          
                lblConnectionStatus.setText("Connected");
           
        }catch(SQLException ex){
            
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
       
    }
}


private class BtnInsertEquipmentListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = tfEquipmentName.getText().trim();
        String department = tfDepartment.getText().trim();
        String priceText = tfPrice.getText().trim();
        boolean inUse = ckbInUse.isSelected();

        if (name.isEmpty() || department.isEmpty() || priceText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    "Please enter the name, department and price.");

            return;
        }

        try {

            BigDecimal price = new BigDecimal(priceText);

            if (price.compareTo(BigDecimal.ZERO) < 0) {

                JOptionPane.showMessageDialog(
                        EquipmentOperationGUI.this,
                        "Price cannot be negative.");

                return;
            }

            String sql =
                    "INSERT INTO APP.LAB_EQUIPMENT "
                    + "(NAME, DEPARTMENT, PURCHASE_DATE, CONDITION, IN_USE, PRICE) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setString(4, "New");
            ps.setBoolean(5, inUse);
            ps.setBigDecimal(6, price);

            int rows = ps.executeUpdate();

            ps.close();

            if (rows > 0) {

                lblOperationStatus.setText(
                        rows + " equipment record inserted.");

                btnViewAll.doClick();

                tfEquipmentName.setText("");
                tfDepartment.setText("");
                tfPrice.setText("");
                ckbInUse.setSelected(false);
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    "Please enter a valid price.");

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "Insert Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


private class BtnViewAllListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            if (baselineResultSet != null) {
                baselineResultSet.close();
            }

            if (baselineStatement != null) {
                baselineStatement.close();
            }

            baselineStatement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            String sql =
                    "SELECT EQUIPMENT_ID, NAME, DEPARTMENT, "
                    + "PURCHASE_DATE, CONDITION, IN_USE, PRICE "
                    + "FROM APP.LAB_EQUIPMENT "
                    + "ORDER BY EQUIPMENT_ID";

            baselineResultSet =
                    baselineStatement.executeQuery(sql);

            tableModel.setRowCount(0);

            int count = 0;

            while (baselineResultSet.next()) {

                Object[] row = {
                    baselineResultSet.getInt("EQUIPMENT_ID"),
                    baselineResultSet.getString("NAME"),
                    baselineResultSet.getString("DEPARTMENT"),
                    baselineResultSet.getDate("PURCHASE_DATE"),
                    baselineResultSet.getString("CONDITION"),
                    baselineResultSet.getBoolean("IN_USE"),
                    baselineResultSet.getBigDecimal("PRICE")
                };

                tableModel.addRow(row);

                count++;
            }

            lblRecordsDisplayed.setText(
                    String.valueOf(count));

            baselineResultSet.beforeFirst();

            lblOperationStatus.setText(
                    count + " records displayed.");

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "View Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

private class BtnLoadDepartmentsClickListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (connection == null || connection.isClosed()) {
                JOptionPane.showMessageDialog(
                        EquipmentOperationGUI.this,
                        "Please connect to the database first.");
                return;
            }

            cmbDepartment.removeAllItems();
            cmbDepartment.addItem("Select Department");

            String sql = "SELECT DISTINCT DEPARTMENT "
                    + "FROM APP.LAB_EQUIPMENT "
                    + "ORDER BY DEPARTMENT ASC";

            int count = 0;

            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {

                while (rs.next()) {
                    cmbDepartment.addItem(rs.getString("DEPARTMENT"));
                    count++;
                }
            }

            cmbDepartment.setSelectedIndex(0);

            lblOperationStatus.setText(
                    count + " departments loaded.");

        } catch (SQLException ex) {
            lblOperationStatus.setText("Could not load departments.");

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "Department Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

private class BtnFilterDepartmentClickListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (connection == null || connection.isClosed()) {
                JOptionPane.showMessageDialog(
                        EquipmentOperationGUI.this,
                        "Please connect to the database first.");
                return;
            }

            String department =
                    (String) cmbDepartment.getSelectedItem();

            if (cmbDepartment.getSelectedIndex() <= 0
                    || department == null
                    || department.trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        EquipmentOperationGUI.this,
                        "Please load departments and select a department.");
                return;
            }

            String sql = "SELECT EQUIPMENT_ID, NAME, DEPARTMENT, "
                    + "PURCHASE_DATE, CONDITION, IN_USE, PRICE "
                    + "FROM APP.LAB_EQUIPMENT "
                    + "WHERE DEPARTMENT = ? "
                    + "ORDER BY EQUIPMENT_ID";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, department);

                try (ResultSet rs = ps.executeQuery()) {

                    tableModel.setRowCount(0);
                    lblRecordsDisplayed.setText("Records displayed: 0");

                    while (rs.next()) {
                        Object[] row = {
                            rs.getInt("EQUIPMENT_ID"),
                            rs.getString("NAME"),
                            rs.getString("DEPARTMENT"),
                            rs.getDate("PURCHASE_DATE"),
                            rs.getString("CONDITION"),
                            rs.getBoolean("IN_USE"),
                            rs.getBigDecimal("PRICE")
                        };

                        tableModel.addRow(row);

                        lblRecordsDisplayed.setText(
                                "Records displayed: " + tableModel.getRowCount());
                    }

                    lblOperationStatus.setText(
                            tableModel.getRowCount()
                            + " matching records for " + department + ".");
                }
            }

        } catch (SQLException ex) {
            lblOperationStatus.setText("Could not filter departments.");

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "Filter Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
private class BtnFilterExistingResultSetListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        String search =
                tfEquipmentSearch.getText().trim();

        if (search.isEmpty()) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    "Enter part of an equipment name.");

            return;
        }

        if (baselineResultSet == null) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    "Click View All first.");

            return;
        }

        try {

            baselineResultSet.beforeFirst();

            tableModel.setRowCount(0);

            int count = 0;

            while (baselineResultSet.next()) {

                String equipmentName =
                        baselineResultSet.getString("NAME");

                if (equipmentName.toLowerCase().contains(
                        search.toLowerCase())) {

                    Object[] row = {
                        baselineResultSet.getInt("EQUIPMENT_ID"),
                        baselineResultSet.getString("NAME"),
                        baselineResultSet.getString("DEPARTMENT"),
                        baselineResultSet.getDate("PURCHASE_DATE"),
                        baselineResultSet.getString("CONDITION"),
                        baselineResultSet.getBoolean("IN_USE"),
                        baselineResultSet.getBigDecimal("PRICE")
                    };

                    tableModel.addRow(row);

                    count++;
                }
            }

            lblRecordsDisplayed.setText(
                    String.valueOf(count));

            lblOperationStatus.setText(
                    count + " matching records.");

            baselineResultSet.beforeFirst();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "Filter Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


private class BtnUpdateMultimediaListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if (cmbPurchaseMonth.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    "Please select a purchase month.");

            return;
        }

        int month =
                cmbPurchaseMonth.getSelectedIndex() + 1;

        try {

            String sql =
                    "UPDATE APP.LAB_EQUIPMENT "
                    + "SET CONDITION = ?, IN_USE = ? "
                    + "WHERE DEPARTMENT = ? "
                    + "AND MONTH(PURCHASE_DATE) = ?";

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setString(1, "Damaged");
            ps.setBoolean(2, false);
            ps.setString(3, "Multimedia");
            ps.setInt(4, month);

            int rows =
                    ps.executeUpdate();

            ps.close();

            lblOperationStatus.setText(
                    rows + " Multimedia records updated.");

            if (rows > 0) {
                btnViewAll.doClick();
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


private class BtnDeleteDamagedNotInUseListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        int option =
                JOptionPane.showConfirmDialog(
                        EquipmentOperationGUI.this,
                        "Delete all damaged equipment that is not in use?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

        if (option != JOptionPane.YES_OPTION) {

            lblOperationStatus.setText(
                    "Delete cancelled.");

            return;
        }

        try {

            String sql =
                    "DELETE FROM APP.LAB_EQUIPMENT "
                    + "WHERE CONDITION = ? "
                    + "AND IN_USE = ?";

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ps.setString(1, "Damaged");
            ps.setBoolean(2, false);

            int rows =
                    ps.executeUpdate();

            ps.close();

            lblOperationStatus.setText(
                    rows + " damaged records deleted.");

            if (rows > 0) {
                btnViewAll.doClick();
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


private class BtnLoadDashboardListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            Statement statement =
                    connection.createStatement();

            String sql =
                    "SELECT COUNT(*) AS TOTAL_RECORDS, "
                    + "SUM(PRICE) AS TOTAL_EXPENDITURE, "
                    + "COUNT(DISTINCT DEPARTMENT) AS TOTAL_DEPARTMENTS "
                    + "FROM APP.LAB_EQUIPMENT";

            ResultSet rs =
                    statement.executeQuery(sql);

            if (rs.next()) {

                lblTotalRecordsValue.setText(
                        String.valueOf(
                                rs.getInt("TOTAL_RECORDS")));

                lblTotalExpenditureValue.setText(
                        "R " + rs.getBigDecimal(
                                "TOTAL_EXPENDITURE"));

                lblTotalDepartmentsValue.setText(
                        String.valueOf(
                                rs.getInt("TOTAL_DEPARTMENTS")));
            }

            rs.close();

            sql =
                    "SELECT NAME, PRICE "
                    + "FROM APP.LAB_EQUIPMENT "
                    + "WHERE PRICE = "
                    + "(SELECT MIN(PRICE) FROM APP.LAB_EQUIPMENT)";

            rs = statement.executeQuery(sql);

            if (rs.next()) {

                lblCheapestEquipmentValue.setText(
                        rs.getString("NAME")
                        + " - R "
                        + rs.getBigDecimal("PRICE"));
            } else {

                lblCheapestEquipmentValue.setText(
                        "No records");
            }

            rs.close();

            sql =
                    "SELECT NAME, PRICE "
                    + "FROM APP.LAB_EQUIPMENT "
                    + "WHERE PRICE = "
                    + "(SELECT MAX(PRICE) FROM APP.LAB_EQUIPMENT)";

            rs = statement.executeQuery(sql);

            if (rs.next()) {

                lblMostExpensiveValue.setText(
                        rs.getString("NAME")
                        + " - R "
                        + rs.getBigDecimal("PRICE"));
            } else {

                lblMostExpensiveValue.setText(
                        "No records");
            }

            rs.close();
            statement.close();

            lblOperationStatus.setText(
                    "Dashboard loaded.");

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "Dashboard Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


private class BtnClearResultsListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        tableModel.setRowCount(0);

        lblRecordsDisplayed.setText("0");

        lblOperationStatus.setText(
                "Visible results cleared.");
    }
}


private class BtnExitListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            if (baselineResultSet != null) {
                baselineResultSet.close();
            }

            if (baselineStatement != null) {
                baselineStatement.close();
            }

            if (connection != null) {
                connection.close();
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    EquipmentOperationGUI.this,
                    ex.getMessage(),
                    "Exit Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        System.exit(0);
    }
}

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                EquipmentOperationGUI gui = new EquipmentOperationGUI();
                gui.setVisible(true);
            }
        });
    }
}
