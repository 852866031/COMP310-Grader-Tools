package Grading;

public class Main {
    public static void main(String[] args) {
        Backend backend = new Backend("Data/zips", "Data/submissions");
        Frontend frontend = new Frontend(backend);
        frontend.start("Data/testcases");
    }
}
