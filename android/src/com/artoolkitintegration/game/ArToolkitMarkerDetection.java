package com.artoolkitintegration.game;

import android.preference.PreferenceManager;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;

import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.camera.CameraEventListener;


public class ArToolkitMarkerDetection implements MarkerDetection, CameraEventListener {

    AndroidApplication androidLauncher;
    PreferenceManager preferenceManager;

    public ArToolkitMarkerDetection(AndroidApplication androidLauncher) {
        this.androidLauncher = androidLauncher;

    }

    @Override
    public int addMarker(String markerPath) {
        return ARToolKit.getInstance().addMarker(markerPath);
    }

    @Override
    public void initializeNative(String assetsFolderBasePath) {
        ARToolKit.getInstance().initialiseNative(assetsFolderBasePath);
    }

    @Override
    public boolean initializeAr(int videoWidth, int videoHeight, int rate, String pixelFormat, String cameraParaPath, int cameraIndex, boolean cameraIsFrontFacing) {
        boolean inited = false;
        if(ARToolKit.getInstance().startWithPushedVideo(videoWidth, videoHeight, pixelFormat,cameraParaPath, cameraIndex, cameraIsFrontFacing)){
            Log.i("", "getGLView(): Camera initialised");
            inited = true;
        } else {
            Log.e("", "getGLView(): Error initialising camera. Cannot continue.");
        }
        return inited;
    }

    @Override
    public boolean queryMarkerVisible(int markerID) {
        return ARToolKit.getInstance().queryMarkerVisible(markerID);

    }

    @Override
    public float[] queryMarkerTransformation(int id) {
        return ARToolKit.getInstance().queryMarkerTransformation(id);
    }

    @Override
    public float[] getProjectionMatrix() {
        return ARToolKit.getInstance().getProjectionMatrix();
    }

    @Override
    public int[] getMarkers() {
        return new int[0];
    }

    @Override
    public void cleanup() {

        ARToolKit.getInstance().stopAndFinal();

    }


    @Override
    public void cameraPreviewStarted(int width, int height, int rate, String pixelFormat, int cameraIndex, boolean cameraIsFrontFacing) {
        this.initializeNative(Gdx.files.getLocalStoragePath() +  "Data");
        this.initializeAr(width , height, rate,pixelFormat, "camera_para.dat", 0, cameraIsFrontFacing);
        addMarker("single;hiro.patt;80");
    }


    @Override
    public void cameraPreviewFrame(byte[] bytes, int frameSize) {
        ARToolKit.getInstance().convertAndDetect1(bytes,frameSize);
    }

    @Override
    public void cameraPreviewStopped() {
        this.cleanup();
    }
}
