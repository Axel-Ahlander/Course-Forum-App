package edu.virginia.sde.reviews;

public class CreateCourseService {
    private Course course;

    public CreateCourseService(Course course){
        this.course = course;
    }

    public void saveCourse(){
        CourseDAO dao = new CourseDAO();
        dao.save(this.course);
    }
}
