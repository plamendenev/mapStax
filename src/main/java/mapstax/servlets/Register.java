/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapstax.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mapstax.lib.CassandraHosts;
import mapstax.models.User;



/**
 *
 * @author Administrator
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})

public class Register extends HttpServlet {

    Cluster cluster = null;

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
       
        Set<String> emailSet = new LinkedHashSet<String>();
        emailSet.add(email);

        if (!name.isEmpty() && !surname.isEmpty() && !username.isEmpty() 
                && !email.isEmpty() && !password.isEmpty()
                && !passwordConfirm.isEmpty() && password.equals(passwordConfirm)) {
            User us = new User();            
            us.setCluster(cluster);
            us.RegisterUser(name, surname, emailSet, username, password);            
             
            response.sendRedirect("/mapStax/login.jsp");
            
        } else {
            
            request.setAttribute("registerErrorMessage", "Something is missing or passwords not matching");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
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
