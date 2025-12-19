package main.controller.facades;

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
