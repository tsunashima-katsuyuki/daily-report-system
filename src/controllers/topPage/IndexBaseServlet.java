package controllers.topPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class IndexBaseServlet
 */
@WebServlet("/IndexBaseServlet")
public abstract class IndexBaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected static final int PAGE_NUM = 10; //１ページあたりの表示件数
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexBaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Report> reports = getReportList(page,em,request,response);
        long reports_count = getReportsCount(em,request,response);

        List<Long> likes_counts= new ArrayList<Long>();
        for(Report r: reports){
            likes_counts.add((long)em.createNamedQuery("getLikesCount", Long.class)
                            .setParameter("report", r)
                            .getSingleResult()
                            );
        }

        em.close();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("likes_counts", likes_counts);
        request.setAttribute("page", page);
        request.setAttribute("page_num", PAGE_NUM);

        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
    }

    protected abstract List<Report> getReportList(int page, EntityManager em,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    protected abstract long getReportsCount(EntityManager em,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
