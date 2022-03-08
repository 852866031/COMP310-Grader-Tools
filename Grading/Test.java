package Grading;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Test {
    public final String name;
    public final String testPath;
    public final String resultPath;

    public Test(String path, String name) {
        this.name = name;
        this.testPath = path+"/"+name;
        this.resultPath = path+"/"+"result"+"/"+name+"_result";
    }

    @Override
    public String toString() {
        return "\ntestPath='" + testPath + '\n' + "resultPath='" + resultPath;
    }
}
