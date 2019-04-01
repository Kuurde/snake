package be.taffein.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private static final int SNAKE_PART_SIZE = 20;

    private Snake game;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private Viewport viewport;

    private float snakeX = 0;
    private float snakeY= 0;

    private double t = 0.0;
    private final double dt = 0.5;
    double currentTime = TimeUtils.millis() / 1000.0;
    double accumulator = 0.0;

    public GameScreen(Snake game) {
        this.game = game;
        this.shapeRenderer = game.getShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        viewport.apply(true);

        this.shapeRenderer.setColor(Color.BLACK);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        double newTime = TimeUtils.millis() / 1000.0;
        double frameTime = newTime - currentTime;
        currentTime = newTime;
        accumulator += frameTime;

        while (accumulator >= dt) {
            doPhysics();
            accumulator -= dt;
            t += dt;
        }

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(snakeX * SNAKE_PART_SIZE, snakeY * SNAKE_PART_SIZE, SNAKE_PART_SIZE, SNAKE_PART_SIZE);
        shapeRenderer.rect((snakeX + 1) * SNAKE_PART_SIZE, snakeY * SNAKE_PART_SIZE, SNAKE_PART_SIZE, SNAKE_PART_SIZE);
        shapeRenderer.end();
    }

    private void doPhysics() {
        snakeX++;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
