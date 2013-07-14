/**
 * 數字抽籤
 * FileName:	MainActivity.java
 *
 * 日期: 		2012.9.25
 * 作者: 		元兒～
 * Version: 	v2.4
 * 更新資訊:
 * ├─ v2.4 -2012.9.25
 * │  └─ 將showAboutDialog抽出成HelpUtils.showAboutDialog(this);
 * ├─ v2.4 beta 1 -2012.9.22
 * │  ├─ 包上ScrollView讓對話框可以捲動
 * │  ├─ 加入水平線外觀
 * │  └─ 細部關於對話框的排版
 * ├─ v2.4 Pre-alpha 1 -2012.9.22
 * │  ├─ 修改AboutDialog分開成showAboutDialog副程式
 * │  ├─ 將about_AlertDialog.setMessage改成自行設計的介面
 * │  ├─ 設定成content_textView.setAutoLinkMask(Linkify.ALL)可由使用者點選上面的網址連結
 * │  └─ 新增讀取/assets/README.txt文字檔內容到about_AlertDialog
 * ├─ v2.3 -2012.9.11
 * │  └─ 正式加入"管理已抽過數字"功能，詳情請見"LottedList.java"
 * ├─ v2.2 -2012.9.5
 * │  └─ 選單新增"管理已抽過數字"選單動作
 * ├─ v2.1.2 -2012.9.7
 * │  └─ 將Data專門暫存資料的類別移出成獨立的Data.java檔
 * ├─ v2.1.1 -2012.9.7
 * │  ├─ 將所有"getResources().getText(Rid)"都替換成"getString(Rid)"
 * │  └─ 修改所有有用到try、catch的成會詳細判斷，並新增"內部錯誤"的例外狀況
 * ├─ v2.1 -2012.9.4
 * │  └─ 修改類別名稱LottedNum→NumList ，為了方便達到多用途（數字清單），將整體名稱更改
 * ├─ v2.0 -2012.9.3
 * │  ├─ 大幅更改架構，將NumRand、獨立出來的LottedNum包成Package
 * │  ├─ 將原本寫在本code裡紀錄以抽過數字的變數陣列獨立出成一個類別，並修改所有相關會用到此陣列成相對應的物件方法
 * │  └─ 更改從網路抓下來的免費圖示（非商業性質）
 * ├─ v1.3.1 -2012.9.3
 * │  └─ 由res/values/color.xml定義版面顏色
 * ├─ v1.2.1 -2012.9.2
 * │  └─ 將Num_rand這個class獨立成Num_rand檔
 * ├─ v1.2 -2012.8.31
 * │  └─ 重新設計橫向畫面
 * │     ├─ 在res那邊增加layout-land專門為橫向螢幕設計的版面
 * │     ├─ 使用HorizontalScrollView和ScrollView（新增）物件針對直像橫向捲動個別處理
 * │     └─ 並修改printLottedNum()副程式輸出成符合直向橫向排版的樣子 
 * ├─ v1.1.3 -2012.8.29
 * │  └─ "關於"對話框
 * │     ├─ 將res/values/strings.xml的"關於"資訊再拆解細分，並在本code裡更新對應
 * │     └─ 增加顯示這支程式的Package和版本: 內容直接從AndroidManifest.xml取得
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
 * ├─ v1.0.a1 -2012.8.22
 * │  └─ v請參閱LottedList.java
 * └─ v1.0.a1 -2012.8.22
 *    └─ v螢幕轉向後重設的問題解決到一半
 * 
 * Description: 介面上有個大按鈕，按下去將會隨機取出範圍內的數字，且不再重複抽到
 */
package org.twbbs.yuan817.random.number;

import game.rand.num.NumList;
import game.rand.num.NumRand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;
import java.util.concurrent.ExecutionException;

