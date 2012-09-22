/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoggerApplet.java
 *
 * Created on 12-Dec-2010, 03:34:46
 */

package admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import pojo.Log;
import user.ConnectManager;

/**
 *
 * @author pythonee
 */
public class LoggerApplet extends javax.swing.JApplet {
    final static Logger log = Logger.getLogger("LoggerApplet");

    @Override
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            log.severe(ex.getMessage());
        }
    }

    @Override
    public void start() {
        setSize(740, 531);

        try {
            URLConnection con = ConnectManager.getConnection(getCodeBase(), "/ebank/service/log");

            OutputStream os = con.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            HashMap<String,String> param = new HashMap<String, String>();
            param.put("action", "fetchAllHistory");
            oos.writeObject(param);
            oos.flush();
            oos.close();

            InputStream is = con.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            ArrayList<Log> allHistory = (ArrayList<Log>) ois.readObject();
            
            DefaultTableModel model = (DefaultTableModel) historyTabel.getModel();

            for (Log row : allHistory) {
                String dORw = "+";
                switch (row.getOperator()) {
                    case 0:
                        dORw = "+";
                        break;
                    case 1:
                        dORw = "-";
                        break;
                }
                model.addRow(new Object[]{row.getUsername(),row.getAccountTitle(), dORw, row.getCount(), row.getTimestamp()});
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoggerApplet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoggerApplet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoggerApplet.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formNameLabel = new javax.swing.JLabel();
        logoLabel = new javax.swing.JLabel();
        historyScrollPane1 = new javax.swing.JScrollPane();
        historyTabel = new javax.swing.JTable();
        refreshBTN = new javax.swing.JButton();

        formNameLabel.setFont(new java.awt.Font("WenQuanYi Micro Hei", 0, 18));
        formNameLabel.setText("System Log");

        logoLabel.setFont(new java.awt.Font("WenQuanYi Micro Hei", 0, 18));
        logoLabel.setText("E-Banking");

        historyTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User", "Account", "Operator", "Count", "Date/Time"
            }
        ));
        historyScrollPane1.setViewportView(historyTabel);

        refreshBTN.setText("Refresh");
        refreshBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(historyScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(formNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                        .addGap(540, 540, 540)
                        .addComponent(logoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(refreshBTN, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(formNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(refreshBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(historyScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBTNActionPerformed

        DefaultTableModel model = (DefaultTableModel) historyTabel.getModel();
        int rows = model.getRowCount();

        for(int i = rows-1; i >= 0; i--){
            model.removeRow(i);
        }

        start();

    }//GEN-LAST:event_refreshBTNActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel formNameLabel;
    private javax.swing.JScrollPane historyScrollPane1;
    private javax.swing.JTable historyTabel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JButton refreshBTN;
    // End of variables declaration//GEN-END:variables

}
