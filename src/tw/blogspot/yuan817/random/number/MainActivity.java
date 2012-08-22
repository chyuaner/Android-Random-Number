/**
 * 數字抽籤
 * FileName:	MainActivity.java
 *
 * 日期: 		2012.8.22
 * 作者: 		元兒～
 * Version: 	v1.0
 * 更新資訊: 
 * └─ v1.0 -2012.8.22
 *    └─ 最初的版本
 * 目前Bug: 
 * └─ v1.0 -2012.8.22
 *    └─ 尚未解決螢幕轉向後變重設的問題
 * 預計打算:
 * └─ 建立多國語言（英文、繁體中文）
 * 
 * Description: 介面上有個大按鈕，按下去將會隨機取出範圍內的數字，且不再重複抽到
 */
package tw.blogspot.yuan817.random.number;

import tw.blogspot.yuan817.random.number.R;
import android.R.bool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

//取亂數專用
class Num_rand {
	private int num_amount; //亂數數的最大值
	private boolean[] num_use; //這個數字是否已用
	public Num_rand(int num_amount){
		this.num_amount = num_amount;
		num_use = new boolean[num_amount];
	}
	//設定亂數的最大值（若超出已宣告的陣列範圍則回傳false）
	public boolean setNumAmount(int num_amount,boolean clear){
		if(num_amount<=num_use.length){
			for(int i=num_amount;i<num_use.length && clear==true;i++){
				num_use[i] = false;
			}
			this.num_amount = num_amount;
			return true;
		}
		else return false;
	}
	//洗牌，重設已選過的數字
	public void shuffle() {
		for(int i=0;i<num_amount;i++) num_use[i] = false;
	}
	//取得數字（參數：取得亂數的最大值,取得後是否標示成已使用數字狀態）
	public int getNumber(int max,boolean setNumUse) {
		if(!(max>num_amount)){
			int i,checka=0;
			boolean[] num_lot = new boolean[max];
			while(true)
			{
				i=(int)(Math.random()*max); //亂數取0~(max-1)範圍內的數
				if(!num_use[i]) //檢查隨機取到的數是否已用，沒用過的話→離開這個while
				{
					if(setNumUse) num_use[i] = true;
					break;
				}
				//若抽到重複的數
				num_lot[i] = true;
				for(int checkb=0;checkb<max;checkb++){
					if(num_lot[checkb] == true) checka++;
					else break;
				}
				if(checka == max) return -1;
				checka=0;
			}
			return i;
		}
		else return -1; //若輸入的最大值超過能處理的最大值時，回傳-1
	}
	public void setUsedNum(int num,boolean tf){
		if(tf == true) num_use[num] = true;
		else num_use[num] = false;
	}
}


public class MainActivity extends Activity implements OnClickListener {
	private final int LOTTY_AMOUNT = 10000;
	private int[] lotted_num;
	private int lotted_num_total = 0;
	private boolean lotting = false;
	private Num_rand num_rand = new Num_rand(LOTTY_AMOUNT);
	
	private TextView lotted_TextView;
	private EditText lot_numMax;
	private ImageButton lot_numMax_sub,lot_numMax_add;
	private Button lot_main_button,lotted_clear_Button;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      lotted_num = new int[LOTTY_AMOUNT];
      
      lot_main_button = (Button)findViewById(R.id.lot_btn);
      lot_main_button.setOnClickListener(this);
      lotted_TextView = (TextView)findViewById(R.id.lotted);
      lotted_clear_Button = (Button)findViewById(R.id.clear_lotted);
      lotted_clear_Button.setOnClickListener(this);
      lot_numMax = (EditText)findViewById(R.id.lot_numMax);
      lot_numMax_sub = (ImageButton)findViewById(R.id.lot_numMax_sub);
      lot_numMax_sub.setOnClickListener(this);
      lot_numMax_add = (ImageButton)findViewById(R.id.lot_numMax_add);
      lot_numMax_add.setOnClickListener(this);
      printLottedNum();
  }

  @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
  	switch(v.getId()){
  	case R.id.lot_btn:
  		//lotting = true;
  		randoming();
  		break;
  	case R.id.clear_lotted:
  		num_rand.shuffle();
  		lotted_num_total = 0;
  		printLottedNum();
  		lot_main_button.setText(getResources().getText(R.string.lot_btn_start));
  		break;
  	case R.id.lot_numMax_sub:
  		lot_numMax.setText(""+(Integer.parseInt(lot_numMax.getText().toString())-1));
  		break;
  	case R.id.lot_numMax_add:
  		lot_numMax.setText(""+(Integer.parseInt(lot_numMax.getText().toString())+1));
  		break;
  	}
		
	}
  public void randoming(){
  	num_rand.setNumAmount(Integer.parseInt(lot_numMax.getText().toString()), false);
  	int getNum = num_rand.getNumber(
	    		Integer.parseInt(lot_numMax.getText().toString()), true);
  	if(getNum != -1){
  			getNum++;
	    	lot_main_button.setText(""+getNum);
	    	lotted_num[lotted_num_total] = getNum;
	    	lotted_num_total++;
  	}
  	else{
  		lot_main_button.setText(getResources().getText(R.string.lotted_exhausted));
  	}
  	printLottedNum();
  }
  public void printLottedNum(){
  	String outputText = "";
  	for(int i=0;i<lotted_num_total;i++){
  		if(i>0) outputText += ", ";
  		outputText += lotted_num[i];
  	}
  	lotted_TextView.setText(outputText);
  }
	@Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
  }

	@SuppressWarnings("deprecation")
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.menu_about:
			AlertDialog about_AlertDialog = new AlertDialog.Builder(this)
			.create();
			about_AlertDialog.setTitle(R.string.menu_about);
			about_AlertDialog.setMessage(getString(R.string.about_text));
			about_AlertDialog.setButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			about_AlertDialog.show();
			break;
		case R.id.menu_exit:
			finish();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
}
