package com.example.gamesudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity{

	Intent intent;
	private void DialogOpen()
	{
		String []a= {"de","trung binh","kho"};
		new AlertDialog.Builder(this).setTitle("Chon do kho").setItems(a, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int i) {
				// TODO Auto-generated method stub
				startGame(i);
			}
		}).show();
	}
	private void startGame(int i)
	{
		intent = new Intent(MainActivity.this, Game.class);
		intent.putExtra(Game.KEY_DIFFICULTY, i);
		startActivity(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button newbutton,continuebutton,exitbutton;
		newbutton = (Button) findViewById(R.id.btnnewgame);
		continuebutton= (Button) findViewById(R.id.btncontinue);
		exitbutton=(Button) findViewById(R.id.btnexit);
		newbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogOpen();
			}
		});
		
        continuebutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				startGame(Game.CONTINUE);
			}
		});
		
        exitbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
