/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.WorldRenderer;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class GameScreen extends ScreenAdapter {
	
//	static final float GAME_WIDTH = Assets.backgroundRegion.getRegionWidth();
//	static final float GAME_HEIGHT = Assets.backgroundRegion.getRegionHeight();	
	
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	BrickBreaker game;

	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;	
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	//int lastScore;
	//String scoreString;

	public GameScreen (BrickBreaker game) {
		this.game = game;

		state = GAME_READY;
		
		// We need to have a target resolution, e.g. 320 x 480
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		
//		guiCam = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
//		guiCam.position.set(GAME_WIDTH / 2, GAME_HEIGHT / 2, 0);
		
		touchPoint = new Vector3();
		
		worldListener = new WorldListener() {
			@Override
			public void hitWall () {
				Assets.playSound(Assets.stepSound);
			}

			@Override
			public void hitPaddle () {
				Assets.playSound(Assets.correctSound);
			}

			@Override
			public void hitBrick () {
				Assets.playSound(Assets.clickSound);
			}
			
			@Override
			public void loseLife () {
				Assets.playSound(Assets.incorrectSound);
			}

		};
		
		world = new World(worldListener);
		renderer = new WorldRenderer(game.batcher, world);
		//pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		//resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
		//quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);
		//lastScore = 0;
		//scoreString = "SCORE: 0";
	}

	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		updateRunning(deltaTime);
		
//		switch (state) {
//		case GAME_READY:
//			updateReady();
//			break;
//		case GAME_RUNNING:
//			updateRunning(deltaTime);
//			break;
//		case GAME_PAUSED:
//			updatePaused();
//			break;
//		case GAME_LEVEL_END:
//			updateLevelEnd();
//			break;
//		case GAME_OVER:
//			updateGameOver();
//			break;
//		}
	}

	private void updateReady () {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning (float deltaTime) {
//		world.update(deltaTime, guiCam);
		world.update(deltaTime, Gdx.input.getAccelerometerX());
		
		/*
		if (world.score != lastScore) {
			lastScore = world.score;
			scoreString = "SCORE: " + lastScore;
		}
		if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
			game.setScreen(new WinScreen(game));
		}
		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			if (lastScore >= Settings.highscores[4])
				scoreString = "NEW HIGHSCORE: " + lastScore;
			else
				scoreString = "SCORE: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save();
		}
		*/
	}


	private void updatePaused () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

//			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
//				Assets.playSound(Assets.clickSound);
//				state = GAME_RUNNING;
//				return;
//			}
//
//			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
//				Assets.playSound(Assets.clickSound);
//				game.setScreen(new MainMenuScreen(game));
//				return;
//			}
		}
	}

	private void updateLevelEnd () {
//		if (Gdx.input.justTouched()) {
//			world = new World(worldListener);
//			renderer = new WorldRenderer(game.batcher, world);
//			world.score = lastScore;
//			state = GAME_READY;
//		}
	}

	private void updateGameOver () {
//		if (Gdx.input.justTouched()) {
//			game.setScreen(new MainMenuScreen(game));
//		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/*Render Objects in screen*/
		renderer.render();

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		
		presentRunning();
		
		/*
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}*/
		
		game.batcher.end();
	}

	private void presentReady () {
		//game.batcher.draw(Assets.ready, 160 - 192 / 2, 240 - 32 / 2, 192, 32);
	}

	private void presentRunning () {
//		game.batcher.draw(Assets.pause, 320 - 64, 480 - 64, 64, 64);
//		Assets.font.draw(game.batcher, scoreString, 16, 480 - 20);
	}

	private void presentPaused () {
//		game.batcher.draw(Assets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
//		Assets.font.draw(game.batcher, scoreString, 16, 480 - 20);
	}

	private void presentLevelEnd () {
//		String topText = "the princess is ...";
//		String bottomText = "in another castle!";
//		float topWidth = Assets.font.getBounds(topText).width;
//		float bottomWidth = Assets.font.getBounds(bottomText).width;
//		Assets.font.draw(game.batcher, topText, 160 - topWidth / 2, 480 - 40);
//		Assets.font.draw(game.batcher, bottomText, 160 - bottomWidth / 2, 40);
	}

	private void presentGameOver () {
//		game.batcher.draw(Assets.gameOver, 160 - 160 / 2, 240 - 96 / 2, 160, 96);
//		float scoreWidth = Assets.font.getBounds(scoreString).width;
//		Assets.font.draw(game.batcher, scoreString, 160 - scoreWidth / 2, 480 - 20);
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause () {
		if (state == GAME_RUNNING) state = GAME_PAUSED;
	}
}