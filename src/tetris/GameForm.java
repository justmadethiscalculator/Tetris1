//Change properties of Area in Design
package tetris;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class GameForm extends JFrame
{
    private GameArea area;
    
    public GameForm() {
        initComponents();
        
        area =  new GameArea(Placeholder, 10); 
        add( area ); 
        
        initControls();
        
        start();
    }

//Key control
    private void initControls()
    {
        InputMap im = getRootPane().getInputMap();
        ActionMap am = getRootPane().getActionMap();
        
        im.put(KeyStroke.getKeyStroke("LEFT"), "left" );
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right" );
        im.put(KeyStroke.getKeyStroke("A"), "rotateL" );
        im.put(KeyStroke.getKeyStroke("D"), "rotateR" );
        im.put(KeyStroke.getKeyStroke("UP"), "hold" );
        im.put(KeyStroke.getKeyStroke("DOWN"), "down" );
        im.put(KeyStroke.getKeyStroke("SPACE"), "space" );
        
        am.put("left", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                area.goLeft();
            }
        });
        am.put("right", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                area.goRight();
            }
        });
        am.put("rotateL", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                area.CounterRotate();
            }
        });
        am.put("rotateR", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                area.Rotate();
            }
        });        
        am.put("down", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                area.SoftDrop();
            }
        });
        am.put("space", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                area.HardDrop();
            }
        });
    }
    
    public void start()
    {
        new TetrisGameThread(area, this).start();
    }
    
    public void updateLine(int line) {
        SwingUtilities.invokeLater(() -> {
            LineUI.setText("Lines: " + line);
        });
    }

    public void updateLevel(int level)
    {
        LevelUI.setText("Level: " + level);
    }
    
    public void updateScore(int score)
    {
        ScoreUI.setText("Score: " + score);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooser1 = new javax.swing.JColorChooser();
        Placeholder = new javax.swing.JPanel();
        ScoreUI = new javax.swing.JLabel();
        LevelUI = new javax.swing.JLabel();
        LineUI = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Placeholder.setBackground(new java.awt.Color(51, 51, 51));
        Placeholder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 204), 5));

        javax.swing.GroupLayout PlaceholderLayout = new javax.swing.GroupLayout(Placeholder);
        Placeholder.setLayout(PlaceholderLayout);
        PlaceholderLayout.setHorizontalGroup(
            PlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        PlaceholderLayout.setVerticalGroup(
            PlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );

        ScoreUI.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        ScoreUI.setText("Score: 0");

        LevelUI.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        LevelUI.setText("Level: 1");

        LineUI.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        LineUI.setText("Lines: 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ScoreUI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LevelUI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LineUI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(125, 125, 125)
                .addComponent(Placeholder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Placeholder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LineUI)
                .addGap(28, 28, 28)
                .addComponent(LevelUI)
                .addGap(35, 35, 35)
                .addComponent(ScoreUI)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameForm().setVisible(true);
            }
        });
    }

//invokeLater: Ensure GUI of a program is being managed in a separate thread
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LevelUI;
    private javax.swing.JLabel LineUI;
    private javax.swing.JPanel Placeholder;
    private javax.swing.JLabel ScoreUI;
    private javax.swing.JColorChooser jColorChooser1;
    // End of variables declaration//GEN-END:variables
}
