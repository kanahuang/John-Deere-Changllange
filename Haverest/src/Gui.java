import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

// assume that the drawing area is 150 by 150
public class Gui extends JPanel {
    int[][] map;
    public Gui(int [][] map){
        this.map = map;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (int i = 0; i < map.length; i++){
            for( int j = 0; j < map[0].length;j++){
                if (map[i][j] == 1){
                    g.setColor(new Color(153,102,0));
                }
                else {
                    g.setColor(Color.GREEN);
                }
                g.fillRect(i*30,j*30,30,30);

            }
        }
    }
    public static void main(String[] a) {
        int [][]map = new int[10][5];
        for (int i = 0; i < map.length; i++){
            for( int j = 0; j < map[0].length;j++){
                map[i][j] = 1;
            }
        }


                /*grid.fillCell(0, 0);
                grid.fillCell(1, 0);
                grid.fillCell(0, 49);
                grid.fillCell(79, 49);
                grid.fillCell(39, 24);*/
    }
}