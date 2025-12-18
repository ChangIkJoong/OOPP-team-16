package main.model.entities.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import audio.controller.AudioController;
import main.controller.Game;
import main.model.entities.states.TriggerPlatformModel;

public class TriggerPlatform extends Entity {

    private final TriggerPlatformModel model;

    //sprite hitbox object to avoid creating garbage
    private final java.awt.geom.Rectangle2D.Float cachedSpriteHitbox = new java.awt.geom.Rectangle2D.Float();

    //Audio controller for playing sounds
    private AudioController audioController;

    public TriggerPlatform(float x, float y, float targetX, float targetY,
                           int width, int height, float speed, BufferedImage sprite,
                           boolean shouldReturn) {

        super(x, y, width, height);
        initHitbox(x, y, width, height);

        this.model = new TriggerPlatformModel(
                hitbox, x, y, targetX, targetY, speed, width, height,
                sprite, shouldReturn
        );
    }

    // Set the offset of the first tile sprite within the bounding box
    public void setFirstTileOffset(float offsetX, float offsetY) {
        model.setFirstTileOffsetX(offsetX);
        model.setFirstTileOffsetY(offsetY);
    }

    // Add a tile to this platform (position relative to bounding box top-left)
    public void addTile(float relX, float relY, BufferedImage tileSprite) {
        model.addTile(relX, relY, tileSprite);
    }

    public void setLoop(boolean loop) {
        model.setLoop(loop);
    }

    public void update() {
        // If looping, start immediately without trigger
        if (model.isLoop() && !model.isTriggered()) {
            model.setTriggered(true);
        }

        if (!model.isTriggered() || model.isReachedTarget()) {
            return;
        }

        // If waiting at target, check if wait is over
        if (model.isWaitingAtTarget()) {
            if (System.currentTimeMillis() - model.getWaitStartTime() >= model.getWaitDurationMs()) {
                model.setWaitingAtTarget(false);
                model.setMovingToTarget(!model.isMovingToTarget());
            }
            return;
        }

        // Determine current destination
        float destX = model.isMovingToTarget() ? model.getTargetX() : model.getStartX();
        float destY = model.isMovingToTarget() ? model.getTargetY() : model.getStartY();

        float dirX = destX - hitbox.x;
        float dirY = destY - hitbox.y;
        float distance = (float) Math.sqrt(dirX * dirX + dirY * dirY);

        float speed = model.getSpeed();
        if (distance < speed) {
            // Reached destination
            hitbox.x = destX;
            hitbox.y = destY;

            if (model.isLoop()) {
                // If looping, wait then reverse direction
                model.setWaitingAtTarget(true);
                model.setWaitStartTime(System.currentTimeMillis());
            } else if (model.isMovingToTarget() && model.isShouldReturn()) {
                // Start waiting at target
                model.setWaitingAtTarget(true);
                model.setWaitStartTime(System.currentTimeMillis());
                model.setMovingToTarget(false); // Next move is returning
            } else {
                // Done moving
                if (!model.isMovingToTarget() && model.isShouldReturn()) {
                    // Returned to start, stop
                    model.setReachedTarget(true);
                } else if (model.isMovingToTarget() && !model.isShouldReturn()) {
                    // Reached target, no return
                    model.setReachedTarget(true);
                }
            }
        } else {
            // Move towards destination
            hitbox.x += (dirX / distance) * speed;
            hitbox.y += (dirY / distance) * speed;
        }
    }

    public void render(Graphics g) {
        int tileSize = Game.TILES_SIZE;

        // Sprite area is centered in the hitbox (hitbox is 1.5x the sprite area)
        float spriteAreaX = hitbox.x + hitbox.width / 6f;
        float spriteAreaY = hitbox.y + hitbox.height / 6f;

        // Draw first tile sprite at its offset within the sprite area
        int firstX = (int) (spriteAreaX + model.getFirstTileOffsetX());
        int firstY = (int) (spriteAreaY + model.getFirstTileOffsetY());

        if (model.getSprite() != null) {
            g.drawImage(model.getSprite(), firstX, firstY, tileSize, tileSize, null);
        } else {
            g.setColor(java.awt.Color.ORANGE);
            g.fillRect(firstX, firstY, tileSize, tileSize);
        }

        // Draw additional tiles relative to sprite area top-left
        for (int i = 0; i < model.getTilePositions().size(); i++) {
            float[] pos = model.getTilePositions().get(i);
            int tileX = (int) (spriteAreaX + pos[0]);
            int tileY = (int) (spriteAreaY + pos[1]);
            BufferedImage tileSprite = model.getTileSprites().get(i);
            if (tileSprite != null) {
                g.drawImage(tileSprite, tileX, tileY, tileSize, tileSize, null);
            }
        }
        // Uncomment to debug hitbox:
        //g.setColor(java.awt.Color.RED);
        //g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    // Check if player is touching this platform
    public boolean checkPlayerCollision(Entity player) {
        return hitbox.intersects(player.getHitbox());
    }

    public void trigger() {
        if (!model.isTriggered()) { // Only play sound on first trigger
            model.setTriggered(true);
            if (audioController != null) {
                audioController.playPlatformSound();
            }
        }
    }

    public boolean isTriggered() {
        return model.isTriggered();
    }

    public boolean hasReachedTarget() {
        return model.isReachedTarget();
    }

    public void reset() {
        hitbox.x = model.getStartX();
        hitbox.y = model.getStartY();
        model.setTriggered(false);
        model.setReachedTarget(false);
        model.setMovingToTarget(true);
        model.setWaitingAtTarget(false);
    }

    public void setSprite(BufferedImage sprite) {
        model.setSprite(sprite);
    }

    public void setHitboxSize(int hitboxWidth, int hitboxHeight, int newX, int newY) {
        // Calculate offset from old position
        float offsetX = newX - hitbox.x;
        float offsetY = newY - hitbox.y;

        hitbox.width = hitboxWidth;
        hitbox.height = hitboxHeight;
        hitbox.x = newX;
        hitbox.y = newY;

        model.setStartX(newX);
        model.setStartY(newY);

        // Also update target to maintain the same movement offset
        model.setTargetX(model.getTargetX() + offsetX);
        model.setTargetY(model.getTargetY() + offsetY);
    }

    public void setSolid(boolean solid) {
        model.setSolid(solid);
    }

    public boolean isSolid() {
        return model.isSolid();
    }

    public void setAudioController(AudioController audioController) {
        this.audioController = audioController;
    }

    // Get the sprite hitbox (the actual collidable area for standing)
    public java.awt.geom.Rectangle2D.Float getSpriteHitbox() {
        // Sprite area is centered in hitbox (hitbox is 1.5x sprite area)
        // Sprite = hitbox / 1.5 = hitbox * 2/3, offset = hitbox / 6
        float spriteAreaW = hitbox.width * 2f / 3f;
        float spriteAreaH = hitbox.height * 2f / 3f;
        float spriteAreaX = hitbox.x + hitbox.width / 6f;
        float spriteAreaY = hitbox.y + hitbox.height / 6f;

        cachedSpriteHitbox.setRect(spriteAreaX, spriteAreaY, spriteAreaW, spriteAreaH);
        return cachedSpriteHitbox;
    }
}

