package main.model.entities.states;

import main.model.entities.entity.Spike;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * State holder for {@link Spike}.
 */
public class SpikeModel {

    private Rectangle2D.Float hitbox;

    private BufferedImage sprite;
    private int spriteWidth;
    private int spriteHeight;

    public SpikeModel(Rectangle2D.Float hitbox, BufferedImage sprite, int spriteWidth, int spriteHeight) {
        this.hitbox = hitbox;
        this.sprite = sprite;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }
}

