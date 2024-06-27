class Document {
    private Student student;
    private ScholarshipType scholarshipType;

    public Document(Student student) {
        this.student = student;
        if (student.getScore() >= 5.50 && student.getIncome() < 500) {
            this.scholarshipType = ScholarshipType.SCORE;
        } else if (student.getScore() >= 5.30 && student.getScore() < 5.50 &&  student.getIncome() < 300) {
            this.scholarshipType = ScholarshipType.SPECIAL;
        }
    }

    public Student getStudent() {
        return student;
    }

    public ScholarshipType getScholarshipType() {
        return scholarshipType;
    }

    @Override
    public String toString() {
        return "Document{" +
                " Scolarship type: " + scholarshipType +
                " student=" + student + '\'' +
                '}';
    }
}