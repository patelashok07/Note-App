/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynotes.mvc;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class NotesDAO {
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
    
    public static ArrayList<Notes> getAllNotes(String email) throws SQLException, IOException{
        ArrayList<Notes> notesList= new ArrayList<>();
        ps = conn.prepareStatement("Select * from notes where email = ? order by 5 desc");
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            email = rs.getString(1);
            String notesTitle = rs.getString(3);
            String notesBody = rs.getString(4);
            int id = rs.getInt(2);
//            Clob clob = rs.getClob(4);
//            Reader r = clob.getCharacterStream();
//            StringBuffer buffer = new StringBuffer();
//            int ch;
//            
//            while ((ch = r.read())!=-1) {   
//                buffer.append(""+(char)ch);
//            }
//            String notesBody = buffer.toString();
//            System.out.println("Contents: "+notesBody);
            
            java.sql.Timestamp date = rs.getTimestamp(5);
            Notes note = new Notes(email, id, notesTitle, notesBody, date);
            notesList.add(note);
        }       
        return notesList;
    }
    
    public static int getNoteId() throws SQLException{
        PreparedStatement p = conn.prepareStatement("select max(notes_id) from notes");
        ResultSet rs = p.executeQuery();
        int id = 1;
        if(rs.next()){
            id = rs.getInt(1)+1;
        }
        return id;
    }
    
    public static boolean createNote(String email, String noteTitle, String noteBody) throws SQLException{
        ps = conn.prepareStatement("insert into notes(email,notes_id,notes_title,notes_body) values(?,?,?,?)");
       
        int noteId = getNoteId();
        System.out.println("Notes id = "+noteId);
        ps.setString(1, email);
        ps.setInt(2, noteId);
        ps.setString(3, noteTitle);
        ps.setString(4, noteBody);
        int ans = ps.executeUpdate();
        
        
        return ans>0;
    }
    
    public static boolean updateNote(String title, String body, int id) throws SQLException{
        ps = conn.prepareStatement("update notes set notes_title=?, notes_body=?, issued=systimestamp where notes_id=?");
        ps.setString(1,title);
        ps.setString(2,body);
        ps.setInt(3,id);
        int ans = ps.executeUpdate();
        return ans>0;
    }
    
    public static boolean deleteNote(int id) throws SQLException{
        ps = conn.prepareStatement("delete from notes where notes_id = ?");
        ps.setInt(1, id);
        int ans = ps.executeUpdate();
        if(ans>0)
                return true;
        return false;
    }
    
}
