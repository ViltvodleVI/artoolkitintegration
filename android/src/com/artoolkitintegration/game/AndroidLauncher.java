package com.artoolkitintegration.game;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;

import org.artoolkit.ar.base.camera.CaptureCameraPreview;

public class AndroidLauncher extends AndroidApplication {

	private FrameLayout mainLayout;
	private CaptureCameraPreview preview;
	private View gameView;

	private ArToolkitMarkerDetection markerDetection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.r = 8;
		config.g = 8;
		config.b = 8;
		config.a = 8;

		mainLayout = new FrameLayout(this);
		markerDetection = new ArToolkitMarkerDetection(this);

		gameView = initializeForView(new ArtoolkitIntegration(markerDetection), config);

		if (graphics.getView() instanceof SurfaceView) {
			SurfaceView glView = (SurfaceView) graphics.getView();
			glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		}

		String dataFolder = "Data";
		boolean dataFolderExist = Gdx.files.local(dataFolder).exists();

		if (!dataFolderExist) {
			Gdx.files.local(dataFolder).mkdirs();
		}
		FileHandle localDataFolder = Gdx.files.local(dataFolder);
		//copy the sample marker and object
		FileHandle[] files = Gdx.files.internal(dataFolder + "/").list();
		for (FileHandle f : files) {
			f.copyTo(Gdx.files.local(localDataFolder.path()));
		}

		graphics.getView().setKeepScreenOn(true);
		this.setContentView(mainLayout);
	}


	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	protected void onPause() {
		super.onPause();
		mainLayout.removeView(gameView);
		mainLayout.removeView(preview);

	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}


	@Override
	protected void onResume() {
		super.onResume();
		preview = new CaptureCameraPreview(this, markerDetection);
		preview.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		SurfaceView glView = (SurfaceView) gameView;
		glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
		mainLayout.addView(gameView, layoutParams);
		mainLayout.addView(preview, layoutParams);
	}


}
