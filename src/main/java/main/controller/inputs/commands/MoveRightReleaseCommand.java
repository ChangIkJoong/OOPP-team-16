package main.controller.inputs.commands;

import main.controller.Game;

public class MoveRightReleaseCommand implements Command {

    private final Game game;

    public MoveRightReleaseCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.getPlayer().setRight(false);
    }
}
