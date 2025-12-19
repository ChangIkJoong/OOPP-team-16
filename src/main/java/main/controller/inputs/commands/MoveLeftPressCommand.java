package main.controller.inputs.commands;

import main.controller.facades.IGameActions;

public class MoveLeftPressCommand implements Command {

    private final IGameActions actions;

    public MoveLeftPressCommand(IGameActions actions) {
        this.actions = actions;
    }

    @Override
    public void execute() {
        actions.moveLeftPressed();
    }
}
