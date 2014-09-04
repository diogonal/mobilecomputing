package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class OptionScreen extends ScreenAdapter {
	BrickBreaker game;
	OrthographicCamera guiCam;

	Rectangle soundBounds, musicBounds, gyrosBounds, backBounds;

	Vector3 touchPoint;

	public static Texture btnsound, btnmusic, btngyros, btnback;
	public int screenWidth, screenHeight, btnsizeWidth, btnsizeHeight,
			btnseparation;

	/* BUTTONS */

	public OptionScreen(BrickBreaker game) {
		this.game = game;
		screenWidth = 320;
		screenHeight = 480;
		btnsizeWidth = 300;
		btnsizeHeight = 32;
		btnseparation = 8;

		guiCam = new OrthographicCamera(screenWidth, screenHeight);
		guiCam.position.set(screenWidth / 2, screenHeight / 2, 0);
		soundBounds = new Rectangle(10, 264, 300, 30);
		musicBounds = new Rectangle(10, 220, 300, 30);
		gyrosBounds = new Rectangle(10, 176, 300, 30);
		backBounds = new Rectangle(10, 10, 32, 32);

		touchPoint = new Vector3();

		btnsound = new Texture("buttons/btn_sound.png");
		btnmusic = new Texture("buttons/btn_music.png");
		btngyros = new Texture("buttons/btn_gyros.png");
		btnback = new Texture("buttons/btn_back.png");

	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Gdx.app.log("deactivate sound", "deactivate sound");

				return;
			}

			if (musicBounds.contains(touchPoint.x, touchPoint.y)) {
				Gdx.app.log("deactivate music", "deactivate music");

				return;
			}
			if (gyrosBounds.contains(touchPoint.x, touchPoint.y)) {

				Gdx.app.log("deactivate gyros", "deactivate gyros");
				return;
			}

			if (backBounds.contains(touchPoint.x, touchPoint.y)) {

				game.setScreen(new MenuScreen(game));
				return;
			}

			// if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
			// // Assets.playSound(Assets.clickSound);
			// // Settings.soundEnabled = !Settings.soundEnabled;
			// // if (Settings.soundEnabled)
			// // Assets.music.play();
			// // else
			// // Assets.music.pause();
			// }
		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.menuScreen, 0, 0, 320, 480);
		game.batcher.draw(btnsound, soundBounds.x, soundBounds.y,
				soundBounds.width, soundBounds.height);
		game.batcher.draw(btnmusic, musicBounds.x, musicBounds.y,
				musicBounds.width, musicBounds.height);
		game.batcher.draw(btngyros, gyrosBounds.x, gyrosBounds.y,
				gyrosBounds.width, gyrosBounds.height);
		game.batcher.draw(btnback, backBounds.x, backBounds.y,
				backBounds.width, backBounds.height);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();

		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	@Override
	public void pause() {
		// Settings.save();
	}
}
