package models;

import utilities.Utilities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Student implements Comparable<Student> {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int tuitionFees;
    private ArrayList <models.Assignment> assignments = new ArrayList<>();

    public Student() {
        readStudent(new Scanner(System.in));

        System.out.println("How many assignments would you like to assign to this student? :");
        int numOfAssignments = Utilities.integerInput();
        for(int i = 0; i < numOfAssignments; i++) {
            models.Assignment assignment = new models.Assignment();
            this.assignments.add(assignment);
            models.PrivateSchool.allAssignments.add(assignment);
            models.Course.assignments.add(assignment);
        }
    }

    public Student(String firstName, String lastName, int tuitionFees, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
        this.assignments = new ArrayList<>();
    }

    // User Input for Students
    public void readStudent(Scanner sc) {
        System.out.println("----------------------------------");
        System.out.print("Please enter student's first name: ");
        this.firstName = sc.nextLine().trim();

        System.out.print("Please enter student's last name: ");
        this.lastName = sc.nextLine().trim();

        System.out.print("Please enter student's birth date: ");
        String birthDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        boolean flag = false;
        while (!flag) {
            try {
                birthDate = sc.next().trim();
                this.dateOfBirth = LocalDate.parse(birthDate, formatter);
                flag = true;
            } catch (DateTimeParseException e) {
                System.out.println("Please insert date in valid format! (dd-MM-yyyy)");
            }

        }
        do {
            System.out.print("Please enter tuition fees (100 up to 5000) for " + firstName.toUpperCase() + " " + lastName.toUpperCase() + ": ");
            while (!sc.hasNextInt()) {
                System.out.print("Tuition fees must be an integer!");
                sc.next();
            }
            this.tuitionFees = sc.nextInt();
        } while ( tuitionFees > 5000 || tuitionFees < 100);
        System.out.println("----------------------------------");
        System.out.println("Student was successfully created!");
    }

    public boolean addAssignment(models.Assignment assignment) {

            if (findAssignment(assignment) < 0) {
                assignments.add(assignment);

                return true;
            }
            return false;
    }

    private int findAssignment(models.Assignment assignment) {
        int pos = 0;
        try {
            pos = this.assignments.indexOf(assignment);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return pos;

    }

    public void showStudentDetails() {
        System.out.print("\t\n "+ "FULLNAME: "+ firstName.toUpperCase() + " " + lastName.toUpperCase() + " | "+ "Date-Of-Birth: " + dateOfBirth + " | "+ "Tuition Fees: " + tuitionFees + "\n");
    }

    // For each assignment in Student class
    public void showListOfAssignments() {
        System.out.println("Assignments for student: " + getFirstName().toUpperCase() + " " + getLastName().toUpperCase());
        System.out.println("------------------------------------------------------------------------------------");
        for(models.Assignment assignment: this.assignments) {
            assignment.showAssignmentDetails();
        }
    }

    public boolean hasAssignment(LocalDate start, LocalDate end) {
        for(models.Assignment assignment: this.assignments) {
            if(assignment.getSubDateTime().isAfter(start) &&
                    assignment.getSubDateTime().isBefore(end)) {
                return true;
            }
        }
        return false;
    }

    public void showListOfAssignmentsDue(LocalDate start, LocalDate end) {
        List<models.Assignment> allAssignmentsDue = new ArrayList<>();
        for(models.Assignment assignment: this.assignments) {
            if (assignment.getSubDateTime().isAfter(start) && assignment.getSubDateTime().isBefore(end)) {
                    allAssignmentsDue.add(assignment);
                    System.out.println(allAssignmentsDue);
                System.out.println("==========================================");
            }
        }
    }

    @Override
    public int compareTo(Student other) {
        return this.lastName.compareTo(other.lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public int getFees() {
        return tuitionFees;
    }

    public ArrayList<models.Assignment> getAssignments() {
        return this.assignments;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, tuitionFees, assignments);
    }

    @Override
    public String toString() {
        return "Student {" +
                " firstName = '" + firstName.toUpperCase() + '\'' +
                ", lastName = '" + lastName.toUpperCase() + '\'' +
                ", tuitionFees = " + tuitionFees + "\n" +
                '}';
    }
}