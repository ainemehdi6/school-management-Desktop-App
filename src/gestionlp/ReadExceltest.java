/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestionlp;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Hp
 */
public class ReadExceltest extends javax.swing.JFrame {

    /**
     * Creates new form ReadExceltest
     */
    public void test()
    {
        int j=0;
        ArrayList<String> Header = new ArrayList<>();
        ArrayList<String> Code_etud = new ArrayList<>();
        ArrayList<String> Nom = new ArrayList<>();
        ArrayList<String> Prenom = new ArrayList<>();
        ArrayList<String> NomAr = new ArrayList<>();
        ArrayList<String> PrenomAr = new ArrayList<>();
        ArrayList<String> Diplome = new ArrayList<>();
        
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
                        }     
                    }
                }
                in.close();
                System.out.println(j);
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
    
    public ReadExceltest() {
        initComponents();
        test();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ReadExceltest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReadExceltest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReadExceltest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReadExceltest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReadExceltest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
