/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapStaxers.servlets;

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
import javax.servlet.http.HttpSession;
import mapstax.models.MapModel;
import mapstax.lib.CassandraHosts;
import mapstax.stores.LoggedIn;
/**
 *
 * @author Naiyon
 */
@WebServlet(name = "NewMap", urlPatterns = {"/NewMap"})
public class NewMap extends HttpServlet {

    Cluster cluster = null;

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
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *     
* @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        HttpSession session = request.getSession();
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
        String user = lg.getUser().getUsername();
        
        //String user = request.getParameter("username");
        String mapName = request.getParameter("mapName");
        //String mapText = request.getParameter("mySavedModel");
        String mapText = "{ \"class\": \"go.TreeModel\",\n"
                + "  \"nodeDataArray\": [ \n"
                + "{\"key\":0, \"text\":\""+mapName+"\", \"loc\":\"0 0\"},\n"
                + "{\"key\":1, \"parent\":0, \"text\":\"Getting more time\", \"brush\":\"skyblue\", \"dir\":\"right\", \"loc\":\"77 -22\"},\n"
                + "{\"key\":2, \"parent\":0, \"text\":\"More effective use\", \"brush\":\"darkseagreen\", \"dir\":\"right\", \"loc\":\"77 43\"},\n"
                + "{\"key\":4, \"parent\":0, \"text\":\"Key issues\", \"brush\":\"coral\", \"dir\":\"left\", \"loc\":\"-20 52.75\"}\n"
                + " ]}";
        if (mapName != null) {
            MapModel map = new MapModel();
            map.setCluster(cluster);
            map.insertMap(user, mapName, mapText);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
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
