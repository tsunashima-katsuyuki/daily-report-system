package controllers.likes;

import java.io.IOException;

import javax.persistence.EntityManager;
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
 * Servlet implementation class LikeServlet
 */
@WebServlet("/likes")
public class LikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            if(request.getSession().getAttribute("like") == null){
                Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
                Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

                Like l = new Like();
                l.setEmployee(login_employee);
                l.setReport(r);
                em.getTransaction().begin();
                em.persist(l);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "いいね！しました。");
                response.sendRedirect(request.getContextPath() + "/reports/show?id="+r.getId());
            }else{
                Like l = em.find(Like.class, Integer.parseInt(request.getParameter("like_id")));
                em.getTransaction().begin();
                em.remove(l);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "いいね！を解除しました。");
                response.sendRedirect(request.getContextPath() + "/reports/show?id="+l.getReport().getId());
            }
        }
    }
}
