/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynotes.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author User
 */
public class OTPControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */ 
           // out.println("<h1>Hello</h1>");
            
         //System.out.println((String)request.getSession().getAttribute("pwd"));
           
         String otp = (Integer)request.getSession().getAttribute("otp")+"";
            System.out.println("otp="+otp);
            String userotp = request.getParameter("userotp");
            System.out.println("userotp="+userotp);
            
                String username = (String)request.getSession().getAttribute("username");
                String pwd = (String)request.getSession().getAttribute("pwd");
                String email = (String)request.getSession().getAttribute("email");
                System.out.println("username = "+username);
                System.out.println("pwd = "+username);
                System.out.println("email = "+username);
                
            if(userotp.equals(otp)){
                Part part = (Part)request.getSession().getAttribute("part");
                
                try{
                    User user = new User(email, username, pwd, part);
                    boolean add = UserDAO.addUser(user);
                    if(!add){
                        out.println("issues");
                    }
                    
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute("email", email);
                    httpSession.setAttribute("username", username);
                    String url = null;
                    try{
                        url = UserDAO.getUserImage(email,"C:\\Users\\dell\\Downloads\\My Notes App\\My Notes App\\web\\images\\userpics");
                        System.out.println("Url = "+url);
                        httpSession.setAttribute("path","images/userpics/"+email+".jpg");
                        System.out.println("Path in OTPController "+url);
                        out.println("correct");
                    }
                    catch(Exception ex){
                        out.println("issues");
                        System.out.println("Exception occurred "+ex);
                        ex.printStackTrace();
                    }
                    
                    
                }
                    
                catch (SQLException ex) {
                    System.out.println("Error occurred while inserting user "+ex);
                    out.println("issues");
                     ex.printStackTrace();
                }
                catch(IOException ex){
                    System.out.println("Error in photo "+ex);
                    ex.printStackTrace();
                    out.println("issues");
               
                } catch (Exception ex) {
                    out.println("issues");
                    Logger.getLogger(OTPControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                      ex.printStackTrace();
                }
             
            }
            else{
                out.println("incorrect");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}