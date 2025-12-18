package main.controller.inputs.commands;

import main.controller.api.IGameActions;

public class MoveRightPressCommand implements Command {

    private final IGameActions actions;

    public MoveRightPressCommand(IGameActions actions) {
        this.actions = actions;
    }

    @Override
    public void execute() {
        actions.moveRightPressed();
    }
}
