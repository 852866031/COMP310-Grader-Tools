package Grading;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Submission {
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

    public static ArrayList<Submission> init(String from, String to){
        ArrayList<Submission> submissions = getZipsFromDirectory(from);
        for(Submission submission : submissions){
            submission.unzip(to);
            submission.dir = to+"/"+submission.id;
            submission.makeDir = findMakeFile(submission.dir);
            submission.make();
            submission.executable = submission.makeDir+"/mysh";
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
                for(String token : tokens){
                    try{
                        Integer.parseInt(token);
                        id = token;
                        break;
                    }catch (Exception ignored){}
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

    public void unzip(String dir){
        try{
            mkdir(dir, this.id);
            if(this.type.contentEquals("zip")){
                String command = "unzip "+this.dir+"/"+this.filename+"."+this.type+" -d "+ dir+"/"+this.id;
                Runtime.getRuntime().exec(command).waitFor();

            }
            else if(this.type.contentEquals("tar")){
                //tar -xf archive.tar -C /opt/files
                String command = "tar -xf "+this.dir+"/"+this.filename+"."+this.type+" -C "+dir+"/"+this.id;
                Runtime.getRuntime().exec(command).waitFor();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void make(){
        try{
            Runtime.getRuntime().exec("make", null, new File(this.makeDir)).waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
