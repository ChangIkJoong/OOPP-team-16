package main.controller.inputs.commands;

import main.controller.api.IGameActions;

public class MoveRightReleaseCommand implements Command {

    private final IGameActions actions;

    public MoveRightReleaseCommand(IGameActions actions) {
        this.actions = actions;
    }

    @Override
    public void execute() {
        actions.moveRightReleased();
    }
}
