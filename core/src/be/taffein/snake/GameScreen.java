package be.taffein.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;

public class GameScreen implements Screen {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

    private static final int WORLD_HEIGHT = 438;
    private static final int WORLD_WIDTH = 878;
    private static final int SNAKE_PART_SIZE = 20;

    private Snake game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private Viewport viewport;
    private long timeSinceLastStep;
    private LinkedList<Vector2> snake;
    private Direction direction = Direction.RIGHT;

    public GameScreen(Snake game) {
        this.game = game;
        this.batch = game.getBatch();
        this.shapeRenderer = game.getShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();

        snake = new LinkedList<Vector2>();
        snake.add(new Vector2(2, 0));
        snake.add(new Vector2(1, 0));
        snake.add(new Vector2(0, 0));
        timeSinceLastStep = System.currentTimeMillis();

        this.shapeRenderer.setColor(Color.WHITE);
        this.shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // draw snake
        for (Vector2 vector2 : snake) {
            shapeRenderer.rect(vector2.x * 22, vector2.y * 22, SNAKE_PART_SIZE, SNAKE_PART_SIZE);
        }

        // move snake?
        if (System.currentTimeMillis() - timeSinceLastStep > 400) {
            moveSnake();
        }

        readInput();

        shapeRenderer.end();
    }

    private void moveSnake() {
        Vector2 newHead = snake.removeLast();

        switch (direction) {
            case UP:
                newHead.x = snake.getFirst().x;
                newHead.y = snake.getFirst().y + 1;
                break;
            case DOWN:
                newHead.x = snake.getFirst().x;
                newHead.y = snake.getFirst().y - 1;
                break;
            case LEFT:
                newHead.x = snake.getFirst().x - 1;
                newHead.y = snake.getFirst().y;
                break;
            case RIGHT:
                newHead.x = snake.getFirst().x + 1;
                newHead.y = snake.getFirst().y;
                break;
        }

        snake.addFirst(newHead);
        timeSinceLastStep = System.currentTimeMillis();
    }

    private void readInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction = Direction.UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction = Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction = Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction = Direction.RIGHT;
        }
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
