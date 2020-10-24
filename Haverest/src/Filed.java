import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
public class Filed {
    double width;
    double length;
    double current_size;
    double blockWith;
    double blockLength;
    ArrayList <Combine> combinelist= new ArrayList<Combine>();
    double[][] filedMap;
    int [][] displayMap;
    int [] location;
    double fullSize;
    public Filed( double width,double length ){
        this.width = width;
        this.length = length;
        this.current_size = width * length;
        this.filedMap = new double[(int)Math.ceil(width/10)][(int)Math.ceil(length/10)];
        this.displayMap = new int[(int)Math.ceil(width/10)][(int)Math.ceil(length/10)];
        blockLength = 10;
        blockWith = 10;
        location = new int[2];
        reSetFile();
        fullSize = width * length;
    }
    private void reSetFile(){
        for (int i = 0; i < filedMap.length; i++){
            for (int j = 0;j < filedMap[i].length; j++){
                filedMap[i][j] = 100;
                displayMap[i][j] = 1;
            }
        }
    }
    public boolean addToFiled (Combine combine){

        if (combinelist.size() <= 4) {
            combinelist.add(combine);
            //System.out.println("Size"+ combinelist.size());
            if (combinelist.size() == 1){
                combine.location[0] = 0;
                combine.location[1] = 0;
            }
            else if (combinelist.size() == 2){
                combine.location[0] = displayMap.length-1;
                combine.location[1] = displayMap[0].length -1;
            }
            else if(combinelist.size() == 3){
                combine.location[0] = 0;
                combine.location[1] = (displayMap[0].length -1)/2 +1;
            }else if(combinelist.size() == 4){
                combine.location[0] = (displayMap.length-1);
                combine.location[1] = (displayMap[0].length -1)/2 ;
            }

            return true;
        }
        return false;

    }
    public double getRemainingSize(){
        return fullSize - current_size;
    }
    public void printDisplayMap(){
        for (int i = 0; i < displayMap.length; i++){
            for (int j  = 0; j < displayMap[1].length; j++){
                System.out.print(displayMap[i][j]);
                System.out.print(' ');

            }
            System.out.println();
        }
    }
    public void printFieldyMap(){
        for (int i = 0; i < filedMap.length; i++){
            for (int j  = 0; j < filedMap[1].length; j++){
                System.out.print(filedMap[i][j]);
                System.out.print(' ');

            }
            System.out.println();
        }
    }
    public int[] getAvaLocation(int[] location){
        if (location[0]+1 < displayMap.length && displayMap[location[0]+1][location[1]] == 1){
            location[0] = location[0]+1;
            return location;
        }else if (location[0]-1 >= 0 && displayMap[location[0]-1][location[1]] == 1){
            location[0] = location[0]-1;
            return location;
        }else if (location[1]+1 < displayMap[0].length && displayMap[location[0]][location[1]+1] == 1){
            location[1] = location[1]+1;
            return location;
        }
        else if (location[1]-1 >= 0 && displayMap[location[0]][location[1]-1] == 1){
            location[1] = location[1]-1;
            return location;
        }
        return location;
    }
    public boolean searchMap(double size,int[] location){
        //System.out.println(location[0]+" "+location[1]);
        if (location[0] < 0 || location[1] < 0 || location[0] >= displayMap.length  || location[1] >= displayMap[0].length){
            return false;
        }
        if (displayMap[location[0]][location[1]] == 0){
            return false;
        }
            double remain = filedMap[location[0]][location[1]] - size;
            if (remain == 0) {

                filedMap[location[0]][location[1]] = 0;
                displayMap[location[0]][location[1]] = 0;
                location = getAvaLocation(location);
                this.location = location;
                return true;
            }
            else if (remain > 0){
                this.location = location;
                filedMap[location[0]][location[1]] = remain;
                return true;
            }
            else {
                filedMap[location[0]][location[1]] = 0;
                displayMap[location[0]][location[1]] = 0;
                if (searchMap(-remain, new int[]{location[0]+1,location[1]})){
                    location[0] =location[0] + 1;
                    location[1] =location[1];
                    return true;
                }
                else if (searchMap(-remain, new int[]{location[0]-1,location[1]})){
                    location[0] =location[0] -1;
                    location[1] =location[1];
                    return true;
                }
                else if (searchMap(-remain, new int[]{location[0],location[1]+1})){
                    location[0] =location[0];
                    location[1] =location[1] + 1;
                    return true;
                }
                else if (searchMap(-remain, new int[]{location[0],location[1]-1})){
                    location[0] =location[0];
                    location[1] =location[1] -1;
                    return true;
                }
                else {return false;}

            }
    }
    public void removeFromField (Combine combine){
        double size = combine.speed * combine.width;

        combinelist.remove(combine);
    }
    public double getCurrent_size(){
        return this.current_size;
    }
    public int[] reduceSize(Combine combine){
        double size = combine.speed* combine.width;
        current_size = current_size - size;
        if (current_size < 0) current_size = 0;//avoid negative number
        searchMap(size,combine.location);
        return location;
    }
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(840, 560);
        Filed filed = new Filed(100, 120);
        Combine combine1 = new Combine(10, 1);
        Combine combine2 = new Combine(10, 1);
        Combine combine3 = new Combine(10, 1);
        Combine combine4 = new Combine(10, 1);
        combine1.setField(filed);
        combine2.setField(filed);
        combine3.setField(filed);
        combine4.setField(filed);
        combine1.startHaverst();
        combine2.startHaverst();
        combine3.startHaverst();
        combine4.startHaverst();
        JLabel label1 = new JLabel("Not Haverst Size" + filed.getCurrent_size()+"\nRemaining Size: " + filed.getRemainingSize());
        Dimension size = label1.getPreferredSize();
        label1.setBounds(600, 300, size.width, size.height);
        JPanel origPanel = new Gui(filed.displayMap);
        window.add(origPanel);
        origPanel.add(label1);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        while (filed.getCurrent_size() > 0) {
            combine1.haverest();
            combine2.haverest();
            combine3.haverest();
            combine4.haverest();
            //System.out.println("Current filed size" + filed.getCurrent_size());
            label1.setText("<html>Current Size" + filed.getCurrent_size()+"<br/>Remaining Size: " + filed.getRemainingSize()+"</html>");
            window.remove(origPanel);
            origPanel = new Gui(filed.displayMap);
            origPanel.add(label1);
            // Revalidate frame to cause it to layout the new panel correctly.
            window.add(origPanel);
            window.setVisible(true);
            //window.getContentPane().add(origPanel), BorderLayout.CENTER);

            //filed.printDisplayMap();
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            }
            catch (Exception e){

            }
        }
        combine1.stopHaverest();
        combine2.stopHaverest();
        combine3.stopHaverest();
        combine4.stopHaverest();
    }
}
