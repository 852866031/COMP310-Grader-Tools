package TeamMaker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        try{
            ArrayList<Group> groups = new ArrayList<>();
            ArrayList<Student> students = Student.readStudents("./students.csv");
            ArrayList<IDPair> idPairs = IDPair.readIDPair("./groups.csv");
            for(Student s : students){
                int cur_id = s.id;
                ArrayList<Integer> matches = IDPair.search(cur_id, idPairs);
                for(Integer match : matches){
                    Student s1 = Student.searchByID(match, students);
                    groups.add(new Group(s, s1));
                }
                if(matches.size()==0){
                    groups.add(new Group(s, null));
                }
            }
            groups = Group.removeDuplicate(groups);
            ArrayList<DuplicateStudent> ds = DuplicateStudent.findDup(groups, students);
            System.out.println(ds.size());
            for(DuplicateStudent d : ds){
                System.out.println(d);
                for(Group g : d.involved){
                    groups.remove(g);
                }
            }
            for(Group group : groups){
                System.out.println(group);
            }
            System.out.println(groups.size());
            Group.writeGroupToCSV("validGroup.csv", groups);
            DuplicateStudent.writeDupToCSV("Dup.csv", ds);
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
    }
}
