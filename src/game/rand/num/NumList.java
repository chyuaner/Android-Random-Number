/**
 * FileName:	NumList.java
 *
 * 日期:  		2012.9.4
 * 作者:			元兒～
 * Version:     v2.0
 * 更新資訊: 
 * ├─ v2.0 -2012.9.4
 * │  └─ 更改整體名稱LottedNum→NumList和相關函式名稱（
 * │     	原本是針對存放已抽過數字設計的，但後來發現其實根本就是個存放數字的清單，為了方便達到多用途，將整體名稱更改
 * └─ v1.0 -2012.9.3
 *    ├─ 將原本寫在Android的Random Number程式裡的MainActivity.java紀錄以抽過數字的變數陣列獨立出成一個類別
 *    └─ 將這個class包成package
 * Description: 紀錄數字清單
 *
 */
package game.rand.num;

public class NumList {
	private final int AMOUNT; //能容納已抽數字的量
	private int[] numList;	//暫存已抽數字陣列
	private int numTotal = 0;	//目前總共抽多少個數字
	public NumList(int inputAmount) {
		AMOUNT = inputAmount;	//設定能容納已抽數字的量
		numList = new int[AMOUNT];	//建立暫存已抽數字陣列空間
	}
	
	
	//取得總共抽了多少個
	public int getTotal(){
		return numTotal;
	}
	
//================================================================
	//個別取得已抽數字
	public int getOneNum(int index){
		if(index >= 0 && index < numTotal)	//若要求的索引符合可處理範圍內
			return numList[index];
		else return -1;	//否則回傳-1
	}
	
	//取得最後得到的數
	public int getLastNum(){
		return numList[numTotal-1];
	}
	
	//取得所有已抽過數字之陣列
	public int[] getNumList(){
		int[] output = new int[numTotal];
		for(int i=0;i<numTotal;i++){
			output[i] = numList[i];
		}
		return output;
	}	

//================================================================
	//增加一個抽到的數字
	public boolean addNum(int inputNum) {
		if(numTotal < AMOUNT){
			numList[numTotal] = inputNum;
			numTotal++;
			return true;	//回傳新增項目正常
		}
		else return false;	//回傳新增項目失敗
	}
	
//================================================================
	//取得是否已經為空
	public boolean cleared(){
		if(numTotal == 0) return true;
		else return false;
	}
	
	//清空已抽過的數字
	public void clear() {
		for(int i=0;i<numTotal;i++){
			numList[i] = 0;
		}
		numTotal = 0;
	}
}
