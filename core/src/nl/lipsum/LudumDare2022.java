package nl.lipsum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import nl.lipsum.ui.UiController;

public class LudumDare2022 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Camera camera;
	UiController uiController;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		uiController = new UiController();
	}

	@Override
	public void render () {
		// Step Methods called here for controllers
		uiController.step();

		camera.update();

		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Render methods called here for controllers
		uiController.render(batch, camera);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();

		uiController.dispose();
	}
}
