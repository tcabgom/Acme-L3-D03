
package acme.features.lecturer.lecturesInCourse;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.lecture.LecturesInCourse;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLecturesInCourseController extends AbstractController<Lecturer, LecturesInCourse> {

	@Autowired
	protected LecturerLecturesInCourseCreateService	createService;

	@Autowired
	protected LecturerLecturesInCourseDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
