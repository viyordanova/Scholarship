import java.util.ArrayList;
public class Student extends User{
    private final ArrayList<String> studentScholarships;
    private double score;
    private double income;
    private String faculty;
    private String facultyNumber;

    public Student(String name, String password, double score, double income, String faculty, String facultyNumber) {
        super(name, password);
        studentScholarships = new ArrayList<>();
        this.score = score;
        this.income = income;
        this.faculty = faculty;
        this.facultyNumber = facultyNumber;
    }

    public double getScore() {
        return score;
    }

    public double getIncome() {
        return income;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public ArrayList<String> getStudentScholarships() {
        synchronized (studentScholarships) {
            return studentScholarships;
        }
    }

    public String displayStudentScholarships() {
        if (studentScholarships != null && !studentScholarships.isEmpty()) {
            StringBuilder scholarshipsStringBuilder = new StringBuilder("Your scholarships:\n");
            for (String scholarship : studentScholarships) {
                scholarshipsStringBuilder.append(scholarship).append("\n");
            }
            return scholarshipsStringBuilder.toString();
        } else {
            return "Unfortunately, you did not qualify for a scholarship!";
        }
    }

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }

    @Override
    public String toString() {
        return "Student{" +
                " name: " + getName() +
                " score: " + score +
                ", income: " + income +
                ", faculty: '" + faculty + '\'' +
                ", facultyNumber: '" + facultyNumber + '\'' +
                '}';
    }
}