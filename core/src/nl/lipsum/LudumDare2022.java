package nl.lipsum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import nl.lipsum.buildings.BuildingController;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;
import nl.lipsum.main_menu.MainMenuController;
import nl.lipsum.controllers.InputController;
import nl.lipsum.ui.UiController;

public class LudumDare2022 extends ApplicationAdapter {

	SpriteBatch batch;
	CameraController cameraController;
	GameController gameController;
	InputController inputController;
	BuildingController buildingController;
	HumanPlayerModel humanPlayerModel;

	private MainMenuController mainMenuController;
	private UiController uiController;

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
		buildingController = new BuildingController(cameraController);
		inputController = new InputController(cameraController, buildingController);
		gameController = new GameController();

		mainMenuController = new MainMenuController();
		uiController = new UiController(gameController, humanPlayerModel);

		gameState = GameState.MAIN_MENU;

		mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/main_menu.wav"));
	}

	@Override
	public void render () {
		// Manage music
		manageMainMenuMusic();

		// Step Methods called here for controllers
		switch (gameState) {
			case MAIN_MENU:
				mainMenuController.step();
			case PLAYING:
				this.cameraController.step();
				this.gameController.step();
				this.uiController.step();
		}


		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		// Render methods called here for controllers
		switch (gameState) {
			case MAIN_MENU:
				mainMenuController.render(batch, null);
				break;
			case PLAYING:
				this.cameraController.render(batch, null);
				this.gameController.render(batch, this.cameraController);
				this.buildingController.render(batch, this.cameraController);
				this.uiController.render(batch, this.cameraController);
				break;
			case EXITING:
				System.exit(0);
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
		this.gameController.dispose();
		this.uiController.dispose();
		this.mainMenuController.dispose();

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
