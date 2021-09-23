/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynotes.mvc;

import java.sql.Blob;
import javax.servlet.http.Part;

/**
 *
 * @author User
 */
public class User {
    private String email;
    private String name;
    private String pwd;
    private Part part;
    private Blob img;

    public User(String email, String name, String pwd, Part part) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
        this.part = part;
    }

    public User(String email, String name, String pwd, Blob img) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
        this.img = img;
    }
    
   
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

   public Part getPart(){
       return part;
   }
   
   public void setPart(Part part){
       this.part = part;
   }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }
    
     
}
