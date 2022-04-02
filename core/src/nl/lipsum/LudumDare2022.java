package nl.lipsum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.ui.UiController;

import static nl.lipsum.Config.*;

public class LudumDare2022 extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;
	CameraController cameraController;
	GameController gameController;

	private MainMenuController mainMenuController;
	private UiController uiController;

	private static GameState gameState;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		//todo: magic constants vervangen voor viewport width/height
		cameraController = new CameraController(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		gameController = new GameController();

		mainMenuController = new MainMenuController();
		uiController = new UiController();

		gameState = GameState.MAIN_MENU;
	}

	@Override
	public void render () {
		// Step Methods called here for controllers
		switch (gameState) {
			case MAIN_MENU:
				mainMenuController.step();
			case PLAYING:
				this.cameraController.step();
				this.gameController.step();
				this.uiController.step();
		}


		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		// Render methods called here for controllers
		switch (gameState) {
			case MAIN_MENU:
				mainMenuController.render(batch, null);
			case PLAYING:
				this.cameraController.render(batch, null);
				this.gameController.render(batch, this.cameraController);
				this.uiController.render(batch, this.cameraController);

		}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		this.gameController.dispose();
		this.uiController.dispose();
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState gameState) {
		LudumDare2022.gameState = gameState;
	}

}
