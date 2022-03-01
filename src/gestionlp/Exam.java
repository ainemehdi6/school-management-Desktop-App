/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestionlp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Hp
 */
public class Exam extends javax.swing.JFrame {

    /**
     * Creates new form Exam
     */
    
    public int getidmodule(String nom)
    {
         try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select id_module from module where nom=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,nom);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next())
        {
            return rs.getInt("id_module");
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
         return -1;
    }
    public int getidsemestre(String nom)
    {
         try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select id_semestre from semestre where libelle=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,nom);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next())
        {
            return rs.getInt("id_semestre");
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
         return -1;
    }
    /*public int getidsemestrelikename(String nom)
    {
         try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select id_semestre from semestre where libelle like '%?%'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,nom);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next())
        {
            return rs.getInt("id_semestre");
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
         return -1;
    }*/
    public int getidprof(String nom)
    {
         try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select id_prof from professeur where nom_prof=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,nom);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next())
        {
            return rs.getInt("id_prof");
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
         return -1;
    }
    public int getlastidexam()
    {
         try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select max(id_examen) from examen";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next())
        {
            return rs.getInt("max(id_examen)")+1;
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
         return -1;
    }
    public void remplirconboxProf()
    {
      try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select nom_prof from professeur";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            String prof = rs.getString("nom_prof");
            profNom.addItem(prof);
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
    }
    public void remplirconboxModule()
    {
      try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select nom from module";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            String module =  rs.getString("nom");
            modulecombobox.addItem(module);
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
    }
    public Exam() {
        initComponents();
        remplirconboxModule();
        remplirconboxProf();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        profNom = new javax.swing.JComboBox<>();
        modulecombobox = new javax.swing.JComboBox<>();
        IdgroupeExam = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        AddExam = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ajouter Un Nouveau Examen");

        profNom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selectionner" }));

        modulecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selectionner" }));

        IdgroupeExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdgroupeExamActionPerformed(evt);
            }
        });

        jLabel2.setText("Professeur :");

        jLabel3.setText("ID Groupe :");

        jLabel4.setText("Module :");

        jLabel5.setText("Date d'Examen :");

        AddExam.setText("Add");
        AddExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddExamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(IdgroupeExam)
                    .addComponent(profNom, 0, 198, Short.MAX_VALUE)
                    .addComponent(modulecombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(170, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(139, 139, 139))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(AddExam, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(228, 228, 228))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(profNom, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdgroupeExam, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(modulecombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(AddExam)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IdgroupeExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdgroupeExamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdgroupeExamActionPerformed

    private void AddExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddExamActionPerformed
        // TODO add your handling code here:
        int moduleId = getidmodule(modulecombobox.getSelectedItem().toString());
        int proId = getidprof(profNom.getSelectedItem().toString());
        int Id = Integer.parseInt(IdgroupeExam.getText());
        int exam = getlastidexam();
        String date = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
         try{
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "Insert into examen values (SEQ_EX.nextval,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, proId);
        pstmt.setInt(2, Id);
        pstmt.setInt(3, moduleId);
        pstmt.setString(4, date);
        pstmt.executeUpdate();
        note test= new note();
        test.setnote(Id, exam);
        JOptionPane.showMessageDialog(null,"insertion sucessful....!");
        conn.close();
        Main a = new Main();
        a.setVisible(true);
    }
    catch(Exception e)
    {e.printStackTrace();} 
    }//GEN-LAST:event_AddExamActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Exam().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddExam;
    private javax.swing.JTextField IdgroupeExam;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox<String> modulecombobox;
    private javax.swing.JComboBox<String> profNom;
    // End of variables declaration//GEN-END:variables
}
