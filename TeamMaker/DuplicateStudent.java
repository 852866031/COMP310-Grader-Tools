package TeamMaker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DuplicateStudent {
    public Student student;
    public ArrayList<Group> involved;

    public DuplicateStudent(Student student, ArrayList<Group> involved){
        this.student=student;
        this.involved=involved;
    }

    public static ArrayList<DuplicateStudent> findDup(ArrayList<Group> groups, ArrayList<Student> students){
        ArrayList<DuplicateStudent> ds = new ArrayList<>();
        for(Student student : students){
            ArrayList<Group> inGroups = new ArrayList<>();
            for(Group group : groups){
                if(student==group.student1 || student==group.student2){
                    inGroups.add(group);
                }
            }
            if(inGroups.size()>1){
                ds.add(new DuplicateStudent(student, inGroups));
            }
        }
        return ds;
    }

    @Override
    public String toString() {
        StringBuilder result= new StringBuilder("DuplicateStudent: " + "student=" + student + "\ninvolved in ");
        for(Group group : involved){
            result.append(group.toString());
        }
        result.append("\n");
        return result.toString();
    }

    public static void writeDupToCSV(String filename, ArrayList<DuplicateStudent> ds) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filename);
        for(DuplicateStudent d : ds){
            for(Group involved : d.involved){
                writer.write("Group,");
                writer.write(involved.student1.toString() + "\n"+""+","+involved.student2.toString()+"\n");
            }
            writer.write("\n");
        }
        writer.close();
    }
}
