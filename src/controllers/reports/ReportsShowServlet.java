package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        long likes_count = (long)em.createNamedQuery("getLikesCount", Long.class)
                .setParameter("report", r)
                .getSingleResult();

        Like l;
        try{
            l = (Like)em.createNamedQuery("checkHasLiked", Like.class)
                    .setParameter("employee", login_employee)
                    .setParameter("report", r)
                    .getSingleResult();
        }catch(NoResultException e){
            l = null;
        }

        em.close();
        request.setAttribute("likes_count", likes_count);
        request.setAttribute("report", r);
        request.getSession().setAttribute("like", l);
        request.getSession().setAttribute("_token", request.getSession().getId());

        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
