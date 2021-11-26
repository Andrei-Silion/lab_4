package Repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import Model.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseRepository extends InMemoryRepository<Course> implements FileRepository<Course> {

    private String file;

    public CourseRepository(String file) {
        super();
        this.file = file;
    }

    @Override
    public List<Course> readFromFile() throws IOException {
        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            String name = pm.path("name").asText();
            String teacher = pm.path("teacher").asText();
            int maxEnrolled = pm.path("maxEnrolled").asInt();
            int credits = pm.path("credits").asInt();

            List<Long> studentsEnrolled = new ArrayList<>();
            for (JsonNode v : pm.path("studentsEnrolled"))
            {
                studentsEnrolled.add(v.asLong());
            }

            Course course = new Course(name, teacher, maxEnrolled, credits, studentsEnrolled);
            repoList.add(course);
        }
        return repoList;

    }

    @Override
    public void writeToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(file), repoList);
    }

    @Override
    public Course update(Course obj) {
        Course CourseToUpdate = this.repoList.stream()
                .filter(course -> Objects.equals(course.getName(), obj.getName()))
                .findFirst()
                .orElseThrow();
        CourseToUpdate.setName(obj.getName());

        return CourseToUpdate;
    }


    public Course exist(long id) throws Exception {
        for (Course element : this.repoList)
            if (id == element.getCourseID())
                return element;
        throw new Exception("ID dosen't exist");
    }

    public void print(){
        for (Course element : this.repoList)
            System.out.println(element);
    }

    public int size(){
        return repoList.size();
    }

    public void sort(){
        this.repoList.sort(Course::compareTo);
    }

}