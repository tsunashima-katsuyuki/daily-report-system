package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeIndexServlet
 */
@WebServlet("/employees/index")
public class EmployeeIndexServlet extends HttpServlet {
    private static final int PAGE_NUM = 15; //１ページあたりの表示件数
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;
        try {
            page= Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            // TODO: handle exception
        }

        List<Employee> employees = em.createNamedQuery("getAllEmployees",Employee.class)
                                    .setFirstResult(PAGE_NUM*(page-1))
                                    .setMaxResults(PAGE_NUM)
                                    .getResultList();

        long employees_count = (long)em.createNamedQuery("getEmployeesCount",Long.class)
                                    .getSingleResult();

        em.close();


        request.setAttribute("employees", employees);
        request.setAttribute("page", page);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page_num",PAGE_NUM);

        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);

    }

}
