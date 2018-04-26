package com.artoolkitintegration.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;

public class ArtoolkitIntegration extends ApplicationAdapter {

    ModelBuilder modelBuilder;
    ModelBatch modelBatch;
    Environment environment;
    Model model;
    ModelInstance modelInstance;
    MarkerDetection markerDetection;
    ARCamera arCamera;
    Matrix4 transform = new Matrix4();


    public ArtoolkitIntegration(MarkerDetection markerDetection) {
        this.markerDetection = markerDetection;
    }

    @Override
    public void create() {

        markerDetection.addMarker("single;hiro.patt;80");

        arCamera = new ARCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        arCamera.position.set(0f, 0f, 1f);
        arCamera.lookAt(0, 0, 0);
        arCamera.near = 0;
        arCamera.far = 1000f;


        modelBatch = new ModelBatch();
        modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5, 5, 5, new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance = new ModelInstance(model);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        arCamera.update();

        float[] prjmat = markerDetection.getProjectionMatrix();

        if (prjmat != null && markerDetection.queryMarkerVisible(0)) {
            Matrix4 tmpProj = new Matrix4(prjmat);
            arCamera.projection.set(tmpProj);


            float[] tm = markerDetection.queryMarkerTransformation(0);
            if (tm != null) {

                transform.set(tm);
                transform.getTranslation(arCamera.position);
                arCamera.position.scl(-1);


                arCamera.update(true);

                transform.rotate(1, 0, 0, 90);
                //transform.scale(10, 10, 10);
                transform.scale(25, 25, 25);
                modelInstance.transform.set(transform);

                modelBatch.begin(arCamera);
                modelBatch.render(modelInstance, environment);
                modelBatch.end();
            }
        }

    }

    @Override
    public void dispose() {
        modelBatch.dispose();

    }
}
