/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynotes.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author User
 */
@MultipartConfig
public class MyProfileControllerServlet extends HttpServlet {

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

            System.out.println("MyProfileControllerServlet");
            String name = request.getParameter("name");
            String pb = request.getParameter("pb");
            System.out.println(pb==null);
          
            System.out.println("pb="+pb);
            String email = request.getParameter("email");
            String mssg = "no";
            if(pb==null){
                Part part = (Part)request.getPart("img");
                System.out.println("Part="+part);
//                request.getSession().setAttribute("part", part);
                System.out.println("Email = "+email);
                try{
                    boolean b = UserDAO.insertImage(part, email);
                    if(b){
                        System.out.println("Inserted");
                        String url = UserDAO.getUserImage(email,"C:\\Users\\dell\\Downloads\\My Notes App\\My Notes App\\web\\images\\userpics");
                        System.out.println("url = "+url);
                        request.getSession().setAttribute("path",url);
                        mssg = "yes";
                    }
                    else{
                        System.out.println("Failed to insert");
                    }
                }
                catch(SQLException ex){
                    System.out.println(ex.getMessage());
                    mssg = "issues";
                    ex.printStackTrace();
                }
            }
            else if(pb.equals("true")) {
                System.out.println("Name = " + name);                
                String currentpwd = request.getParameter("currentpwd");
                String newpwd = request.getParameter("newpwd");
                String confirmpwd = request.getParameter("confirmpwd");
                try{
                    boolean ans = UserDAO.updateNameAndPassword(name, newpwd, email);
                    if(ans){
                        HttpSession session = request.getSession();
                        session.setAttribute("username", name);
                        String username = (String)session.getAttribute("username");
                        System.out.println("username = "+username);
                        mssg = "yes";
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    
                    mssg = "issues";
                }
            } else {
                    System.out.println("Name = " + name);
                     try{
                        boolean ans = UserDAO.updateName(name, email);
                    if(ans){
                        mssg = "yes";
                        HttpSession session = request.getSession();
                        session.setAttribute("username", name);
                        String username = (String)session.getAttribute("username");
                        System.out.println("username = "+username);
                    }
                } catch (SQLException ex) {
                    
                    mssg = "issues";
                    ex.printStackTrace();
                }
                }
            out.println(mssg);
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