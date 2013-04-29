/**
 * 數字抽籤
 * FileName:	HelpUtils.java
 *
 * 日期: 		2012.9.25
 * 作者: 		元兒～
 * Version: 	v1.0
 * 更新資訊:
 * └─ v1.0 -2012.9.25
 *    ├─ 將原本在MainActivity.java裡的showAboutDialog()內容抽出成這個class
 *    └─ 解決在Android 2.x手機上因佈景主題而呈現暗底黑字的問題（改成白字）
 * 
 * Description: 所有有關"支援"資訊
 */
package tw.blogspot.yuan817.random.number;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HelpUtils {
	//顯示"關於"資訊的對話框	
	public static void showAboutDialog(Context context){
		//建立對話方塊AlertDialog
		AlertDialog about_AlertDialog = new AlertDialog.Builder(context).create();
		about_AlertDialog.setTitle(R.string.menu_about);	//設定AlertDialog標題
		
		try {
			/*PackageManager package_manager = this.getPackageManager();
			PackageInfo package_info = package_manager.getPackageInfo(this.getPackageName(), 0);*/
			//建立Layout面板
			ScrollView about_AlertDialog_scrollView = new ScrollView(context); 
			LinearLayout about_AlertDialog_content = new LinearLayout(context);
			about_AlertDialog_content.setOrientation(LinearLayout.VERTICAL);	//設定為直向的layout
			about_AlertDialog_content.setPadding(
					context.getResources().getDimensionPixelSize(R.dimen.padding_large), 
					context.getResources().getDimensionPixelSize(R.dimen.padding_small), 
					context.getResources().getDimensionPixelSize(R.dimen.padding_large), 
					context.getResources().getDimensionPixelSize(R.dimen.padding_small));	//設定layout的邊界大小（左、上、右、下）
			//宣告"取得套件資訊"的物件
			PackageInfo package_info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			
			//前面的關於字串
			TextView content_textView = new TextView(context);
			content_textView.setTextAppearance(context, android.R.style.TextAppearance_Medium);	//指定文字樣式為中等大小
			content_textView.setAutoLinkMask(Linkify.ALL);	//設定成會自動加上連結
			if(VERSION.SDK_INT <11) content_textView.setTextColor(context.getResources().getColor(android.R.color.background_light));
			content_textView.setText(
					context.getString(R.string.app_name) + "\n\n"
					+ context.getString(R.string.package_name) +"\n"+ package_info.packageName + "\n"
					+ context.getString(R.string.version) + package_info.versionName + "\n"
					+ context.getString(R.string.author) + context.getString(R.string.author_content) + "\n"
					+ context.getString(R.string.author_website) +"\n"+ context.getString(R.string.author_website_content)
					+ "\n");
			about_AlertDialog_content.addView(content_textView);
			
			//分隔線
			View hr = new View(context);	//用View模擬出水平線外觀
			hr.setBackgroundColor(Color.BLACK);	//設定水平線顏色
			hr.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 2));	//設定水平線的長、寬
			about_AlertDialog_content.addView(hr);
			
			//顯示README.txt內容
			TextView readme_textView = new TextView(context);
			readme_textView.setTextAppearance(context, android.R.style.TextAppearance_Small);	//指定文字樣式為小等大小
			if(VERSION.SDK_INT <11) readme_textView.setTextColor(context.getResources().getColor(android.R.color.background_light));
			readme_textView.setAutoLinkMask(Linkify.ALL);	//設定成會自動加上連結
			readme_textView.setMovementMethod(ScrollingMovementMethod.getInstance());
			readme_textView.setText(getFileTxt(context,"README.txt"));
			about_AlertDialog_content.addView(readme_textView);
			
			//指定這個面板到這個對話框
			about_AlertDialog_scrollView.addView(about_AlertDialog_content);
			about_AlertDialog.setView(about_AlertDialog_scrollView);
			
			/*about_AlertDialog.setMessage(
					getString(R.string.app_name) + "\n"
					+ getString(R.string.package_name) + package_info.packageName + "\n"
					+ getString(R.string.version) + package_info.versionName + "\n"
					+ getString(R.string.author) + getString(R.string.author_content) + "\n"
					+ getString(R.string.author_website) + getString(R.string.author_website_content)
			);*/
		} catch (NameNotFoundException ex) {
			about_AlertDialog.setMessage(context.getString(R.string.getPackageInfo_error));
			//e.printStackTrace();
		} catch(Exception ex){
			Toast.makeText(context, context.getString(R.string.inside_process_error), Toast.LENGTH_LONG).show();
		}
		about_AlertDialog.setButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		about_AlertDialog.show();
	}
	private static String getFileTxt(Context context, final String FILENAME) {
		//讀取文字檔
		//此code是修改自http://androidbiancheng.blogspot.tw/2011/04/assetmanagerassest.html
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = null;
		String MyStream;
		
		try {
			inputStream = assetManager.open("README.txt"); // 指定/assets/README.txt
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] bytes = new byte[4096];
			
			int len;
			while ((len = inputStream.read(bytes)) > 0){
				byteArrayOutputStream.write(bytes, 0, len);
			}
			
			MyStream = new String(byteArrayOutputStream.toByteArray(), "UTF8");
		} catch (IOException e) {
			e.printStackTrace();
			MyStream = e.toString();
		}
		
		return MyStream;
	}
}
