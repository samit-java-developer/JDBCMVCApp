package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.ineuron.dto.Student;
import in.ineuron.factory.StudentServiceFactory;
import in.ineuron.service.IStudentService;

@WebServlet("/controller/*")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IStudentService studentService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		RequestDispatcher rd = null;

		if (requestURI.endsWith("layout")) {
			rd = request.getRequestDispatcher("../layout.html");
			rd.forward(request, response);
		}
		if (requestURI.endsWith("addform")) {
			studentService = StudentServiceFactory.getStudentService();

			String studentAge = request.getParameter("sage");
			String studentSname = request.getParameter("sname");
			String studentAdd = request.getParameter("saddr");

			Student student = new Student();
			student.setSname(studentSname);
			student.setSage(Integer.parseInt(studentAge));
			student.setSaddr(studentAdd);

			String status=studentService.save(student);
			if (status.equals("success")) {
				rd=request.getRequestDispatcher("../success.html");
				rd.forward(request, response);
			}else {
				rd=request.getRequestDispatcher("../failure.html");
				rd.forward(request, response);
			}
		}
		if (requestURI.endsWith("searchform")) {
			studentService = StudentServiceFactory.getStudentService();
			String studentId=request.getParameter("sid");
			Student student=studentService.findById(Integer.parseInt(studentId));
			System.out.println(student);
			if (student!=null) {
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				out.println("<html><head><title>STUDENT DATA</title></head>");
				out.println("<body bgcolor='lightblue'>");
				out.println("<table align='center' border='1'>");
				out.println("<tr>");
				out.println("<th>SID</th><td>"+student.getSid()+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th>NAME</th><td>"+student.getSname()+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th>AGE</th><td>"+student.getSage()+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th>ADDRESS</th><td>"+student.getSaddr()+"</td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</body></html>");
			}else {
				rd=request.getRequestDispatcher("../notfound.html");
				rd.forward(request, response);
			}
		}
	}

}
