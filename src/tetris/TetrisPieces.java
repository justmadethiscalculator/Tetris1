package tetris;

import java.awt.Color;

public class TetrisPieces
{
    private int[][] shape;
    private Color color;
    private int x, y;
    private int[][][] shapes;
    private int PieceRotation;
    
    public TetrisPieces(int[][] shape, Color color)
    {
        this.shape = shape;
        this.color = color;
        
        initShapes();
    }

//Create 4 rotation of a piece and store them to use    
    private void initShapes()
    {
        shapes = new int[4][][];
        
        for (int i = 0; i < 4; i++)
        {
            int r = shape[0].length;
            int c =shape.length;
            
            shapes[i] = new int[r][c];
            
            for (int y = 0; y < r; y++)
            {
                for (int x = 0; x < c; x++)
                {
                    shapes[i][y][x] = shape[c - x - 1][y]; //Rotate 90 degrees clockwise
                }
            }
            
            shape = shapes[i];
        }
    }
    
    public void spawn(int gridWidth) //Location to spawn Tetris pieces
    {
        PieceRotation = 0;//Piece spawn with no rotation
        shape = shapes[PieceRotation];
        
        y = -getHeight();
        x = (gridWidth - getWidth()) / 2;
    }
    
    public int[][] getShape(){ return shape; }
    
    public Color getColor(){ return color; }
    
    public int getHeight(){ return shape.length; }
    
    public int getWidth(){ return shape[0].length; }
    
    public int getX(){ return x; }
    
    public int getY(){ return y; }
    
    public void Down(){ y++; }
    
    public void Left(){ x--; }
    
    public void Right(){ x++; }
    
    public void Rotate()
    {
        PieceRotation++;
        if(PieceRotation > 3) PieceRotation = 0; //Reset to 0 if index exceeds 3
        shape = shapes[PieceRotation];
    }
    
    public void CounterRotate()
    {
        PieceRotation--;
        if (PieceRotation < 0) PieceRotation = 3; //Set to 3 if index goes negative
        shape = shapes[PieceRotation];
}
    
    public int reachBottom(){ return y + getHeight(); }
    
    public int reachLeftSide(){ return x; } //can be replaced by getX()
    
    public int reachRightSide(){ return x + getWidth(); }    
}