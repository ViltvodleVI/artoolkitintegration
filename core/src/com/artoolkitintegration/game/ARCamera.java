package com.artoolkitintegration.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;


public class ARCamera extends Camera {

    public float fieldOfView = 67;

    public ARCamera(float fieldOfViewY, float viewportWidth, float viewportHeight) {
        this.fieldOfView = fieldOfViewY;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        update();
    }

    final Vector3 tmp = new Vector3();

    @Override
    public void update () {
        update(true);
    }

    @Override
    public void update (boolean updateFrustum) {
        view.setToLookAt(position, tmp.set(position).add(direction), up);
        combined.set(projection);
        Matrix4.mul(combined.val, view.val);

        if (updateFrustum) {
            invProjectionView.set(combined);
            Matrix4.inv(invProjectionView.val);
            frustum.update(invProjectionView);
        }
    }
}
