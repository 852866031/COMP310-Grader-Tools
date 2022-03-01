package GradeBoard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Course {
    private final static Course course = new Course();
    private final ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Team> teams = new ArrayList<>();

    public static Course getInstance(){
        return course;
    }

    public void LoadInfo(String filename){
        this.teams = Team.TeamLoader("teams.csv");
        for(Team team : this.teams){
            students.add(team.student1);
            if(team.student2!=null) students.add(team.student2);
        }
    }

    public void LoadTeamGrade(String filename, double fullScore, double fullRatio, int idColNum, int gradeColNum, double factor){
        try{
            Grade placeholder = new Grade(0, fullScore, fullRatio);
            String evaluation = filename.substring(0, filename.length() - 4);
            for(Team team : this.teams){
                team.setGrade(evaluation, placeholder);
            }
            BufferedReader br = new BufferedReader(new FileReader((filename)));
            String line;
            while ((line = br.readLine()) != null)  {
                String[] s = line.split(",");
                String id = s[idColNum].substring(1);
                try{
                    Integer.parseInt(id);
                }catch (Exception e){
                    continue;
                }
                String grade = s[gradeColNum];
                if(grade.length()==0) grade = "0.0";
                Grade grade1 = new Grade(Double.parseDouble(grade)*factor, fullScore, fullRatio);
                Team related = Team.searchByID(this.teams, id);
                if(related != null && ((int)grade1.score)!=0) related.setGrade(evaluation, grade1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LoadIndividualGrade(String filename,  double fullScore, double ratio, int idColNum, int gradeColNum){
        try{
            Grade placeholder = new Grade(0, fullScore, ratio);
            String evaluation = filename.substring(0, filename.length() - 4);
            for(Student student : this.students){
                student.setGrade(evaluation, placeholder);
            }
            BufferedReader br = new BufferedReader(new FileReader((filename)));
            String line;
            while ((line = br.readLine()) != null)  {
                String[] s = line.split(",");
                String id = s[idColNum].substring(1);
                try{
                    Integer.parseInt(id);
                }catch (Exception e){
                    continue;
                }
                String grade = s[gradeColNum];
                if(grade.length()==0) grade = "0.0";
                Grade grade1 = new Grade(Double.parseDouble(grade), fullScore, ratio);
                Student student = Student.searchByID(this.students, id);
                if(student!=null) student.setGrade(evaluation, grade1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sortTeams(CompareStrategy strategy){
        Team.TeamComparator teamComparator = new Team.TeamComparator(strategy);
        this.teams.sort(teamComparator);
    }

    public void sortStudent(CompareStrategy strategy){
        Student.StudentComparator studentComparator = new Student.StudentComparator(strategy);
        this.students.sort(studentComparator);
    }

    public void exportByTeams(String filename, ExportFormat format){
        try{
            PrintWriter writer = new PrintWriter(filename);
            writer.write("ID,Name,Email,ID,Name,Email,"+teams.get(0).gradeBoard.getEvaluations()+"\n");
            for(Team team : this.teams){
                if(team.student2!=null){
                    writer.write(team.student1.getInfo()+",");
                    writer.write(team.student2.getInfo()+",");
                    writer.write(team.gradeBoard.getGrades(format)+"\n");
                }
                else{
                    writer.write(team.student1.getInfo()+", "+", "+", "+", ");
                    writer.write(team.gradeBoard.getGrades(format)+"\n");
                }
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exportByStudents(String filename, ExportFormat format){
        try{
            PrintWriter writer = new PrintWriter(filename);
            writer.write("ID,Name,Email,"+students.get(0).gradeBoard.getEvaluations()+"\n");
            for(Student student : this.students){
                writer.write(student.getInfo()+","+student.gradeBoard.getGrades(format)+"\n");
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printTeams(){
        for(Team team : teams){
            System.out.println(team);
        }
    }
}
