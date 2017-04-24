package com.example.gamesudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class KeyPad extends Dialog {

    protected static final String TAG = "Sudoku";
    private final int useds[];
	private final View keys[] = new View[10];
	private View keypad;
	
	//private final int useds[];
	private final PuzzleView puzzleView;
	public KeyPad(Context context, int useds[], PuzzleView puzzleView) {
		super(context);
		// TODO Auto-generated constructor stub
		this.useds = useds;
		this.puzzleView = puzzleView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_key_pad);
		findViews();
//		for (int element : useds) {
//			if(element != 0) {
//				keys[element].setVisibility(View.INVISIBLE);
//			}
//		}
		setListeners();
	}

	private void setListeners() {
		for (int i = 0; i < keys.length; i++) {
			final int t = i;
			keys[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					returnResult(t);
				}
			});
		}
	}

	protected void returnResult(int tile) {
		puzzleView.setSelectedTile(tile);
		dismiss();
	}

	private void findViews() {
		// Mapujemy nasza klawiature wraz z kazdym przyciskiem
		keypad = findViewById(R.id.keypad);
		keys[0] = findViewById(R.id.keypad_xoa);
		keys[1] = findViewById(R.id.keypad_1);
		keys[2] = findViewById(R.id.keypad_2);
		keys[3] = findViewById(R.id.keypad_3);
		keys[4] = findViewById(R.id.keypad_4);
		keys[5] = findViewById(R.id.keypad_5);
		keys[6] = findViewById(R.id.keypad_6);
		keys[7] = findViewById(R.id.keypad_7);
		keys[8] = findViewById(R.id.keypad_8);
		keys[9] = findViewById(R.id.keypad_9);
	}
	
}
