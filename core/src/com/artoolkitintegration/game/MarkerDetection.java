package com.artoolkitintegration.game;


public interface MarkerDetection {


    public int addMarker(String markerPath);

    public void initializeNative(String assetsFolderBasePath);

    boolean initializeAr(int videoWidth, int videoHeight, int rate, String pixelFormat, String cameraParaPath, int cameraIndex, boolean cameraIsFrontFacing);

    boolean queryMarkerVisible(int markerID);

    float[] queryMarkerTransformation(int id);

    float[] getProjectionMatrix();

    int[] getMarkers();

    void cleanup();

}
