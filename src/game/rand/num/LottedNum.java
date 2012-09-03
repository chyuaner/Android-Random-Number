/**
 * FileName:	LottedNum.java
 *
 * 日期:  		2012.9.3
 * 作者:			元兒～
 * Version:     v1.0
 * 更新資訊: 
 * └─ v1.0 -2012.9.3
 *    ├─ 將原本寫在Android的Random Number程式裡的MainActivity.java紀錄以抽過數字的變數陣列獨立出成一個類別
 *    └─ 將這個class包成package
 * Description: 紀錄已抽過的數字清單
 *
 */
package game.rand.num;

public class LottedNum {
	private final int LOTTY_AMOUNT; //能容納已抽數字的量
	private int[] lottedNum;	//暫存已抽數字陣列
	private int lottedNumTotal = 0;	//目前總共抽多少個數字
	public LottedNum(int inputAmount) {
		LOTTY_AMOUNT = inputAmount;	//設定能容納已抽數字的量
		lottedNum = new int[LOTTY_AMOUNT];	//建立暫存已抽數字陣列空間
	}
	
	
	//取得總共抽了多少個
	public int getTotal(){
		return lottedNumTotal;
	}
	
//================================================================
	//個別取得已抽數字
	public int getNum(int index){
		if(index >= 0 && index < lottedNumTotal)	//若要求的索引符合可處理範圍內
			return lottedNum[index];
		else return -1;	//否則回傳-1
	}
	
	//取得最後得到的數
	public int getLastNum(){
		return lottedNum[lottedNumTotal-1];
	}
	
	//取得所有已抽過數字之陣列
	public int[] getLottedNum(){
		int[] output = new int[lottedNumTotal];
		for(int i=0;i<lottedNumTotal;i++){
			output[i] = lottedNum[i];
		}
		return output;
	}	

//================================================================
	//增加一個抽到的數字
	public boolean addNum(int inputNum) {
		if(lottedNumTotal < LOTTY_AMOUNT){
			lottedNum[lottedNumTotal] = inputNum;
			lottedNumTotal++;
			return true;	//回傳新增項目正常
		}
		else return false;	//回傳新增項目失敗
	}
	
//================================================================
	//取得是否已經為空
	public boolean cleared(){
		if(lottedNumTotal == 0) return true;
		else return false;
	}
	
	//清空已抽過的數字
	public void clear() {
		for(int i=0;i<lottedNumTotal;i++){
			lottedNum[i] = 0;
		}
		lottedNumTotal = 0;
	}
}
