package main.model.entities.states;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TriggerPlatformModel {

    // Geometry
    private Rectangle2D.Float hitbox;

    private float startX;
    private float startY;
    private float targetX;
    private float targetY;
    private float offsetX;
    private float offsetY;
    private float speed;

    // Movement state
    private boolean triggered;
    private boolean reachedTarget;
    private boolean movingToTarget = true;

    private boolean waitingAtTarget;
    private long waitStartTime;
    private long waitDurationMs = 1000;

    // Platform properties
    private boolean solid;
    private boolean loop;
    private boolean shouldReturn;

    // Sprite/meta (optional; renderer can still use these)
    private BufferedImage sprite;
    private int spriteWidth;
    private float spriteHeight;
    private float spriteOffsetX;
    private float spriteOffsetY;

    // Multi-tile
    private List<float[]> tilePositions = new ArrayList<>();
    private List<BufferedImage> tileSprites = new ArrayList<>();
    private float firstTileOffsetX;
    private float firstTileOffsetY;

    // Original bounds
    private float originalX;
    private float originalY;
    private int originalWidth;
    private float originalHeight;

    public TriggerPlatformModel(
            Rectangle2D.Float hitbox, float startX, float startY,
            float targetX, float targetY, float speed,
            int spriteWidth, float spriteHeight,
            BufferedImage sprite, boolean shouldReturn) {
        this.hitbox = hitbox;
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.offsetX = targetX - startX;
        this.offsetY = targetY - startY;
        this.speed = speed;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.sprite = sprite;
        this.shouldReturn = shouldReturn;

        this.originalX = startX;
        this.originalY = startY;
        this.originalWidth = spriteWidth;
        this.originalHeight = spriteHeight;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public boolean isReachedTarget() {
        return reachedTarget;
    }

    public void setReachedTarget(boolean reachedTarget) {
        this.reachedTarget = reachedTarget;
    }

    public boolean isMovingToTarget() {
        return movingToTarget;
    }

    public void setMovingToTarget(boolean movingToTarget) {
        this.movingToTarget = movingToTarget;
    }

    public boolean isWaitingAtTarget() {
        return waitingAtTarget;
    }

    public void setWaitingAtTarget(boolean waitingAtTarget) {
        this.waitingAtTarget = waitingAtTarget;
    }

    public long getWaitStartTime() {
        return waitStartTime;
    }

    public void setWaitStartTime(long waitStartTime) {
        this.waitStartTime = waitStartTime;
    }

    public long getWaitDurationMs() {
        return waitDurationMs;
    }

    public void setWaitDurationMs(long waitDurationMs) {
        this.waitDurationMs = waitDurationMs;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isShouldReturn() {
        return shouldReturn;
    }

    public void setShouldReturn(boolean shouldReturn) {
        this.shouldReturn = shouldReturn;
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

    public float getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(float spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public float getSpriteOffsetX() {
        return spriteOffsetX;
    }

    public void setSpriteOffsetX(float spriteOffsetX) {
        this.spriteOffsetX = spriteOffsetX;
    }

    public float getSpriteOffsetY() {
        return spriteOffsetY;
    }

    public void setSpriteOffsetY(float spriteOffsetY) {
        this.spriteOffsetY = spriteOffsetY;
    }

    public List<float[]> getTilePositions() {
        return tilePositions;
    }

    public void setTilePositions(List<float[]> tilePositions) {
        this.tilePositions = tilePositions;
    }

    public List<BufferedImage> getTileSprites() {
        return tileSprites;
    }

    public void setTileSprites(List<BufferedImage> tileSprites) {
        this.tileSprites = tileSprites;
    }

    public float getFirstTileOffsetX() {
        return firstTileOffsetX;
    }

    public void setFirstTileOffsetX(float firstTileOffsetX) {
        this.firstTileOffsetX = firstTileOffsetX;
    }

    public float getFirstTileOffsetY() {
        return firstTileOffsetY;
    }

    public void setFirstTileOffsetY(float firstTileOffsetY) {
        this.firstTileOffsetY = firstTileOffsetY;
    }

    public float getOriginalX() {
        return originalX;
    }

    public void setOriginalX(float originalX) {
        this.originalX = originalX;
    }

    public float getOriginalY() {
        return originalY;
    }

    public void setOriginalY(float originalY) {
        this.originalY = originalY;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(int originalWidth) {
        this.originalWidth = originalWidth;
    }

    public float getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(float originalHeight) {
        this.originalHeight = originalHeight;
    }

    public void addTile(float relX, float relY, BufferedImage tileSprite) {
        tilePositions.add(new float[]{relX, relY});
        tileSprites.add(tileSprite);
    }

    public void moveHitbox(float deltaX, float deltaY) {
        hitbox.x += deltaX;
        hitbox.y += deltaY;
    }
}

