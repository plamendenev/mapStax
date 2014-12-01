/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.aec.instagrim.models.MapModel;
import uk.ac.dundee.computing.aec.instagrim.stores.Map;
import uk.ac.dundee.computing.aec.mapStax.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.mapStax.lib.Convertors;

/**
 *
 * @author plamendenev
 */
@WebServlet(name = "DislpayMap", urlPatterns = {"/DislpayMap"})
public class DislpayMa extends HttpServlet {
    
    private Cluster cluster = null;
    
     public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

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
            out.println("<title>Servlet DislpayMap</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DislpayMap at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        String args[] = Convertors.SplitRequestPath(request);
        GetmapData(args[2], request, response);
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
        //processRequest(request, response);
        
        
        
    }    
    
     private void GetmapData(String uuid, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returnData = null;
        MapModel aMap = new MapModel();
        aMap.setCluster(cluster);
        java.util.LinkedList<Map> mapsList = aMap.getAllMaps();
        for (int i = 0; i < mapsList.size(); i++) {
            if (mapsList.get(i).getUUID().toString().equals(uuid)) {
                returnData = mapsList.get(i).getMapText();
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("/map.jsp");
        request.setAttribute("mapdata", returnData);
        rd.forward(request, response);
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
