package Grading;

public class Main {
    static String mode = "grade";

    private static void help(String s) {
        System.out.println("Could not parse argument \""+s+"\".  \nPlease use only the following arguments: Main -unzip or Main");
        System.exit(1);
    }

    private static void opts(String[] args) {
        if(args.length==1 && args[0].contentEquals("-unzip")){
            mode = "unzip";
        }
    }

    public static void main(String[] args) {
        opts(args);
        String from = "Data/zips";
        String to = "Data/submissions";
        String testDir = "Data/testcases";
        if(mode.contentEquals("unzip")){
            Submission.unzip(from, to);
            System.out.println("Unzip script generated");
        }
        else{
            Backend backend = new Backend(from, to);
            Frontend frontend = new Frontend(backend);
            frontend.start(testDir);
        }
    }
}
