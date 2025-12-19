package main.controller.facades;

/**
 * Minimal write-only API for input/commands.
 * Keeps UI/input decoupled from the concrete {@code main.controller.Game} class.
 */
public interface IGameActions {
    void moveLeftPressed();

    void moveLeftReleased();

    void moveRightPressed();

    void moveRightReleased();

    void jumpPressed();

    void jumpReleased();

    void togglePause();

    void goToMenu();
}
