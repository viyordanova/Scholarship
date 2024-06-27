import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class UniversitySystem {

    private final ArrayList<User> users;
    private PrintStream printOutStream;

    public UniversitySystem() {
        this.users = new ArrayList<>();
        this.createAdminUser();
    }

    private void createAdminUser() {
        Administartor admin = new Administartor("admin", "admin");
        users.add(admin);
    }
    public void loginUser(String name, String password, Socket clientSocket) throws IOException {
        printOutStream = new PrintStream(clientSocket.getOutputStream());
        for (User user : users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                printOutStream.println("Logged in successfully!");
                switch (user.getUserType()) {
                    case STUDENT:
                        Student student = (Student) user;
                        studentMenu(student);
                        break;
                    case SECRETARY:
                        Secretary secretary = (Secretary) user;
                        secretaryMenu(secretary, clientSocket);
                        break;
                    case ADMINISTRATOR:
                        Administartor admin = (Administartor) user;
                        adminMenu(admin, clientSocket);
                        break;
                }
                return;
            }
        }
        printOutStream.println("Invalid username or password!");
        printOutStream.println("Please try again!");
    }


    public void adminMenu(Administartor admin, Socket clientSocket) throws IOException {
        printOutStream.println("Welcome " + admin.getName() + "!");
        printOutStream.println("Creating new user...");
        Scanner scanner = new Scanner(clientSocket.getInputStream());
        printOutStream.println("Please enter user type (1 - Student, 2 - Secretary)");
        int userType = Integer.parseInt(scanner.nextLine());
        switch (userType) {
            case 1:
                if (createStudentUser(scanner)) {
                    printOutStream.println("Student created successfully!");
                } else {
                    printOutStream.println("Student creation failed! Please try again!");
                    createStudentUser(scanner);
                }
                break;
            case 2:
                if (createSecretaryUser(scanner)) {
                    printOutStream.println("Secretary created successfully!");
                } else {
                    printOutStream.println("Secretary creation failed! Please try again!");
                    createSecretaryUser(scanner);
                }
                break;
        }
        printOutStream.println("Thank you and goodbye " + admin.getName() + "!");
    }

    private boolean createStudentUser(Scanner scanner) {
        printOutStream.println("Please enter name:");
        String studentUsername = scanner.nextLine();
        printOutStream.println("Please enter password:");
        String studentPassword = scanner.nextLine();
        printOutStream.println("Please enter score: ");
        double score = scanner.nextDouble();
        scanner.nextLine();
        printOutStream.println("Please enter income per 1 person in your family: ");
        double income = scanner.nextDouble();
        scanner.nextLine();
        printOutStream.println("Please enter faculty:");
        String faculty = scanner.nextLine();
        printOutStream.println("Please enter faculty number:");
        String facultyNumber = scanner.nextLine();
        if (validateStudentCreation(facultyNumber)) {
            Student student = new Student(studentUsername, studentPassword, score, income, faculty,  facultyNumber);
            users.add(student);
            return true;
        }
        return false;
    }

    private boolean createSecretaryUser(Scanner scanner) {
        printOutStream.println("Please enter name:");
        String secretaryUsername = scanner.nextLine();
        printOutStream.println("Please enter password:");
        String secretaryPassword = scanner.nextLine();
        if (validateSecretaryCreation(secretaryPassword)) {
            Secretary secretary = new Secretary(secretaryUsername, secretaryPassword);
            users.add(secretary);
            return true;
        }
        return false;
    }

    boolean validateStudentCreation(String facultyNumber) {
        String facultyNumberRegex = "[1-9]{9}";
        if (!facultyNumber.matches(facultyNumberRegex)) {
            printOutStream.println("Faculty number must be 9 digits long!");
            return false;
        }
        return true;
    }

    boolean validateSecretaryCreation(String secretaryPassword) {
        String passwordRegex = "\\S{5,}";
        if (!secretaryPassword.matches(passwordRegex)) {
            printOutStream.println("Password must be at least 5 characters long!");
            return false;
        }
        return true;
    }

    private void secretaryMenu(Secretary secretary, Socket clientSocket) throws IOException {
        printOutStream.println("Welcome " + secretary.getName() + "!");
        Scanner scanner = new Scanner(clientSocket.getInputStream());
        printOutStream.println("Approving form...");
        if (checkForm(secretary, scanner)) {
            printOutStream.println("Form processed!");
        } else {
            printOutStream.println("Error processing form!");
            checkForm(secretary, scanner);
        }

        printOutStream.println("Do you want to view the approved student forms? (Yes/No)");
        String choice1 = scanner.nextLine();
        if (choice1.equalsIgnoreCase("yes")) {
            secretary.displayCorrectScholarshipForms(printOutStream);
        }

        printOutStream.println("Do you want to view the disapproved student forms? (Yes/No)");
        String choice2 = scanner.nextLine();
        if (choice2.equalsIgnoreCase("yes")) {
            secretary.displayIncorrectScholarshipForms(printOutStream);
        }

        printOutStream.println("Thank you and goodbye " + secretary.getName() + "!");
    }


    private boolean checkForm(Secretary secretary, Scanner scanner) {
        printOutStream.println("Please enter faculty number:");
        String facultyNumber = scanner.nextLine();
        Student student = findStudentByFacultyNumber(facultyNumber);
        if (student == null) {
            printOutStream.println("Student not found!");
            return false;
        } else {
            Document document = new Document(student);
            secretary.processForm(document);
            return true;
        }
    }

    private Student findStudentByFacultyNumber(String facultyNumber) {
        for (User user : users) {
            if (user.getUserType() == UserType.STUDENT) {
                Student student = (Student) user;
                if (student.getFacultyNumber().equals(facultyNumber)) {
                    return student;
                }
            }
        }
        return null;
    }

    private void studentMenu(Student student) {
        printOutStream.println("Welcome " + student.getName() + "!");
        printOutStream.println(student.displayStudentScholarships());
        printOutStream.println("Thank you and goodbye " + student.getName() + "!");
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}