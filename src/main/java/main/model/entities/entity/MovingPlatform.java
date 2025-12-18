package main.model.entities.entity;

import main.model.entities.states.MovingPlatformModel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MovingPlatform extends Entity {

    private final MovingPlatformModel model;

    private BufferedImage sprite;

    public MovingPlatform(float startX, float startY, float endX, float endY,
                          int width, int height, float speed, BufferedImage sprite) {
        super(startX, startY, width, height);
        this.sprite = sprite;
        initHitbox(startX, startY, width, height);
        this.model = new MovingPlatformModel(hitbox, startX, startY, endX, endY, speed);
    }

    public void update() {
        float targetX = model.isMovingToEnd() ? model.getEndX() : model.getStartX();
        float targetY = model.isMovingToEnd() ? model.getEndY() : model.getStartY();

        float dirX = targetX - hitbox.x;
        float dirY = targetY - hitbox.y;
        float distance = (float) Math.sqrt(dirX * dirX + dirY * dirY);

        float speed = model.getSpeed();
        if (distance < speed) {
            // Reached target, switch direction
            hitbox.x = targetX;
            hitbox.y = targetY;
            model.setMovingToEnd(!model.isMovingToEnd());
        } else {
            // Move towards target
            hitbox.x += (dirX / distance) * speed;
            hitbox.y += (dirY / distance) * speed;
        }

        x = hitbox.x;
        y = hitbox.y;
    }

    public void render(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, (int) hitbox.x, (int) hitbox.y, width, height, null);
        } else {
            g.setColor(java.awt.Color.GRAY);
            g.fillRect((int) hitbox.x, (int) hitbox.y, width, height);
        }
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
}
