package TeamMaker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import javax.mail.Session;

public class Group {
    public Student student1;
    public Student student2;

    public Group(Student s1, Student s2){
        this.student1 = s1;
        this.student2 = s2;
    }


    public static boolean compare(Group group1, Group group2) {
        if(group2.student2==null && group1.student2==null){
            return group1.student1==group2.student1;
        }
        else if(group2.student2 == null || group1.student2 == null){
            return false;
        }
        else if(group1.student1 == group2.student1 && group1.student2==group2.student2){
            return true;
        }
        else return group1.student1 == group2.student2 && group1.student2 == group2.student1;
    }

    public static ArrayList<Group> removeDuplicate(ArrayList<Group> students){
        ArrayList<Group> result = new ArrayList<>();
        for(Group group : students){
            boolean added = false;
            for(Group add : result){
                if(compare(add, group)){
                    added=true;
                    break;
                }
            }
            if(!added){
                result.add(group);
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student1, student2);
    }

    public static void writeGroupToCSV(String filename, ArrayList<Group> groups) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filename);
        for(Group group : groups){
            if(group.student2!=null){
                writer.write(group.student1.toString()+",");
                writer.write(group.student2.toString()+"\n");
            }
            else{
                writer.write(group.student1.toString()+"\n");
            }
        }
        writer.close();
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
