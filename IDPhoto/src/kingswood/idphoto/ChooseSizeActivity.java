package kingswood.idphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseSizeActivity extends Activity {

	Button btnCancel = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosesize);
		
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

}
