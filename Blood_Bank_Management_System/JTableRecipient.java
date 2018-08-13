package recipientdatabase;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
//import donor.DonorFormpk;
import java.awt.event.*;
import java.util.Vector;

public class JTableRecipient extends JFrame{
    ResultSet rs;

    public JTableRecipient(String s){
    super(s);
    final Vector columnNames = new Vector();
        final Vector data = new Vector();

     try{
    Class.forName("oracle.jdbc.driver.OracleDriver");
     Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "shivani", "chotu");
        Statement st = con.createStatement();
        ResultSet rs= st.executeQuery("Select * from Recipient");
    ResultSetMetaData md = rs.getMetaData();
int columns = md.getColumnCount();
for (int i = 1; i <= columns; i++) {
columnNames.addElement( md.getColumnName(i) );
}
while (rs.next()) {
Vector row = new Vector(columns);
for (int i = 1; i <= columns; i++) {
row.addElement( rs.getObject(i) );
}
data.addElement( row );
}
}
catch(Exception e){}

JTable table = new JTable(data, columnNames);
table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    table.getColumnModel().getColumn(0).setPreferredWidth(150);
    table.getColumnModel().getColumn(1).setPreferredWidth(150);
    table.getColumnModel().getColumn(2).setPreferredWidth(150);
    table.getColumnModel().getColumn(3).setPreferredWidth(210);
    table.getColumnModel().getColumn(4).setPreferredWidth(210);
table.getColumnModel().getColumn(5).setPreferredWidth(210);
table.getColumnModel().getColumn(6).setPreferredWidth(210);




table.setPreferredScrollableViewportSize(new Dimension(1500,700));

table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

JScrollPane scrollPane = new JScrollPane(table);
add(scrollPane);
//add(panel);
}
}