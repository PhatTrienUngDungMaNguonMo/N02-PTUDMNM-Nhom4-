package com.example.gamesudoku;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;


public class PuzzleView extends View {

	private static final String TAG ="Sudoku" ;
    private final Game game; 
    public PuzzleView(Context context) {
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
    private float width;
    private float height;
    private int selectX;
    private int selectY;
    private final Rect selectRect = new Rect();
    
    private void select(int x,int y)
    {
    	invalidate(selectRect);
    	selectX=Math.min(Math.max(x, 0),8);
    	selectY=Math.min(Math.max(y, 0),8);
    	getRect(selectX, selectY, selectRect);
    	invalidate(selectRect);
    }
    @Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		
	}
    
    @Override 
    protected void onSizeChanged(int w,int h, int oldw,int oldh)
	{
		width = w/9f;
		height=h/9f;
		getRect(selectX, selectY, selectRect);
		super.onSizeChanged(w, h, oldw, oldh);
		
	}
    public void setSelectedTile(int tile) {
		if (game.setTileIfValid(selectX, selectY, tile)) {
			invalidate();
			game.WinGame();
		} 
		else
		{ 	
		Toast toast = Toast.makeText(game, "số này đã có rồi", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0,	0);
		toast.show();
		}
	}
    private void getRect(int x,int y , Rect rect)
    {
    	rect.set((int)(x*width), (int)(y*height),(int)(x*width + width),(int) (y*height + height));
    }

	@Override
	protected void onDraw(Canvas canvas)
	{
		
	}
}
