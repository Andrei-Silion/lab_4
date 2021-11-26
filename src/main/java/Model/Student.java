package Model;

import java.util.List;
import java.util.ArrayList;
import Repository.CourseRepository;

public class Student extends Person  {

    long studentID;
    int credits;
    List<Long> enrolledCourses;
    private static int nextID = 1000;

    public Student(String firstName, String lastName){
        super(firstName, lastName);
        this.studentID = ++nextID;
        this.credits = 0;
        this.enrolledCourses = new ArrayList<>();
    }

    public Student(String firstName, String lastName, long studentID,  int credits, List<Long> enrolledCourses){
        super(firstName, lastName);
        this.studentID = studentID;
        this.credits = credits;
        this.enrolledCourses = enrolledCourses;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Long> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", studentID=" + studentID +
                ", credits=" + credits +
                ", enrolledCourses=" + enrolledCourses +
                '}';
    }
    /**
     * @param courseid die Vorlesung, die addiert wird
     */
    public void addCourse(long courseid){
        this.enrolledCourses.add(courseid);
    }

    /**
     * man loscht einen Kurs vom enrolled Courses
     * @param courseid id fur den Kurs, den geloscht wird
     * @param repo die Liste mit den Kurse
     */
    public void deleteCourse(long courseid, CourseRepository repo) throws Exception {
        this.enrolledCourses.remove(courseid);
        this.credits -= repo.exist(courseid).getCredits();
    }

    public int compareTo(Student stud) {
        return this.credits - stud.getCredits();
    }
}