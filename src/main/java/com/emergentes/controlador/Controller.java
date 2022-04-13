/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.emergentes.controlador;

import com.emergentes.modelo.GestorTareas;
import com.emergentes.modelo.Tarea;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boris Leonel
 */
@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Tarea objTarea = new Tarea();
        int id;
        int pos;
        String opcion = request.getParameter("op");
        String op = (opcion != null) ? request.getParameter("op"):"view";
        if (op.equals("nuevo")) {
            HttpSession ses = request.getSession();
            GestorTareas agenda = (GestorTareas) ses.getAttribute("agenda");
            objTarea.setId(agenda.obtieneId());
            request.setAttribute("op", op);
            request.setAttribute("miTarea", objTarea);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        }
        
        if (op.equals("modificar")) {
            id = Integer.parseInt(request.getParameter("id"));
            HttpSession ses = request.getSession();
            GestorTareas agenda = (GestorTareas) ses.getAttribute("agenda");
            pos = agenda.ubicarTarea(id);
            objTarea = agenda.getLista().get(pos);
            
            request.setAttribute("op", op);
            request.setAttribute("miTarea", objTarea);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        }
        
        if (op.equals("eliminar")) {
            id = Integer.parseInt(request.getParameter("id"));
            HttpSession ses = request.getSession();
            GestorTareas agenda = (GestorTareas) ses.getAttribute("agenda");
            pos = agenda.ubicarTarea(id);
            
            agenda.eliminarTarea(pos);
            ses.setAttribute("agenda", agenda);
            response.sendRedirect("index.jsp");
        }
        if (op.equals("view")) {
            response.sendRedirect("index.jsp");
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
        Tarea objTarea = new Tarea();
        int pos;
        String op = request.getParameter("op");
        if (op.equals("grabar")) {
            objTarea.setId(Integer.parseInt(request.getParameter("id")));
            objTarea.setTarea(request.getParameter("tarea"));
            objTarea.setPrioridad(request.getParameter("prioridad"));

            HttpSession ses =  request.getSession();
            GestorTareas agenda = (GestorTareas)ses.getAttribute("agenda");
            
            String opg = request.getParameter("opg");
            if (opg.equals("nuevo")) {
                agenda.insertarTarea(objTarea);
            }else{
                pos = agenda.ubicarTarea(objTarea.getId());
                agenda.modificarTarea(pos, objTarea);
            }
            ses.setAttribute("agenda", agenda);
            response.sendRedirect("index.jsp");
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
