package main.controller.inputs.commands;

import main.controller.Game;

public class JumpReleaseCommand implements Command {

    private final Game game;

    public JumpReleaseCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.getPlayer().setJump(false);
    }
}

