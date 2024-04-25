//Thread: To run a code periodically separately from a different constant code
//Purpose: To drop tetris pieces
package tetris;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TetrisGameThread extends Thread
{
    private GameArea area;
    private  GameForm form;
    private int line;
    private int level = 1;
    private int linePerLevel = 5;
        
    public TetrisGameThread(GameArea area, GameForm form)
    {
        this.area = area;
        this.form = form;
    }
    
    @Override
    public void run()
    {
        while(true) //Infinite loop
        {
            area.createPiece();
            while ( area.Down() )
            {
                try {
                    Thread.sleep(500); //speed of piece
                }
                catch (InterruptedException ex) {
                    Logger.getLogger(TetrisGameThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(area.OutOfBound())
            {
                System.out.println("GG");
                break;
            }
            
            area.PiecetoBG();
            line += area.FullLineClear();
            form.updateLine(line);
        }
    }
}
