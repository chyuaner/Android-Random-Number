數字抽籤
=======================

這是我第一個以"稍微實用一點"的方向來寫出這個Android App。
是以之前寫過的兩份作業部份程式碼為基礎，來搞個在Android平台裡簡單的抽籤App當作練習:P。

## 修改紀錄
* v3.0 -2013.7.14
    * 套用Threan, Handler ，讓抽籤按鈕變成按一下就會開始不斷的亂數，再按一下停止亂數並選定這個數。
* v2.5 -2013.7.14
    * 更改PackageName和關於資訊裡的網址成 http://yuan817.twbbs.org
* v2.4 -2012.9.25
    * showAboutDialog抽出成HelpUtils.showAboutDialog(this);
* v2.4 beta 1 -2012.9.22
    * 包上ScrollView讓對話框可以捲動
    * 加入水平線外觀
    * 細部關於對話框的排版
* v2.4 Pre-alpha 1 -2012.9.22
    * 修改AboutDialog分開成showAboutDialog副程式
    * 將about_AlertDialog.setMessage改成自行設計的介面
    * 設定成content_textView.setAutoLinkMask(Linkify.ALL)可由使用者點選上面的網址連結
    * 新增讀取/assets/README.txt文字檔內容到about_AlertDialog
* v2.3 -2012.9.11
    * 正式加入"管理已抽過數字"功能，詳情請見"LottedList.java"
* v2.2 -2012.9.5
    * 選單新增"管理已抽過數字"選單動作
* v2.1.2 -2012.9.7
    * 將Data專門暫存資料的類別移出成獨立的Data.java檔
* v2.1.1 -2012.9.7
    * 將所有"getResources().getText(Rid)"都替換成"getString(Rid)"
    * 修改所有有用到try、catch的成會詳細判斷，並新增"內部錯誤"的例外狀況
* v2.1 -2012.9.4
    * 修改類別名稱LottedNum→NumList ，為了方便達到多用途（數字清單），將整體名稱更改
* v2.0 -2012.9.3
    * 大幅更改架構，將NumRand、獨立出來的LottedNum包成Package
    * 將原本寫在本code裡紀錄以抽過數字的變數陣列獨立出成一個類別，並修改所相關會用到此陣列成相對應的物件方法
    * 更改從網路抓下來的免費圖示（非商業性質）
* v1.3.1 -2012.9.3
    * 由res/values/color.xml定義版面顏色
* v1.2.1 -2012.9.2
    * 將Num_rand這個class獨立成Num_rand檔
* v1.2 -2012.8.31
    * 重新設計橫向畫面
    * 在res那邊增加layout-land專門為橫向螢幕設計的版面
    * 使用HorizontalScrollView和ScrollView（新增）物件針對直像橫向捲動個別處理
    * 並修改printLottedNum()副程式輸出成符合直向橫向排版的樣子 
* 1.1.3 -2012.8.29
    * "關於"對話框
    * 將res/values/strings.xml的"關於"資訊再拆解細分，並在本code裡更新對應
    * 增加顯示這支程式的Package和版本: 內容直接從AndroidManifest.xml取得
* 1.1.2 -2012.8.28
    * 使用者透過按鈕微調+1-1: 將此動作獨立出addsub_lot_numMax()副程式
    * 使用者透過按鈕微調+1-1抽取範圍改用自行撰寫的addsubNum();處理（可判斷程式可處理的範圍）
    * 修改randoming(): 加入判斷使用者輸入的最大值是否正確，以至於不會因此而FC
* 1.1 -2012.8.27
    * 將資料變數移至MainActivity之外 → 如果螢幕翻轉之後，就算MainActivity被清掉重新建立，資料還能保留下來
    * 加入"以抽過數字"自動捲到最後: 改寫printLottedNum()
    * 改寫printLottedStatus(): 在呼叫此方法時，自動一起呼叫printLottedNum()
    * 將LOTTY_AMOUNT（設定能抽的數字範圍）調大到1000000
    * 改寫lot_numMax_add、lot_numMax_sub，不會讓使用者微調到超出可抽數字的範圍
    * 改寫randoming(): 加入判斷使用者輸入的抽籤最大值有沒有錯誤or超出範圍
* 1.0.a1 -2012.8.22
    * 最初的版本

## 日文翻譯
感謝我大學的同班同學[~kobayashi();](http://www.plurk.com/abe_manabi) 幫我翻譯成日文...

## 程式圖示來源
* 程式圖示: /drawable/target.png
* 下載網址: http://findicons.com/icon/55353/target?id=55353
* 圖示包: The Spherical
* 設計師: Ahmad Hania
* 版權宣告: Creative Commons Attribution Non-commercial Share Alike (by-nc-sa)
