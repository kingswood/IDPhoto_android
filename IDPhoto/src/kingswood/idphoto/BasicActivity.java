package kingswood.idphoto;

import kingswood.idphoto.log.AppLogger;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class BasicActivity extends Activity {

	private Camera mCamera = null;
	private CameraPreview mCameraPreview = null;
	private Button btn1 = null;
	private float xdpi;
	private float ydpi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);

		initCameraInstance();

		mCameraPreview = new CameraPreview(this, mCamera);
		FrameLayout previewFrame = (FrameLayout) findViewById(R.id.camera_preview);
		previewFrame.addView(mCameraPreview);
		
		// get x and y DPI of the device
		initialDpi();
		
		// get button instance
		btn1 = (Button)findViewById(R.id.btnChangeSize);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeCameraViewSize(2.5, 3.5);
			}
		});
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int densityDpi = (int)(metrics.density * 160f);
		AppLogger.log("densityDpi: " + densityDpi);
	}
	
	private void initialDpi(){
		AppLogger.log("initialDpi()...");
		DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    xdpi = dm.xdpi;
	    ydpi = dm.ydpi;
	    AppLogger.log("xdpi: " + xdpi + " ydpi: " + ydpi);
	}
	
	private void changeCameraViewSize(double widthInCM, double heightInCM){
		
		AppLogger.log("Changing camera view size to : " + widthInCM + " cm X " + heightInCM + " cm");
		
		double widthInPixels = xdpi * widthInCM / 2.54;
		double heightInPixels = ydpi * heightInCM / 2.54;
		
		mCameraPreview.setViewWidth((int)widthInPixels);
		mCameraPreview.setViewHeight((int)heightInPixels);
		
		
		mCameraPreview.invalidate();
		
		//LinearLayout frameLayout = (LinearLayout)findViewById(R.id.root_layout);
		
		ViewGroup.LayoutParams params = mCameraPreview.getLayoutParams();                     
        params.width = (int)widthInPixels;
        params.height = (int)heightInPixels;
        mCameraPreview.setLayoutParams(params);
		
		//frameLayout.invalidate();
	}

	/*private void printScreenDimenssion() {
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);

		// since SDK_INT = 1;
		int mWidthPixels = displayMetrics.widthPixels;
		int mHeightPixels = displayMetrics.heightPixels;

		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
			try {
				mWidthPixels = (Integer) Display.class.getMethod("getRawWidth")
						.invoke(display);
				mHeightPixels = (Integer) Display.class.getMethod(
						"getRawHeight").invoke(display);
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
		}

		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 17) {
			try {
				Point realSize = new Point();
				Display.class.getMethod("getRealSize", Point.class).invoke(
						display, realSize);
				mWidthPixels = realSize.x;
				mHeightPixels = realSize.y;
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
		}
		
		AppLogger.log("mWidthPixels: " + mWidthPixels);
		AppLogger.log("mHeightPixels: " + mHeightPixels);
		
		
	    double x = mWidthPixels/dm.xdpi;
	    double y = mHeightPixels/dm.ydpi;
	    //double screenInches = Math.sqrt(x+y);
	    AppLogger.log("screen width = : " + x + " " + x * 2.54 + " cm");
	    AppLogger.log("screen height = : " + y + " " + y * 2.54 + " cm"); 

	}*/

	private void setCameraDimension(int width, int height) {
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(width, height);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

	public void initCameraInstance() {
		try {
			mCamera = Camera.open(); // attempt to get a Camera instance
		} catch (Exception e) {
			e.printStackTrace();
			AppLogger.log(e.getMessage());
			// Camera is not available (in use or does not exist)
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// doesn't need a menu
		return false;
	}

}
