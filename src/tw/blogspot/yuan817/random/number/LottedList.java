/**
 * 數字抽籤
 * FileName:	LottedList.java
 *
 * 日期: 		2012.9.5
 * 作者: 		元兒～
 * Version: 	v1.0
 * 更新資訊:
 * └─ v1.0 -2012.9.5
 *    └─ 最初的版本
 * 目前Bug: 
 * └─ v1.0 -2012.9.5
 *    ├─ 表格排版尚未完成、表格按鈕、動作尚未建立
 *    └─ 尚未完成新增按鈕
 * 
 * Description: 可在這個頁面個別新增、移除已抽過的數字之清單
 */
package tw.blogspot.yuan817.random.number;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LottedList extends Activity implements OnClickListener{
	private Data data = new Data(); 
		
	TableLayout lotted_list_tableLayout;
	ImageButton add_button,deleteAll_button,reload_button,back_button;
	
	TableRow[] lotted_list_TableRow = new TableRow[Data.LOTTY_AMOUNT];
	TextView[] lotted_list_id_TextView = new TextView[Data.LOTTY_AMOUNT];
	TextView[] lotted_list_TextView = new TextView[Data.LOTTY_AMOUNT];
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lotted_list);
		lotted_list_tableLayout = (TableLayout)findViewById(R.id.lotted_list_tableLayout);
		add_button = (ImageButton)findViewById(R.id.lotted_list_add);
		add_button.setOnClickListener(this);
		deleteAll_button = (ImageButton)findViewById(R.id.lotted_list_deleteAll);
		reload_button = (ImageButton)findViewById(R.id.lotted_list_reload);
		reload_button.setOnClickListener(this);
		deleteAll_button.setOnClickListener(this);
		back_button = (ImageButton)findViewById(R.id.lotted_list_back);
		back_button.setOnClickListener(this);
		
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
		case R.id.lotted_list_reload:
			displayLottedNumItem();
			break;
		case R.id.lotted_list_back:
			finish();
			break;
		}
	}

	public void displayLottedNumItem(){
		lotted_list_tableLayout.removeAllViews();	//清除所有項目
		
		//建立所有項目的物件
		for(int i=0; i<Data.lottedNum.getTotal(); i++){
			lotted_list_TableRow[i] = new TableRow(this);
			lotted_list_tableLayout.addView(lotted_list_TableRow[i]);
			
			lotted_list_id_TextView[i] = new TextView(this);
			lotted_list_id_TextView[i].setText(""+i);
			lotted_list_TableRow[i].addView(lotted_list_id_TextView[i]);
			
			lotted_list_TextView[i] = new TextView(this);
			lotted_list_TextView[i].setText(""+data.lottedNum.getOneNum(i));
			lotted_list_TableRow[i].addView(lotted_list_TextView[i]);
		}
	}
}
