package main.model.entities.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.controller.Game;
import main.model.entities.states.PlayerModel;
import utilities.LoadSave;

import static utilities.Constants.PlayerConstants.IDLE_LEFT;
import static utilities.Constants.PlayerConstants.IDLE_RIGHT;
import static utilities.Constants.PlayerConstants.JUMPING_LEFT;
import static utilities.Constants.PlayerConstants.JUMPING_RIGHT;
import static utilities.Constants.PlayerConstants.RUNNING_LEFT;
import static utilities.Constants.PlayerConstants.RUNNING_RIGHT;
import static utilities.Constants.PlayerConstants.getSpriteAmount;
import static utilities.HelpMethods.canMoveHere;
import static utilities.HelpMethods.getEntityXPosNextToWall;
import static utilities.HelpMethods.getEntityYPosUnderOrAbove;
import static utilities.HelpMethods.isEntityDead;
import static utilities.HelpMethods.isEntityOnFloor;
import static utilities.HelpMethods.isOnLevelEnd;

public class Player extends Entity {

    private static final long RESPAWN_DELAY_MS = 500;

    private final PlayerModel model;

    private BufferedImage[][] animation;

    private main.model.levels.Level currentLevel;

    private final float xDrawOffset = 9.5f * Game.SCALE;
    private final float yDrawOffset = 8.25f * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimatons();
        initHitbox(x, y, 12 * Game.SCALE, 22 * Game.SCALE);
        this.model = new PlayerModel(hitbox, x, y);
    }

    public void update() {
        if (model.isDead()) {
            if (System.currentTimeMillis() - model.getDeathTime() >= RESPAWN_DELAY_MS) {
                respawn();
            }
            return;
        }

        int[][] lvlData = model.getLvlData();

        if (lvlData != null && (isEntityDead(hitbox, lvlData)
                || (currentLevel != null && currentLevel.checkSpikeCollision(this))
                || (currentLevel != null && currentLevel.checkTriggerSpikeCollision(this)))) {
            die();
            return;
        }

        if (lvlData != null && isOnLevelEnd(hitbox, lvlData)) {
            model.setReachedLevelEnd(true);
        }

        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    private void die() {
        model.setDeathCount(model.getDeathCount() + 1);
        model.setDead(true);
        model.setDeathTime(System.currentTimeMillis());

        if (currentLevel != null) {
            BufferedImage deathSprite = LoadSave.getSpriteAtlas(LoadSave.PLAYER_DEAD);
            currentLevel.recordDeathPosition(hitbox.x - xDrawOffset, hitbox.y - yDrawOffset, deathSprite);
        }

        // Keep the previous visual behavior (hide player) but no longer rely on it as the death signal.
        hitbox.x = 2000;
        hitbox.y = 2000;

        resetInAir();
        resetDirBooleans();
    }

    private void respawn() {
        model.setDead(false);
        hitbox.x = model.getSpawnX();
        hitbox.y = model.getSpawnY();

        resetDirBooleans();
        model.setAirSpeed(0);
        model.setMoving(false);
        model.setJump(false);

        int[][] lvlData = model.getLvlData();
        model.setInAir(lvlData != null && !isEntityOnFloor(hitbox, lvlData));
    }

    public void updatePos() {
        model.setMoving(false);

        if (model.isJump()) {
            jump();
        }

        if (!model.isLeft() && !model.isRight() && !model.isInAir()) {
            return;
        }

        float xSpeed = 0;
        if (model.isLeft()) {
            xSpeed -= model.getPlayerSpeed();
        }
        if (model.isRight()) {
            xSpeed += model.getPlayerSpeed();
        }

        int[][] lvlData = model.getLvlData();
        if (lvlData == null) {
            return;
        }

        if (!model.isInAir()) {
            if (!isEntityOnFloor(hitbox, lvlData)
                    && (currentLevel == null || !currentLevel.isOnSolidPlatform(hitbox))) {
                model.setInAir(true);
            }
        }

        if (model.isInAir()) {
            float airSpeed = model.getAirSpeed();
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                model.setAirSpeed(airSpeed + model.getGravity());

                updateXPos(xSpeed);

                if (currentLevel != null && model.getAirSpeed() > 0) {
                    float platformY = currentLevel.getSolidPlatformY(hitbox, model.getAirSpeed());
                    if (platformY >= 0) {
                        hitbox.y = platformY;
                        resetInAir();
                    }
                }
            } else {
                hitbox.y = getEntityYPosUnderOrAbove(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    model.setAirSpeed(model.getFallSpeedAfterCollision());
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }

        model.setMoving(true);
    }

    private void jump() {
        if (model.isInAir()) {
            return;
        }
        model.setInAir(true);
        model.setAirSpeed(model.getJumpSpeed());
    }

    private void resetInAir() {
        model.setInAir(false);
        model.setAirSpeed(0);
    }

    private void updateXPos(float xSpeed) {
        int[][] lvlData = model.getLvlData();
        if (lvlData == null) {
            return;
        }

        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    // Getters & Setters ------------------------

    public boolean hasReachedLevelEnd() {
        return model.hasReachedLevelEnd();
    }

    public void resetLevelEnd() {
        model.setReachedLevelEnd(false);
    }

    public int getDeathCount() {
        return model.getDeathCount();
    }

    public void resetDeathCount() {
        model.setDeathCount(0);
    }

    public void resetDirBooleans() {
        model.setLeft(false);
        model.setRight(false);
    }

    public boolean isLeft() {
        return model.isLeft();
    }

    public void setLeft(boolean left) {
        model.setLeft(left);
    }

    public boolean isRight() {
        return model.isRight();
    }

    public void setRight(boolean right) {
        model.setRight(right);
    }

    public void setJump(boolean jump) {
        model.setJump(jump);
    }

    /**
     * Explicit death state query; do not infer death from coordinates.
     */
    public boolean isDead() {
        return model.isDead();
    }

    public void setCurrentLevel(main.model.levels.Level level) {
        this.currentLevel = level;
    }

    public void loadLvlData(int[][] lvlData) {
        model.setLvlData(lvlData);
        if (lvlData != null && !isEntityOnFloor(hitbox, lvlData)) {
            model.setInAir(true);
        }
    }

    public void setSpawnPoint(float x, float y) {
        model.setSpawnX(x);
        model.setSpawnY(y);
    }

    public void spawnAtLevelStart() {
        hitbox.x = model.getSpawnX();
        hitbox.y = model.getSpawnY();
        resetDirBooleans();
        model.setAirSpeed(0);
        model.setMoving(false);
        model.setJump(false);
        model.setDead(false);
        model.setReachedLevelEnd(false);

        int[][] lvlData = model.getLvlData();
        model.setInAir(lvlData != null && !isEntityOnFloor(hitbox, lvlData));
    }

    // Rendering---------------------------------------

    public void render(Graphics g) {
        g.drawImage(animation[model.getPlayerAction()][model.getAniIndex()],
                (int) (hitbox.x - xDrawOffset),
                (int) (hitbox.y - yDrawOffset), width, height, null);
    }

    private void loadAnimatons() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animation = new BufferedImage[4][8];
        for (int j = 0; j < animation.length; j++) {
            for (int i = 0; i < animation[j].length; i++) {
                animation[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    private void setAnimation() {
        int startAni = model.getPlayerAction();

        if (model.isMoving()) {
            if (model.isLeft()) {
                model.setPlayerAction(RUNNING_LEFT);
                model.setFacingRight(false);
            } else if (model.isRight()) {
                model.setFacingRight(true);
                model.setPlayerAction(RUNNING_RIGHT);
            }
        } else {
            if (!model.isFacingRight()) {
                model.setPlayerAction(IDLE_LEFT);
            } else {
                model.setPlayerAction(IDLE_RIGHT);
            }
        }

        if (model.isJump()) {
            if (!model.isFacingRight()) {
                model.setPlayerAction(JUMPING_LEFT);
            } else {
                model.setPlayerAction(JUMPING_RIGHT);
            }
        }

        if (startAni != model.getPlayerAction()) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        model.setAniTick(0);
        model.setAniIndex(0);
    }

    private void updateAnimationTick() {
        model.setAniTick(model.getAniTick() + 1);
        if (model.getAniTick() >= model.getAniSpeed()) {
            model.setAniTick(0);
            model.setAniIndex(model.getAniIndex() + 1);
            if (model.getAniIndex() >= getSpriteAmount(model.getPlayerAction())) {
                model.setAniIndex(0);
            }
        }
    }
}
