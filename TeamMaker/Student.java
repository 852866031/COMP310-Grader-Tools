package TeamMaker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Student {
    public int id;
    public String name;
    public String email;

    public Student(String name, int id, String email){
        this.id = id;
        this.name = name;
        this.email =email;
    }

    public static Student searchByID(int id, ArrayList<Student> students){
        for(Student student : students){
            if(student.id==id){
                return student;
            }
        }
        return null;
    }

    public static ArrayList<Student> readStudents(String filename) throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null)  {
            String[] s = line.split(",");    // use comma as separator
            if(s[0].contains(" is online")){
                String[] str = s[0].split(" ");
                int len = (str.length-2)/2;
                s[0] = "";
                for(int i = 0; i<len; i++){
                    s[0]=s[0]+str[i];
                }
            }
            students.add(new Student(s[0], Integer.parseInt(s[2]), s[1]));
        }
        return students;
    }




    @Override
    public String toString() {
        return name + "," + id + "," + email;
    }
}
