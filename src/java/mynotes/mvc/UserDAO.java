/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynotes.mvc;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.Part;

/**
 *
 * @author User
 */
public class UserDAO {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    
    static{
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-IB4S84P:1521/XE","javabatch","myscholars");
        } catch (Exception ex) {
            System.out.println("Error in DB "+ex);
            ex.printStackTrace();
        }
    }
        public static boolean insertImage(Part p) throws SQLException, IOException{
            
            ps = conn.prepareStatement("update users set photo = ? where email='1'");
            InputStream is = p.getInputStream();

            ps.setBlob(1,is);
            int res = ps.executeUpdate();
            if(res>0)
            return true;
            return false;
        }
        public static boolean insertImage(Part p,String email) throws SQLException, IOException{
            
            ps = conn.prepareStatement("update users set photo = ? where email=?");
            InputStream is = p.getInputStream();

            ps.setBlob(1,is);
            ps.setString(2,email);
            int res = ps.executeUpdate();
            if(res>0)
            return true;
            return false;
        }

    @Override
    public String toString() {
        return "UserDAO{" + '}';
    }
        public static boolean addUser(User user) throws SQLException, IOException, Exception{
            System.out.println(conn.isValid(1));
            System.out.println(conn);
            ps = conn.prepareStatement("insert into users values(?,?,?,?)");
            ps.setString(1,user.getEmail());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPwd());
            InputStream is = null;
            Part part = user.getPart();
           if(part!=null){
//               boolean b = insertImage(user.getPart());
            is = part.getInputStream();
           }
           
            ps.setBlob(4, is);
           
           
            int res = ps.executeUpdate();
            if(res>0)
                return true;
            
            return false;
        }
        
        public static boolean alreadyAUser(String email) throws SQLException{
            ps = conn.prepareStatement("select email from users where email = ?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        
        public static String getUserImage(String email, String path) throws SQLException, FileNotFoundException, IOException, IOException{
            ps = conn.prepareStatement("select * from users where email = ?");
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery(); 
            String url = null;
            if(rs.next()){
                Blob b = rs.getBlob(4);
                if(b!=null){
                byte[] brr = b.getBytes(1, (int)b.length());
                url = path+File.separator+email+".jpg";
                System.out.println(url);
                File f = new File(url);
                FileOutputStream fout = new FileOutputStream(f);
                fout.write(brr);
                fout.close();
                }
            } 
                return url;
        }
        
        public static User getUser(String email, String pwd) throws SQLException{
            ps = conn.prepareStatement("select name,photo from users where email=? and pwd=?");
            ps.setString(1,email);
            ps.setString(2,pwd);
            ResultSet rs = ps.executeQuery();
            User user = null;
            if(rs.next()){
                String name = rs.getString(1);
                Blob img = rs.getBlob(2);
                user = new User(email, name, pwd, img);
            }
            return user;
        }
        
        public static boolean updateName(String name, String email) throws SQLException{
            ps = conn.prepareStatement("update users set name=? where email=?");
            ps.setString(1, name);
            ps.setString(2, email);
            int ans = ps.executeUpdate();
            if(ans==1){
                return true;
            }
            return false;
        }
        
        public static boolean updateNameAndPassword(String name, String pwd, String email) throws SQLException{
            ps = conn.prepareStatement("update users set name=?, pwd=? where email=?");
            ps.setString(1,name);
            ps.setString(2,pwd);
            ps.setString(3,email);
            int ans = ps.executeUpdate();
            if(ans==1){
                return true;
            }
            
            return false;
            
        }
        
    
}
