package GradeBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GradeBoard {
    public final HashMap<String, Grade> grades = new HashMap<>();
    public Double overall =0.0;

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
}
