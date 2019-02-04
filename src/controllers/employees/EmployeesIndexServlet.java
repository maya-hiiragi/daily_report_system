package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //値ゲット
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {

        }

        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long employees_count = em.createNamedQuery("getEmployeesCount", Long.class).getSingleResult();
        em.close();

        //値セット
        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);
        Object flush = request.getSession().getAttribute("flush");

        if (flush != null) {
            request.setAttribute("flush", flush);
            request.getSession().removeAttribute("flush");
        }

        //ページ表示
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }

}