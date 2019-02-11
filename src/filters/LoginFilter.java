package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!req.getServletPath().matches("/css.*")) {
            Employee e = (Employee) req.getSession().getAttribute("login_employee");

            if (req.getServletPath().matches("/login")) {
                if (e != null) {
                    res.sendRedirect(req.getContextPath() + "/");
                    return;
                }

            } else if (e == null) {
                res.sendRedirect(req.getContextPath() + "/login");
                return;

            } else if (req.getServletPath().matches("/employees.*") && e.getAdmin_flag() == 0) {
                res.sendRedirect(req.getContextPath() + "/");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
