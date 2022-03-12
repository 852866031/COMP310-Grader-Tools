package Grading;

import Grading.Submission;
import Grading.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Backend {
    private ArrayList<Submission> submissions;
    private final String workingDir;
    private final String dataDir;
    private int current = 0;
    private ArrayList<Test> tests = new ArrayList<>();
    private String testDir;
    private FileWriter logWriter = null;


    public Backend(String from, String to) {
        this.workingDir = to;
        this.dataDir = from;
    }

    public void setCurrent(String input) {
        int index = 0;
        try{
            index = Integer.parseInt(input);
        }catch (Exception ignored){}
        this.current = Math.max(0, index-1);
    }

    public int countSubmissions(){
        File directory = new File(dataDir);
        String[] contents = directory.list();
        if(!directory.exists() || contents==null){
            System.out.println("Error: no such directory exists: "+dataDir);
            System.exit(2);
        }
        int count = 0;
        for(String content : contents){
            if(content.charAt(0)!='.') count++;
        }
        return count;
    }

    public boolean hasNext(){
        return current < submissions.size();
    }

    public void next(){
        current++;
    }

    public void init() {
        try{
            this.logWriter = new FileWriter("Log.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        this.submissions = Submission.init(dataDir, workingDir);
        System.out.println(this.submissions.size()+" submissions parsed and compiled");
    }

    public void saveCurrentInfo(){
        System.out.println("Submission: "+(current+1)+"/"+submissions.size());
        System.out.println("Current submission: "+submissions.get(current).id);
        System.out.println("Executable: "+submissions.get(current).executable);
    }

    public void loadTests(String dir){
        dir = dir+"/tests";
        File directory = new File(dir);
        String[] contents = directory.list();
        if(!directory.exists() || contents==null){
            System.out.println("Error: no such directory exists: "+dir);
            System.exit(2);
        }
        for(String content : contents){
            if(content.charAt(0)!='.' && !content.contentEquals("result") && !content.contentEquals("files")) {
                tests.add(new Test(dir, content));
            }
        }
        this.testDir = dir;
        System.out.println(tests.size()+" testcases loaded");
    }

    public ArrayList<Compare> runTests(){
        Submission cur = submissions.get(current);
        ArrayList<Compare> result = new ArrayList<>();
        try{
            String tmp = "Data/tmp";
            Submission.mkdir("Data", "tmp");
            String command = "cp "+cur.executable+" "+tmp+"/binary";
            Runtime.getRuntime().exec(command).waitFor();
            Files.deleteIfExists(Path.of(tmp + "/test.sh"));
            FileWriter script = new FileWriter(tmp+"/test.sh");
            File file = new File(tmp+"/test.sh");
            boolean set = file.setExecutable(true);
            for(Test test : tests){
                command = "cp "+test.testPath+" "+tmp+"/"+test.name;
                Runtime.getRuntime().exec(command).waitFor();
            }
            File directory = new File("Data/testcases/files");
            String[] contents = directory.list();
            assert contents != null;
            for(String content : contents){
                command = "cp "+"Data/testcases/files/"+content+" "+tmp+"/"+content;
                Runtime.getRuntime().exec(command).waitFor();
            }
            File binary = new File("Data/tmp/binary");
            set = binary.setExecutable(true);
            for(Test test : tests){
                script.write("./binary < "+test.name+" > "+test.name+"_output\n");
            }
            script.close();
            Process process = Runtime.getRuntime().exec("./test.sh", null, new File("Data/tmp"));
            Thread.sleep(500);
            process.destroy();
            for(Test test : tests){
                String output = readFile("Data/tmp/"+test.name+"_output");
                String expected = readFile("Data/testcases/result/"+test.name+"_result");
                result.add(new Compare(output, expected, test));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void writeFeedback(ArrayList<Compare> tests){
        Submission cur = submissions.get(current);
        try {
            StringBuilder content = new StringBuilder();
            Submission.mkdir("Data", "feedback");
            FileWriter myWriter = new FileWriter("Data/feedback/"+cur.id);
            content.append("Submission of ID: ").append(cur.id);
            int pass = 0;
            for(Compare test : tests){
                if(test.pass) content.append("Pass ").append(test.test.name).append("\n");
                else content.append("Fail ").append(test.test.name).append("\n");
                if(test.pass) pass++;
            }
            content.append(pass).append("/").append(tests.size()).append(" tests passed\n\n");
            System.out.println(content.toString());
            System.out.println("Please write additional feedback: ");
            String feedback = Frontend.getInput();
            System.out.println("Do you want to mark the submission? [y/AnyKey]");
            String mark = Frontend.getInput();
            if(mark.contentEquals("y")) mark(cur.id);
            content.append(feedback);
            myWriter.write(content.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mark(String id){
        try {
            logWriter.write(id+" marked\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String filename){
        StringBuilder sb = new StringBuilder();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                sb.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void end(){
        if(logWriter!=null){
            try{
                this.logWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

