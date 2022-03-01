package gestionlp;

import java.sql.*;
import javax.swing.JOptionPane;
import java.util.Properties;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultTextUI;
import java.io.*;
import javax.swing.JFileChooser;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public void importEtudiants()
    {
        int j=0;
        ArrayList<String> Header = new ArrayList<>();
        ArrayList<String> Code_etud = new ArrayList<>();
        ArrayList<String> Nom = new ArrayList<>();
        ArrayList<String> Prenom = new ArrayList<>();
        ArrayList<String> NomAr = new ArrayList<>();
        ArrayList<String> PrenomAr = new ArrayList<>();
        ArrayList<String> Diplome = new ArrayList<>();
        ArrayList<Integer> GroupeId = new ArrayList<>();
        
        JFileChooser openFileChooser = new JFileChooser();
        openFileChooser.setDialogTitle("Open File");
        openFileChooser.removeChoosableFileFilter(openFileChooser.getFileFilter());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File (.xlsx)","xlsx");
        openFileChooser.setFileFilter(filter);
        
        if(openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File inpuFile = openFileChooser.getSelectedFile();
            try (FileInputStream in = new FileInputStream(inpuFile)){
                
                XSSFWorkbook importedFile = new XSSFWorkbook(in);
                XSSFSheet sheet1 = importedFile.getSheetAt(0);
                
                Iterator<Row> rowIterator = sheet1.iterator();
                while(rowIterator.hasNext())
                {
                    j++;
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while(cellIterator.hasNext())
                    {
                        Cell cell = cellIterator.next();
                        if(row.getRowNum()==0)
                        {
                            Header.add(cell.getStringCellValue());
                        }
                        else
                        {
                            if(cell.getColumnIndex()==0)
                            {
                                Code_etud.add(cell.getStringCellValue());
                            }
                            else if(cell.getColumnIndex()==1)
                            {
                                Nom.add(cell.getStringCellValue());
                            }
                            else if(cell.getColumnIndex()==2)
                            {
                                Prenom.add(cell.getStringCellValue());
                            }
                            else if(cell.getColumnIndex()==3)
                            {
                                NomAr.add(cell.getStringCellValue());
                            }
                            else if(cell.getColumnIndex()==4)
                            {
                                PrenomAr.add(cell.getStringCellValue());
                            }
                            else if(cell.getColumnIndex()==5)
                            {
                                Diplome.add(cell.getStringCellValue());
                            }
                            else if(cell.getColumnIndex()==6)
                            {
                                GroupeId.add((int)cell.getNumericCellValue());
                            }
                        }     
                    }
                }
                in.close();
                for (int i = 0; i <j-1  ; i++) {
                    insertintoEtud(Code_etud.get(i).toString(),Nom.get(i).toString(),Prenom.get(i).toString(),NomAr.get(i).toString(),PrenomAr.get(i).toString(),Diplome.get(i).toString(),GroupeId.get(i).toString());
                }
                JOptionPane.showMessageDialog(null, "Import sucessful....!");
                System.out.println("Excel file id read succ");
                System.out.println("\n\n\n");
                System.out.println("Liste Code" +Code_etud);
                System.out.println("\n\n");
                System.out.println("Liste Nom" +Nom);
                System.out.println("\n\n");
                System.out.println("Liste Prenom" +Prenom);
                System.out.println("\n\n");
                System.out.println("Liste NomAr" +NomAr);
                System.out.println("\n\n");
                System.out.println("Liste PrenomAr" +PrenomAr);
                System.out.println("\n\n");
                System.out.println("Liste Diplome" +Diplome);
                System.out.println("\n\n");
                
            }    
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    };
    public void insertintoEtud(String code,String nom,String prenom,String nom_ar,String prenom_ar,String diplome,String groupeId)
    {
        try{
                    String url = "jdbc:oracle:thin:@localhost:1521:XE";
                   Properties props = new Properties();

                   props.setProperty("user", "system");
                   props.setProperty("password", "20192020");
                   Class.forName("oracle.jdbc.driver.OracleDriver");
                   Connection conn = DriverManager.getConnection(url,props);
                   String sql = "insert into Etudiant values (SEQ_ETU.nextval,?,?,?,?,?,?,?)";
                   PreparedStatement pstmt = conn.prepareStatement(sql);
                   pstmt.setString(1,code);
                   pstmt.setString(2, nom);
                   pstmt.setString(3, prenom);
                   pstmt.setString(4, nom_ar);
                   pstmt.setString(5, prenom_ar);    
                   pstmt.setString(6, diplome);   
                   pstmt.setString(7, groupeId);   
                   pstmt.executeUpdate();
                   conn.close();
                }
                catch(Exception e)
                    {e.printStackTrace();}
    };
    public void table_module(){
    try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select id_module,code_module,module.nom,module.coefficient,semestre.id_semestre,libelle,professeur.id_prof,nom_prof,prenom_prof from module,professeur,semestre where module.id_semestre=semestre.id_semestre and module.id_prof=professeur.id_prof");
        while(rs.next())
        {
           
            String id = String.valueOf(rs.getInt("id_module"));
            String code_module = rs.getString("code_module");
            String nom = rs.getString("nom");
            String coefficient = String.valueOf(rs.getFloat("coefficient"));
            String id_semestre = String.valueOf(rs.getInt("id_semestre"));
            String libelle = rs.getString("libelle");
            String id_prof = String.valueOf(rs.getInt("id_prof"));
            String nomp = rs.getString("nom_prof")+" "+rs.getString("prenom_prof");
            String tbData[] = {id,code_module,nom,coefficient,id_semestre,libelle,id_prof,nomp};
            DefaultTableModel tblModel = (DefaultTableModel)moduleTable.getModel();
            tblModel.addRow(tbData);
        }
    }
    catch(Exception e)
    {System.out.println(e);}
    };
    public void table_prof(){
    try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from professeur");
        while(rs.next())
        {
           
            String prof_id = String.valueOf(rs.getInt("id_prof"));
            String prof_nom = rs.getString("nom_prof");
            String prof_prenom = rs.getString("prenom_prof");
            String prof_code = rs.getString("codeppr");
            String prof_type = rs.getString("type");
            String tbData[] = {prof_id,prof_nom,prof_prenom,prof_code,prof_type};
            DefaultTableModel prfMdl = (DefaultTableModel)prof_table.getModel();
            prfMdl.addRow(tbData);
        }
    }
    catch(Exception e)
    {System.out.println(e);}
    };
    public void table_etudiant(){
    try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select id_etudiant,code_etudiant,nom,prenom,nom_ar,prenom_ar,diplome,etudiant.id_groupe,groupe.libele from etudiant,groupe where groupe.id_groupe=etudiant.id_groupe order by id_etudiant");
        while(rs.next())
        {
           
            String etud_id = String.valueOf(rs.getInt("id_etudiant"));
            String etud_code = rs.getString("code_etudiant");
            String etud_nom = rs.getString("nom");
            String etud_prenom = rs.getString("prenom");
            String etud_nomar = rs.getString("nom_ar");
            String etud_prenomar = rs.getString("prenom_ar");
            String etud_diplome = rs.getString("diplome");
            String etud_groupe_id = rs.getString("id_groupe");
            String etud_groupe_nom = rs.getString("libele");
            String tbData[] = {etud_id,etud_code,etud_nom,etud_prenom,etud_nomar,etud_prenomar,etud_diplome,etud_groupe_id,etud_groupe_nom};
            DefaultTableModel etudMdl = (DefaultTableModel)etud_table.getModel();
            etudMdl.addRow(tbData);
        }
    }
    catch(Exception e)
    {e.printStackTrace();}
    };
    public void table_note(int examen)
    {
       try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select note.id_etudiant,moyenne,etudiant.nom,etudiant.prenom from note,etudiant where etudiant.id_etudiant=note.id_etudiant and id_examen=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, examen);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
           
            String etud_id = rs.getString("id_etudiant");
            String etud_nom = rs.getString("nom")+" "+rs.getString("prenom");
            String moyenne = rs.getString("moyenne");
            String tbData[] = {etud_id,etud_nom,moyenne};
            DefaultTableModel noteMdl = (DefaultTableModel)note_table.getModel();
            noteMdl.addRow(tbData);
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
    }
   
    public void table_delibymod(int modid){
    try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select note.id_etudiant,etudiant.nom,etudiant.prenom,examen.id_examen,module.id_module,module.nom as module,note.moyenne from note,etudiant,module,examen where note.id_etudiant=etudiant.id_etudiant and module.id_module=examen.id_module and note.id_examen=examen.id_examen and module.id_module=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,modid);
        ResultSet rs = pstmt.executeQuery();      
        while(rs.next())
        {
           
            String etud_id = String.valueOf(rs.getInt("id_etudiant"));
            String etud_nom = rs.getString("nom")+" "+rs.getString("prenom");
            String exam_id = rs.getString("id_examen");
            String mod_nom = rs.getString("module");
            String moyenne = rs.getString("moyenne");
            String tbData[] = {etud_id,etud_nom,exam_id,mod_nom,moyenne};
            DefaultTableModel prfMdl = (DefaultTableModel)delibModuleTable.getModel();
            prfMdl.addRow(tbData);
        }
    }
    catch(Exception e)
    {System.out.println(e);}
    };
    public void table_delibysemestre(int semid){
    try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select note.id_etudiant,etudiant.nom,etudiant.prenom,semestre.libelle,module.id_module,module.nom as module,note.moyenne from note,etudiant,module,examen,semestre where note.id_etudiant=etudiant.id_etudiant and semestre.id_semestre=module.id_semestre and module.id_module=examen.id_module and note.id_examen=examen.id_examen and semestre.id_semestre=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,semid);
        ResultSet rs = pstmt.executeQuery();      
        while(rs.next())
        {
           
            String etud_id = String.valueOf(rs.getInt("id_etudiant"));
            String etud_nom = rs.getString("nom")+" "+rs.getString("prenom");
            String semestre = rs.getString("libelle");
            String mod_nom = rs.getString("module");
            String moyenne = rs.getString("moyenne");
            String tbData[] = {etud_id,etud_nom,semestre,mod_nom,moyenne};
            DefaultTableModel prfMdl = (DefaultTableModel)delibSemestreTable.getModel();
            prfMdl.addRow(tbData);
        }
    }
    catch(Exception e)
    {System.out.println(e);}
    };
     public void table_delibyAnne(String anne){
    try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select note.id_etudiant,etudiant.nom,etudiant.prenom,semestre.libelle,module.id_module,module.nom as module,note.moyenne from note,etudiant,module,examen,semestre where note.id_etudiant=etudiant.id_etudiant and semestre.id_semestre=module.id_semestre and module.id_module=examen.id_module and note.id_examen=examen.id_examen and semestre.libelle like '%"+anne+"%'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();      
        while(rs.next())
        {         
            String etud_id = String.valueOf(rs.getInt("id_etudiant"));
            String etud_nom = rs.getString("nom")+" "+rs.getString("prenom");
            String semestre = rs.getString("libelle");
            String mod_nom = rs.getString("module");
            String moyenne = rs.getString("moyenne");
            String tbData[] = {etud_id,etud_nom,semestre,mod_nom,moyenne};
            DefaultTableModel prfMdl = (DefaultTableModel)delibAnneTable.getModel();
            prfMdl.addRow(tbData);
        }
    }
    catch(Exception e)
    {e.printStackTrace();}
    };
    public void remplirModulebox()
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
            String module = rs.getString("nom");
            ModuleComboBox.addItem(module);
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
    }
    public void remplirsemestrecombobox()
    {
      try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select libelle from semestre";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            String seme = rs.getString("libelle");
            SemestreComboBox.addItem(seme);
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
    }
    public void remplirconbox()
    {
      try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "select id_examen,examen.id_groupe,module.nom,dateExamen from examen,module where examen.id_module=module.id_module  order by examen.id_examen";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            String exam = "Exam N:"+rs.getString("id_examen")+"  Groupe N:"+rs.getString("id_groupe")+" de "+rs.getString("nom")+" en "+rs.getString("dateExamen");
            exambox.addItem(exam);
        }
    }
    catch(Exception e)
    {e.printStackTrace();} 
    }
    public void updatenote(int id,double moyenne,int exam_id)
    {
        try{
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "update note set moyenne=? where id_etudiant=? and id_examen=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDouble(1, moyenne);
        pstmt.setInt(2, id);
        pstmt.setInt(3, exam_id);
        pstmt.executeUpdate();
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}  
    }
    public Main() {
        initComponents();
        table_module();
        id_etud.setEditable(false);
        id_etud.setVisible(false);
        id_module.setEditable(false);
        id_module.setVisible(false);
        ProfId.setEditable(false);
        ProfId.setVisible(false);
        remplirconbox();
        remplirsemestrecombobox();
        remplirModulebox();
         
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        modules = new javax.swing.JButton();
        professeurs = new javax.swing.JButton();
        notes = new javax.swing.JButton();
        etudiants = new javax.swing.JButton();
        delibirations = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        id_module = new javax.swing.JTextField();
        code_module = new javax.swing.JTextField();
        nom_module = new javax.swing.JTextField();
        coef_module = new javax.swing.JTextField();
        semestre_module = new javax.swing.JTextField();
        prof_module = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        moduleTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        Addmodule = new javax.swing.JButton();
        Updatemodule = new javax.swing.JButton();
        DeleteModule = new javax.swing.JButton();
        ClearModule = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        ProfId = new javax.swing.JTextField();
        ProfNom = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        ProfPrenom = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        ProfCode = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        ProfType = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        prof_table = new javax.swing.JTable();
        addprof = new javax.swing.JButton();
        updateprof = new javax.swing.JButton();
        deleteprof = new javax.swing.JButton();
        clearprof = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        note_table = new javax.swing.JTable();
        updatenote = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        exambox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        id_etud = new javax.swing.JTextField();
        code_etud = new javax.swing.JTextField();
        nom_etud = new javax.swing.JTextField();
        prenom_etud = new javax.swing.JTextField();
        nomar_etud = new javax.swing.JTextField();
        prenomar_etud = new javax.swing.JTextField();
        diplome_etud = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        groupe_etud = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        etud_table = new javax.swing.JTable();
        add_etud = new javax.swing.JButton();
        update_etud = new javax.swing.JButton();
        delete_atud = new javax.swing.JButton();
        clear_etud = new javax.swing.JButton();
        ImportEtud = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ModuleComboBox = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        delibModuleTable = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        SemestreComboBox = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        delibSemestreTable = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        AnneComboBox = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        delibAnneTable = new javax.swing.JTable();
        ImprimerdelibAnneTable = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        delibSemes = new javax.swing.JButton();
        delibAnne = new javax.swing.JButton();
        delibMod = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Sélectionner l'option voulue ");

        modules.setText("Gestion des Modules");
        modules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modulesActionPerformed(evt);
            }
        });

        professeurs.setText("Gestion des Professeurs");
        professeurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                professeursActionPerformed(evt);
            }
        });

        notes.setText("Gestion des Notes");
        notes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notesActionPerformed(evt);
            }
        });

        etudiants.setText("Gestion des Etudiants");
        etudiants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etudiantsActionPerformed(evt);
            }
        });

        delibirations.setText("Gestion des Délibirations");
        delibirations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delibirationsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(etudiants, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(professeurs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(modules, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel2))
                        .addComponent(delibirations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(notes, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(modules)
                .addGap(36, 36, 36)
                .addComponent(professeurs)
                .addGap(50, 50, 50)
                .addComponent(etudiants)
                .addGap(45, 45, 45)
                .addComponent(notes)
                .addGap(45, 45, 45)
                .addComponent(delibirations)
                .addContainerGap(249, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 650));

        id_module.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_moduleActionPerformed(evt);
            }
        });

        jLabel5.setText("Code :");

        jLabel6.setText("Nom :");

        jLabel7.setText("Coefficient :");

        jLabel8.setText("ID Semestre :");

        jLabel9.setText("ID Professeur :");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(coef_module, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(prof_module, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(semestre_module)
                    .addComponent(id_module)
                    .addComponent(code_module, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nom_module, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(id_module, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(code_module, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nom_module, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coef_module, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(41, 41, 41)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(semestre_module, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prof_module, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(22, 22, 22))
        );

        moduleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Code Module", "Nom", "Coefficient", "ID Semestre", "Semestre", "Id Prof", "Nom Prof"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        moduleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moduleTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(moduleTable);
        if (moduleTable.getColumnModel().getColumnCount() > 0) {
            moduleTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            moduleTable.getColumnModel().getColumn(0).setMaxWidth(50);
            moduleTable.getColumnModel().getColumn(1).setPreferredWidth(80);
            moduleTable.getColumnModel().getColumn(1).setMaxWidth(80);
            moduleTable.getColumnModel().getColumn(3).setPreferredWidth(70);
            moduleTable.getColumnModel().getColumn(3).setMaxWidth(70);
            moduleTable.getColumnModel().getColumn(4).setPreferredWidth(90);
            moduleTable.getColumnModel().getColumn(4).setMaxWidth(90);
            moduleTable.getColumnModel().getColumn(6).setPreferredWidth(60);
            moduleTable.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Module Registration");

        Addmodule.setText("Add");
        Addmodule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddmoduleActionPerformed(evt);
            }
        });

        Updatemodule.setText("Update");
        Updatemodule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdatemoduleActionPerformed(evt);
            }
        });

        DeleteModule.setText("Delete");
        DeleteModule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteModuleActionPerformed(evt);
            }
        });

        ClearModule.setText("Clear");
        ClearModule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearModuleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(825, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(224, 224, 224))
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DeleteModule, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addComponent(Addmodule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ClearModule, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(Updatemodule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(34, 34, 34)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Addmodule, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Updatemodule, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ClearModule, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteModule, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Professeur Registration");

        jLabel13.setText("Nom :");

        jLabel14.setText("Prenom :");

        jLabel15.setText("Code PPR :");

        jLabel16.setText("Type :");

        ProfType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Permanant", "Vacataire", " " }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProfCode)
                    .addComponent(ProfPrenom)
                    .addComponent(ProfNom)
                    .addComponent(ProfId)
                    .addComponent(ProfType, 0, 192, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ProfId, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProfNom, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ProfPrenom, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProfCode, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProfType, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        prof_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Professeur ", "Nom", "Pénom", "Code PPR", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        prof_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prof_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(prof_table);

        addprof.setText("Add");
        addprof.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addprofActionPerformed(evt);
            }
        });

        updateprof.setText("Update");
        updateprof.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateprofActionPerformed(evt);
            }
        });

        deleteprof.setText("Delete");
        deleteprof.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteprofActionPerformed(evt);
            }
        });

        clearprof.setText("Clear");
        clearprof.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearprofActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(deleteprof, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(addprof, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(82, 82, 82)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clearprof, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                            .addComponent(updateprof, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(updateprof, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addprof, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteprof, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearprof, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", jPanel3);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setText("Note Registration");

        note_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Etudiant", "Nom Etudiant", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(note_table);
        if (note_table.getColumnModel().getColumnCount() > 0) {
            note_table.getColumnModel().getColumn(0).setPreferredWidth(100);
            note_table.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        updatenote.setText("Update");
        updatenote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatenoteActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Selectionner l'examin :");

        exambox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selectionner" }));
        exambox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                examboxMouseClicked(evt);
            }
        });
        exambox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                examboxActionPerformed(evt);
            }
        });

        jButton1.setText("Ajouter un Examen");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(455, 455, 455))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1064, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(416, 416, 416)
                        .addComponent(updatenote, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(exambox, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exambox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updatenote, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab3", jPanel4);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Etudiant Registration");

        jLabel23.setText("Code :");

        jLabel24.setText("Nom :");

        jLabel25.setText("Prénom :");

        jLabel26.setText("Nom Arabe :");

        jLabel27.setText("Prénom Arabe :");

        jLabel28.setText("Diplome :");

        id_etud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_etudActionPerformed(evt);
            }
        });

        nom_etud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nom_etudActionPerformed(evt);
            }
        });

        jLabel29.setText("ID Groupe :");

        groupe_etud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupe_etudActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(45, 45, 45)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(code_etud, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(nom_etud, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(prenom_etud, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomar_etud, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id_etud)
                    .addComponent(diplome_etud)
                    .addComponent(prenomar_etud, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupe_etud))
                .addGap(14, 14, 14))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(id_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(code_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nom_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prenom_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomar_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prenomar_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(diplome_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(groupe_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        etud_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Etudiant", "Code Etudiant", "Nom", "Prenom", "Nom Arabe", "Prenom Arabe", "Diplome", "Groupe Id", "Groupe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        etud_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etud_tableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(etud_table);

        add_etud.setText("Add");
        add_etud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_etudActionPerformed(evt);
            }
        });

        update_etud.setText("Update");
        update_etud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_etudActionPerformed(evt);
            }
        });

        delete_atud.setText("Delete");
        delete_atud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_atudActionPerformed(evt);
            }
        });

        clear_etud.setText("Clear");
        clear_etud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_etudActionPerformed(evt);
            }
        });

        ImportEtud.setText("Import");
        ImportEtud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportEtudActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel21))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(ImportEtud, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(delete_atud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(add_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(81, 81, 81)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(update_etud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clear_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(add_etud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(update_etud, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clear_etud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete_atud, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(ImportEtud, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane4)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab4", jPanel5);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Sélectionner le Module");

        ModuleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sélectionner" }));
        ModuleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModuleComboBoxActionPerformed(evt);
            }
        });

        delibModuleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Etudiant", "Nom Etudiant", "Id Examen", "Nom Module", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(delibModuleTable);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(ModuleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 881, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ModuleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab1", jPanel9);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Sélectionner Semestre");

        SemestreComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sélectionner" }));
        SemestreComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SemestreComboBoxActionPerformed(evt);
            }
        });

        delibSemestreTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Etudiant", "Nom Etudiant", "Semestre", "Module", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(delibSemestreTable);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SemestreComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SemestreComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab2", jPanel11);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Sélectionner l'Année");

        AnneComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sélectionner", "2020/2021", "2021/2022", "2022/2023", "2023/2024", "2024/2025", " " }));
        AnneComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnneComboBoxActionPerformed(evt);
            }
        });

        delibAnneTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Etudiant", "Nom Etudiant", "Année", "Module", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(delibAnneTable);

        ImprimerdelibAnneTable.setText("Imprimer");
        ImprimerdelibAnneTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImprimerdelibAnneTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane7))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AnneComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 242, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ImprimerdelibAnneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(375, 375, 375))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AnneComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ImprimerdelibAnneTable)
                .addGap(18, 18, 18))
        );

        jTabbedPane2.addTab("tab3", jPanel12);

        jPanel6.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, -36, -1, 690));

        delibSemes.setText("Délibiration de Semestre");
        delibSemes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delibSemesActionPerformed(evt);
            }
        });

        delibAnne.setText("Délibiration de  l'Année");
        delibAnne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delibAnneActionPerformed(evt);
            }
        });

        delibMod.setText("Délibiration de Module");
        delibMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delibModActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(delibSemes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delibMod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delibAnne, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(delibMod)
                .addGap(125, 125, 125)
                .addComponent(delibSemes)
                .addGap(139, 139, 139)
                .addComponent(delibAnne)
                .addContainerGap(192, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 23, -1, -1));

        jTabbedPane1.addTab("tab5", jPanel6);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, -40, 1130, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void modulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modulesActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
        DefaultTableModel dtm = (DefaultTableModel) moduleTable.getModel();
        dtm.setRowCount(0);
        table_module();
    }//GEN-LAST:event_modulesActionPerformed

    private void professeursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_professeursActionPerformed
        // TODO add your handling code here:
         jTabbedPane1.setSelectedIndex(1);
         DefaultTableModel dtm = (DefaultTableModel) prof_table.getModel();
         dtm.setRowCount(0);
         table_prof();
    }//GEN-LAST:event_professeursActionPerformed

    private void notesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notesActionPerformed
        // TODO add your handling code here:
         jTabbedPane1.setSelectedIndex(2);
         
    }//GEN-LAST:event_notesActionPerformed

    private void etudiantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etudiantsActionPerformed
        // TODO add your handling code here:
         jTabbedPane1.setSelectedIndex(3);
         DefaultTableModel dtm = (DefaultTableModel) etud_table.getModel();
         dtm.setRowCount(0);
         table_etudiant();
    }//GEN-LAST:event_etudiantsActionPerformed

    private void delibirationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delibirationsActionPerformed
        // TODO add your handling code here:
         jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_delibirationsActionPerformed

    private void id_moduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_moduleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_moduleActionPerformed

    private void AddmoduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddmoduleActionPerformed
        // TODO add your handling code here:
        try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "insert into module values (seq_mod.nextval,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, code_module.getText());
        pstmt.setString(2, nom_module.getText());
        pstmt.setDouble(3, Double.parseDouble(coef_module.getText()));
        pstmt.setInt(4, Integer.parseInt(semestre_module.getText()));
        pstmt.setInt(5, Integer.parseInt(prof_module.getText()));
        
        if(pstmt.executeUpdate()==1)
        {
            JOptionPane.showMessageDialog(null, "insertion sucessful....!");
            
            DefaultTableModel dtm = (DefaultTableModel) moduleTable.getModel();
            dtm.setRowCount(0);
            table_module();
        }
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}
    
    }//GEN-LAST:event_AddmoduleActionPerformed

    private void nom_etudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nom_etudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nom_etudActionPerformed

    private void moduleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moduleTableMouseClicked
        // TODO add your handling code here:
        int ligne = moduleTable.getSelectedRow();
        id_module.setText(moduleTable.getModel().getValueAt(ligne,0).toString());
        code_module.setText(moduleTable.getModel().getValueAt(ligne,1).toString());
        nom_module.setText(moduleTable.getModel().getValueAt(ligne,2).toString());
        coef_module.setText(moduleTable.getModel().getValueAt(ligne,3).toString());
        semestre_module.setText(moduleTable.getModel().getValueAt(ligne,4).toString());
        prof_module.setText(moduleTable.getModel().getValueAt(ligne,6).toString());
    }//GEN-LAST:event_moduleTableMouseClicked

    private void UpdatemoduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdatemoduleActionPerformed
        // TODO add your handling code here:
    try{
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "update module set code_module = ?, nom = ?,coefficient = ?, id_semestre = ?, id_prof = ? where id_module = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, code_module.getText());
        pstmt.setString(2, nom_module.getText());
        pstmt.setDouble(3, Double.parseDouble(coef_module.getText()));
        pstmt.setInt(4, Integer.parseInt(semestre_module.getText()));
        pstmt.setInt(5, Integer.parseInt(prof_module.getText()));
        pstmt.setInt(6, Integer.parseInt(id_module.getText()));
        if(pstmt.executeUpdate()==1)
        {
            JOptionPane.showMessageDialog(null, "Update sucessful....!");
            
            DefaultTableModel dtm = (DefaultTableModel) moduleTable.getModel();
            dtm.setRowCount(0);
            table_module();
        }
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}    
    }//GEN-LAST:event_UpdatemoduleActionPerformed

    private void DeleteModuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteModuleActionPerformed
        // TODO add your handling code here:
        try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "delete from module where id_module=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id_module.getText().toString());
        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Delete sucessful....!");
        DefaultTableModel dtm = (DefaultTableModel) moduleTable.getModel();
        dtm.setRowCount(0);
        table_module();
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}
     
    }//GEN-LAST:event_DeleteModuleActionPerformed

    private void ClearModuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearModuleActionPerformed
        // TODO add your handling code here:
        id_module.setText("");
        code_module.setText("");
        nom_module.setText("");
        coef_module.setText("");
        semestre_module.setText("");
        prof_module.setText("");
    }//GEN-LAST:event_ClearModuleActionPerformed

    private void ImportEtudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportEtudActionPerformed
        // TODO add your handling code here:
        importEtudiants();
        DefaultTableModel dtm = (DefaultTableModel) etud_table.getModel();
        dtm.setRowCount(0);
        table_etudiant();
        
    }//GEN-LAST:event_ImportEtudActionPerformed

    private void prof_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prof_tableMouseClicked
        // TODO add your handling code here:
        int ligne = prof_table.getSelectedRow();
        ProfId.setText(prof_table.getModel().getValueAt(ligne,0).toString());
        ProfNom.setText(prof_table.getModel().getValueAt(ligne,1).toString());
        ProfPrenom.setText(prof_table.getModel().getValueAt(ligne,2).toString());
        ProfCode.setText(prof_table.getModel().getValueAt(ligne,3).toString());
        ProfType.setSelectedItem(prof_table.getModel().getValueAt(ligne,4).toString());
    }//GEN-LAST:event_prof_tableMouseClicked

    private void addprofActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addprofActionPerformed
        // TODO add your handling code here:
        try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "insert into professeur values (seq_prof.nextval,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //pstmt.setInt(1, Integer.parseInt(ProfId.getText()));
        pstmt.setString(1, ProfNom.getText());
        pstmt.setString(2, ProfPrenom.getText());
        pstmt.setString(3, ProfCode.getText());
        pstmt.setString(4, ProfType.getSelectedItem().toString());    
        if(pstmt.executeUpdate()==1)
        {
            JOptionPane.showMessageDialog(null, "insertion sucessful....!");
            
            DefaultTableModel dtm = (DefaultTableModel) prof_table.getModel();
            dtm.setRowCount(0);
            table_prof();
        }
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_addprofActionPerformed

    private void clearprofActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearprofActionPerformed
        // TODO add your handling code here:
        ProfId.setText("");
        ProfNom.setText("");
        ProfPrenom.setText("");
        ProfCode.setText("");    
    }//GEN-LAST:event_clearprofActionPerformed

    private void updateprofActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateprofActionPerformed
        // TODO add your handling code here:
        try{
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "update professeur set nom_prof=?,prenom_prof=?,codeppr=?,type=? where id_prof=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, ProfNom.getText());
        pstmt.setString(2, ProfPrenom.getText());
        pstmt.setString(3, ProfCode.getText());
        pstmt.setString(4, ProfType.getSelectedItem().toString());
        pstmt.setInt(5, Integer.parseInt(ProfId.getText()));
        if(pstmt.executeUpdate()==1)
        {
            JOptionPane.showMessageDialog(null, "Update sucessful....!");
            
            DefaultTableModel dtm = (DefaultTableModel) prof_table.getModel();
            dtm.setRowCount(0);
            table_prof();
        }
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}  
    }//GEN-LAST:event_updateprofActionPerformed

    private void deleteprofActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteprofActionPerformed
        // TODO add your handling code here:
        try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "delete from professeur where id_prof=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, ProfId.getText().toString());
        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Delete sucessful....!");
        DefaultTableModel dtm = (DefaultTableModel) prof_table.getModel();
        dtm.setRowCount(0);
        table_prof();
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_deleteprofActionPerformed

    private void add_etudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_etudActionPerformed
        // TODO add your handling code here:
        try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "insert into etudiant values (seq_etu.nextval,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //pstmt.setInt(1, Integer.parseInt(id_etud.getText()));
        pstmt.setString(1, code_etud.getText());
        pstmt.setString(2, nom_etud.getText());
        pstmt.setString(3, prenom_etud.getText());
        pstmt.setString(4, nomar_etud.getText());
        pstmt.setString(5, prenomar_etud.getText());
        pstmt.setString(6, diplome_etud.getText());
        pstmt.setInt(7,Integer.parseInt(groupe_etud.getText()));
        if(pstmt.executeUpdate()==1)
        {
            JOptionPane.showMessageDialog(null, "insertion sucessful....!");
            
            DefaultTableModel dtm = (DefaultTableModel) etud_table.getModel();
            dtm.setRowCount(0);
            table_etudiant();
        }
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_add_etudActionPerformed

    private void delete_atudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_atudActionPerformed
        // TODO add your handling code here:
        try{
         String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "delete from etudiant where id_etudiant=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id_etud.getText().toString());
        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Delete sucessful....!");
        DefaultTableModel dtm = (DefaultTableModel) etud_table.getModel();
        dtm.setRowCount(0);
        table_etudiant();
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_delete_atudActionPerformed

    private void update_etudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_etudActionPerformed
        // TODO add your handling code here:
        try{
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Properties props = new Properties();

        props.setProperty("user", "system");
        props.setProperty("password", "20192020");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url,props);
        String sql = "update etudiant set code_etudiant=?,nom=?,prenom=?,nom_ar=? ,prenom_ar=?,diplome=?, id_groupe=? where id_etudiant=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, code_etud.getText());
        pstmt.setString(2, nom_etud.getText());
        pstmt.setString(3, prenom_etud.getText());
        pstmt.setString(4, nomar_etud.getText());
        pstmt.setString(5, prenomar_etud.getText());
        pstmt.setString(6, diplome_etud.getText());
        pstmt.setInt(7,Integer.parseInt(groupe_etud.getText()));
        pstmt.setInt(8, Integer.parseInt(id_etud.getText()));
        if(pstmt.executeUpdate()==1)
        {
            JOptionPane.showMessageDialog(null, "Update sucessful....!");
            
            DefaultTableModel dtm = (DefaultTableModel) etud_table.getModel();
            dtm.setRowCount(0);
            table_etudiant();
        }
        conn.close();
    }
    catch(Exception e)
    {JOptionPane.showMessageDialog(null, e);}  
    }//GEN-LAST:event_update_etudActionPerformed

    private void clear_etudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_etudActionPerformed
        // TODO add your handling code here:
        id_etud.setText("");  
        code_etud.setText("");
        nom_etud.setText("");
        prenom_etud.setText("");
        nomar_etud.setText("");
        prenomar_etud.setText("");  
        diplome_etud.setText("");
        groupe_etud.setText("");
    }//GEN-LAST:event_clear_etudActionPerformed

    private void etud_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etud_tableMouseClicked
        // TODO add your handling code here:
        int ligne = etud_table.getSelectedRow();
        id_etud.setText(etud_table.getModel().getValueAt(ligne,0).toString());
        code_etud.setText(etud_table.getModel().getValueAt(ligne,1).toString());
        nom_etud.setText(etud_table.getModel().getValueAt(ligne,2).toString());
        prenom_etud.setText(etud_table.getModel().getValueAt(ligne,3).toString());
        nomar_etud.setText(etud_table.getModel().getValueAt(ligne,4).toString());
        prenomar_etud.setText(etud_table.getModel().getValueAt(ligne,5).toString());
        diplome_etud.setText(etud_table.getModel().getValueAt(ligne,6).toString());
        groupe_etud.setText(etud_table.getModel().getValueAt(ligne,7).toString());
    }//GEN-LAST:event_etud_tableMouseClicked

    private void groupe_etudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupe_etudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_groupe_etudActionPerformed

    private void id_etudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_etudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_etudActionPerformed

    private void updatenoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatenoteActionPerformed
        // TODO add your handling code here:
         String exam = exambox.getSelectedItem().toString();
        String ch = exam.substring(7,8);  
        int n = Integer.parseInt(ch);
        for (int i = 0; i < note_table.getRowCount(); i++) {
            int id =    Integer.parseInt(note_table.getValueAt(i, 0).toString()) ;
            double moy =  Double.valueOf(note_table.getValueAt(i, 2).toString());
            updatenote(id,moy,n);
        }
        JOptionPane.showMessageDialog(null, "Update sucessful....!");           
    }//GEN-LAST:event_updatenoteActionPerformed

    private void examboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_examboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_examboxMouseClicked

    private void examboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examboxActionPerformed
        // TODO add your handling code here:
        String exam = exambox.getSelectedItem().toString();
        String ch = exam.substring(7,8);  
        int n = Integer.parseInt(ch);
        DefaultTableModel dtm = (DefaultTableModel) note_table.getModel();
         dtm.setRowCount(0);
         table_note(n);
    }//GEN-LAST:event_examboxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Exam exam = new Exam();
        exam.setVisible(true);
        exam.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void delibSemesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delibSemesActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_delibSemesActionPerformed

    private void delibModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delibModActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_delibModActionPerformed

    private void delibAnneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delibAnneActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_delibAnneActionPerformed

    private void ModuleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModuleComboBoxActionPerformed
        // TODO add your handling code here:
        Exam test1 = new Exam();
        int idm = test1.getidmodule(ModuleComboBox.getSelectedItem().toString());
        DefaultTableModel dtm = (DefaultTableModel) delibModuleTable.getModel();
        dtm.setRowCount(0);
        table_delibymod(idm);
        
    }//GEN-LAST:event_ModuleComboBoxActionPerformed

    private void SemestreComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SemestreComboBoxActionPerformed
        // TODO add your handling code here:
        Exam test1 = new Exam();
        int idm = test1.getidsemestre(SemestreComboBox.getSelectedItem().toString());
        DefaultTableModel dtm = (DefaultTableModel) delibSemestreTable.getModel();
        dtm.setRowCount(0);
        table_delibysemestre(idm);
    }//GEN-LAST:event_SemestreComboBoxActionPerformed

    private void AnneComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnneComboBoxActionPerformed
        // TODO add your handling code here:
        String anne = AnneComboBox.getSelectedItem().toString();
        DefaultTableModel dtm = (DefaultTableModel) delibAnneTable.getModel();
        dtm.setRowCount(0);
        table_delibyAnne(anne);
    }//GEN-LAST:event_AnneComboBoxActionPerformed

    private void ImprimerdelibAnneTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImprimerdelibAnneTableActionPerformed
        // TODO add your handling code here:
        MessageFormat header = new MessageFormat("Délibiration de l'Année : "+ AnneComboBox.getSelectedItem().toString()+"");
        MessageFormat footer = new MessageFormat("Page{0,number,integer}");
        try {
            delibAnneTable.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        } catch (java.awt.print.PrinterException e) {
            System.err.format("Erreur d'impression", e.getMessage());
        }
    }//GEN-LAST:event_ImprimerdelibAnneTableActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /* Create and display the form */
        
        // TODO add your handling code here:     
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Addmodule;
    private javax.swing.JComboBox<String> AnneComboBox;
    private javax.swing.JButton ClearModule;
    private javax.swing.JButton DeleteModule;
    private javax.swing.JButton ImportEtud;
    private javax.swing.JButton ImprimerdelibAnneTable;
    private javax.swing.JComboBox<String> ModuleComboBox;
    private javax.swing.JTextField ProfCode;
    private javax.swing.JTextField ProfId;
    private javax.swing.JTextField ProfNom;
    private javax.swing.JTextField ProfPrenom;
    private javax.swing.JComboBox<String> ProfType;
    private javax.swing.JComboBox<String> SemestreComboBox;
    private javax.swing.JButton Updatemodule;
    private javax.swing.JButton add_etud;
    private javax.swing.JButton addprof;
    private javax.swing.JButton clear_etud;
    private javax.swing.JButton clearprof;
    private javax.swing.JTextField code_etud;
    private javax.swing.JTextField code_module;
    private javax.swing.JTextField coef_module;
    private javax.swing.JButton delete_atud;
    private javax.swing.JButton deleteprof;
    private javax.swing.JButton delibAnne;
    private javax.swing.JTable delibAnneTable;
    private javax.swing.JButton delibMod;
    private javax.swing.JTable delibModuleTable;
    private javax.swing.JButton delibSemes;
    private javax.swing.JTable delibSemestreTable;
    private javax.swing.JButton delibirations;
    private javax.swing.JTextField diplome_etud;
    private javax.swing.JTable etud_table;
    private javax.swing.JButton etudiants;
    private javax.swing.JComboBox<String> exambox;
    private javax.swing.JTextField groupe_etud;
    private javax.swing.JTextField id_etud;
    private javax.swing.JTextField id_module;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable moduleTable;
    private javax.swing.JButton modules;
    private javax.swing.JTextField nom_etud;
    private javax.swing.JTextField nom_module;
    private javax.swing.JTextField nomar_etud;
    private javax.swing.JTable note_table;
    private javax.swing.JButton notes;
    private javax.swing.JTextField prenom_etud;
    private javax.swing.JTextField prenomar_etud;
    private javax.swing.JTextField prof_module;
    private javax.swing.JTable prof_table;
    private javax.swing.JButton professeurs;
    private javax.swing.JTextField semestre_module;
    private javax.swing.JButton update_etud;
    private javax.swing.JButton updatenote;
    private javax.swing.JButton updateprof;
    // End of variables declaration//GEN-END:variables
}
