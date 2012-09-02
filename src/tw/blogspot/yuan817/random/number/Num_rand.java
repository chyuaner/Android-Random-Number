/**
 * FileName:	Num_rand.java
 *
 * 日期:  		2012.9.2
 * 作者:			元兒～
 * Version:     v1.1
 * 更新資訊: 
 * ├─ v1.1
 * │  ├─ 新增setNumAmount(num_amount,clear)、setUsedNum(num,tf)
 * │  └─ 修改getNumber(max,setNumUse)，抽出數字時自動檢查所有範圍內的數字是否已被抽完（解決無窮迴圈的問題）
 * └─ v1.0 -2012.8.22
 *    └─ 改使用Eclipse作為這支程式的開發
 * Description: 隨機取數字，而且取到的數字不再重複取到
 *
 */
package tw.blogspot.yuan817.random.number;

//取亂數專用
public class Num_rand {
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
	//手動設定這個數是否已被抽過
	public void setUsedNum(int num,boolean tf){
		if(tf == true) num_use[num] = true;
		else num_use[num] = false;
	}
}
