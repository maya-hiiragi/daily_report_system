package controllers.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //値セット
        request.setAttribute("_token", request.getSession().getId());

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        //ページ移動
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            //値ゲット
            String code = request.getParameter("code");
            String password = request.getParameter("password");
            List<String> errors = new ArrayList<String>();

            EntityManager em = DBUtil.createEntityManager();
            Employee e = null;

            if (code != null && !code.equals("") && password != null && !password.equals("")) {
                try {
                    e = em.createNamedQuery("checkLoginCodeAndPassword", Employee.class)
                            .setParameter("code", code)
                            .setParameter("pass", EncryptUtil.getPasswordEncrypt(password,
                                    request.getServletContext().getAttribute("salt").toString()))
                            .getSingleResult();
                } catch (NoResultException ex) {

                }
            }

            if (e == null) {
                errors.add("社員番号かパスワードが間違っています。");
            }

            em.close();

            //ページ表示
            if (errors.size() > 0) {
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("errors", errors);
                request.setAttribute("code", code);
                request.setAttribute("password", password);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
                rd.forward(request, response);

            } else {
                request.getSession().setAttribute("login_employee", e);
                request.getSession().setAttribute("flush", "ログインしました。");
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }

}
