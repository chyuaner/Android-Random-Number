/**
 * 數字抽籤
 * FileName:	LottedList.java
 *
 * 日期: 		2012.9.5
 * 作者: 		元兒～
 * Version: 	v1.0.2
 * 更新資訊:
 * ├─ v1.0.2 -2012.9.5
 * │  ├─ 新增可上移、下移功能之按鈕
 * │  └─ 針對Android 2.1、3.0，有無ActionBar做最佳化視覺調整
 * ├─ v1.0.1 -2012.9.5
 * │  ├─ 加入TableLayout裡的所有物件陣列
 * │  └─ ActionBar左上角圖示加入返回功能
 * └─ v1.0 -2012.9.5
 *    └─ 最初的版本
 * 目前Bug:
 * ├─ v1.0.1 -2012.9.5
 * │  └─ 雖然以完成個別、全部清除按鈕之動作，但新增、編輯尚未完成
 * └─ v1.0 -2012.9.5
 *    ├─ 表格排版尚未完成、表格按鈕、動作尚未建立
 *    └─ 尚未完成新增按鈕
 * 
 * Description: 可在這個頁面個別新增、移除已抽過的數字之清單
 */
package tw.blogspot.yuan817.random.number;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.OnMenuVisibilityListener;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.graphics.Path.FillType;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.style.ParagraphStyle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class LottedList extends Activity implements OnClickListener{
	private Data data = new Data(); 
	
	TableLayout lotted_list_tableLayout;
	ImageButton add_button,deleteAll_button,back_button;
	ActionBar actionBar;
	
	//指定可處理的範圍
	//private final int lottedAmount = data.LOTTY_AMOUNT;
	//註：因為在Android2.3 AVD測試發現不能宣告太大的量，所以折衷用目前數再外加50個為可處理之空間
	final int LOTTED_AMOUNT = data.lottedNum.getTotal()+50;
	TableRow[] lotted_list_TableRow = new TableRow[LOTTED_AMOUNT];
	TextView[] lotted_list_id_TextView = new TextView[LOTTED_AMOUNT];
	TextView[] lotted_list_TextView = new TextView[LOTTED_AMOUNT];
	Button[] lotted_list_up = new Button[LOTTED_AMOUNT];
	Button[] lotted_list_down = new Button[LOTTED_AMOUNT];
	ImageButton[] lotted_list_edit = new ImageButton[LOTTED_AMOUNT];
	ImageButton[] lotted_list_delete = new ImageButton[LOTTED_AMOUNT];
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lotted_list);
		lotted_list_tableLayout = (TableLayout)findViewById(R.id.lotted_list_tableLayout);
		
		if(VERSION.SDK_INT <11){
			add_button = (ImageButton)findViewById(R.id.lotted_list_add);
			add_button.setOnClickListener(this);
			deleteAll_button = (ImageButton)findViewById(R.id.lotted_list_deleteAll);
			deleteAll_button.setOnClickListener(this);
			back_button = (ImageButton)findViewById(R.id.lotted_list_back);
			back_button.setOnClickListener(this);
		}
		
		displayLottedNumItem();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.lotted_list_deleteAll:
			data.numRand.shuffle();
	  		data.lottedNum.clear();
	  		displayLottedNumItem();
	  		break;
		case R.id.lotted_list_back:
			finish();
			break;
		default:
			for(int i=0; i<LOTTED_AMOUNT; i++){
				if(i*10+1 == v.getId()){
					data.lottedNum.changeNum(i, i-1);
					displayLottedNumItem();
					break;
				}
				
				else if(i*10+2 == v.getId()){
					data.lottedNum.changeNum(i, i+1);
					displayLottedNumItem();
					break;
				}
				else if(i*10+3 == v.getId()){
					break;
				}
				else if(i*10+4 == v.getId()){
					data.numRand.setUsedNum(data.lottedNum.getOneNum(i)-1,false);
					data.lottedNum.deleteNum(i);
					displayLottedNumItem();
					break;
				}
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//ActionBar左上角圖示加入返回功能
		if(VERSION.SDK_INT >=11){
			actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			getMenuInflater().inflate(R.menu.lotted_list, menu);
			LinearLayout lotted_list_menuPanel;
			lotted_list_menuPanel = (LinearLayout)findViewById(R.id.lotted_list_menuPanel);
			lotted_list_menuPanel.setVisibility(View.GONE);
		}
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		//當按下ActionBar上的返回圖示
		case android.R.id.home:
			finish();
			break;
		case R.id.lotted_list_deleteAll:
			data.numRand.shuffle();
	  		data.lottedNum.clear();
	  		displayLottedNumItem();
	  		break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@SuppressWarnings("deprecation")
	public void displayLottedNumItem(){
		lotted_list_tableLayout.removeAllViews();	//清除所有項目
		
		//在標題顯示已抽過數字總計
		this.setTitle(""+getString(R.string.title_lotted_list)+" | " + 
				data.lottedNum.getTotal());
		/*if(VERSION.SDK_INT >=11){
			actionBar = getActionBar();
			actionBar.setTitle(""+getString(R.string.title_lotted_list)+" | " + 
					data.lottedNum.getTotal());
		}*/
		
		//建立所有項目的物件
		for(int i=0; i<Data.lottedNum.getTotal(); i++){
			lotted_list_TableRow[i] = new TableRow(this);
			lotted_list_tableLayout.addView(lotted_list_TableRow[i]);
			
			lotted_list_id_TextView[i] = new TextView(this);
			lotted_list_id_TextView[i].setText(""+i);
			lotted_list_id_TextView[i].setWidth(40);
			lotted_list_TableRow[i].addView(lotted_list_id_TextView[i]);
			
			lotted_list_TextView[i] = new TextView(this);
			lotted_list_TextView[i].setText(""+data.lottedNum.getOneNum(i));
			lotted_list_TextView[i].setWidth(100);
			lotted_list_TextView[i].setTextAppearance(this, android.R.style.TextAppearance_Large);
			lotted_list_TableRow[i].addView(lotted_list_TextView[i]);
			
			lotted_list_up[i] = new Button(this);
			lotted_list_up[i].setText("↑");
			lotted_list_up[i].setId(i*10+1);
			lotted_list_up[i].setOnClickListener(this);
			lotted_list_TableRow[i].addView(lotted_list_up[i]);
			
			lotted_list_down[i] = new Button(this);
			lotted_list_down[i].setText("↓");
			lotted_list_down[i].setId(i*10+2);
			lotted_list_down[i].setOnClickListener(this);
			lotted_list_TableRow[i].addView(lotted_list_down[i]);
			
			lotted_list_edit[i] = new ImageButton(this);
			lotted_list_edit[i].setImageResource(android.R.drawable.ic_menu_edit);
			lotted_list_TableRow[i].addView(lotted_list_edit[i]);
			
			lotted_list_delete[i] = new ImageButton(this);
			lotted_list_delete[i].setImageResource(android.R.drawable.ic_delete);
			//lotted_list_delete[i].setGravity(Gravity.RIGHT);
			lotted_list_delete[i].setId(i*10+4);
			lotted_list_delete[i].setOnClickListener(this);
			lotted_list_TableRow[i].addView(lotted_list_delete[i]);
		}
	}
}
