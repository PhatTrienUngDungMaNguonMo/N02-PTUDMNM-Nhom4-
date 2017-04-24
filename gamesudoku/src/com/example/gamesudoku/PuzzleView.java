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
		
        new Paint();
        Paint background = new Paint();   
        Paint pen1 = new Paint();
        Paint pen2 = new Paint();                  
        Paint pen3 = new Paint();
        background.setColor(-3355444);
        canvas.drawRect(0.0F, 0.0F, (float)this.getWidth(), (float)this.getHeight(), background);
        pen1.setColor(-16777216);
        pen2.setColor(-16711936);
        pen3.setColor(-1);


       






        int fm;
 for(fm = 0; fm < 9; ++fm) {
            canvas.drawLine(0.0F, (float)fm * this.height, (float)this.getWidth(), (float)fm * this.height, pen3);
            canvas.drawLine(0.0F, (float)fm * this.height + 1.0F, (float)this.getWidth(), (float)fm * this.height + 1.0F, pen2);
            canvas.drawLine((float)fm * this.width, 0.0F, (float)fm * this.width, (float)this.getHeight(), pen3);
            canvas.drawLine((float)fm * this.width + 1.0F, 0.0F, (float)fm * this.width + 1.0F, (float)this.getHeight(), pen2);
        }

        pen1.setStrokeWidth(5.0F);

        for(fm = 0; fm < 9; ++fm) {
            if(fm % 3 == 0) {
                canvas.drawLine(0.0F, (float)fm * this.height, (float)this.getWidth(), (float)fm * this.height, pen1);
                canvas.drawLine(0.0F, (float)fm * this.height + 1.0F, (float)this.getWidth(), (float)fm * this.height + 1.0F, pen2);
                canvas.drawLine((float)fm * this.width, 0.0F, (float)fm * this.width, (float)this.getHeight(), pen1);
                canvas.drawLine((float)fm * this.width + 1.0F, 0.0F, (float)fm * this.width + 1.0F, (float)this.getHeight(), pen2);
            }
        }


        Paint foreground = new Paint(1);
        foreground.setTextSize(70.0F);
        foreground.setStyle(Style.FILL);
        foreground.setTextAlign(Align.CENTER);
        FontMetrics var12 = foreground.getFontMetrics();
        float x = this.width / 2.0F;
        float y = this.height / 2.0F - (var12.ascent + var12.descent) / 2.0F;

        for(int select = 0; select < 9; ++select) {
            for(int j = 0; j < 9; ++j) {
                if(this.game.getOriTile(select, j) == this.game.getTile(select, j)) {
                    foreground.setColor(-65536);
                } else {
                    foreground.setColor(-16776961);
                }

                canvas.drawText(this.game.getTileString(select, j), (float)select * this.width + x, (float)j * this.height + y, foreground);
            }
        }

        Paint var13 = new Paint();
        var13.setColor(Color.argb(64, 255, 80, 0));
        canvas.drawRect(this.selectRect, var13);
    }
}

	}
}
