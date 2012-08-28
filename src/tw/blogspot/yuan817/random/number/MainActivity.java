/**
 * 數字抽籤
 * FileName:	MainActivity.java
 *
 * 日期: 		2012.8.28
 * 作者: 		元兒～
 * Version: 	v1.1.2
 * 更新資訊:
 * ├─ v1.1.2 -2012.8.28
 * │  ├─ 使用者透過按鈕微調+1-1: 將此動作獨立出addsub_lot_numMax()副程式
 * │  ├─ 使用者透過按鈕微調+1-1抽取範圍改用自行撰寫的addsubNum();處理（可判斷程式可處理的範圍）
 * │  └─ 修改randoming(): 加入判斷使用者輸入的最大值是否正確，以至於不會因此而FC
 * ├─ v1.1 -2012.8.27
 * │  ├─ 將資料變數移至MainActivity之外 → 如果螢幕翻轉之後，就算MainActivity被清掉重新建立，資料還能保留下來
 * │  ├─ 加入"以抽過數字"自動捲到最後: 改寫printLottedNum()
 * │  ├─ 改寫printLottedStatus(): 在呼叫此方法時，自動一起呼叫printLottedNum()
 * │  ├─ 將LOTTY_AMOUNT（設定能抽的數字範圍）調大到1000000
 * │  ├─ 改寫lot_numMax_add、lot_numMax_sub，不會讓使用者微調到超出可抽數字的範圍
 * │  └─ 改寫randoming(): 加入判斷使用者輸入的抽籤最大值有沒有錯誤or超出範圍
 * └─ v1.0.a1 -2012.8.22
 *    └─ 最初的版本
 * 目前Bug: 
 * └─ v1.0.a1 -2012.8.22
 *    └─ 螢幕轉向後重設的問題解決到一半
 * 預計打算:
 * └─ 建立多國語言（英文、繁體中文）
 * 
 * Description: 介面上有個大按鈕，按下去將會隨機取出範圍內的數字，且不再重複抽到
 */
package tw.blogspot.yuan817.random.number;

import java.text.BreakIterator;
import java.util.concurrent.ExecutionException;

import tw.blogspot.yuan817.random.number.R;
import android.R.bool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

class Data{
	public static final int LOTTY_AMOUNT = 1000000; //設定能抽的數字範圍
	public static int[] lotted_num = new int[LOTTY_AMOUNT];;
	public static int lotted_num_total = 0;
	public static boolean lotting = false;
	public static Num_rand num_rand = new Num_rand(LOTTY_AMOUNT);
}


public class MainActivity extends Activity implements OnClickListener {
	private Data data = new Data();
	//建立介面物件
	private TextView lotted_TextView,lotted_total_TextView;
	private HorizontalScrollView lotted_scrollView;
	private EditText lot_numMax;
	private ImageButton lot_numMax_sub,lot_numMax_add;
	private Button lot_main_button,lotted_clear_Button;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lot_main_button = (Button)findViewById(R.id.lot_btn);
		lot_main_button.setOnClickListener(this);
		lotted_TextView = (TextView)findViewById(R.id.lotted);
		lotted_total_TextView = (TextView)findViewById(R.id.lotted_total);
		lotted_scrollView = (HorizontalScrollView)findViewById(R.id.lotted_scrollView);
		lotted_clear_Button = (Button)findViewById(R.id.clear_lotted);
		lotted_clear_Button.setOnClickListener(this);
		lot_numMax = (EditText)findViewById(R.id.lot_numMax);
		lot_numMax_sub = (ImageButton)findViewById(R.id.lot_numMax_sub);
		lot_numMax_sub.setOnClickListener(this);
		lot_numMax_add = (ImageButton)findViewById(R.id.lot_numMax_add);
		lot_numMax_add.setOnClickListener(this);
		printLottedStatus();
		//printLottedNum();
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
  		data.num_rand.shuffle();
  		data.lotted_num_total = 0;
  		printLottedStatus();
  		//printLottedNum();
  		//lot_main_button.setText(getResources().getText(R.string.lot_btn_start));
  		break;
  	case R.id.lot_numMax_sub:
  		addsub_lot_numMax(-1);
  		break;
  	case R.id.lot_numMax_add:
  		addsub_lot_numMax(+1);
  		break;
  	}	
	}
	private void addsub_lot_numMax(int chg) {
		try {
			lot_numMax.setText(""+
	  				(int) addsubNum(Double.parseDouble(lot_numMax.getText().toString()), 1, data.LOTTY_AMOUNT, chg) );
		} catch (Exception e) {
			Toast.makeText(this, getResources().getString(R.string.lot_numMax_addsub_error), Toast.LENGTH_SHORT).show();
		}
	}
	//在範圍內微調目前的數字（像是+1-1的）（參數: 目前數,最小值,最大值,微調數）
	//註: 最大值和最小值本身也包含進去
	public double addsubNum(double currentNum,double min,double max,double change){
		currentNum+=change;
		if(currentNum > max) return max;
		else if(currentNum < min) return min;
		else return currentNum;
	}
	
	public void randoming(){
		try{
			//設定並判斷使用者輸入的抽籤最大值
			if(data.num_rand.setNumAmount(
					Integer.parseInt(lot_numMax.getText().toString()), false)
					&& Integer.parseInt(lot_numMax.getText().toString()) != 0)
			{
				
				//建立抽到的數字變數getNum，並隨機抽取一個數字
				int getNum = data.num_rand.getNumber(
						Integer.parseInt(lot_numMax.getText().toString()), true);
				if(getNum != -1){	//若正常抽到數字的話
					getNum++;
					//lot_main_button.setText(""+getNum);
					data.lotted_num[data.lotted_num_total] = getNum;
					data.lotted_num_total++;
					printLottedStatus();	//輸出到介面
				}
				else{	//如果數子已經抽完的話
					lot_main_button.setText(getResources().getText(R.string.lotted_exhausted));
				}
			}
			else	//如果使用者輸入的範圍錯誤的話
				throw new Exception("range error");
		}
		catch(Exception ex){
			Toast.makeText(this, getResources().getString(R.string.lot_max_num_error), Toast.LENGTH_LONG).show();
		}
	}
	
	//將主要變數裡的狀態輸出到介面
	private void printLottedStatus() {
		printLottedNum();
		lotted_total_TextView.setText(""+data.lotted_num_total);
		if(data.lotted_num_total == 0) lot_main_button.setText(getResources().getText(R.string.lot_btn_start));
		else lot_main_button.setText(""+data.lotted_num[data.lotted_num_total-1]);
	}
	
	//將"已抽過數字"裡的狀態輸出到介面
	public void printLottedNum(){
		//輸出文字
		String outputText = "";
		for(int i=0;i<data.lotted_num_total;i++){
			if(i>0) outputText += ", ";
			outputText += data.lotted_num[i];
		}
		lotted_TextView.setText(outputText);
		
		//將lotted_scrollView自動捲到最右邊
		//lotted_scrollView.scrollTo(lotted_TextView.getMeasuredWidth(), 0);
		lotted_scrollView.post(new Runnable() {
			@Override
			public void run() {
				lotted_scrollView.fullScroll(lotted_scrollView.FOCUS_RIGHT); 
			}
		});
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