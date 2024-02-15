package in.ineuron.controller;

import java.io.IOException;

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
	}

}
