package GradeBoard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Team {
    public Student student1;
    public Student student2;
    public GradeBoard gradeBoard = new GradeBoard();

    public Team(Student s1, Student s2){
        this.student1 = s1;
        this.student2 = s2;
    }

    public static ArrayList<Team> TeamLoader(String filename) {
        ArrayList<Team> teams = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader((filename)));
            String line;
            while ((line = br.readLine()) != null)  {
                String[] s = line.split(",");    // use comma as separator
                Student s1;
                Student s2 = null;
                if(s.length==6){
                    s1 = new Student(s[0], s[1], s[2]);
                    s2 = new Student(s[3], s[4], s[5]);
                }else{
                    s1 = new Student(s[0], s[1], s[2]);
                }
                teams.add(new Team(s1, s2));
            }
        }catch (Exception e){
            System.out.println("Invalid format or file not exist");
        }
        return teams;
    }

    public static Team searchByID(ArrayList<Team> teams, String id){
        for(Team team : teams){
            if(team.student1.id.contentEquals(id)){
                return team;
            }
            if(team.student2!=null && team.student2.id.contentEquals(id)){
                return team;
            }
        }
        return null;
    }

    public void setGrade(String gradeKey, double value){
        try {
            this.gradeBoard.put(gradeKey, value);
            if(this.student1!=null) this.student1.gradeBoard.put(gradeKey, value);
            if(this.student2!=null) this.student2.gradeBoard.put(gradeKey, value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class TeamComparator implements Comparator<Team> {
        private final CompareStrategy strategy;
        public TeamComparator(CompareStrategy strategy){
            assert strategy!=null;
            this.strategy = strategy;
        }

        public int compare(Team f1, Team f2){
            switch (this.strategy){
                case GRADE -> {
                    return -Double.compare(f1.gradeBoard.getOverall(), f2.gradeBoard.getOverall());
                }
                case ID -> {
                    return -f1.student1.id.compareTo(f2.student1.id);
                }
                case NAME -> {
                    return -f1.student1.name.compareTo(f2.student1.name);
                }
                default -> {
                    throw new AssertionError("Invalid comparing strategy");
                }
            }
        }
    }

    @Override
    public String toString() {
        if(student2!=null){
            return student1 + "\n" + student2;
        }
        else{
            return student1.toString();
        }
    }
}
