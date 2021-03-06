package controllers.reports;

import java.io.IOException;
import java.sql.Date;
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
import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet("/reports/create")
public class ReportsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            Report r = new Report();

            r.setEmployee((Employee)(request.getSession().getAttribute("login_employee")));

            Date report_date = new Date(System.currentTimeMillis());
            String report_date_param = request.getParameter("report_date");

            if (report_date_param != null && !report_date_param.equals("")) {
                report_date = Date.valueOf(report_date_param);
            }

            r.setReport_date(report_date);

            String title = request.getParameter("title");
            r.setTitle(title);

            String content = request.getParameter("content");
            r.setContent(content);

            Timestamp t = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(t);
            r.setUpdated_at(t);

            List<String> errors = ReportValidator.validate(r);

            if (errors.size() > 0) {
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("reports", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
                rd.forward(request, response);

            } else {
                EntityManager em = DBUtil.createEntityManager();
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();

                request.getSession().setAttribute("flush", "登録が完了しました。");
                response.sendRedirect(request.getContextPath() + "/reports/index");
            }
        }
    }

}
