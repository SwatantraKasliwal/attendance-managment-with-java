package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class Attendance {
    private JTextField nameData;
    private JTextField totalClasses;
    private JTable table1;
    private JButton ADDRECORDButton;
    private JButton UPDATERECORDButton;
    private JPanel resultPanel;
    private JComboBox<String> subject;
    private JTextField attendance;
    private int totalMarks = 0;
    JFrame attendF = new JFrame("Attendance Management System");

    // PostgreSQL connection details
    private static final String URL = "jdbc:postgresql://localhost:5432/attendance_db";
    private static final String USER = "postgres";  // Replace with your PostgreSQL username
    private static final String PASSWORD = "xyz";  // Replace with your PostgreSQL password

    public Attendance() {
        // Initialize GUI components
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        // Text fields and combo box
        nameData = new JTextField(20);
        totalClasses = new JTextField(10);
        attendance = new JTextField(10);
        subject = new JComboBox<>(new String[]{"PHYSICS", "CHEMISTRY", "MATHS"});

        // Table and buttons
        table1 = new JTable();
        ADDRECORDButton = new JButton("Add Record");
        UPDATERECORDButton = new JButton("Update Record");

        resultPanel.add(new JLabel("Name:"));
        resultPanel.add(nameData);
        resultPanel.add(new JLabel("Subject:"));
        resultPanel.add(subject);
        resultPanel.add(new JLabel("Total Classes:"));
        resultPanel.add(totalClasses);
        resultPanel.add(new JLabel("Classes Attended:"));
        resultPanel.add(attendance);
        resultPanel.add(ADDRECORDButton);
        resultPanel.add(UPDATERECORDButton);
        resultPanel.add(new JScrollPane(table1));

        attendF.setContentPane(resultPanel);
        attendF.pack();
        attendF.setLocationRelativeTo(null);
        attendF.setVisible(true);

        // Populate table with data
        tableData();

        // Add Record Button
        ADDRECORDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameData.getText().isEmpty() || attendance.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    try {
                        String sql = "INSERT INTO attendance (NAME, SUBJECT, TOTAL_CLASSES, CLASSES_ATTENDED, TOTAL_ATTENDANCE) VALUES (?,?,?,?,?)";
                        Class.forName("org.postgresql.Driver");
                        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        PreparedStatement statement = connection.prepareStatement(sql);

                        double attend = (Double.parseDouble(attendance.getText()) / Double.parseDouble(totalClasses.getText())) * 100.0;
                        statement.setString(1, nameData.getText());
                        statement.setString(2, (String) subject.getSelectedItem());
                        statement.setInt(3, Integer.parseInt(totalClasses.getText()));
                        statement.setInt(4, Integer.parseInt(attendance.getText()));
                        statement.setString(5, String.format("%.2f", attend) + "%");
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record added successfully!");
                        tableData(); // Refresh table data
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                }
            }
        });

        // Update Record Button
        UPDATERECORDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = "UPDATE attendance SET CLASSES_ATTENDED=?, TOTAL_ATTENDANCE=? WHERE NAME=? AND SUBJECT=?";
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);

                    double attend = (Double.parseDouble(attendance.getText()) / Double.parseDouble(totalClasses.getText())) * 100.0;
                    statement.setInt(1, Integer.parseInt(attendance.getText()));
                    statement.setString(2, String.format("%.2f", attend) + "%");
                    statement.setString(3, nameData.getText());
                    statement.setString(4, (String) subject.getSelectedItem());

                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record updated successfully!");
                    tableData(); // Refresh table data
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        // Table row selection to populate fields for editing
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                nameData.setText(table1.getValueAt(row, 0).toString());
                subject.setSelectedItem(table1.getValueAt(row, 1).toString());
                totalClasses.setText(table1.getValueAt(row, 2).toString());
                attendance.setText(table1.getValueAt(row, 3).toString());
            }
        });
    }

    // Populate the JTable with data from the database
    public void tableData() {
        try {
            String sql = "SELECT * FROM attendance";
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            table1.setModel(buildTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    // Build table model from ResultSet
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                vector.add(rs.getObject(i));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
