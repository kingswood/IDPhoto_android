package kingswood.idphoto;

import java.util.ArrayList;
import java.util.List;

import kingswood.idphoto.vo.PhotoSize;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ChooseSizeActivity extends Activity {

	Button btnCancel = null;
	Button btnOk = null;
	Button btn1Inch = null;
	Button btn2Inch = null;
	Button btnCustomize = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosesize);
		
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnOk = (Button) findViewById(R.id.btn_ok);
		
		btn1Inch = (Button)findViewById(R.id.btn_1inch);
		btn2Inch = (Button)findViewById(R.id.btn_2inch);
		btnCustomize = (Button)findViewById(R.id.btn_customize_size);
		
		setButtonText();
		
		registerBtnListeners();

	}
	
	private void setButtonText(){
		
		btn1Inch.setText(getButtonsText(getString(R.string.btn_1_inch), getString(R.string.btn_1_inch_2_5x3_5)));
		
		btn2Inch.setText(getButtonsText(getString(R.string.btn_2_inch), getString(R.string.btn_2_inch_3_5x4_9)));
		
		btnCustomize.setText(getButtonsText(getString(R.string.btn_customize_inch), ""));
	}
	
	private SpannableStringBuilder getButtonsText(String str1, String str2){
		
		
		SpannableString spannableString1 = new SpannableString(str1);
		
		spannableString1.setSpan(new AbsoluteSizeSpan(50, true), 0, str1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		
		SpannableString spannableString2 = new SpannableString(str2);
		
		spannableString1.setSpan(new AbsoluteSizeSpan(25, true), 0, str1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		
		SpannableStringBuilder ssb = new SpannableStringBuilder();
		
	    ssb.append(spannableString1).append(spannableString2);
	    
	    return ssb;
	}

	private void registerBtnListeners() {
		
		btn1Inch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Runtime.CAMERA_PREVIEW_WIDTH = 2.5f;
				Runtime.CAMERA_PREVIEW_HEIGHT = 3.5f;
			}
		});
		
		btn2Inch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Runtime.CAMERA_PREVIEW_WIDTH = 3.5f;
				Runtime.CAMERA_PREVIEW_HEIGHT = 4.9f;
			}
		});

		
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// finish(); //TODO what's this for?

				Intent intent = new Intent();
				intent.setClass(getBaseContext(), BasicActivity.class);
				startActivity(intent);

				overridePendingTransition(R.anim.left_in, R.anim.right_out);

			}
		});
		
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(getBaseContext(), BasicActivity.class);
				startActivity(intent);

				overridePendingTransition(R.anim.left_in, R.anim.right_out);

			}
		});

	}
	
	private void addSelectiveList(Context context){
		
		List<PhotoSize> preDefinedSizes = loadPreDefinedSizes();
		
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_choose_size);
		
		for(int i = 0;i<preDefinedSizes.size();i++){
			PhotoSize photoSize = preDefinedSizes.get(i);
			Button btnChooseSize = new Button(context);
			btnChooseSize.setText(photoSize.getDesc() + photoSize.getWidth() + " cm X " + photoSize.getHeight() + " cm");
			btnChooseSize.setTextSize(15); // set text size
			btnChooseSize.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			linearLayout.addView(btnChooseSize);
		}
		
	}
	
	private List<PhotoSize> loadPreDefinedSizes(){
		
		List<PhotoSize> list = new ArrayList<PhotoSize>();
		
		for(int i = 0;i<10;i++){
			PhotoSize size = new PhotoSize();
			size.setDesc(i + " Inch");
			size.setHeight(2.5f);
			size.setWidth(1.5f);
			
			list.add(size);
		}
		
		return list;
		
	}
	
	

}
