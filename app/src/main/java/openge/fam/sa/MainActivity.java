package openge.fam.sa;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new SqrGLSurfaceView(this);
        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        super.onPause();
        mGLView.onPause();
    }


    @Override
    protected void onResume() {
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        super.onResume();
        mGLView.onResume();
    }


}

// Write a my surfaceview
class SqrGLSurfaceView extends GLSurfaceView {

    private final SqrLRenderer mRenderer;

    public SqrGLSurfaceView(Context context) {
        super(context);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new SqrLRenderer();
        setRenderer(mRenderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    class SqrLRenderer implements GLSurfaceView.Renderer {

        // take the square object
        private Square mSquare;
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
           //set up background
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            mSquare = new Square();
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            // Draw background color
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
             // Set GL_MODELVIEW transformation mode
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();   // reset the matrix to its default state

            // When using GL_MODELVIEW, you must set the view point
            GLU.gluLookAt(gl, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            // Draw square
            mSquare.draw(gl);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // Adjust the viewport based on geometry changes
            // such as screen rotations
            gl.glViewport(0, 0, width, height);
            // make adjustments for screen ratio
            float ratio = (float) width / height;
            gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
            gl.glLoadIdentity();                        // reset the matrix to its default state
            gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
        }

    }

}
