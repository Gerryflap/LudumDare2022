package nl.lipsum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import nl.lipsum.buildings.BuildingController;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.entities.EntityController;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;
import nl.lipsum.gameover.GameOverController;
import nl.lipsum.main_menu.MainMenuController;
import nl.lipsum.controllers.InputController;
import nl.lipsum.ui.UiController;

public class LudumDare2022 extends ApplicationAdapter {

	SpriteBatch batch;
	public static CameraController cameraController;
	public static GameController gameController;
	public static InputController inputController;
	public static BuildingController buildingController;
	public static HumanPlayerModel humanPlayerModel;

	private MainMenuController mainMenuController;
	private UiController uiController;
	public static EntityController entityController = new EntityController();
	private static GameOverController gameOverController;

	private static GameState previousGameState;
	private static GameState gameState;

	private static Music mainMenuMusic;

	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());

		humanPlayerModel = new HumanPlayerModel();

		batch = new SpriteBatch();
		//todo: magic constants vervangen voor viewport width/height
		cameraController = new CameraController(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		buildingController = new BuildingController(cameraController, humanPlayerModel);
		inputController = new InputController(cameraController, buildingController);
		gameController = new GameController(humanPlayerModel);
		gameOverController = new GameOverController();

		mainMenuController = new MainMenuController();
		uiController = new UiController(gameController, humanPlayerModel);

		gameState = GameState.MAIN_MENU;

		mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/main_menu.wav"));

	}

	private void handleGameOver() {
		resetGame();

		batch = new SpriteBatch();

		humanPlayerModel = new HumanPlayerModel();

		cameraController = new CameraController(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		buildingController = new BuildingController(cameraController, humanPlayerModel);
		inputController = new InputController(cameraController, buildingController);
		gameController = new GameController(humanPlayerModel);
		mainMenuController = new MainMenuController();
		uiController = new UiController(gameController, humanPlayerModel);

		setGameState(GameState.MAIN_MENU);
	}

	private void resetGame() {
		batch.dispose();
		cameraController.dispose();
		buildingController.dispose();
		gameController.dispose();
		mainMenuController.dispose();
		uiController.dispose();
	}

	@Override
	public void render () {
		// Manage music
		manageMainMenuMusic();

		if (gameState == GameState.GAME_OVER && previousGameState != GameState.GAME_OVER) {
			handleGameOver();
		}

		// Step Methods called here for controllers
		switch (gameState) {
			case MAIN_MENU:
				mainMenuController.step();
				break;
			case PLAYING:
				cameraController.step();
				gameController.step();
				entityController.step();
				uiController.step();
				buildingController.step();
				break;
			case GAME_OVER:
				gameOverController.step();
				break;
		}

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		// Render methods called here for controllers
		switch (gameState) {
			case MAIN_MENU:
				mainMenuController.render(batch, null);
				break;
			case PLAYING:
				cameraController.render(batch, null);
				gameController.render(batch, cameraController);
				buildingController.render(batch, cameraController);
				entityController.render(batch, cameraController);
				uiController.render(batch, cameraController);
				break;
			case EXITING:
				System.exit(0);
				break;
			case GAME_OVER:
				gameOverController.render(batch, null);
				break;
		}
		batch.end();
	}

	private static void manageMainMenuMusic() {
		if (gameState == GameState.MAIN_MENU) {
			if (!mainMenuMusic.isPlaying()) {
				mainMenuMusic.play();
				mainMenuMusic.setVolume(0.1f);
				mainMenuMusic.setLooping(true);
			}
		} else {
			if (mainMenuMusic.isPlaying()) {
				mainMenuMusic.stop();
			}
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		gameController.dispose();
		uiController.dispose();
		mainMenuController.dispose();
		mainMenuMusic.dispose();
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState gameState) {
		LudumDare2022.previousGameState = LudumDare2022.gameState;
		LudumDare2022.gameState = gameState;
	}

	public static GameState getPreviousGameState() {
		return previousGameState;
	}
}
