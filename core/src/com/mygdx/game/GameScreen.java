package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;


public class GameScreen implements Screen {
    final MyGdxGame game;
    Texture ghostImage, stickImage;
    Rectangle ghost, stick;
    OrthographicCamera camera;
    float dy, dx, dy_s, dx_s, magnitude;
    float dTime;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        ghostImage = new Texture(Gdx.files.internal("ghost.png"));
        stickImage = new Texture(Gdx.files.internal("stickfigure.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        ghost = new Rectangle();
        ghost.x = 800/2 - 64/2;
        ghost.y = 20;
        ghost.width = ghost.height = 64;

        stick = new Rectangle();
        stick.x = stick.y = 0;
        stick.width = stick.height = 64;
        dy = 1;
        dx = 2;
        dTime = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        ghost.x += dx;
        ghost.y += dy;
        if((dTime += Gdx.graphics.getDeltaTime()) > 1) {
            dx *= 1.05;
            dy *= 1.05;
            dTime = 0;
        }
        if((ghost.x > 800) || (ghost.x < 0))
            dx = -dx;
        if ((ghost.y > 480) || (ghost.y < 0))
            dy = -dy;

        stick.x += dx_s;
        stick.y += dy_s;




        game.batch.begin();
        game.batch.draw(ghostImage, ghost.x, ghost.y, 64f, 64f);
        game.batch.draw(stickImage, stick.x, stick.y, 64f, 64f);
        game.batch.end();


        if (Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            dx_s = touchPos.x - stick.x;
            dy_s = touchPos.y - stick.y;

            magnitude = (float) Math.sqrt(Math.pow(dx_s, 2) + Math.pow(dy_s, 2));
            dx_s =  dx_s / magnitude * 5;
            dy_s =  dy_s / magnitude * 5;
        }
        if (ghost.overlaps(stick)){
            System.out.println("testing");
            game.setScreen(new EndScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {

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
