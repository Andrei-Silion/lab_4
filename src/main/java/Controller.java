import Exception.ObjectDoesentExist;
import Exception.AlreadyInList;
import Model.Course;
import Model.Student;
import Repository.CourseRepository;
import Repository.StudentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    private StudentRepository studRepo;
    private CourseRepository courseRepo;


    public Controller(String fileStudent,String fileCourse){
        studRepo = new StudentRepository(fileStudent);
        courseRepo = new CourseRepository(fileCourse);
    }


    /**
     * man schreibt ein Student an einen Kurs ein
     * @param courseid ID vom Kurs
     * @param studid ID vom dem Student
     *
     * @throws Exception wenn der Kurs oder der Student nicht existieren
     * @throws Exception wenn der Student schon eingeschrieben ist
     */
    public void register(long courseid, long studid) throws Exception {
        Student auxS = null;
        Course auxC = null;

        for (Course elem: this.courseRepo.getAll())
            if (elem.getCourseID() == courseid){
                auxC = elem;
                break;
            }

        for (Student elem: this.studRepo.getAll())
            if (elem.getStudentID() == studid){
                auxS = elem;
                break;
            }

        if (auxC== null || auxS == null)
            throw new ObjectDoesentExist("Student or course doesn't exist");

        if (auxS.getEnrolledCourses().contains(auxC.getCourseID()))
            throw new AlreadyInList("The student is already enrolled");

        if (auxC.free() && ((auxC.getCredits()+auxS.getCredits()) <= 30)){
            auxC.addStudent(studid);
            auxS.addCourse(courseid);
            auxS.setCredits(auxS.getCredits() + auxC.getCredits());
        }
    }


    /**
     * man gibt die Studenten, die an einem Kurs teilnehmen, an
     * @param courseid ID vom Kurs
     * @return eine Liste mit dem Studenten, die an einem bestimmten Kurs teilnehmen
     */
    public List<Student> retrieveStudentsEnrolledForACourse(long courseid){
        List<Student> studentsEnrolledForTheCourse = new LinkedList<>();
        for (Student student : studRepo.getAll()){
            if (student.getEnrolledCourses().contains(courseid)){
                studentsEnrolledForTheCourse.add(student);
            }
        }
        return studentsEnrolledForTheCourse;
    }

    /**
     * man sucht die Kurse mit freien Platze
     * @return die Kurse mit freien Platze
     */
    public List<Course> retrieveCoursesWithFreePlaces(){
        List<Course> freeCourses = new ArrayList<>();
        for (Course course : this.courseRepo.getAll())
            if (course.free()) {
                freeCourses.add(course);
            }
        return freeCourses;
    }


    public void readAll (){
        try {
            studRepo.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            courseRepo.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeAll (){
        try {
            studRepo.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            courseRepo.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CourseRepository getAllCourse() {
        return courseRepo;
    }

    public StudentRepository getAllStudents() {
        return studRepo;
    }

    public void sortStudents(){
        studRepo.sort();
    }

    public void sortCourses(){
        courseRepo.sort();
    }

    public void printStudents(){
        studRepo.print();
    }

    public void printCourses(){
        courseRepo.print();
    }

    public List<Student> filterStudents(){
        List<Student> students = studRepo.getAll();
        return students.stream().filter(stud -> stud.getCredits() > 0).toList();
    }

    public List<Course> filterCourses(){
        List<Course> courses = courseRepo.getAll();
        return courses.stream().filter(curs -> curs.getCredits() == 6).toList();
    }

}
