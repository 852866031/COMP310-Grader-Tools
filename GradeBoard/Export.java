package GradeBoard;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Export {

    public static String exportStudentInfo(Student student){
        return student.id+","+student.name+","+ student.email;
    }

    public static String exportEvaluations(GradeBoard gradeBoard){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> keys = new ArrayList<>(gradeBoard.grades.keySet());
        Collections.sort(keys);
        for(String key : keys){
            sb.append(key);
            sb.append(",");
        }
        sb.append("Overall");
        return sb.toString();
    }

    public static String exportGrade(GradeBoard gradeBoard, ExportFormat format){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> keys = new ArrayList<>(gradeBoard.grades.keySet());
        Collections.sort(keys);
        for(String key : keys){
            if(format == ExportFormat.BOTH){
                sb.append(gradeBoard.grades.get(key));
                sb.append(",");
            }
            else if(format==ExportFormat.SCORE){
                sb.append(String.format("%.2f", gradeBoard.grades.get(key).score)).append("/");
                sb.append(String.format("%.2f", gradeBoard.grades.get(key).fullScore)).append(",");
            }
            else{
                sb.append(String.format("%.2f", gradeBoard.grades.get(key).ratio)).append("/");
                sb.append(String.format("%.2f", gradeBoard.grades.get(key).fullRatio)).append(",");
            }
        }
        if(format!=ExportFormat.SCORE) sb.append(String.format("%.2f", gradeBoard.overall));
        return sb.toString();
    }

    public static void exportByTeams(Course course, String filename, ExportFormat format){
        try{
            PrintWriter writer = new PrintWriter(filename);
            writer.write("ID,Name,Email,ID,Name,Email,"+exportEvaluations(course.teams.get(0).gradeBoard)+"\n");
            for(Team team : course.teams){
                if(team.student2!=null){
                    writer.write(exportStudentInfo(team.student1)+",");
                    writer.write(exportStudentInfo(team.student2)+",");
                    writer.write(exportGrade(team.gradeBoard, format)+"\n");
                }
                else{
                    writer.write(exportStudentInfo(team.student1)+", "+", "+", "+", ");
                    writer.write(exportGrade(team.gradeBoard, format)+"\n");
                }
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void exportByStudents(Course course, String filename, ExportFormat format){
        try{
            PrintWriter writer = new PrintWriter(filename);
            writer.write("ID,Name,Email,"+exportEvaluations(course.teams.get(0).gradeBoard)+"\n");
            for(Student student : course.students){
                writer.write(exportStudentInfo(student)+","+exportGrade(student.gradeBoard, format)+"\n");
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
