package main.model.levels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utilities.LoadSave;

public class LevelManager {
    private final LevelManagerHost host;
    private BufferedImage[] levelSprite;
    private BufferedImage[] objectSprite;
    private BufferedImage spawnTube;
    private BufferedImage deathSprite;
    private List<Level> levels;
    private int currentLevelIndex = 0;
    private final Set<Integer> completedLevels = new HashSet<>();

    public LevelManager(LevelManagerHost host) {
        this.host = host;
        importOutsideSprites();
        buildAllLevels();
        // Level 1 is always unlocked
        completedLevels.add(0);

        completedLevels.add(1);
        completedLevels.add(2);
        completedLevels.add(3);
        completedLevels.add(4);
        completedLevels.add(5);
        completedLevels.add(6);
    }

    private void buildAllLevels() {
        levels = new ArrayList<>();
        spawnTube = LoadSave.getSpriteAtlas(LoadSave.SPAWN_TUBE);
        deathSprite = LoadSave.getSpriteAtlas(LoadSave.PLAYER_DEAD);

        // Level 1
        LevelConfigLoader.LevelConfig config1 = LevelConfigLoader.loadConfig("level1.txt");
        Level level1 = new Level(
                LoadSave.getLevelData(LoadSave.LEVEL_ONE_DATA),
                LoadSave.getLevelObstacleData(LoadSave.LEVEL_ONE_OBSTACLE_DATA),
                LoadSave.getLevelObjData(LoadSave.LEVEL_ONE_OBJ_DATA),
                config1.spawnX, config1.spawnY);
        LevelConfigLoader.applyConfig(level1, config1, levelSprite, objectSprite, spawnTube);
        level1.setAudioControllerForPlatforms(host.getAudioController());
        levels.add(level1);

        // Level 2
        LevelConfigLoader.LevelConfig config2 = LevelConfigLoader.loadConfig("level2.txt");
        Level level2 = new Level(
                LoadSave.getLevelData(LoadSave.LEVEL_TWO_DATA),
                LoadSave.getLevelObstacleData(LoadSave.LEVEL_TWO_OBSTACLE_DATA),
                LoadSave.getLevelObjData(LoadSave.LEVEL_TWO_OBJ_DATA),
                config2.spawnX, config2.spawnY);
        LevelConfigLoader.applyConfig(level2, config2, levelSprite, objectSprite, spawnTube);
        level2.setAudioControllerForPlatforms(host.getAudioController());
        levels.add(level2);

        // Level 3
        LevelConfigLoader.LevelConfig config3 = LevelConfigLoader.loadConfig("level3.txt");
        Level level3 = new Level(
                LoadSave.getLevelData(LoadSave.LEVEL_THREE_DATA),
                LoadSave.getLevelObstacleData(LoadSave.LEVEL_THREE_OBSTACLE_DATA),
                LoadSave.getLevelObjData(LoadSave.LEVEL_THREE_OBJ_DATA),
                config3.spawnX, config3.spawnY);
        LevelConfigLoader.applyConfig(level3, config3, levelSprite, objectSprite, spawnTube);
        level3.setAudioControllerForPlatforms(host.getAudioController());
        levels.add(level3);

        // Level 4
        LevelConfigLoader.LevelConfig config4 = LevelConfigLoader.loadConfig("level4.txt");
        Level level4 = new Level(
                LoadSave.getLevelData(LoadSave.LEVEL_FOUR_DATA),
                LoadSave.getLevelObstacleData(LoadSave.LEVEL_FOUR_OBSTACLE_DATA),
                LoadSave.getLevelObjData(LoadSave.LEVEL_FOUR_OBJ_DATA),
                config4.spawnX, config4.spawnY);
        LevelConfigLoader.applyConfig(level4, config4, levelSprite, objectSprite, spawnTube);
        level4.setAudioControllerForPlatforms(host.getAudioController());
        levels.add(level4);

        // Level 5
        LevelConfigLoader.LevelConfig config5 = LevelConfigLoader.loadConfig("level5.txt");
        Level level5 = new Level(
                LoadSave.getLevelData(LoadSave.LEVEL_FIVE_DATA),
                LoadSave.getLevelObstacleData(LoadSave.LEVEL_FIVE_OBSTACLE_DATA),
                LoadSave.getLevelObjData(LoadSave.LEVEL_FIVE_OBJ_DATA),
                config5.spawnX, config5.spawnY);
        LevelConfigLoader.applyConfig(level5, config5, levelSprite, objectSprite, spawnTube);
        level5.setAudioControllerForPlatforms(host.getAudioController());
        levels.add(level5);

        // Level 6
        LevelConfigLoader.LevelConfig config6 = LevelConfigLoader.loadConfig("level6.txt");
        Level level6 = new Level(
                LoadSave.getLevelData(LoadSave.LEVEL_SIX_DATA),
                LoadSave.getLevelObstacleData(LoadSave.LEVEL_SIX_OBSTACLE_DATA),
                LoadSave.getLevelObjData(LoadSave.LEVEL_SIX_OBJ_DATA),
                config6.spawnX, config6.spawnY);
        LevelConfigLoader.applyConfig(level6, config6, levelSprite, objectSprite, spawnTube);
        level6.setAudioControllerForPlatforms(host.getAudioController());
        levels.add(level6);

        // Level 7
        LevelConfigLoader.LevelConfig config7 = LevelConfigLoader.loadConfig("level7.txt");
        Level level7 = new Level(
                LoadSave.getLevelData(LoadSave.LEVEL_SEVEN_DATA),
                LoadSave.getLevelObstacleData(LoadSave.LEVEL_SEVEN_OBSTACLE_DATA),
                LoadSave.getLevelObjData(LoadSave.LEVEL_SEVEN_OBJ_DATA),
                config7.spawnX, config7.spawnY);
        LevelConfigLoader.applyConfig(level7, config7, levelSprite, objectSprite, spawnTube);
        level7.setAudioControllerForPlatforms(host.getAudioController());
        levels.add(level7);
    }

