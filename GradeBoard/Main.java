package GradeBoard;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Course course= Course.getInstance();
        course.LoadInfo("Teams.csv");
        course.LoadTeamGrade("Assignment1.csv", 20.0, 10, 0, 1, 2);
        course.LoadIndividualGrade("quiz.csv", 18.0, 10, 0, 1);
        course.sortTeams(CompareStrategy.GRADE);
        course.sortStudent(CompareStrategy.GRADE);
        course.exportByTeams("TeamScoreBoard.csv", ExportFormat.SCORE);
        course.exportByStudents("StudentScoreBoard.csv", ExportFormat.SCORE);
        course.exportByTeams("TeamGradeBoard.csv", ExportFormat.BOTH);
        course.exportByStudents("StudentGradeBoard.csv", ExportFormat.BOTH);
    }
}