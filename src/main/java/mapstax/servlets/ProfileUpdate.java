/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mapstax.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mapstax.lib.CassandraHosts;
import mapstax.models.User;
import mapstax.stores.LoggedIn;

/**
 *
 * @author plamendenev
 */
@WebServlet(name = "ProfileUpdate", urlPatterns = {"/ProfileUpdate"})
public class ProfileUpdate extends HttpServlet {

    Cluster cluster = null;
    
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProfileUpdate</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileUpdate at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        
        HttpSession session=request.getSession();
        LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
       
        Set<String> emailSet = new LinkedHashSet<String>();
        emailSet.add(email);

        if (!name.isEmpty() && !surname.isEmpty() 
                && !email.isEmpty() && !password.isEmpty()
                && !passwordConfirm.isEmpty() && password.equals(passwordConfirm)) {
            User us = new User();     
            
            lg.getUser().setName(name);
            lg.getUser().setSurname(surname);
            lg.getUser().setEmail(emailSet);
            
            us.setCluster(CassandraHosts.getCluster());
            us.UpdateUser(name, surname, emailSet, password, lg.getUser().getUsername());            
            request.setAttribute("updateSuccess", "Update successful!");
            response.sendRedirect("/mapStax/profile.jsp");
            
        } else {
            
            request.setAttribute("updateErrorMessage", "Something is missing or passwords not matching");
            RequestDispatcher rd = request.getRequestDispatcher("profileUpdate.jsp");
            rd.forward(request, response);
        }
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
