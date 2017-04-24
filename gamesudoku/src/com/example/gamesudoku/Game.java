package com.example.gamesudoku;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.EasyEditSpan;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

public class Game extends Activity {
  
    public static String KEY_DIFFICULTY;
    
    protected static final int CONTINUE= -1;
    
    public static final int DIFFICULTY_EASY   = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD   = 2;
    
    private static String PREF_PUZZLE,Ori;
    
    private int puzzle[] = new int[9*9];
    private int originpuzzle[] = new int[9*9];
    
    private final String easyPuzzle =
        "360000000004230800000004200" +
        "070460003820000014500013020" +
        "001900000007048300000000045";
 //   private final String easyPuzzle = "362581479914237856785694231" + "179462583823759614546813927" + "431925768657148392298370045";
    private final String mediumPuzzle =
        "650000070000506000014000005" +
        "007009000002314700000700800" +
        "500000630000201000030000097";
    private final String hardPuzzle =
        "009000000080605020501078000" +
        "000000700706040102004000000" +
        "000720903090301080000000600";
    
    private int[] getPuzzle(int diff) {
    	String puz,ori;
    	switch(diff) {
    	case CONTINUE:
    		puz = getSharedPreferences("mydata", MODE_PRIVATE).getString(PREF_PUZZLE, easyPuzzle);
    		ori = getSharedPreferences("mydata1", MODE_PRIVATE).getString(Ori, easyPuzzle);
    		originpuzzle= fromPuzzleString(ori);
    		break;
    	case DIFFICULTY_HARD:
    	    puz = hardPuzzle;
    	    break;
    	case DIFFICULTY_MEDIUM:
    		puz = mediumPuzzle;
    		break;
    	case DIFFICULTY_EASY:
    	default:
    		puz = easyPuzzle;
    		break;
    	}
        return fromPuzzleString(puz);
    }
    
    static private String toPuzzleString(int[] puz) {
    	StringBuilder buf = new StringBuilder();
    	for (int element : puz) {
    		buf.append(element);
    	}
    	return buf.toString();
    }
    public void WinGame()
    {
    	int dem=0;
    	for(int i:puzzle)
    		if(i==0) dem++;
    	if(dem==0) 
    	{
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //Thiết lập tiêu đề hiển thị
            builder.setTitle("Thong bao");
            //Thiết lập thông báo hiển thị
            builder.setMessage("Chuc mung ban da thang! ");
            builder.setCancelable(false);
            //Tạo nút
            builder.setNeutralButton("Xem lai", null);
            builder.setPositiveButton("Quay lai", new DialogInterface. OnClickListener() {
            	@Override
            	public void onClick(DialogInterface dialog, int which)
            	{
            		Intent intent = new Intent(Game.this,MainActivity.class);
            		startActivity(intent);
            	}});
            builder.create().show();
    	}
    }
    static protected int[] fromPuzzleString(String string) {
        int[] puz = new int[string.length()];
        for (int i=0; i<puz.length; i++) {
        	puz[i] = string.charAt(i) - '0';
        }
        return puz;
    }
    
    public int getTile(int x, int y) {
    	return puzzle[y*9+x];
    }
    
    public int getOriTile(int x, int y) {
    	return originpuzzle[y*9+x];
    }
    
    private void setTile(int x, int y, int value) {
    	puzzle[y*9+x] = value;
    }
    
    public String getTileString(int x, int y) {
    	int v = getTile(x, y);
    	if (v==0)
    		return "";
    	else
    		return String.valueOf(v);
    }
    
    private PuzzleView puzzleView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       int diff = getIntent().getIntExtra(KEY_DIFFICULTY,DIFFICULTY_EASY);
       puzzle = getPuzzle(diff);
       if(diff!=CONTINUE)
       originpuzzle=getPuzzle(diff);
       calculateUsedTiles();

       puzzleView = new PuzzleView(this);
       setContentView(puzzleView);
       puzzleView.requestFocus();
       
       getIntent().putExtra(KEY_DIFFICULTY, CONTINUE);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	// save the puzzle
    	getSharedPreferences("mydata", MODE_PRIVATE).edit().putString(PREF_PUZZLE, toPuzzleString(puzzle)).commit();
    	getSharedPreferences("mydata1", MODE_PRIVATE).edit().putString(Ori, toPuzzleString(originpuzzle)).commit();
    }
    
    protected void showKeypadOrError(int x, int y) {
		int tiles[];
		if(getOriTile(x,y)==0)
		{	
		tiles = getUsedTiles(x, y);	
		if (tiles.length == 9) {
			Toast toast = Toast.makeText(this,"không còn số có thể điền vào", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0,	0);
			toast.show();
		} 
		else {
			Dialog v= new KeyPad(this,tiles, puzzleView);
			v.show();
		}
		}
	}
    
    public boolean setTileIfValid(int x, int y, int value) {
    	int tiles[] = getUsedTiles(x, y);
    	if (value != 0) {
    		for (int tile : tiles) {
    			if (tile == value)
    				return false;
    		}
    	}
    	setTile(x, y, value);  // nếu số không bị trùng thì chèn số vào mảng
    	calculateUsedTiles();
    	return true;
    }
    
    private final int used[][][] = new int[9][9][];
    
    protected int[] getUsedTiles(int x, int y) {
    	return used[x][y];
    }
    
    private void calculateUsedTiles() {
    	for (int x=0; x<9; x++) {
    		for (int y=0; y<9; y++) {
    			used[x][y] = calculateUsedTiles(x, y);
    		}
    	}
    }
    
    private int[] calculateUsedTiles(int x, int y) {
    	int c[] = new int[9];
    	
    	// horizontal
    	for (int i=0; i<9; i++) {
    		if (i==y)
    			continue;
    		int t = getTile(x, i);
    		if (t != 0)
    			c[t-1] = t;
    	}
    	
    	// vertical
    	for (int i=0; i<9; i++) {
    		if (i==x)
    			continue;
    		int t = getTile(i, y);
    		if (t != 0)
    			c[t-1] = t;
    	}
    	
    	// same cell block
    	int startx = (x/3) * 3;
    	int starty = (y/3) * 3;
    	
    	for (int i=startx; i<startx+3; i++) {
    		for (int j=starty; j<starty+3; j++) {
    			if (i==x && j==y)
    				continue;
    			int t = getTile(i, j);
    			if (t != 0)
    				c[t-1] = t;
    		}
    	}
    	// compress
    	int nused = 0;
    	for (int t: c) {
    		if (t != 0)
    			nused++;
    	}
    	int c1[] = new int[nused];
    	nused = 0;
    	for (int t: c) {
    		if (t!=0) 
    			c1[nused++] = t;
    	}
    	return c1;
    }
}