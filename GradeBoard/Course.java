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

    public void LoadTeamGrade(String filename, int idColNum, int gradeColNum){
        try{
            String evaluation = filename.substring(0, filename.length() - 4);
            for(Team team : this.teams){
                team.setGrade(evaluation, 0.0);
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
                double value = Double.parseDouble(grade);
                Team related = Team.searchByID(this.teams, id);
                if(related != null && ((int)value)!=0) related.setGrade(evaluation, value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LoadIndividualGrade(String filename, int idColNum, int gradeColNum){
        try{
            String evaluation = filename.substring(0, filename.length() - 4);
            for(Student student : this.students){
                student.setGrade(evaluation, 0.0);
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
                double value = Double.parseDouble(grade);
                Student student = Student.searchByID(this.students, id);
                if(student!=null) student.setGrade(evaluation, value);
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

    public void exportByTeams(String filename){
        try{
            PrintWriter writer = new PrintWriter(filename);
            writer.write("ID,Name,Email,ID,Name,Email,"+teams.get(0).gradeBoard.getEvaluations()+"\n");
            for(Team team : this.teams){
                if(team.student2!=null){
                    writer.write(team.student1.getInfo()+",");
                    writer.write(team.student2.getInfo()+",");
                    writer.write(team.gradeBoard.getGrades()+"\n");
                }
                else{
                    writer.write(team.student1.getInfo()+", "+", "+", "+", ");
                    writer.write(team.gradeBoard.getGrades()+"\n");
                }
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exportByStudents(String filename){
        try{
            PrintWriter writer = new PrintWriter(filename);
            writer.write("ID,Name,Email,"+students.get(0).gradeBoard.getEvaluations()+"\n");
            for(Student student : this.students){
                writer.write(student.getInfo()+","+student.gradeBoard.getGrades()+"\n");
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
