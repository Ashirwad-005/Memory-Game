package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class frame extends JFrame {
    private ArrayList<String> symbols;
    private Card firstCard;
    private Card secondCard;
    private Timer timer;
    private int matchedPairs = 0;
    private JLabel scoreLabel;
    private JButton restartButton;

    public frame() {
        setTitle("Memory Game");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the game grid
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4));
        add(gridPanel, BorderLayout.CENTER);

        // Create a panel for the score and restart button
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        scoreLabel = new JLabel("Score: 0");
        controlPanel.add(scoreLabel, BorderLayout.WEST);

        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame(gridPanel);
            }
        });
        controlPanel.add(restartButton, BorderLayout.EAST);

        add(controlPanel, BorderLayout.NORTH);

        symbols = new ArrayList<>();
        symbols.add("*");
        symbols.add("*");
        symbols.add("+");
        symbols.add("+");
        symbols.add("-");
        symbols.add("-");
        symbols.add("/");
        symbols.add("/");
        symbols.add("!");
        symbols.add("!");
        symbols.add("@");
        symbols.add("@");
        symbols.add("#");
        symbols.add("#");
        symbols.add("$");
        symbols.add("$");
        Collections.shuffle(symbols);

        for (int i = 0; i < 16; i++) {
            gridPanel.add(new Card(symbols.get(i), this));
        }

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkCards();
            }
        });
        timer.setRepeats(false);

        setVisible(true);
    }

    public void cardSelected(Card card) {
        if (firstCard == null) {
            firstCard = card;
            firstCard.showSymbol();
        } else if (secondCard == null) {
            secondCard = card;
            secondCard.showSymbol();
            timer.start();
        }
    }

    private void checkCards() {
        if (firstCard.getSymbol().equals(secondCard.getSymbol())) {
            firstCard.setEnabled(false);
            secondCard.setEnabled(false);
            matchedPairs++;
            scoreLabel.setText("Score: " + matchedPairs);
            if (matchedPairs == 8) {
                JOptionPane.showMessageDialog(this, "Congratulations! You've matched all pairs!");
            }
        } else {
            firstCard.hideSymbol();
            secondCard.hideSymbol();
        }
        firstCard = null;
        secondCard = null;
    }

    private void resetGame(JPanel gridPanel) {
        matchedPairs = 0;
        scoreLabel.setText("Score: 0");
        gridPanel.removeAll();
        symbols.clear();
        symbols.add("*");
        symbols.add("*");
        symbols.add("+");
        symbols.add("+");
        symbols.add("-");
        symbols.add("-");
        symbols.add("/");
        symbols.add("/");
        symbols.add("!");
        symbols.add("!");
        symbols.add("@");
        symbols.add("@");
        symbols.add("#");
        symbols.add("#");
        symbols.add("$");
        symbols.add("$");
        Collections.shuffle(symbols);

        for (int i = 0; i < 16; i++) {
            gridPanel.add(new Card(symbols.get(i), this));
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new frame();
            }
        });
    }
}

class Card extends JButton {
    private String symbol;
    private frame game;

    public Card(String symbol, frame game) {
        this.symbol = symbol;
        this.game = game;
        hideSymbol();
        setFont(new Font("Arial", Font.PLAIN, 24));
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.cardSelected(Card.this);
            }
        });
    }

    public String getSymbol() {
        return symbol;
    }

    public void showSymbol() {
        setText(symbol);
        setBackground(Color.LIGHT_GRAY);
    }

    public void hideSymbol() {
        setText("");
        setBackground(null);
    }
}
