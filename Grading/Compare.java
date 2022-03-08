package Grading;

public class Compare {
    String output;
    String expected;
    Test test;
    public boolean pass;

    public Compare(String output, String expected, Test test){
        this.expected =expected;
        this.output = output;
        this.test = test;
    }

    public String merge(){
        String[] parts1 = output.split("\n");
        String[] parts2 = expected.split("\n");
        StringBuilder merged = new StringBuilder();
        // modify this if strings are not of the same size
        merged.append(String.format("%-100s", "Output:")).append("Expected:\n");
        int len = Math.max(parts1.length, parts2.length);
        for (int i=0; i < len; i++) {
            String part1 = String.format("%-100s", "");
            String part2 = "";
            if(i<parts1.length) {
                part1 = String.format("%-100s", parts1[i]);
            }
            if(i<parts2.length) {
                part2 = parts2[i];
            }
            merged.append(part1).append(part2).append("\n");
        }
        return merged.toString();
    }
}
