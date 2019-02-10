package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //値ゲット
        int page = 1;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {

        }

        EntityManager em = DBUtil.createEntityManager();
        List<Report> reports = em.createNamedQuery("getAllReports", Report.class)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long reports_count = (long) (em.createNamedQuery("getReportsCount", Long.class)
                .getSingleResult());

        em.close();

        //値セット
        request.setAttribute("reports", reports);
        request.setAttribute("page", page);
        request.setAttribute("reports_count", reports_count);

        String flush = (String) request.getSession().getAttribute("flush");

        if (flush != null) {
            request.setAttribute("flush", flush);
            request.getSession().removeAttribute("flush");
        }

        //ページ表示
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
    }

}
