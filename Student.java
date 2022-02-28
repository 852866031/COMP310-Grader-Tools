import java.util.ArrayList;
import java.util.Comparator;

public class Student {
    public String id;
    public String name;
    public String email;
    public GradeBoard gradeBoard = new GradeBoard();

    public Student(String name, String id, String email){
        this.id = id;
        this.name = name;
        this.email =email;
    }

    public static Student searchByID(String id, ArrayList<Student> students){
        for(Student student : students){
            if(student.id.contentEquals(id)){
                return student;
            }
        }
        return null;
    }

    public static Student searchByID(ArrayList<Student> students, String id){
        for(Student student : students){
            if(student.id.contentEquals(id)) return student;
        }
        return null;
    }

    public void setGrade(String evaluation, double v) {
        this.gradeBoard.put(evaluation, v);
    }

    public String getInfo(){
        return id+","+name+","+email;
    }

    public static class StudentComparator implements Comparator<Student> {
        private final CompareStrategy strategy;
        public StudentComparator(CompareStrategy strategy){
            assert strategy!=null;
            this.strategy = strategy;
        }

        public int compare(Student f1, Student f2){
            switch (this.strategy){
                case GRADE -> {
                    return -Double.compare(f1.gradeBoard.getOverall(), f2.gradeBoard.getOverall());
                }
                case ID -> {
                    return -f1.id.compareTo(f2.id);
                }
                case NAME -> {
                    return -f1.name.compareTo(f2.name);
                }
                default -> {
                    throw new AssertionError("Invalid comparing strategy");
                }
            }
        }
    }

    @Override
    public String toString() {
        return name + "," + id + "," + email + "," + gradeBoard.getGrades();
    }


}