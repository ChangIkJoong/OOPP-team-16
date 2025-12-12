package main.controller.inputs.commands;

import main.controller.Game;

public class JumpPressCommand implements Command {

    private final Game game;

    public JumpPressCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.getPlayer().setJump(true);
    }
}
