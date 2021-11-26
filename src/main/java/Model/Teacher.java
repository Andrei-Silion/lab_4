package Model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person{
    private List<Course> course;

    public Teacher(String firstName, String lastName){
        super(firstName, lastName);
        this.course = new ArrayList<>();
    }
}