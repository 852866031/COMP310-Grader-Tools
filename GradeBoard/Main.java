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
        Export.exportByTeams(course,"TeamScoreBoard.csv", ExportFormat.SCORE);
        Export.exportByStudents(course,"StudentScoreBoard.csv", ExportFormat.SCORE);
        Export.exportByTeams(course,"TeamGradeBoard.csv", ExportFormat.BOTH);
        Export.exportByStudents(course,"StudentGradeBoard.csv", ExportFormat.BOTH);
    }
}