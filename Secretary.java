import java.io.PrintStream;
import java.util.ArrayList;

public class Secretary extends User {
    private final ArrayList<Document> correctScholarshipForms;
    private final ArrayList<Document> incorrectScholarshipForms;
    public Secretary(String name, String password) {
        super(name, password);
        correctScholarshipForms = new ArrayList<>();
        incorrectScholarshipForms = new ArrayList<>();
    }

    public ArrayList<Document> getCorrectScholarshipForms() {
        synchronized (correctScholarshipForms) {
            return correctScholarshipForms;
        }

    }

    public ArrayList<Document> getIncorrectScholarshipForms() {
        synchronized (incorrectScholarshipForms) {
            return incorrectScholarshipForms;
        }
    }

    public void processForm(Document document) {
        synchronized (correctScholarshipForms) {
            if (document.getScholarshipType() != null) {
                correctScholarshipForms.add(document);
            } else {
                incorrectScholarshipForms.add(document);
            }
        }
        if (document.getStudent().getScore() >= 5.50 && document.getStudent().getIncome() <= 500) {
            ArrayList<String> studentScholarships = document.getStudent().getStudentScholarships();
            synchronized (studentScholarships) {
                studentScholarships.add("Score Scholarship");
                for (int i = 0; i < 5; i++) {
                    studentScholarships.add("Special Scholarship");
                }
            }
        }
        if (document.getStudent().getScore() >= 5.30 && document.getStudent().getIncome() <= 300) {
            ArrayList<String> studentScholarships = document.getStudent().getStudentScholarships();
            synchronized (studentScholarships) {
                for (int i = 0; i < 4; i++) {
                    studentScholarships.add("Special Scholarship");
                }
            }
        }
    }

    public void displayCorrectScholarshipForms(PrintStream printOutStream) {
        synchronized (correctScholarshipForms) {
            for (Document document : correctScholarshipForms) {
                printOutStream.println("Approved Form: " + document);
            }
        }
    }

    public void displayIncorrectScholarshipForms(PrintStream printOutStream) {
        synchronized (incorrectScholarshipForms) {
            for (Document document : incorrectScholarshipForms) {
                printOutStream.println("Disapproved Form: " + document);
            }
        }
    }

    @Override
    public UserType getUserType() {
        return UserType.SECRETARY;
    }

    @Override
    public String toString() {
        return "Secretary{" + getName() + '}';

    }
}