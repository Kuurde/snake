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
import com.badlogic.gdx.math.MathUtils;
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
    private Vector2 food;

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
        food = new Vector2(MathUtils.random(-20, 19), MathUtils.random(-10, 9));

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

        // draw food
        shapeRenderer.rect(food.x * 22, food.y * 22, SNAKE_PART_SIZE, SNAKE_PART_SIZE);

        // move snake?
        if (System.currentTimeMillis() - timeSinceLastStep > 200) {
            moveSnake();
        }

        readInput();

        shapeRenderer.end();
    }

    private void moveSnake() {
        Vector2 newHead;
        if (snake.getFirst().equals(food)) {
            newHead = new Vector2();
            food = new Vector2(MathUtils.random(-20, 19), MathUtils.random(-10, 9));
        } else {
            newHead = snake.removeLast();
        }

        switch (direction) {
            case UP:
                newHead.x = snake.getFirst().x;
                if (snake.getFirst().y == 9) {
                    newHead.y = -10;
                } else {
                    newHead.y = snake.getFirst().y + 1;
                }
                break;
            case DOWN:
                newHead.x = snake.getFirst().x;
                if (snake.getFirst().y == -10) {
                    newHead.y = 9;
                } else {
                    newHead.y = snake.getFirst().y - 1;
                }
                break;
            case LEFT:
                if (snake.getFirst().x == -20) {
                    newHead.x = 19;
                } else {
                    newHead.x = snake.getFirst().x - 1;
                }
                newHead.y = snake.getFirst().y;
                break;
            case RIGHT:
                if (snake.getFirst().x == 19) {
                    newHead.x = -20;
                } else {
                    newHead.x = snake.getFirst().x + 1;
                }
                newHead.y = snake.getFirst().y;
                break;
        }

        snake.addFirst(newHead);
        timeSinceLastStep = System.currentTimeMillis();
    }

    private void readInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && direction != Direction.DOWN) {
            direction = Direction.UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && direction != Direction.UP) {
            direction = Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && direction != Direction.LEFT) {
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
