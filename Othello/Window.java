import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Window implements ActionListener {

    private Othello othello;
    private OthelloAI ai;
    private int width, height;
    private JFrame frame;

    private JPanel contentPane;

    private JPanel gamePanel;
    private JPanel uiPanel;
    private JLabel currentTurn;
    private JLabel blackLabel;
    private JLabel whiteLabel;
    private JLabel winner;
    private JCheckBox validBox;
    private ImageIcon whitePiece;
    private ImageIcon blackPiece;

    private int tileSize = 60;

    public Window(Othello othello, int width, int height){
        this.othello = othello;
        ai = new OthelloAI(othello);
        this.width = width;
        this.height = height;

        //Setup icons
        Image img = null;
        try {
            img = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\WhitePiece.png"));
            Image newimg = img.getScaledInstance( 50, 50,  Image.SCALE_SMOOTH ) ;
            whitePiece = new ImageIcon(newimg);

            img = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\BlackPiece.png"));
            newimg = img.getScaledInstance( 50, 50,  Image.SCALE_SMOOTH ) ;
            blackPiece = new ImageIcon(newimg);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Setup frame
        frame = new JFrame("Othello");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        //frame.getContentPane().setBackground(new Color(0, 150, 250));
        frame.setIconImage(whitePiece.getImage());

        // Create content pane to house UI and game panels
        contentPane = new JPanel();
        // Ref: https://stackoverflow.com/questions/21167133/adding-vertical-spacing-to-north-component-in-borderlayout
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  // Adds padding between edges of window and panels
        contentPane.setLayout(new BorderLayout(5, 0));  // Adds padding between panels
        frame.setContentPane(contentPane);

        //Setup Panels
        gamePanel = new JPanel(new GridLayout(8, 8, 0, 0));
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border padding = new EmptyBorder(5, 5, 5, 5);
        Border compound = BorderFactory.createCompoundBorder(etchedBorder, padding);
        gamePanel.setBorder(compound);
        // gamePanel.setBounds(5,5, 490, 490);
        //gamePanel.setBackground(new Color(0, 150, 250));
        contentPane.add(gamePanel, BorderLayout.CENTER);

        uiPanel = new JPanel(null);
        uiPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        uiPanel.setPreferredSize(new Dimension(125, height));
        // uiPanel.setBounds(500, 5, 125, 490);
        //uiPanel.setBackground(new Color(0, 150, 250));
        //frame.add(uiPanel);
        contentPane.add(uiPanel, BorderLayout.LINE_END);

        //Setup board
        for(int row = 0; row < othello.getBoard().length; row++){
            for(int col = 0; col < othello.getBoard()[0].length; col++){
                JButton button = new JButton();
                // button.setBounds((col * tileSize) + 5, (row * tileSize) + 5, tileSize, tileSize);
                button.setPreferredSize(new Dimension(tileSize, tileSize));
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEtchedBorder(0));
                button.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent me) {
                        button.setBackground(Color.blue); // change the color to green when mouse over a button
                    }
                    public void mouseExited(MouseEvent me) {
                        button.setBackground(Color.white);
                        showValidTiles();
                    }
                });
                if(othello.getBoard()[row][col] == 1){
                    button.setIcon(blackPiece);
                }
                else if(othello.getBoard()[row][col] == 2){
                    button.setIcon(whitePiece);
                }
                button.setActionCommand(row + " " + col);
                button.addActionListener(this);
                gamePanel.add(button);
            }
        }

        //Setup UI
        currentTurn = new JLabel("Blacks's turn");
        uiPanel.add(currentTurn);
        currentTurn.setBounds(5, 5, 90, 20);

        blackLabel = new JLabel("Black: 2");
        uiPanel.add(blackLabel);
        blackLabel.setBounds(5, 30, 90, 20);

        whiteLabel = new JLabel("White: 2");
        uiPanel.add(whiteLabel);
        whiteLabel.setBounds(5, 55, 90, 20);

        winner = new JLabel();
        uiPanel.add(winner);
        winner.setBounds(5, 80, 100, 20);
        winner.setForeground(Color.red);

        JButton newGameButton = new JButton("New Game");
        uiPanel.add(newGameButton);
        newGameButton.setBounds(5, 100, 110, 30);
        newGameButton.setActionCommand("new game");
        newGameButton.addActionListener(this);

        JButton undoButton = new JButton("Undo");
        uiPanel.add(undoButton);
        undoButton.setBounds(5, 135, 110, 30);
        undoButton.setActionCommand("undo");
        undoButton.addActionListener(this);

        JButton aiButton = new JButton("AI Play");
        uiPanel.add(aiButton);
        aiButton.setBounds(5, 170, 110, 30);
        aiButton.setActionCommand("ai");
        aiButton.addActionListener(this);

        validBox = new JCheckBox("Legal Moves");
        uiPanel.add(validBox);
        validBox.setBounds(5, 205, 110, 30);
        validBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                showValidTiles();
            }
        });

        //Make frame visible
        frame.setVisible(true);
    }

    public void showValidTiles(){
        if(validBox.isSelected()){
            for(int row = 0; row < othello.getBoard().length; row++) {
                for (int col = 0; col < othello.getBoard()[0].length; col++) {
                    JButton button = (JButton)gamePanel.getComponent((row * 8) + col);
                    if(othello.legalMove(row, col)){
                        button.setBackground(Color.green);
                    }
                    else{
                        button.setBackground(Color.white);
                    }
                }
            }
        }
        else{
            for(int row = 0; row < othello.getBoard().length; row++) {
                for (int col = 0; col < othello.getBoard()[0].length; col++) {
                    JButton button = (JButton)gamePanel.getComponent((row * 8) + col);
                    button.setBackground(Color.white);
                }
            }
        }
    }

    public void updateTiles(){
        for(int row = 0; row < othello.getBoard().length; row++) {
            for (int col = 0; col < othello.getBoard()[0].length; col++) {
                JButton button = (JButton)gamePanel.getComponent((row * 8) + col);
                if(othello.getBoard()[row][col] == 1){
                    button.setIcon(blackPiece);
                }
                else if(othello.getBoard()[row][col] == 2){
                    button.setIcon(whitePiece);
                }
                else{
                    button.setIcon(null);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("new game")){
            othello.resetBoard();
            updateTiles();
            blackLabel.setText("Black: " + othello.getNumBlackPieces());
            whiteLabel.setText("White: " + othello.getNumWhitePieces());
            currentTurn.setText("Black's turn");
            winner.setText("");
            showValidTiles();
        }
        else if(e.getActionCommand().equals("undo")){
            othello.undoMove();
            updateTiles();
            blackLabel.setText("Black: " + othello.getNumBlackPieces());
            whiteLabel.setText("White: " + othello.getNumWhitePieces());
            if(othello.getCurrentTurn() == 1){
                currentTurn.setText("Black's turn");
            }
            else{
                currentTurn.setText("Whites's turn");
            }
            winner.setText("");
            showValidTiles();
        }
        else if(e.getActionCommand().equals("ai")){
            if(!othello.isGameEnded()){
                ai.play();
                updateTiles();
                othello.updateScore();
                blackLabel.setText("Black: " + othello.getNumBlackPieces());
                whiteLabel.setText("White: " + othello.getNumWhitePieces());

                othello.changeTurns();
                if(othello.getCurrentTurn() == 1){
                    currentTurn.setText("Black's turn");
                }
                else{
                    currentTurn.setText("White's turn");
                }

                if(othello.getNumWhitePieces() + othello.getNumBlackPieces() == 64 || othello.getNumWhitePieces() == 0 || othello.getNumBlackPieces() == 0 || othello.getNumLegalMoves() == 0){
                    othello.setGameEnded(true);
                    if(othello.getNumBlackPieces() > othello.getNumWhitePieces()){
                        winner.setText("Black Wins!");
                    }
                    else if(othello.getNumBlackPieces() < othello.getNumWhitePieces()){
                        winner.setText("White Wins!");
                    }
                    else{
                        winner.setText("It's a Draw");
                    }

                }

                showValidTiles();
            }
        }
        else{
            if(!othello.isGameEnded()){
                String[] split = e.getActionCommand().split(" ");
                int row = Integer.parseInt(split[0]);
                int col = Integer.parseInt(split[1]);
                if(othello.legalMove(row, col)){
                    othello.placeTile(row, col);
                    updateTiles();
                    othello.updateScore();
                    blackLabel.setText("Black: " + othello.getNumBlackPieces());
                    whiteLabel.setText("White: " + othello.getNumWhitePieces());

                    othello.changeTurns();
                    if(othello.getCurrentTurn() == 1){
                        currentTurn.setText("Black's turn");
                    }
                    else{
                        currentTurn.setText("White's turn");
                    }

                    if(othello.getNumWhitePieces() + othello.getNumBlackPieces() == 64 || othello.getNumWhitePieces() == 0 || othello.getNumBlackPieces() == 0 || othello.getNumLegalMoves() == 0){
                        othello.setGameEnded(true);
                        if(othello.getNumBlackPieces() > othello.getNumWhitePieces()){
                            winner.setText("Black Wins!");
                        }
                        else if(othello.getNumBlackPieces() < othello.getNumWhitePieces()){
                            winner.setText("White Wins!");
                        }
                        else{
                            winner.setText("It's a Draw");
                        }

                    }
                }
                showValidTiles();
            }
        }
    }
}