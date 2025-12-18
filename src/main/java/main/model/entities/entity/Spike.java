package main.model.entities.entity;

import main.model.entities.states.SpikeModel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Spike extends Entity {

    private final SpikeModel model;

    public Spike(float x, float y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height);
        // Hitbox is half height, positioned at bottom of sprite
        initHitbox(x, y + height / 2f, width, height / 2f);
        this.model = new SpikeModel(hitbox, sprite, width, height);
    }

    public void render(Graphics g) {
        // Draw sprite at top of hitbox (since hitbox is bottom half)
        int spriteY = (int) (hitbox.y - model.getSpriteHeight() / 2f);
        if (model.getSprite() != null) {
            g.drawImage(model.getSprite(), (int) hitbox.x, spriteY,
                    model.getSpriteWidth(), model.getSpriteHeight(), null);
        } else {
            g.setColor(java.awt.Color.RED);
            g.fillRect((int) hitbox.x, spriteY, model.getSpriteWidth(), model.getSpriteHeight());
        }
    }

    public boolean checkPlayerCollision(Entity player) {
        return hitbox.intersects(player.getHitbox());
    }

    public void setSprite(BufferedImage sprite) {
        model.setSprite(sprite);
    }
}
