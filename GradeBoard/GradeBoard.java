package GradeBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GradeBoard {
    private final HashMap<String, Double> grades = new HashMap<>();
    private Double overall =0.0;

    public void put(String name, Double value){
        if(this.grades.get(name)==null) {
            this.overall+=value;
        }
        else{
            this.overall-=this.grades.get(name);
            this.overall+=value;
        }
        this.grades.put(name, value);
    }

    public double get(String name){
        return grades.get(name);
    }

    public double getOverall(){
        return overall;
    }

    public String getGrades(){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> keys = new ArrayList<>(grades.keySet());
        Collections.sort(keys);
        for(String key : keys){
            sb.append(grades.get(key));
            sb.append(",");
        }
        sb.append(this.overall);
        return sb.toString();
    }

    public String getEvaluations(){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> keys = new ArrayList<>(grades.keySet());
        Collections.sort(keys);
        for(String key : keys){
            sb.append(key);
            sb.append(",");
        }
        sb.append("Overall");
        return sb.toString();
    }
}
