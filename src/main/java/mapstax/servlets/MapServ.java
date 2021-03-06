/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapstax.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mapstax.models.MapModel;
import mapstax.stores.Map;
import mapstax.lib.CassandraHosts;
import mapstax.lib.Convertors;
import mapstax.models.User;
import mapstax.stores.LoggedIn;

/**
 *
 * @author plamendenev
 */
@WebServlet(name = "MapServ", urlPatterns = {"/MapServ", "/DisplayMap", "/DisplayMap/*"})
public class MapServ extends HttpServlet {

    private Cluster cluster = null;
    private HashMap CommandsMap = new HashMap();    

    public MapServ() {
        super();
        CommandsMap.put("MapServ", 1);
        CommandsMap.put("DisplayMap", 2);
    }

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    private void DisplayMapsList(String user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MapModel aMap = new MapModel();
        aMap.setCluster(cluster);
        java.util.LinkedList<Map> mapsList = aMap.getMapsForUser(user);
        RequestDispatcher rd = request.getRequestDispatcher("/userMaps.jsp");
        request.getSession().setAttribute("maps", mapsList);
        rd.forward(request, response);
    }

    private void GetmapData(String uuid, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returnData;
        MapModel aMap = new MapModel();
        aMap.setCluster(cluster);
        java.util.UUID returnId; 
        java.util.LinkedList<Map> mapsList = (java.util.LinkedList<Map>) request.getSession().getAttribute("maps");
        
        for (int i = 0; i < mapsList.size(); i++) {
            if (mapsList.get(i).getUUID().toString().equals(uuid)) {
                returnData = mapsList.get(i).getMapText();
                request.setAttribute("mapdata", returnData);
                returnId = mapsList.get(i).getUUID();
                request.getSession().setAttribute("mapid", returnId);                
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("/map.jsp");
        rd.forward(request, response);

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
            out.println("<title>Servlet Map</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Map at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
        String user = lg.getUser().getUsername();
        
        String args[] = Convertors.SplitRequestPath(request);
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            error("Bad Operator", response);
            return;
        }
        switch (command) {
            case 1:
                DisplayMapsList(user, request, response);
                break;
            case 2:
                GetmapData(args[2], request, response);
                break;
            default:
                error("Bad Operator", response);
        }
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
        User us = new User();        
        us.setCluster(CassandraHosts.getCluster());
        String mapStuff = request.getParameter("mySavedModel");        
        MapModel aMap = new MapModel();        
        aMap.setCluster(cluster);   
        
        //Object temp =   request.getSession().getAttribute("mapid");
        java.util.UUID mapId = (java.util.UUID) request.getSession().getAttribute("mapid");        
        
        aMap.updateMap(mapId, mapStuff);
        
        request.setAttribute("mapdata", mapStuff);
        RequestDispatcher rd = request.getRequestDispatcher("/map.jsp");
        rd.forward(request, response); 
    }

    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have an error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
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