    /**
     * NOTE: Kept temporarily because LevelConfigLoader.applyConfig currently needs sprites to
     * build model objects with images (platforms/spikes). A future step is separating those
     * images into view renderers.
     */
    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[81];
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                int index = j * 9 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }

        BufferedImage objImg = LoadSave.getSpriteAtlas(LoadSave.OBJECT_ATLAS);
        objectSprite = new BufferedImage[48];
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 8; i++) {
                int index = j * 8 + i;
                objectSprite[index] = objImg.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }


    public BufferedImage getDeathSprite() {
        return deathSprite;
    }

    public void update() {
        getCurrentLvl().updatePlatforms(host.getPlayer());
        getCurrentLvl().updateTriggerSpikes(host.getPlayer());
        getCurrentLvl().updateSpawnPlatform();
    }

    public Level getCurrentLvl() {
        //return levels.get(6); // For Testing TODO
        return levels.get(currentLevelIndex);
    }

    public void loadNextLevel() {
        // Mark current level as completed (this also unlocks the next level)
        markLevelCompleted(currentLevelIndex);

        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;

            host.reloadPlayerCurrentLevel();
        } else {
            // Last level completed - return to main menu
            host.setGameState(main.controller.Game.GameState.MENU);
        }
    }

    public void markCurrentLevelCompleted() {
        markLevelCompleted(currentLevelIndex);
    }

    public void markLevelCompleted(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levels.size()) {
            completedLevels.add(levelIndex);
            // Unlock next level
            if (levelIndex + 1 < levels.size()) {
                completedLevels.add(levelIndex + 1);
            }
        }
    }

    public boolean isLevelUnlocked(int levelIndex) {
        return completedLevels.contains(levelIndex);
    }

    public int getLevelCount() {
        return levels.size();
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public void setCurrentLevelIndex(int index) {
        if (index >= 0 && index < levels.size()) {
            currentLevelIndex = index;
        }
    }

    public void setLevelScore(int death) {
        Level currLevel = levels.get(getCurrentLevelIndex());
        currLevel.updateDeathScore(death);
    }

    public BufferedImage getSprite(int tileId) {
        if (tileId >= 0 && tileId < levelSprite.length) {
            return levelSprite[tileId];
        }
        return levelSprite[0];
    }

    public void resetToFirstLevel() {
        currentLevelIndex = 0;
    }
}
