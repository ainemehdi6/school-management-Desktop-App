/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionlp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hp
 */
public class note {
    private int id_etudiant;
    private int id_groupe;
    private int id_examen;
    private double moyenne;
    ArrayList<Integer> ids = new ArrayList<Integer>();

    public note() {
    }
    
    
    public note(int id_etudiant,int id_groupe, int id_examen, double moyenne) {
        this.id_etudiant = id_etudiant;
        this.id_groupe=id_groupe;
        this.id_examen = id_examen;
        this.moyenne = moyenne;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public void setId_examen(int id_examen) {
        this.id_examen = id_examen;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }
    
    
    public int getId_etudiant() {
        return id_etudiant;
    }

    public int getId_examen() {
        return id_examen;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public int getId_groupe() {
        return id_groupe;
    }
    
    /*
    public ArrayList<Integer> GetEtud(int id_groupe)
    {
        try{
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select id_etudiant from etudiant where id_groupe=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_groupe);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {           
            int id_etudiant = rs.getInt("id_etudiant");    
            ids.add(id_etudiant);
            System.out.println(id_etudiant);
        }
        conn.close();
    }
    catch(Exception e)
    {System.out.println(e);}  
        return ids;
    }*/
    public void setnote(int idgroupe,int exam)
    {
        try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();
        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "insert into note(id_etudiant,id_examen) select etudiant.id_etudiant,examen.id_examen from etudiant,examen where etudiant.id_groupe=? and examen.id_examen=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idgroupe);
        pstmt.setInt(2,exam);
        pstmt.executeQuery();
    }
    catch(Exception e)
    {System.out.println(e);}
    }
    
}
