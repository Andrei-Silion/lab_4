import Model.Student;

import java.util.Scanner;

public class ConsoleView {


    public void  printMenu(){
        System.out.println("""
                1.Sort Students\s
                2.Sort Courses\s
                3.Filter Courses\s
                4.Filter Students\s
                5.Courses with free places\s
                6.Students enrolled for a course\s
                7.Show Courses\s
                8.Show Students\s
                9.Register\s
                0.Exit\s
                """);
    }


    public void menu(){
        Controller object = new Controller("java/student.json", "java/course.json");
        object.readAll();
        printMenu();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Wahle eine Option:");
        int option = keyboard.nextInt();
        while (option != 0){
            if (option == 1){
                this.sortStudents(object);
            }
            else if (option == 2){
                this.sortCourses(object);
            }
            else if (option == 3){
                this.filterCourses(object);
            }
            else if(option == 4){
                this.filterStudents(object);
            }
            else if (option == 5){
                System.out.println(object.retrieveCoursesWithFreePlaces());
            }
            else if (option == 6){
                this.studentenrolledforcourse(object);
            }
            else if (option == 7){
                object.printCourses();
            }
            else if (option == 8){
                object.printStudents();
            }
            else if (option == 9){
                this.register(object);
            }
            System.out.print("Wahlen eine Option: ");
            option = keyboard.nextInt();

        }
        object.writeAll();
    }

    public void sortStudents(Controller object){
        object.sortStudents();
        object.printStudents();
    }

    public void sortCourses(Controller object){
        object.sortCourses();
        object.printCourses();
    }

    public void filterCourses(Controller object){
        System.out.println(object.filterCourses());
    }

    public void filterStudents(Controller object){
        System.out.println(object.filterStudents());
    }

    public void studentenrolledforcourse(Controller object){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Type ID des Kurses");
        long id = keyboard.nextLong();

        System.out.println(object.retrieveStudentsEnrolledForACourse(id));
    }

    /**
     * man liest die IDs vom Kurse und Studenten
     * @param object Controller object
     */
    public void register(Controller object){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Type ID des Kurses");
        long courseid = keyboard.nextLong();

        System.out.println("Type ID der Studenten");
        long studentid = keyboard.nextLong();

        try {
            object.register(courseid, studentid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}