package kingswood.idphoto;

import kingswood.idphoto.log.AppLogger;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class BasicActivity extends Activity {

	private Camera mCamera = null;
	private CameraPreview mCameraPreview = null;
	private Button btnChooseSize = null;
	private Button btnTakePhoto = null;

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

		// add button listeners
		registerBtnListeners();

	}
	
	@Override
	protected void onPause(){
		AppLogger.log("calling BasicActivity.onPause() method");
		
		super.onPause();
		
		mCamera.release();
	}

	@Override
	protected void onResume() {

		AppLogger.log("calling onResume method");
		
		super.onResume();

		//initCameraInstance();

		mCameraPreview = new CameraPreview(this, mCamera);
		FrameLayout previewFrame = (FrameLayout) findViewById(R.id.camera_preview);
		previewFrame.addView(mCameraPreview);
	}

	private void registerBtnListeners() {

		btnChooseSize = (Button) findViewById(R.id.btn_choosesize);
		btnChooseSize.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(getBaseContext(), ChooseSizeActivity.class);
				startActivity(intent);

				overridePendingTransition(R.anim.right_in, R.anim.left_out);

			}
		});

		btnTakePhoto = (Button) findViewById(R.id.btn_take_photo);
		btnTakePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				PictureCallback callback = new PhotoCallBack();
				
				mCamera.takePicture(null, null, callback);

			}
		});

	}
	
	

	private void initialDpi() {
		AppLogger.log("initialDpi()...");
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Runtime.X_DPI = dm.xdpi;
		Runtime.Y_DPI = dm.ydpi;
		AppLogger.log("xdpi: " + Runtime.X_DPI + " ydpi: " + Runtime.Y_DPI);
	}

	public void initCameraInstance() {
		try {
			mCamera = Camera.open(); // attempt to get a Camera instance
			// setup camera parameter
			Camera.Parameters parameters = mCamera.getParameters();
			if(parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
			}
			
			mCamera.autoFocus(new Camera.AutoFocusCallback() {
				
				@Override
				public void onAutoFocus(boolean success, Camera camera) {
					// TODO Auto-generated method stub
					if(success){
						AppLogger.log("Auto focus success.");
					}else{
						AppLogger.log("Auto focus failed.");
					}
				}
			});
			
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
