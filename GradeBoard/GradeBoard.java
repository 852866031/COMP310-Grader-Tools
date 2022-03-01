package GradeBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GradeBoard {
    private final HashMap<String, Grade> grades = new HashMap<>();
    private Double overall =0.0;

    public void put(String name, Grade grade){
        if(this.grades.get(name)==null) {
            this.overall+=grade.ratio;
        }
        else{
            this.overall-=this.grades.get(name).ratio;
            this.overall+=grade.ratio;
        }
        this.grades.put(name, grade);
    }

    public double getOverall(){
        return overall;
    }

    public String getGrades(ExportFormat format){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> keys = new ArrayList<>(grades.keySet());
        Collections.sort(keys);
        for(String key : keys){
            if(format == ExportFormat.BOTH){
                sb.append(grades.get(key));
                sb.append(",");
            }
            else if(format==ExportFormat.SCORE){
                sb.append(String.format("%.2f", grades.get(key).score)).append("/");
                sb.append(String.format("%.2f", grades.get(key).fullScore)).append(",");
            }
            else{
                sb.append(String.format("%.2f", grades.get(key).ratio)).append("/");
                sb.append(String.format("%.2f", grades.get(key).fullRatio)).append(",");
            }
        }
        if(format!=ExportFormat.SCORE) sb.append(String.format("%.2f", this.overall));
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
