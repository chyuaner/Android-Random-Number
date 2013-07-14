/**
 * 數字抽籤
 * FileName:	Data.java
 *
 * 日期: 		2012.9.7
 * 作者: 		元兒～
 * Version: 	v1.0
 * 更新資訊:
 * └─ v1.0 -2012.9.7
 *    └─ 將Data專門暫存資料的類別移出成獨立的Data.java檔
 *  
 * Description: 專門暫存資料的類別
 */
package org.twbbs.yuan817.random.number;

import game.rand.num.NumList;
import game.rand.num.NumRand;

public class Data {
	public static final int LOTTY_AMOUNT = 1000000; //設定能抽的數字範圍
	public static NumList lottedNum = new NumList(LOTTY_AMOUNT);
	public static boolean lotting = false;
	public static NumRand numRand = new NumRand(LOTTY_AMOUNT);
}