import org.twbbs.yuan817.random.number.R;
import android.R.bool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Data data = new Data();
	private boolean isRandoming = false;
	//建立介面物件
	private TextView lotted_TextView,lotted_total_TextView;
	private HorizontalScrollView lotted_scrollView;
	private ScrollView lotted_scrollView_land;
	private EditText lot_numMax;
	private ImageButton lot_numMax_sub,lot_numMax_add;
	private Button lot_main_button,lotted_clear_Button;
	private Thread lottingThread;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isRandoming = false;
		lot_main_button = (Button)findViewById(R.id.lot_btn);
		lot_main_button.setOnClickListener(this);
		lotted_TextView = (TextView)findViewById(R.id.lotted);
		lotted_total_TextView = (TextView)findViewById(R.id.lotted_total);
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)	//如果螢幕轉向為直向
			lotted_scrollView = (HorizontalScrollView)findViewById(R.id.lotted_scrollView);
		else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)	//如果螢幕轉向為橫向
			lotted_scrollView_land = (ScrollView)findViewById(R.id.lotted_scrollView);
		lotted_clear_Button = (Button)findViewById(R.id.clear_lotted);
		lotted_clear_Button.setOnClickListener(this);
		lot_numMax = (EditText)findViewById(R.id.lot_numMax);
		lot_numMax_sub = (ImageButton)findViewById(R.id.lot_numMax_sub);
		lot_numMax_sub.setOnClickListener(this);
		lot_numMax_add = (ImageButton)findViewById(R.id.lot_numMax_add);
		lot_numMax_add.setOnClickListener(this);
		printLottedStatus();
	}
	
	@Override
	public void onClick(View v) {
  	switch(v.getId()){
  	case R.id.lot_btn:
  		//lotting = true;
  		
  		if(!isRandoming){
  			randoming();
  		}
  		else{
  			lottingThread.interrupt();
  		}
  		break;
  	case R.id.clear_lotted:
  		data.numRand.shuffle();
  		data.lottedNum.clear();
  		printLottedStatus();
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
		} catch (IllegalArgumentException ex) {
			Toast.makeText(this, getString(R.string.notNumber_error), Toast.LENGTH_SHORT).show();
		} catch(Exception ex){
			Toast.makeText(this, getString(R.string.inside_process_error), Toast.LENGTH_LONG).show();
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		printLottedStatus();
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
			if(data.numRand.setNumAmount(
					Integer.parseInt(lot_numMax.getText().toString()), false)
					&& Integer.parseInt(lot_numMax.getText().toString()) != 0)
			{
				lottingThread = new LottingThread();
				lottingThread.start();
			}
			else	//如果使用者輸入的範圍錯誤的話
				throw new IllegalArgumentException("range error");
		}
		catch (IllegalArgumentException ex) {
			Toast.makeText(this, getString(R.string.lot_max_num_error), Toast.LENGTH_SHORT).show();
		}
		catch(Exception ex){
			Toast.makeText(this, getString(R.string.inside_process_error), Toast.LENGTH_LONG).show();
		}
	}
	
	private Handler updatelotteBtn = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//Toast.makeText(getBaseContext(), "XD", Toast.LENGTH_SHORT).show();
			int getNum = msg.getData().getInt("lotNum", -1);
			if(getNum != -1){	//若正常抽到數字的話
				getNum++;
				lot_main_button.setText( Integer.toString(getNum) );
			}
			else{
				Toast.makeText(getBaseContext(), getString(R.string.lotted_exhausted), Toast.LENGTH_LONG).show();
			}
		}
	};
	
	class LottingThread extends Thread{
		protected int getNum = -1;
		protected int randMax;
		@Override
		public void run() {
			super.run();
			try {
				isRandoming = true;
				randMax = Integer.parseInt(lot_numMax.getText().toString());
				while(isRandoming){
					Thread.sleep(10);
					getNum = data.numRand.getNumber(randMax, false);
					
					Bundle theLotNumBundle = new Bundle();
					theLotNumBundle.putInt("lotNum", getNum);
					
					Message msg = new Message();
					msg.setData(theLotNumBundle);
					updatelotteBtn.sendMessage(msg);
					
					if(getNum == -1){
						isRandoming = false;
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void interrupt() {
			// TODO Auto-generated method stub
			super.interrupt();
			isRandoming = false;
			
			data.numRand.setUsedNum(getNum, true);
			data.lottedNum.addNum(getNum+1);
			printLottedStatus();	//輸出到介面
		}
		
	}
	
	//將主要變數裡的狀態輸出到介面
	private void printLottedStatus() {
		printLottedNum();
		lotted_total_TextView.setText(""+data.lottedNum.getTotal());
		if(data.lottedNum.cleared()) lot_main_button.setText(getString(R.string.lot_btn_start));
		else lot_main_button.setText(""+data.lottedNum.getLastNum());
	}
	
	//將"已抽過數字"裡的狀態輸出到介面
	public void printLottedNum(){
		//輸出文字
		String outputText = "";
		for(int i=0;i<data.lottedNum.getTotal();i++){
			if(i>0){
				//如果螢幕轉向為直向
				if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
					outputText += ", ";
				//如果螢幕轉向為橫向
				else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
					outputText += "\n";
			}
			outputText += data.lottedNum.getOneNum(i);
		}
		lotted_TextView.setText(outputText);
		
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			//將lotted_scrollView自動捲到最右邊
			//lotted_scrollView.scrollTo(lotted_TextView.getMeasuredWidth(), 0);
			lotted_scrollView.post(new Runnable() {
				@Override
				public void run() {
					lotted_scrollView.fullScroll(lotted_scrollView.FOCUS_RIGHT); 
				}
			});
		}
		else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			//將lotted_scrollView自動捲到最底下
			lotted_scrollView_land.post(new Runnable() {
				@Override
				public void run() {
					lotted_scrollView_land.fullScroll(lotted_scrollView_land.FOCUS_DOWN);
				}
			});
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		//"管理已抽過的數字"按鈕
		case R.id.menu_toLottedList:
			Intent toLottedList = new Intent(this,LottedList.class);
			startActivity(toLottedList);
			break;
		//"關於"按鈕
		case R.id.menu_about:
			HelpUtils.showAboutDialog(this);
			break;
		//"離開"按鈕
		case R.id.menu_exit:
			System.exit(0);
			//finish();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}