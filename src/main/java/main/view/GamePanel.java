package main.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.controller.inputs.KeyboardInputs;
import main.controller.inputs.MouseInputs;
import main.controller.Game;
import main.controller.facades.IGameActions;
import main.controller.facades.IGameReadOnly;
import static main.controller.Game.GAME_HEIGHT;
import static main.controller.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs();
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs((IGameActions) game, (IGameReadOnly) game));
        mouseInputs.setGamePanel(this);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size:" + GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    public void updateGame() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
