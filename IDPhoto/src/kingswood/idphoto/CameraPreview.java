package kingswood.idphoto;

import java.io.IOException;
import java.util.List;

import kingswood.idphoto.log.AppLogger;
import kingswood.idphoto.util.DimensionConvertor;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;
	
	private List<Camera.Size> mSupportedPreviewSizes;
	private Size mPreviewSize;
	
	private int viewWidth = 500;
	private int viewHeight = 700;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, now tell the camera where to draw the
		// preview.
		try {
			
			AppLogger.log("calling surfaceCreated method");
			
			mCamera.setDisplayOrientation(90);
			
			
			Camera.Parameters parameters = mCamera.getParameters();
			parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
			
			mCamera.setParameters(parameters);
			
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			AppLogger.log("Error setting camera preview: " + e.getMessage());
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// empty. Take care of releasing the Camera preview in your activity.
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		
		
		
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or reformatting changes
		// here

		
		/*
		Camera.Parameters parameters = mCamera.getParameters();
		Size size = parameters.getPreviewSize();
		AppLogger.log("width : " + size.width);
		AppLogger.log("height : " + size.height);
		parameters.setPreviewSize(500, 900);
		mCamera.setParameters(parameters);
		*/

		// start preview with new settings
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();

		} catch (Exception e) {
			AppLogger.log("Error starting camera preview: " + e.getMessage());
		}
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		AppLogger.log("Calling onMeasure method.");
		
		if(null == mCamera){
			try {
				mCamera = Camera.open(); // attempt to get a Camera instance
			} catch (Exception e) {
				e.printStackTrace();
				AppLogger.log(e.getMessage());
				// Camera is not available (in use or does not exist)
			}
		}
		
		mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
		
		final int width = resolveSize(getSuggestedMinimumWidth(),
				widthMeasureSpec);
		final int height = resolveSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);
		
		

		if (mSupportedPreviewSizes != null) {
			mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width,
					height);
		}
		
		AppLogger.log("mPreviewSize.width: " + mPreviewSize.width);
		AppLogger.log("mPreviewSize.height: " + mPreviewSize.height);
		
		if(Runtime.CAMERA_PREVIEW_WIDTH > 0 && Runtime.CAMERA_PREVIEW_HEIGHT > 0){
			
			int tmpWidth = (int)DimensionConvertor.convertCMToDpi(Runtime.CAMERA_PREVIEW_WIDTH);
			int tmpHeight = (int)DimensionConvertor.convertCMToDpi(Runtime.CAMERA_PREVIEW_HEIGHT);
			
			setMeasuredDimension(tmpWidth, tmpHeight);
			
		}
		
		//setMeasuredDimension(viewWidth, viewHeight);
	}
	
	
	private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w,
			int h) {
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) h / w;

		if (sizes == null)
			return null;

		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		for (Camera.Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public void setViewWidth(int viewWidth) {
		this.viewWidth = viewWidth;
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public void setViewHeight(int viewHeight) {
		this.viewHeight = viewHeight;
	}
	
	
}
