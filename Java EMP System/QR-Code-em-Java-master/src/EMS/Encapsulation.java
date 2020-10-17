/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EMS;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class Encapsulation {
    
    private String Uname;
    private String Upw;
    
    public Encapsulation(){
    
    }
    
    public Encapsulation(String name,String Pw){
        Uname = name;
        Upw = Pw;
 
    }    
    
    
    
    public Boolean CheckLogin (){
        
        boolean res = false;
        
       PreparedStatement ps;
       

        
            try {
                //Class.forName("com.mysql.jdbc.Driver");
                //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp","root","");
                Connection conn = DBConn.connect();
                ps = conn.prepareStatement("Select * FROM admin WHERE Username = ? AND Password = ?");
                ps.setString(1, Uname);
                ps.setString(2, Upw);
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){

                    res=true;
                    
                    Employee em = new Employee();
                    em.setVisible(true);
                    
   
                }
            else{
                
                res = false;
                JOptionPane.showMessageDialog(null,"Sorry, Incorrect Username or Password...!!! ");
                     LogIn lg = new LogIn();
                        lg.setVisible(true);
                        
            }
                            
                            
        } catch (HeadlessException | SQLException e) {
            System.out.println(e.getMessage());
        } 
  
     
            
        return res;
        
    }
    
}
