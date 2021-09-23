/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynotes.mvc;

import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class Notes {
    private String email;
    private int notesId;
    private String notesTitle;
    private String notesBody;
    private Timestamp issued;

    public Notes(String email, int notesId, String notesTitle, String notesBody, Timestamp issued) {
        this.email = email;
        this.notesId = notesId;
        this.notesTitle = notesTitle;
        this.notesBody = notesBody;
        this.issued = issued;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNotesId() {
        return notesId;
    }

    public void setNotesId(int notesId) {
        this.notesId = notesId;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public String getNotesBody() {
        return notesBody;
    }

    public void setNotesBody(String notesBody) {
        this.notesBody = notesBody;
    }

    public Timestamp getIssued() {
        return issued;
    }

    public void setIssued(Timestamp issued) {
        this.issued = issued;
    }

    
}
