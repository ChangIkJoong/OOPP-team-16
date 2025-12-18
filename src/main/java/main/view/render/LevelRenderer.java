package main.view.render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.controller.Game;
import main.model.levels.Level;
import main.model.levels.LevelManager;
import utilities.LoadSave;

import static main.controller.Game.GAME_HEIGHT;
import static main.controller.Game.GAME_WIDTH;
import static main.controller.Game.TILES_SIZE;

/**
 * View-side renderer for levels.
 *
 * Keeps rendering and sprite loading out of {@link LevelManager} for higher cohesion.
 */
public class LevelRenderer {

    private final BufferedImage[] levelSprite;
    private final BufferedImage[] objectSprite;
    private final BufferedImage background;

    public LevelRenderer() {
        this.background = LoadSave.getSpriteAtlas(LoadSave.BG_DATA);
        this.levelSprite = importLevelSprites();
        this.objectSprite = importObjectSprites();
    }

    public void renderBackgroundAndTerrain(Graphics g, LevelManager levelManager) {
        g.drawImage(background, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        Level currentLevel = levelManager.getCurrentLvl();
        currentLevel.drawTriggerSpikes(g);

        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = currentLevel.getSpriteIndex(i, j);
                if (index < 0 || index >= levelSprite.length) {
                    index = 0;
                }
                g.drawImage(levelSprite[index], i * TILES_SIZE, j * TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
            }
        }

        currentLevel.drawPlatforms(g);
        currentLevel.drawSpikes(g);
        currentLevel.drawDeathSprites(g);
    }

    public void renderObjectLayer(Graphics g, LevelManager levelManager) {
        Level currentLevel = levelManager.getCurrentLvl();
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = currentLevel.getObjectSpriteIndex(i, j);
                if (index > 0 && index < objectSprite.length) {
                    g.drawImage(objectSprite[index], i * TILES_SIZE, j * TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
                }
            }
        }
    }

    private static BufferedImage[] importLevelSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        BufferedImage[] sprites = new BufferedImage[81];
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                int index = j * 9 + i;
                sprites[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
        return sprites;
    }

    private static BufferedImage[] importObjectSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.OBJECT_ATLAS);
        BufferedImage[] sprites = new BufferedImage[48];
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 8; i++) {
                int index = j * 8 + i;
                sprites[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
        return sprites;
    }
}

