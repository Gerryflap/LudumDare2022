package nl.lipsum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class LudumDare2022 extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;
	Camera camera;

	private MainMenuController mainMenuController;

	private static GameState gameState;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera(1280, 720);

		mainMenuController = new MainMenuController();

		gameState = GameState.MAIN_MENU;
	}

	@Override
	public void render () {
		// Step Methods called here for controllers
		switch (gameState) {
			case MAIN_MENU:
				mainMenuController.step();
			case PLAYING:
				// gameController.step();
		}

		camera.update();

		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// Render methods called here for controllers
		switch (gameState) {
			case MAIN_MENU:
				mainMenuController.render(batch, camera);
			case PLAYING:
				// gameController.render();
		}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState gameState) {
		LudumDare2022.gameState = gameState;
	}

}
