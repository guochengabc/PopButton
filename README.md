# PopButton
高仿美团、京东下拉筛选列表


如果觉得下载比较麻烦，可以添加引用


1、项目gradle添加如下：
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
2、library里gradle添加如下：
dependencies {
	        implementation 'com.github.guochengabc:PopButton:Tag'
	}
3、使用：layout布局文件里添加如下代码：
  <com.gc.popbutton.PopButton
        android:id="@+id/btn"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:text="按钮一"
        android:textSize="13sp"
        popupbtn:normalBg="@drawable/tab_bkg_line"
        popupbtn:normalIcon="@drawable/arrow_down_shop"
        popupbtn:pressBg="@drawable/tab_bkg_selected"
        popupbtn:pressIcon="@drawable/arrow_up_shop" />
   
   4、然后在activity，可实现不同功能，具体可start后看具体的功能即可：
     1、btn2.setDoubleClickData(this, pList, cList);//功能1
     2、btn.setOneClickData(this, listOne);//功能2
