package Grading;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

public class Submission{
    public final String id;
    public final String filename;
    public String dir;
    public final String type;
    public String makeDir;
    public String executable;

    public Submission(String id, String filename, String dir, String type) {
        this.id = id;
        this.filename = filename;
        this.dir = dir;
        this.type = type;
    }

    public static void unzip(String from, String to){
        ArrayList<Submission> submissions = getZipsFromDirectory(from);
        unzipScript(submissions, to);
    }

    public static ArrayList<Submission> init(String from, String to){
        ArrayList<Submission> submissions = getFoldersFromDirectory("Data/submissions");
        SubmissionComparator sc = new SubmissionComparator();
        submissions.sort(sc);
        for(Submission submission : submissions){
            submission.dir = to+"/"+submission.id;
            submission.makeDir = findMakeFile(submission.dir);
            submission.make();
            submission.executable = submission.makeDir+"/mysh";
        }
        return submissions;
    }

    public static ArrayList<Submission> getFoldersFromDirectory(String dir){
        ArrayList<Submission> submissions = new ArrayList<>();
        try{
            File directory = new File(dir);
            String[] contents = directory.list();
            assert contents != null;
            for(String content : contents){
                if(content.charAt(0)=='.') continue;
                submissions.add(new Submission(content, content, dir, null));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return submissions;
    }

    public static ArrayList<Submission> getZipsFromDirectory(String dir){
        ArrayList<Submission> submissions = new ArrayList<>();
        try{
            File directory = new File(dir);
            String[] contents = directory.list();
            assert contents != null;
            for(String content : contents){
                if(content.charAt(0)=='.') continue;
                String[] tokens = content.split("\\.");
                content = tokens[0];
                StringBuilder type = new StringBuilder();
                for(int i = 1; i<tokens.length; i++){
                    type.append(tokens[i]);
                }
                tokens = content.split("_");
                String id = null;
                for(int i = tokens.length-1; i>=0; i--){
                    try{
                        Integer.parseInt(tokens[i]);
                        id = tokens[i];
                        break;
                    }catch (Exception ignored){}
                }
                if(id == null){
                    System.err.println(content+"."+type+" id not found");
                }
                submissions.add(new Submission(id, content, dir, type.toString()));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return submissions;
    }

    public static void printSubmissions(ArrayList<Submission> submissions){
        for(Submission submission : submissions){
            System.out.println(submission.filename+"\t"+submission.dir+"\t"+submission.id+"\t"+ submission.type);
        }
    }

    public static String findMakeFile(String dir){
        File directory = new File(dir);
        String[] contents = directory.list();
        if(contents==null || contents.length==0) return null;
        for(String content : contents){
            if(content.contentEquals("Makefile")){
                return dir;
            }
        }
        for(String content : contents){
            String result = findMakeFile(dir+"/"+content);
            if(result!=null){
                return result;
            }
        }
        return null;
    }

    public static void mkdir(String dir, String target){
        try {
            if(dir == null){
                File directory = new File(target);
                if(!directory.exists()){
                    directory.mkdir();
                }
                return;
            }
            File directory = new File(dir);
            if(!directory.exists()){
                directory.mkdir();
            }
            String[] contents = directory.list();
            if (contents != null) {
                for (String content : contents) {
                    if (content.contentEquals(target)) {
                        Runtime.getRuntime().exec("rm -rf " + dir+"/"+target).waitFor();
                        break;
                    }
                }
            }
            Runtime.getRuntime().exec("mkdir " + dir + "/" +target).waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void unzipScript(ArrayList<Submission> submissions, String dir){
        try {
            Files.deleteIfExists(Path.of("unzip.sh"));
            FileWriter myWriter = new FileWriter("unzip.sh");
            FileWriter script = new FileWriter("unzip.sh");
            boolean set = new File("unzip.sh").setExecutable(true);
            for(Submission submission : submissions){
                if(submission.type.contentEquals("zip")){
                    String command = "unzip "+submission.dir+ "/'" +submission.filename+"."+submission.type+"' -d "+ dir+"/"+submission.id+"\n";
                    myWriter.write(command);
                }
                else if(submission.type.contentEquals("tar")){
                    //tar -xf archive.tar -C /opt/files
                    String command = "tar -xf "+submission.dir+"/'"+submission.filename+"."+submission.type+"' -C "+dir+"/"+submission.id+"\n";
                    myWriter.write(command);
                }
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void make(){
        try{
            if(makeDir==null){
                System.err.println("Submission: "+this.id+" make directory missing");
            }
            else{
                Runtime.getRuntime().exec("make", null, new File(this.makeDir)).waitFor();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class SubmissionComparator implements Comparator<Submission> {
        @Override
        public int compare(Submission o1, Submission o2) {
            try{
                return Integer.compare(Integer.parseInt(o1.id), Integer.parseInt(o2.id));
            }catch (Exception e){
                e.printStackTrace();
            }
            return 0;
        }
    }
}
