package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesDeleteServlet
 */
@WebServlet("/employees/delete")
public class EmployeesDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            //値セット
            EntityManager em = DBUtil.createEntityManager();
            Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

            e.setDelete_flag(1);

            Timestamp t = new Timestamp(System.currentTimeMillis());
            e.setUpdated_at(t);

            //値更新
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();

            //ページ表示
            request.getSession().setAttribute("flush", "削除が完了しました。");
            response.sendRedirect(request.getContextPath() + "/employees/index");
        }
    }

}
