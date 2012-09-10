/**
 * 數字抽籤
 * FileName:	MainActivity.java
 *
 * 日期: 		2012.9.7
 * 作者: 		元兒～
 * Version: 	v2.2
 * 更新資訊:
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
package tw.blogspot.yuan817.random.number;

import game.rand.num.NumList;
import game.rand.num.NumRand;

import java.text.BreakIterator;
import java.util.concurrent.ExecutionException;

import tw.blogspot.yuan817.random.number.R;
import android.R.bool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
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

public class MainActivity extends Activity implements OnClickListener {
	private Data data = new Data();
	//建立介面物件
	private TextView lotted_TextView,lotted_total_TextView;
	private HorizontalScrollView lotted_scrollView;
	private ScrollView lotted_scrollView_land;
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
  		randoming();
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
				
				//建立抽到的數字變數getNum，並隨機抽取一個數字
				int getNum = data.numRand.getNumber(
						Integer.parseInt(lot_numMax.getText().toString()), true);
				if(getNum != -1){	//若正常抽到數字的話
					getNum++;
					data.lottedNum.addNum(getNum);
					printLottedStatus();	//輸出到介面
				}
				else{	//如果數子已經抽完的話
					Toast.makeText(this, getString(R.string.lotted_exhausted), Toast.LENGTH_LONG).show();
				}
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
			//顯示"關於"資訊的對話框
			AlertDialog about_AlertDialog = new AlertDialog.Builder(this)
			.create();
			about_AlertDialog.setTitle(R.string.menu_about);
			try {
				/*PackageManager package_manager = this.getPackageManager();
				PackageInfo package_info = package_manager.getPackageInfo(this.getPackageName(), 0);*/
				PackageInfo package_info = getPackageManager().getPackageInfo(this.getPackageName(), 0);
				
				about_AlertDialog.setMessage(
						getString(R.string.app_name) + "\n"
						+ getString(R.string.package_name) + package_info.packageName + "\n"
						+ getString(R.string.version) + package_info.versionName + "\n"
						+ getString(R.string.author) + getString(R.string.author_content) + "\n"
						+ getString(R.string.author_website) + getString(R.string.author_website_content)
				);
			} catch (NameNotFoundException ex) {
				about_AlertDialog.setMessage(getString(R.string.getPackageInfo_error));
				//e.printStackTrace();
			} catch(Exception ex){
				Toast.makeText(this, getString(R.string.inside_process_error), Toast.LENGTH_LONG).show();
			}
			about_AlertDialog.setButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			about_AlertDialog.show();
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