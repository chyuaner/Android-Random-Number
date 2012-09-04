/**
 * FileName:	NumList.java
 *
 * 日期:  		2012.9.4
 * 作者:			元兒～
 * Version:     v2.1
 * 更新資訊: 
 * ├─ v2.1 -2012.9.4
 * │  ├─ 新增updateNum()、insertNum()、deleteNum()、updateLastNum()、deleteLastNum()，可透過此函數更改、插入、刪除其中一個項目
 * │  ├─ 更新註解說明
 * │  └─ 更名 getNum()→getOneNum() 、 getNumList() → getNumArray()，方便辨識用
 * ├─ v2.0 -2012.9.4
 * │  └─ 更改整體名稱LottedNum→NumList和相關函式名稱
 * │     	原本是針對存放已抽過數字設計的，但後來發現其實根本就是個存放數字的清單，為了方便達到多用途，將整體名稱更改
 * └─ v1.0 -2012.9.3
 *    ├─ 將原本寫在Android的Random Number程式裡的MainActivity.java紀錄以抽過數字的變數陣列獨立出成一個類別
 *    └─ 將這個class包成package
 *    
 * Description: 紀錄數字清單
 *
 */
package game.rand.num;

public class NumList {
	private final int AMOUNT; //能容納多少個數字的量
	private int[] numList;	//暫存數字清單陣列
	private int numTotal = 0;	//目前總共存放多少個
	public NumList(int inputAmount) {
		AMOUNT = inputAmount;	//設定能容納多少個數字的量
		numList = new int[AMOUNT];	//建立暫存數字清單陣列空間
	}
	
	
	//取得總共存放多少個
	public int getTotal(){
		return numTotal;
	}
	
//================================================================
	//個別取得一個數字
	public int getOneNum(int index){
		if(index >= 0 && index < numTotal)	//若要求的索引符合可處理範圍內
			return numList[index];
		else return -1;	//否則回傳-1
	}
	
	//取得最後的數
	public int getLastNum(){
		return numList[numTotal-1];
	}
	
	//取得所有數字清單之陣列
	public int[] getNumArray(){
		int[] output = new int[numTotal];
		for(int i=0;i<numTotal;i++){
			output[i] = numList[i];
		}
		return output;
	}	

//================================================================
	//增加一個數字
	public boolean addNum(int inputNum) {
		if(numTotal < AMOUNT){
			numList[numTotal] = inputNum;
			numTotal++;	//標記總計存放的數+1
			return true;	//回傳新增項目正常
		}
		else return false;	//回傳新增項目失敗
	}
	
	//更改一個數字
	public boolean updateNum(int index,int inputNum) {
		if(index >=0 && index < numTotal){
			numList[index] = inputNum;	//寫入數字到數字清單之陣列裡
			return true;	//回傳更新項目正常
		}
		else return false;	//回傳更新項目失敗
	}
	
	//插入一個數字
	public boolean insertNum(int index,int inputNum) {
		if((index >=0 && index <= numTotal) && numTotal < AMOUNT){
			numTotal++;	//標記總計存放的數+1
			//索引由下而上開始，數字由上而下順移
			for(int i=numTotal-1; i>index; i--){
				numList[i] = numList[i-1]; //將上一個數順移到這一個索引
			}
			numList[index] = inputNum;	//寫入數字到數字清單之陣列裡
			return true;	//回傳插入項目正常
		}
		else return false;	//回傳插入項目失敗
	}
	
	//刪除一個數字
	public boolean deleteNum(int index) {
		if(index >=0 && index < numTotal){
			//索引由上而下開始，數字由下而上順移
			for(int i=index; i<numTotal; i++){
				if(i != numTotal-1) numList[i] = numList[i+1];	//將下一個數順移到這一個索引
				else numList[i] = 0;	//刪除當前的數
			}
			numTotal--;	//標記總計存放的數-1
			return true;	//回傳新增項目正常
		}
		else return false;	//回傳新增項目失敗
	}
	
	//更改最後的數字
	public boolean updateLastNum(int inputNum) {
		if(updateNum(numTotal-1,inputNum)) return true;	//回傳更新項目正常
		else return false;	//回傳更新項目失敗
	}
	
	//刪除最後的數字
	public boolean deleteLastNum() {
		if(deleteNum(numTotal-1)) return true;	//回傳更新項目正常
		else return false;	//回傳更新項目失敗
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
		numTotal = 0;	//標記總計存放的數為0
	}
}
