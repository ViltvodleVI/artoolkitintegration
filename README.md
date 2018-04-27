#Artoolkit Integration

Just a simple ARToolkit/Libgdx integration.

- ARToolkit: http://www.artoolkitx.org
- LibGDX: https://libgdx.badlogicgames.com
- TrancsendentAR: https://glud.github.io/trascendentAR


####Example
#####Adding a marker


```java
public ArtoolkitIntegration(MarkerDetection markerdetection){
	this.markerDetection = markerDetection;
}

@Override
public void create(){
	this.markerDetection.addMarker("single;markername.patt; markerSize");
	...
	}
```
#####Query marker transform


```java
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
                transform.scale(25, 25, 25);
                modelInstance.transform.set(transform);
				
                modelBatch.begin(arCamera);
                modelBatch.render(modelInstance, environment);
                modelBatch.end();
				
            }
        }
    }
``` 
###END
