package kingswood.idphoto;

import java.util.List;

import kingswood.idphoto.vo.PhotoSize;
import kingswood.idphoto.vo.PreDefinedPhotoSizes;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ChooseSizeActivity extends Activity implements OnClickListener {

	/*Button btn1Inch = null;
	Button btn2Inch = null;
	Button btnCustomize = null;*/
	
	private TextView sizeHeader = null;
	private TextView sizeDetail = null;
	
	private LinearLayout sizeView = null;
	private Button[] buttons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosesize);
		
		
		/*btn1Inch = (Button)findViewById(R.id.btn_1inch);
		btn2Inch = (Button)findViewById(R.id.btn_2inch);
		btnCustomize = (Button)findViewById(R.id.btn_customize_size);*/
		
		// initialize text views
		sizeHeader = (TextView)findViewById(R.id.current_size_header);
		sizeDetail = (TextView)findViewById(R.id.current_size_detail);
		
		loadSizes();
		
		initiateAnimationOnButton(0);
		
		//setButtonText();
		
		//registerBtnListeners();

	}
	
	private void loadSizes(){
		
		sizeView = (LinearLayout) findViewById(R.id.size_btn_layout);
		
		List<PhotoSize> sizes = PreDefinedPhotoSizes.getInstance().getPhotoSizes();
		
		if(null != sizes && sizes.size() > 0){
			
			buttons = new Button[sizes.size()];
			
			for(int i = 0;i<sizes.size();i++){
				
				PhotoSize photoSize = sizes.get(i);
				
				// button layout
				Button button = new Button(this.getBaseContext());
				
				button.setId(photoSize.getId());
				button.setText(photoSize.getWidth() + " mm * " + photoSize.getHeight() + " mm  " + photoSize.getDesc());
				
				if(photoSize.getWidth() == 0){
					button.setText(photoSize.getDesc());
				}
				
				LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(0, 0, 0, 1);
				
				button.setLayoutParams(layoutParams);
				
				button.setBackgroundColor(getResources().getColor(R.color.gray_99Transparent));
				button.setPadding(0, 50, 0, 50);
				button.setGravity(Gravity.CENTER);
				
				button.setAlpha(0.0f);
				
				//add listener
				button.setOnClickListener(this);
				
				buttons[i] = button;
				
				// fade in animation
				//Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
				//button.startAnimation(fadeInAnimation);
				//fadeInAnimation.setAnimationListener(this);
				
				
				sizeView.addView(button);
			}
			
		}else{
			// would not happen
			return;
		}
	}
	
	private void initiateAnimationOnButton(final int buttonIndex)
    {
		// fade in animation
		Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        if (buttonIndex < buttons.length - 1)
        {
        	fadeInAnimation.setAnimationListener(new TranslateAnimation.AnimationListener() 
            {

                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationRepeat(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) 
                {
                    initiateAnimationOnButton(buttonIndex + 1);                         
                }
            });
        }

        Button btn = buttons[buttonIndex];
        btn.setAlpha(1.0f);
        btn.startAnimation(fadeInAnimation);
    }
	
	@Override
	protected void onResume() {
		
		super.onResume();
		
		sizeHeader.setText(Runtime.HEADER_TEXT1);
		sizeDetail.setText(Runtime.HEADER_TEXT2);
	}
	
	/*private void setButtonText(){
		
		btn1Inch.setText(getButtonsText(getString(R.string.btn_1_inch), getString(R.string.btn_1_inch_2_5x3_5)));
		
		btn2Inch.setText(getButtonsText(getString(R.string.btn_2_inch), getString(R.string.btn_2_inch_3_5x4_9)));
		
		btnCustomize.setText(getButtonsText(getString(R.string.btn_customize_inch), ""));
	}*/
	
	private SpannableStringBuilder getButtonsText(String str1, String str2){
		
		
		SpannableString spannableString1 = new SpannableString(str1);
		
		spannableString1.setSpan(new AbsoluteSizeSpan(50, true), 0, str1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		
		SpannableString spannableString2 = new SpannableString("\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020" + str2);
		
		spannableString1.setSpan(new AbsoluteSizeSpan(25, true), 0, str1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		
		SpannableStringBuilder ssb = new SpannableStringBuilder();
		
	    ssb.append(spannableString1).append(spannableString2);
	    
	    return ssb;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if(id == -1){
			// custom button
		}else{
			PhotoSize photoSize = PreDefinedPhotoSizes.getInstance().getPhotoSize(id);
			if(photoSize.getWidth() > 0 && photoSize.getHeight() > 0){
				
				Runtime.CAMERA_PREVIEW_WIDTH = photoSize.getWidth() / 10;
				Runtime.CAMERA_PREVIEW_HEIGHT = photoSize.getHeight() / 10;
				
				Runtime.HEADER_TEXT1 = photoSize.getWidth() + " mm X " + photoSize.getHeight() + " mm"; // TODO
				
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), BasicActivity.class);
				startActivity(intent);

				overridePendingTransition(R.anim.right_out, R.anim.left_in);
			}
		}
		
	}

	/*private void registerBtnListeners() {
		
		btn1Inch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Runtime.CAMERA_PREVIEW_WIDTH = 2.5f;
				Runtime.CAMERA_PREVIEW_HEIGHT = 3.5f;
				
				sizeHeader.setText(R.string.btn_1_inch);
				sizeDetail.setText(R.string.btn_1_inch_2_5x3_5);
				
				Runtime.HEADER_TEXT1 = getString(R.string.btn_1_inch);
				Runtime.HEADER_TEXT2 = getString(R.string.btn_1_inch_2_5x3_5);
				
			}
		});
		
		btn2Inch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Runtime.CAMERA_PREVIEW_WIDTH = 3.5f;
				Runtime.CAMERA_PREVIEW_HEIGHT = 4.9f;
				
				AppLogger.log("Setting HEADER_TEXT values");
				sizeHeader.setText(R.string.btn_2_inch);
				sizeDetail.setText(R.string.btn_2_inch_3_5x4_9);
				
			}
		});

		
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(getBaseContext(), BasicActivity.class);
				startActivity(intent);

				overridePendingTransition(R.anim.right_out, R.anim.left_in);

			}
		});
		
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Runtime.HEADER_TEXT1 = sizeHeader.getText().toString();
				Runtime.HEADER_TEXT2 = sizeDetail.getText().toString();

				AppLogger.log("HEADER_TEXT1 : " + Runtime.HEADER_TEXT1);
				AppLogger.log("HEADER_TEXT2 : " + Runtime.HEADER_TEXT2);
				
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), BasicActivity.class);
				startActivity(intent);

				overridePendingTransition(R.anim.right_out, R.anim.left_in);

			}
		});

	}*/
	
	
	
	
	
	

}
