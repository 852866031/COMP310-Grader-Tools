package GradeBoard;

public class Grade {
    double score=0.0;
    double fullScore=0.0;
    double ratio=0.0;
    double fullRatio=0.0;

    public Grade(double score, double fullScore, double fullRatio){
        this.fullScore = fullScore;
        this.score = score;
        this.fullRatio =fullRatio;
        this.ratio = score/fullScore*fullRatio;
    }

    @Override
    public String toString() {
        return String.format("%.2f", score)+"/"+String.format("%.2f", fullScore)+" ("+String.format("%.2f", ratio)+")";
    }
}
