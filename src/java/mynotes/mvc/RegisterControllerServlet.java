/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynotes.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
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
public class RegisterControllerServlet extends HttpServlet {

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

            String username = request.getParameter("username");
            String pwd = request.getParameter("pwd");
            String email = request.getParameter("email");

            System.out.println("username = " + username);
            System.out.println("pwd = " + pwd);
            System.out.println("Email = " + email);

            boolean user = false;
            try {
                user = UserDAO.alreadyAUser(email);
            } catch (SQLException ex) {
                System.out.println("Error in DB " + ex);
                out.println("<h1 style='color:red'>Problem in DB</h1>");
            }

            if (user) {
//                request.setAttribute("error","Email already in use...");
//                RequestDispatcher rd = request.getRequestDispatcher("Registration.jsp");
//                rd.include(request, response);
                out.println("already");
            } else {
                String img = request.getParameter("img");
                System.out.println("img = "+img);
                Part part = null;
//                part= request.getPart("img");
//                System.out.println("part = "+part);
                if(img==null || !img.equals("null")){
                    part= request.getPart("img");
                }
                System.out.println("Part = " + part);
                request.getSession().setAttribute("part", part);
            part = (Part)request.getSession().getAttribute("part");
                System.out.println("Part in RegisterControllerServlet = "+part);
            if(part!=null){
            try{
                boolean inserted = UserDAO.insertImage((Part)request.getSession().getAttribute("part"));
                if(inserted){
                    System.out.println("Image inserted successfully!!");
                }
                else{
                    System.out.println("Not inserted!!\n\n\n\n");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegisterControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            else{
               request.getSession().setAttribute("path",null); 
            }
            
////           
////           

            Random r = new Random();
            int otp = r.nextInt(999999);

            while (otp < 100000) {
                otp *= 10;
                otp += r.nextInt(9);
            }
            System.out.println("otp = " + otp);

            Properties p = System.getProperties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "465");
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.auth", "true");

            String from = "ykbjavaprojects@gmail.com";

            //Step-1 > To get the session object
            Session session = Session.getInstance(p, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // TODO Auto-generated method stub
                    return new PasswordAuthentication("ykbjavaprojects@gmail.com", "Yogesh@123");
                }

            });

            session.setDebug(true);

            //Step-2 > Compose the message
            MimeMessage m = new MimeMessage(session);

            try {
                //from email
                m.setFrom(from);

                //from recipient
                m.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

                // adding subject
                m.setSubject("My Notes Verification OTP");

                //adding message body
                m.setText("Hello " + username + ", Your Verification OTP is " + otp);

                //Step-3 > Sending message via Transport
                Transport.send(m);

                System.out.println("Message sent successfully!!! ");
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("pwd", pwd);
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("otp", otp);
                request.setAttribute("mssg", null);

//                        RequestDispatcher rd = request.getRequestDispatcher("OTPVerification.jsp");
//                        rd.forward(request, response);
                out.println("new");
            } catch (Exception e) {
                e.printStackTrace();
            }

//            FileInputStream fin = (FileInputStream) part.getInputStream();                   
//                    
//            String path = "F:\\Advance Java Online\\My Notes App\\web\\images\\userpics";
//            File userpics = new File(path);
//            if(!userpics.exists()){
//               
//                if(!userpics.mkdir()){
//                    Exception ex = new Exception("Folder not created");
//                    throw ex;
//                }
//                System.out.println("Folder created successfully!!");
//            }
//            String imgpath = path+"\\"+email.substring(0,email.indexOf('@'))+".jpg";
//            FileOutputStream fout = new FileOutputStream(imgpath);
//            byte[] buf = new byte[1024];
//            int len;
//
//               while ((len = fin.read(buf)) > 0) {
//                fout.write(buf, 0, len);
//            }
//
//            fout.close();
//            fin.close();
//            System.out.println("Image saved successfully!!");
//
//            String userpath = "images/userpics/"+email.substring(0,email.indexOf('@'))+".jpg";
//          
//            request.setAttribute("img", userpath);
//            request.setAttribute("username", username);
//            request.setAttribute("email",email);
//            RequestDispatcher rd = request.getRequestDispatcher("Display.jsp");
//            rd.forward(request, response);
//        } catch (Exception ex) {
//            System.out.println("Folder not created "+ex);
//        }
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