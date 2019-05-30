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

import controllers.topPage.IndexBaseServlet;
import models.Report;

/**
 * Servlet implementation class ReportIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends IndexBaseServlet {
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
        super.doGet(request, response);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
    }

    @Override
    protected List<Report> getReportList(int page, EntityManager em,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        return em.createNamedQuery("getAllReports",Report.class)
                .setFirstResult(PAGE_NUM * (page - 1))
                .setMaxResults(PAGE_NUM)
                .getResultList();
    }

    @Override
    protected long getReportsCount(EntityManager em,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        return (long)em.createNamedQuery("getReportsCount", Long.class)
                .getSingleResult();
    }



}
