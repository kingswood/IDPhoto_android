package kingswood.idphoto;

import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

import kingswood.idphoto.vo.PhotoSize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ChooseSizeActivity extends Activity {

	Button btnCancel = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosesize);
		
		//addSelectiveList(getApplicationContext());
		
		registerBtnListeners();

	}

	private void registerBtnListeners() {

		btnCancel = (Button) findViewById(R.id.btn_cancel);
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
