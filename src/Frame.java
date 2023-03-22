import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Frame extends JFrame implements ActionListener  {

    private JButton beginBtn;
    
    private boolean isReady;

    private SimpleAudioPlayer audio;

    private boolean playerXTurn;

    private boolean playerOTurn;

    private String[][] currentBoard = new String[3][3];
    private Random random = new Random();
    final private JButton [] gridButtons = new JButton[9];

    private JLabel turnLabel;

    private Color color1 = new Color(128, 203, 107);
    private Color color2 = new Color(58, 114, 48);

    public Frame(SimpleAudioPlayer audioPlayer) {

        this.audio = audioPlayer;
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
        panel.setBackground(color2);
        var border = BorderFactory.createLineBorder(color1,10);
        panel.setBorder(border);

        var settingsBtn = new JButton("Settings");
        settingsBtn.setBounds(1920/2 -100,1080/2 - 200,200,50);
        settingsBtn.setBackground(color1);
        settingsBtn.setForeground(color2
        );
        var playBtn = new JButton("Play");
        playBtn.setBounds(1920/2 -100,1080/2 -100,200,50);
        playBtn.setBackground(color1);
        playBtn.setForeground(color2);

        panel.add(settingsBtn );
        panel.add(playBtn) ;

        settingsBtn.addActionListener(e -> {
            this.getContentPane().removeAll(); // Remove the Settings and Play buttons
            this.getContentPane().add(getSettingsPanel(), null); // Add the Settings page
            this.validate();
            cueAudio("click");
        });

        playBtn.addActionListener(e -> {
            this.getContentPane().removeAll();
            this.getContentPane().add(getGamePanel(), null);
            this.validate();
            cueAudio("click");
        });

        isReady = false;
        playerOTurn = false;
        playerXTurn = false;
        return panel;
    }

    private JPanel getSettingsPanel() {
        // Create the Settings page
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(color2);
        var border = BorderFactory.createLineBorder(color1,10);
        panel.setBorder(border);
        panel.setBounds(0,0,1920,1080);

        var backBtn = new JButton("Back");
        backBtn.setBounds(100,50,200,50);
        backBtn.setBackground(color1);
        backBtn.setForeground(color2);
        panel.add(backBtn);

        backBtn.addActionListener(e -> {
            this.getContentPane().removeAll(); // Remove the Settings page
            this.getContentPane().add(getTitleScreen(),null); // Add the Title Screen
            this.validate();
            cueAudio("click");
        });

        return panel;
    }

    private JPanel getGamePanel() {

        var gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setBounds(0,0,1920,1080);

        var border = BorderFactory.createLineBorder(color1,10);
        gamePanel.setBackground(color2);
        gamePanel.setBorder(border);

        var turnPanel = new JPanel();
        turnPanel.setBounds(1270,448,500,500);
        turnPanel.setBackground(new Color(19, 68, 12));
        turnPanel.setLayout(new BorderLayout());

        turnPanel.setBorder(border);

        var topPanel = new JPanel();
        topPanel.setBackground(color1);
        topPanel.setPreferredSize(new Dimension(100,100));

        var label1 = new JLabel("TURN");
        label1.setFont(new Font("Arial",Font.BOLD, 80));
        label1.setForeground(new Color(19, 68, 12));
        topPanel.add(label1);

        var bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(null);

        this.turnLabel = new JLabel();
        turnLabel.setFont(new Font("Arial", Font.BOLD,280));

        turnLabel.setForeground(color1);
        turnLabel.setBounds(55,0,400,400);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER); // center align the text
        turnLabel.setVerticalTextPosition(SwingConstants.CENTER);


        updateTurnPanel();
        bottomPanel.add(turnLabel);

        turnPanel.add(topPanel,BorderLayout.NORTH);
        turnPanel.add(bottomPanel, BorderLayout.CENTER);
        gamePanel.add(turnPanel);


        var backBtn = new JButton("Back");
        backBtn.setBounds(100,50,200,50);
        backBtn.setBackground(color1);
        backBtn.setForeground(new Color(19, 68, 12));
        backBtn.addActionListener(e -> {
            this.getContentPane().removeAll(); // Remove the Settings page
            this.getContentPane().add(getTitleScreen(),null); // Add the Title Screen
            this.validate();
            cueAudio("click");
        });
        gamePanel.add(backBtn);

        this.beginBtn = new JButton("Begin Game");
        beginBtn.setBounds(1500,200,200,50);
        beginBtn.addActionListener(this);
        beginBtn.setBackground(color1);
        beginBtn.setForeground(new Color(19, 68, 12));
        gamePanel.add(beginBtn);

        JPanel gamePadding = new JPanel();
        gamePadding.setLayout(new GridLayout(3,3));
        gamePadding.setBounds(200, 150, 900, 800); // set bounds for gamePadding


        for (int i = 0; i < gridButtons.length; i++){
            gridButtons[i] = new JButton();
            gridButtons[i].addActionListener(this);
            gridButtons[i].setFocusable(false);
            gridButtons[i].setBackground(new Color(19, 68, 12));
            gridButtons[i].setBorder(border);

            gridButtons[i].setFont(new Font("Arial",Font.BOLD, 160));
            gamePadding.add(gridButtons[i]);

        }

        gamePadding.setBorder(border);
        gamePanel.add(gamePadding);
        return gamePanel;
    }

    public void updateTurnPanel(){
        if (!playerOTurn && !playerXTurn){
            turnLabel.setText("/");
        }
        else if (playerXTurn) {
            turnLabel.setText("X");
        }
        else if (playerOTurn) {
            turnLabel.setText("O");
        }
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
                cueAudio("click");
                isReady = true;
                initialTurn();
                updateTurnPanel();
                beginBtn.setText("reset");
            } else {
                reset();
                cueAudio("click");
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
                            cueAudio("click");
                            gridButtons[i].setForeground(color1);

                            if(checkWin("X")){
                                cueAudio("success");
                                JOptionPane.showMessageDialog(this, "you won","win message",JOptionPane.PLAIN_MESSAGE);
                            }
                            else {
                                updateTurnPanel();
                            }
                        }

                        else {
                            playerOTurn = false;
                            playerXTurn = true;
                            gridButtons[i].setText("O");
                            cueAudio("click");
                            gridButtons[i].setForeground(color1);

                            if(checkWin("O")){
                                cueAudio("success");
                                JOptionPane.showMessageDialog(this, "you won","win message",JOptionPane.PLAIN_MESSAGE);
                            }
                            else {
                                updateTurnPanel();
                            }

                        }
                    }
                }
                else{
                    cueAudio("error");
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

    public void cueAudio (String str){
        try {
            audio.setFileName(str);
            audio.play();
        }
        catch (Exception e) {
            System.out.println("audio is unable to play");
        }
    }

}
