/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynotes.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
public class LoginControllerServlet extends HttpServlet {

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

//            out.println("<h1>Servlet LoginControllerServlet at " + request.getContextPath() + "</h1>");
            String email = request.getParameter("email");
            String pwd = request.getParameter("pwd");
            String mssg="wrong";
            System.out.println("Email = "+email);
            System.out.println("Pwd = "+pwd);
            User user = null;
            try {
                user = UserDAO.getUser(email, pwd);
            } catch (SQLException ex) {
                System.out.println("error in fetching user " + ex);
                ex.printStackTrace();
            }
            if (user != null) {
             System.out.println("Inside");
                String path = null;
                Blob b = user.getImg();
                if (b != null) {
//                    System.out.println("Inside b");
                    try {
                        path = "C:\\Users\\dell\\Downloads\\My Notes App\\My Notes App\\web\\images\\userpics" + File.separator + user.getEmail() + ".jpg";
                        byte[] img = b.getBytes(1, (int) b.length());
                        FileOutputStream fout = new FileOutputStream(path);
                        fout.write(img);
                        fout.close();
                        System.out.println("Image saved successfully");

                    } catch (SQLException ex) {
                        Logger.getLogger(LoginControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Image not saved..!!");
                    }
                   
                }
                 HttpSession session = request.getSession();
                    session.setAttribute("email", user.getEmail());
                    session.setAttribute("path", path);
                    session.setAttribute("username", user.getName());
                    System.out.println("Username in LoginController = "+user.getName());
                    mssg="correct";
                    System.out.println(mssg);
//                    response.sendRedirect("MyNotes.jsp");
            } else {
//                request.setAttribute("error", "Incorrect email or password!!!");
                System.out.println("User not found");
                    mssg = "wrong";
                    System.out.println(mssg);
//                RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
//                rd.forward(request, response);
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
