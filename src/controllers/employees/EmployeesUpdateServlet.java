package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.validators.EmployeeValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class EmployeesUpdateServlet
 */
@WebServlet("/employees/update")
public class EmployeesUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesUpdateServlet() {
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

            String code = request.getParameter("code");
            Boolean code_duplicate_check_flg = false;

            if (!code.equals(e.getCode())) {
                e.setCode(code);
                code_duplicate_check_flg = true;
            }

            String name = request.getParameter("name");
            e.setName(name);

            String password = request.getParameter("password");
            Boolean password_check_flg = false;

            if (password != null && !password.equals("")) {
                e.setPassword(EncryptUtil.getPasswordEncrypt(password,
                        request.getServletContext().getAttribute("salt").toString()));

                password_check_flg = true;
            }

            int admin_flg = Integer.parseInt(request.getParameter("admin_flg"));
            e.setAdmin_flg(admin_flg);

            Timestamp updated_at = new Timestamp(System.currentTimeMillis());
            e.setUpdated_at(updated_at);

            List<String> errors = EmployeeValidator.validate(e, code_duplicate_check_flg, password_check_flg);

            //ページ表示
            if (errors.size() > 0) {
                em.close();
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("employees", e);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/edit.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                request.getSession().setAttribute("flush", "更新が完了しました。");
                response.sendRedirect(request.getContextPath() + "/employees/index");
            }
        }
    }

}
