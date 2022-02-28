import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Course course= Course.getInstance();
        course.LoadInfo("teams.csv");
        course.LoadTeamGrade("Assignment1.csv", 0, 1);
        course.LoadIndividualGrade("quiz.csv", 0, 1);
        course.sortTeams(CompareStrategy.GRADE);
        course.sortStudent(CompareStrategy.GRADE);
        course.exportByTeams("TeamGradeBoard.csv");
        course.exportByStudents("StudentGradeBoard.csv");
    }
}
