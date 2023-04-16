package acme.features.student.lecture;

import acme.entities.lecture.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class StudentLectureController extends AbstractController<Student, Lecture> {

    @Autowired
    StudentLectureListService listService;
    @Autowired
    StudentLectureShowService showService;

    @PostConstruct
    public void initialise() {
        super.addBasicCommand("list", this.listService);
        super.addBasicCommand("show", this.showService);
    }

}
