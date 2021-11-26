package Repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import Model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository extends InMemoryRepository<Student> {

    private String file;

    public StudentRepository(String file){
        super();
        this.file = file;
    }

    /**
     * updates ein Element in repo
     * @param obj student object
     * @return the updated student
     */
    @Override
    public Student update(Student obj) {
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentID() == obj.getStudentID())
                .findFirst()
                .orElseThrow();

        studentToUpdate.setLastName(obj.getLastName());
        studentToUpdate.setFirstName(obj.getFirstName());

        return studentToUpdate;
    }

    /**
     * @param stud, der Syudent nach dem man gesucht wird
     * @return true wenn dieser gefunden ist, false im Gegenteil
     */
    public boolean exist(Student stud) {
        for (Student element : this.repoList)
            if (element == stud){
                return true;
            }
        return false;
    }

    /**
     * man liest Information von der Datei
     * @return die erstellte Liste mit den Information von der Datei
     */
    public List<Student> readFromFile() throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            String lastName = pm.path("lastName").asText();
            int credits = pm.path("credits").asInt();
            String firstName = pm.path("firstName").asText();
            long id = pm.path("studentid").asLong();

            List<Long> enrolledCourses = new ArrayList<>();
            for (JsonNode v : pm.path("enrolledCourses"))
            {
                enrolledCourses.add(v.asLong());
            }

            Student stud = new Student(firstName,lastName, id, credits,enrolledCourses);
            repoList.add(stud);
        }

        return repoList;

    }

    /**
     * man schreibt Information in der Datei
     */
    public void writeToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(file), repoList);

    }

    public void print(){
        for (Student stud: repoList)
            System.out.println(stud);
    }

    public int size(){
        return repoList.size();
    }

    /**
     * man sortiert steigend die Liste der Studenten
     */
    public void sort(){
        repoList.sort(Student::compareTo);
    }

}