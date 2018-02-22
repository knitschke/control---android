package pl.froger.hellointents;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends Activity {
	///private static final int THIRD_ACTIVITY_REQUEST_CODE = 1;
	public String ip="172.24.1.1";
	public String port="1234";
	private Button btnOpenActivity;
	private EditText etResponse;
	private EditText etResponse1;
	public static final String RESPONSE = "Response";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnOpenActivity = (Button) findViewById(R.id.btnOpenActivity);


		//initButtonsOnClickListeners();
		btnOpenActivity.setOnClickListener(new OnClickListener() {
			@Override


			public void onClick(View v) {
				etResponse = (EditText) findViewById(R.id.etResponse);
				etResponse1 = (EditText) findViewById(R.id.etResponse1);
				if(etResponse.length()!=0)
				ip=etResponse.getText().toString();
				if(etResponse.length()!=0)
				port=etResponse1.getText().toString();

				openSecondActivity();

			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		btnOpenActivity = (Button) findViewById(R.id.btnOpenActivity);

		btnOpenActivity.setOnClickListener(new OnClickListener() {
			@Override


			public void onClick(View v) {
				etResponse = (EditText) findViewById(R.id.etResponse);
				etResponse1 = (EditText) findViewById(R.id.etResponse1);
				if(etResponse.length()!=0)
					ip=etResponse.getText().toString();
				if(etResponse.length()!=0)
					port=etResponse1.getText().toString();

				openSecondActivity();

			}
		});
	}


	private void openSecondActivity() {
		Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
		intent.putExtra("ip",ip);
		intent.putExtra("port",port);
		startActivity(intent);
	}



}