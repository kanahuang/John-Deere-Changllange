import javafx.util.Pair;
public class Combine {
    double speed;
    double width;
    Filed currentFiled;
    boolean working;
    int []location;



    public Combine(double speed,double width){
        this.speed = speed;
        this.width = width;
        this.working = false;
        location = new int[2];
    }
    public void setField (Filed field){
        this.currentFiled = field;
    }
    public void startHaverst(){
        this.working = true;
        currentFiled.addToFiled(this);
    }
    public void stopHaverest(){
        this.working = false;
    }
    public void haverest(){
        if (this.working){
            this.location = this.currentFiled.reduceSize(this);
        }
    }
}

