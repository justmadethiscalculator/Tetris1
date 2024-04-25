// Area: where the tetris blocks fall 
//to draw on JPanel we need to extends JPanel override its paintComponent methods
package tetris;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GameArea extends JPanel
{
    private int Row;
    private int Column;
    private int PieceSize;
    private Color[][] bg;

//Call and measure the area of the game
    public GameArea(JPanel placeholder, int c)
    {
        placeholder.setVisible(false); //Make the shape appear above the area
        setBounds( placeholder.getBounds() );
        setBackground( placeholder.getBackground() );
        setBorder( placeholder.getBorder() );
        
        Column = c;
        PieceSize = getBounds().width/ Column;
        Row = getBounds().height/ PieceSize;
        
        bg = new Color[Row][Column];
    }
      
    public void createPiece()
    {
        piece = new TetrisPieces( new int[][]{ {1, 0}, {1, 0}, {1, 1} }, Color.orange );
        piece.spawn(Column);
    }
    
//Check when you game over (pieces go out of bound)    
    public boolean OutOfBound()
    {
        if(piece.getY() < 0)
        {
            piece = null;
            return true;
        }
        
        return false;
    }

 //Pieces move by being painted to a different location everytime    
    public boolean Down()
    {
        if (checkBottom() == false)
        {
            return false;
        }
        
        piece.Down();
        repaint();
        
        return true;
    }
    
//Check if the piece reach the bottom
    private boolean checkBottom()
    {
        if( piece.reachBottom() == Row)
        {
            return false;
        }
        int[][] shape = piece.getShape();
        int w = piece.getWidth();
        int h = piece.getHeight();
        
        for (int col = 0; col < w; col++)
        {
            for (int row = h - 1; row >= 0; row--)
            {
                if(shape[row][col] != 0)
                {
                    //Make sure the piece touch and stop each other
                    int x = col + piece.getX();
                    int y = row + piece.getY() + 1;
                    if(y < 0) break;
                    if (bg[y][x] != null) return false;
                    break;
                }
            }
        }
        
        return true;
    }
    
//Check if the piece touch the sides    
    private boolean checkLeft()
    {
    int[][] shape = piece.getShape();
    int w = piece.getWidth();
    int h = piece.getHeight();

    for (int row = 0; row < h; row++)
    {
        for (int col = 0; col < w; col++)
        {
            if (shape[row][col] != 0)
            {
                int x = col + piece.getX() - 1;  // Calculate possible new position of pieces
                int y = row + piece.getY();
                if (x < 0 || (y >= 0 && bg[y][x] != null)) // Check if pieces are colliding into walls
                { 
                    return false;
                }
                break;
            }
        }
    }
    
    return true;
}


    private boolean checkRight()
    {
        int[][] shape = piece.getShape();
        int w = piece.getWidth();
        int h = piece.getHeight();

        for (int row = 0; row < h; row++)
        {
            for (int col = w - 1; col >= 0; col--)
            {
                if (shape[row][col] != 0)
                {
                    int x = col + piece.getX() + 1;  // Calculate possible new position of pieces
                    int y = row + piece.getY();
                    if (x >= Column || (y >= 0 && bg[y][x] != null)) // Check if pieces are colliding into walls
                    {  
                       return false;
                    }
                }
            }
        }
    
    return true;
    }
//BUG: Possible to clip a piece through right wall, creating errors

//When a line is filled, clear the line  
    public int FullLineClear() {
        boolean lineFilled;
        int LineClearTracker = 0;
    
        for (int r = Row - 1; r >= 0; r--) {
            lineFilled = true;
            for (int c = 0; c < Column; c++) {
                if (bg[r][c] == null) {
                    lineFilled = false;
                    break;
                }
            }
        
            if (lineFilled) {
                LineClearTracker++;
                LineClear(r);
                GravDown(r);
                r++; // Ensures the loop re-checks this row index since all above have shifted down
            }
        }
        System.out.println(LineClearTracker);
        return LineClearTracker;
    }
    
    public void LineClear(int r)
    {
        for(int i = 0; i < Column; i++)
        {
            bg[r][i] = null;
        }
    }

//Make sure pieces fall down after clear    
    private void GravDown(int r)
    {
        for (int row = r; row > 0; row--)
        {
            for (int col = 0; col < Column; col++)
            {
                bg[row][col] = bg[row - 1][col]; //Dropped pieces check pieces above them
            }
        }
    }

//All the controls    
    public void goLeft()
    {
        if (piece == null || !checkLeft()) return;
        piece.Left();
        repaint();
    }

    public void goRight()
    {
        if (piece == null || !checkRight()) return;
        piece.Right();
        repaint();
    }
    
    public void HardDrop()
    {
        while( checkBottom() )
        {
            piece.Down();
        }
        PiecetoBG();
        FullLineClear();
        createPiece();
        repaint();
    }

    public void Rotate()
    {
        piece.Rotate();
        repaint();
    }
    
    public void CounterRotate()
    {
        piece.CounterRotate();
        repaint();
    }
    
    public void SoftDrop()
    {
        if( piece == null || !checkBottom() )
        {
            piece.Down();   
        }
        repaint(); 
    }
//repaint() makes actions perform faster  
    
//Paint used piece onbackground to stack new pieces     
    public void PiecetoBG()
    {
        int[][] shape = piece.getShape();
        int h = piece.getHeight();
        int w = piece.getWidth();
        
        int xPos = piece.getX();
        int yPos = piece.getY();
        
        Color color = piece.getColor();
        
        for (int r = 0; r < h; r++)
        {
            for (int c = 0; c < w; c++)
            {
                if(shape[r][c] == 1)
                {
                    bg[r + yPos][c + xPos] = color;
                }
            }
        }
        
        piece = null; //Disable further movement from pieces
    }
    
//Mechanism to draw pieces
    private TetrisPieces piece;
    
    private void drawPieces(Graphics g)
    {
        int h = piece.getHeight();
        int w = piece.getWidth();
        Color c = piece.getColor();
        int[][] s = piece.getShape();
        
        for(int row = 0; row < h; row++)
        {
            for (int col = 0; col < w; col++)
            {
                if(s[row][col] == 1)
                {
                    int x = (piece.getX() + col) * PieceSize;
                    int y = (piece.getY() + row) * PieceSize;
                    
                    paintGrid(g, c, x, y);
                }
            }
        }
    }

//Paint all pieces that "bg" array stored    
    private void paintBG(Graphics g)
    {
        Color color;
        
        for(int r = 0; r < Row; r++)
        {
            for (int c = 0; c < Column; c++)
            {
                color = bg[r][c];
                
                if(color != null)
                {
                    int x = c * PieceSize;
                    int y = r * PieceSize;
                    
                    paintGrid(g, color, x, y);
                }
            }
        }
    }
    
    private void paintGrid(Graphics g, Color color, int x, int y)
    {
        g.setColor(color);
        g.fillRect(x, y, PieceSize, PieceSize);
        g.setColor(Color.black);
        g.drawRect(x, y, PieceSize, PieceSize);
    }

    @Override
    protected void paintComponent(Graphics g)  //Graphics g draws our Game Area
    {
        super.paintComponent(g); //super: call paintComponent of JPanel
        paintBG(g);
        drawPieces(g);
    }
}