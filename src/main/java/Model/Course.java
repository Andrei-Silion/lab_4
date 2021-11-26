package Model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private static int nextID = 10;
    private long courseID;
    private String name;
    private String teacher;
    private int maxEnrolled;
    private List<Long> studentsEnrolled;
    private int credits;

    public Course(String name, String teacher, int maxEnrolled, int credits){
        this.courseID = ++nextID;
        this.name = name;
        this.teacher = teacher;
        this.maxEnrolled = maxEnrolled;
        this.credits = credits;
        this.studentsEnrolled = new ArrayList<>();
    }

    public Course(String name, String teacher, int maxEnrolled, int credits, List<Long> studentsEnrolled){
        this.courseID = ++nextID;
        this.name = name;
        this.teacher = teacher;
        this.maxEnrolled = maxEnrolled;
        this.credits = credits;
        this.studentsEnrolled = studentsEnrolled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getMaxEnrolled() {
        return maxEnrolled;
    }

    public void setMaxEnrolled(int maxEnrolled) {
        this.maxEnrolled = maxEnrolled;
    }

    public long getCourseID() {
        return courseID;
    }

    public String getTeacher() {
        return teacher;
    }

    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void addStudent(Long stud) {
        this.studentsEnrolled.add(stud);
    }

    public boolean free(){
        return this.maxEnrolled > this.studentsEnrolled.size();
    }

    public void print(){
        for (long element: studentsEnrolled)
            System.out.println(element);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + courseID + '\'' +
                "name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrolled=" + maxEnrolled +
                ", credits=" + credits +
                '}';
    }



    public int compareTo(Course course){
        return this.credits - course.getCredits();
    }
}