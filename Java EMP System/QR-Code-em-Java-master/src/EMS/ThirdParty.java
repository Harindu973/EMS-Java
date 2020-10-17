/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EMS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class ThirdParty {
    
    Connection conn = DBConn.connect();
    boolean res = false;
    public int Mark;
    public String Name;
    //Scan sc = new Scan();
    
    
    public void checkemp(String QR){
         
        
                         try{
                 String query = "SELECT * FROM attendance where EMPID = '"+QR+"' ";
                 Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(query);
                 
                        while (rs.next())
                        {
                          res = true;
                          Mark = rs.getInt("Mark");
                          Name = rs.getString("Name");
                          
                          
                         if(Mark == 0){
                            
                            
                            
                                    String attquery = "update attendance set Attended = 'Present', Arriving = '"+java.time.LocalDateTime.now()+"', Mark = 1 where EMPID = ?";
                                    PreparedStatement preparedStmt = conn.prepareStatement(attquery);
                                    preparedStmt.setString(1, QR);
                                    preparedStmt.executeUpdate();
                                    JOptionPane.showMessageDialog(null,"Good Morning "+Name+", Your Attending is Marked...!!! ");
                                    
                                  
 
                            
                         }else{
                             LocalDateTime dtarrive = null;
                                                String arriveScanQry = "SELECT * FROM attendance where EMPID = '"+QR+"' ";
                                                Statement stt = conn.createStatement();
                                                ResultSet rss = stt.executeQuery(arriveScanQry);

                                              while (rss.next())
                                              {

                                                dtarrive = rss.getTimestamp("Arriving").toLocalDateTime();

                                              }
                                              
                                             
                             
                                         LocalDateTime dtlev = java.time.LocalDateTime.now();
                                         
                                         long WorkTime =  dtlev.getHour()- dtarrive.getHour();
                                         int SL =0;
                                         int OT = 0;
                                         
                                         if(WorkTime <= 7){
                                         
                                                SL = 1;
                                         }else if (WorkTime > 8){
                                                OT = (int)WorkTime - 8;
                                         }
                                              
                             
                                    String attquery = "update attendance set Leaving = '"+dtlev+"', ShortL = '"+SL+"', Hours = '"+WorkTime+"', OT = '"+OT+"' where EMPID = ?";
                                    PreparedStatement preparedStmt = conn.prepareStatement(attquery);
                                    preparedStmt.setString(1, QR);
                                    preparedStmt.executeUpdate();
                             
                            JOptionPane.showMessageDialog(null,"Good Evening "+Name+", Your leave is Marked...!!! ");
                         }
                          

                        }
                 
                 }catch(SQLException e){
                     JOptionPane.showMessageDialog(null,"Error in Selecting EMPID "+e); 
                 }
        
        
    }
    
    
    
    
    
    
    
    
    
    
}
