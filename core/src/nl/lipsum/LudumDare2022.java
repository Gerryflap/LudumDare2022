package nl.lipsum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import nl.lipsum.ui.UiController;
import nl.lipsum.gameLogic.GameController;

import static nl.lipsum.Config.*;

public class LudumDare2022 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	UiController uiController;

	CameraController cameraController;
	GameController gameController;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		//todo: magic constants vervangen voor viewport width/height
		cameraController = new CameraController(new OrthographicCamera(1280, 720));
		gameController = new GameController();
//		img = new Texture("badlogic.jpg");

//		Gdx.graphics.setWindowedMode(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
		uiController = new UiController();
	}

	@Override
	public void render () {
		// Step Methods called here for controllers
		this.cameraController.step();
		this.gameController.step();
		uiController.step();

		camera.update();

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		// Render methods called here for controllers
		this.cameraController.render(batch, null);
		this.gameController.render(batch, this.cameraController);

		uiController.render(batch, this.cameraController);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		this.gameController.dispose();
		uiController.dispose();
	}
}
