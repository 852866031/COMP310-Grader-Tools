package Grading;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Frontend {
    private final Backend backend;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Frontend(Backend backend) {
        this.backend = backend;
    }

    public static String getInput(){
        String result = null;
        try{
            result = reader.readLine();
        }catch (Exception e){
            System.out.println("Cannot read from command line");
            System.exit(1);
        }
        return result;
    }

    public void evaluate(Compare compare){
        System.out.println(compare.merge());
        System.out.println("Do you consider it pass? [AnyKey/n]");
        compare.pass = !getInput().contentEquals("n");
    }

    public void start(String testDir){
        System.out.println("Welcome to COMP310/ECSE427 Grading Tool");
        System.out.println(backend.countSubmissions()+" submissions found");
        System.out.println("Do you want to proceed? [AnyKey/n]");
        if(getInput().contentEquals("n")) System.exit(0);
        backend.init();
        System.out.println("Loading testcases from "+testDir);
        backend.loadTests(testDir);
        System.out.println("Start grading? [AnyKey/n]");
        if(getInput().contentEquals("n")) System.exit(0);
        while(backend.hasNext()){
            System.out.println();
            backend.printCurrentInfo();
            ArrayList<Compare> result = backend.runTests();
            for(Compare compare : result){
                evaluate(compare);
            }
            backend.next();
        }
    }
}
