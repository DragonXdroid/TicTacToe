import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Frame extends JFrame implements ActionListener  {

    private JButton beginBtn;
    
    private boolean isReady;

    private boolean playerXTurn;

    private boolean playerOTurn;

    private String[][] currentBoard = new String[3][3];
    private Random random = new Random();
    final private JButton [] gridButtons = new JButton[9];

    private JPanel turnPanel;

    public Frame() {
        // Set up the title screen
        this.setTitle("Title Screen");
        this.setSize(1920 , 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().add(getTitleScreen(), null);
        this.validate();
        this.setVisible(true);

    }

    private JPanel getTitleScreen(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0,0,1920,1080);

        var settingsBtn = new JButton("Settings");
        settingsBtn.setBounds(1920/2 -100,1080/2 - 200,200,50);
        var playBtn = new JButton("Play");
        playBtn.setBounds(1920/2 -100,1080/2 -100,200,50);

        panel.add(settingsBtn );
        panel.add(playBtn) ;

        settingsBtn.addActionListener(e -> {
            this.getContentPane().removeAll(); // Remove the Settings and Play buttons
            this.getContentPane().add(getSettingsPanel(), null); // Add the Settings page
            this.validate();
        });

        playBtn.addActionListener(e -> {
            this.getContentPane().removeAll(); // Remove the Settings and Play buttons
            this.getContentPane().add(getGamePanel(), null); // Add the Settings page
            this.validate();
        });

        isReady = false;
        return panel;
    }

    private JPanel getSettingsPanel() {
        // Create the Settings page
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0,0,1920,1080);


        // Create the Back button and add it to the Settings page
        var backBtn = new JButton("Back");
        backBtn.setBounds(100,50,200,50);
        panel.add(backBtn);

        backBtn.addActionListener(e -> {
            this.getContentPane().removeAll(); // Remove the Settings page
            this.getContentPane().add(getTitleScreen(),null); // Add the Title Screen
            this.validate();
        });

        return panel;
    }

    private JPanel getGamePanel() {

        var gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setBounds(0,0,1920,1080);

        this.turnPanel = new JPanel();
        turnPanel.setBounds(1270,448,500,500);
        turnPanel.setBackground(new Color(19, 68, 12));
        turnPanel.setLayout(new BorderLayout());

        var topPanel = new JPanel();
        topPanel.setBackground(new Color(128, 203, 107));
        topPanel.setPreferredSize(new Dimension(100,100));

        var label = new JLabel("TURN");
        label.setFont(new Font("Arial",Font.BOLD, 80));
        label.setForeground(new Color(19, 68, 12));
        topPanel.add(label);



        turnPanel.add(topPanel,BorderLayout.NORTH);
        gamePanel.add(turnPanel);



        var backBtn = new JButton("Back");
        backBtn.setBounds(100,50,200,50);
        backBtn.addActionListener(e -> {
            this.getContentPane().removeAll(); // Remove the Settings page
            this.getContentPane().add(getTitleScreen(),null); // Add the Title Screen
            this.validate();
        });
        gamePanel.add(backBtn);

        this.beginBtn = new JButton("Begin Game");
        beginBtn.setBounds(1500,200,200,50);
        beginBtn.addActionListener(this);
        gamePanel.add(beginBtn);

        JPanel gamePadding = new JPanel();
        gamePadding.setLayout(new GridLayout(3,3));
        gamePadding.setBounds(200, 150, 900, 800); // set bounds for gamePadding


        for (int i = 0; i < gridButtons.length; i++){
            gridButtons[i] = new JButton();
            gridButtons[i].addActionListener(this);
            gridButtons[i].setFocusable(false);
            gridButtons[i].setFont(new Font("Arial",Font.BOLD, 40));
            gamePadding.add(gridButtons[i]);

        }
       
        gamePanel.add(gamePadding);
        return gamePanel;
    }

    public void updateTurnPanel(){

    }

    public void initialTurn(){

        if (random.nextInt(2) == 1){
             playerOTurn = true;
             playerXTurn = false;
        }
        else {
            playerXTurn = true;
            playerOTurn = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.beginBtn ) {
            if (!isReady){
                isReady = true;
                initialTurn();
                beginBtn.setText("reset");
            } else {
                reset();
            }
        }

        for (int i = 0; i < gridButtons.length; i++){
            if (e.getSource() == gridButtons[i]){
                if (isReady){
                    if (gridButtons[i].getText() ==""){
                        if (playerXTurn){
                            playerXTurn = false;
                            playerOTurn = true;
                            gridButtons[i].setText("X");
                            gridButtons[i].setForeground(new Color(44, 130, 197));

                            if(checkWin("X")){

                            }
                        }

                        else {
                            playerOTurn = false;
                            playerXTurn = true;
                            gridButtons[i].setText("O");
                            gridButtons[i].setForeground(new Color(238, 27, 27));

                            if(checkWin("O")){

                            }

                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Press begin to start the game","Error Message",JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    public void reset(){
        isReady = false;
        for (int i = 0; i < gridButtons.length; i++){
            gridButtons[i].setText("");
        }
        beginBtn.setText("begin");
    }

    public boolean checkWin(String player) {

        int z = 0;
        for (int x = 0;x < currentBoard.length; x++){
            for (int y = 0; y < currentBoard[x].length;y++ ){
                if(!gridButtons[z].equals("")){
                    currentBoard[x][y] = gridButtons[z].getText();
                    z++;
                }
            }
        }

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (currentBoard[i][0].equals(player) && currentBoard[i][1].equals(player) && currentBoard[i][2].equals(player)) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (currentBoard[0][j].equals(player) && currentBoard[1][j].equals(player) && currentBoard[2][j].equals(player)) {
                return true;
            }
        }

        // Check diagonals
        if (currentBoard[0][0].equals(player) && currentBoard[1][1].equals(player) && currentBoard[2][2].equals(player)) {
            return true;
        }
        if (currentBoard[0][2].equals(player) && currentBoard[1][1].equals(player) && currentBoard[2][0].equals(player)) {
            return true;
        }

        // No win found
        return false;
    }

}
