# BarChartLoadingView
A bar chart loading view that you can specify the number of bars.

### [中文](https://github.com/samlss/BarChartLoadingView/blob/master/README-ZH.md)

 <br/>

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/BarChartLoadingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/BarChartLoadingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)


![gif1](https://github.com/samlss/BarChartLoadingView/blob/master/screenshots/screenshot1.gif)

![gif2](https://github.com/samlss/BarChartLoadingView/blob/master/screenshots/screenshot2.gif)

### Use<br>
Add it in your root build.gradle at the end of repositories：
```java
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```java
dependencies {
    implementation 'com.github.samlss:BarChartLoadingView:1.0'
}
```


in layout.xml：
```java
 <com.iigo.library.BarChartLoadingView
            android:id="@+id/bclv_loading1"
            app:barNumber="4"
            android:layout_centerInParent="true"
            android:layout_width="100dp"
            android:layout_height="100dp" />

```

<br>

in java code：
```java
  barChartLoadingView.setBarNumber(6); //set bar number
  barChartLoadingView.setColorSchemeColors(new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.GRAY, Color.YELLOW}); //set the color array
  
  barChartLoadingView.start(); //start animation
  barChartLoadingView.stop(); //stop animation
  
  barChartLoadingView.release(); //release when you do net need the view anyway.
```
<br>

About the **setColorSchemeColors** method:

I will use the following code as an example:
```java
barChartLoadingView.setColorSchemeColors(new int[]{
            Color.parseColor("#F47E60"), //color1
            Color.parseColor("#E15B64"), //color2
            Color.parseColor("#ABBD81"), //color3
            Color.parseColor("#F8B26A"), //color4
    });
```
![picture](https://github.com/samlss/BarChartLoadingView/blob/master/screenshots/description.png)

For each bar, the color of the corresponding color array subscript is set according to the set color array. If the number of bars exceeds the size of the color array, the color setting is performed by taking the color array as the remainder.


Attributes description：

| attr            |         description         |
| --------------- | :-------------------------: |
| barNumber  | specify how many bars you want to set |

<br>

# Note

I don't limit the number of bars (the default number is 4). When you set the **barNumber**, you should combine the actual quantity requirements and performance impact to set a suitable number.

In addition, if you need to set the relevant properties of the bar animation, you can modify it by downloading the source code.

------

```
valueAnimator.setStartDelay(startDelay);
```
At present, the bars animation is to make the visual animated irregularity by setting different random **startDelay** values, so it is possible that the **startDelay** values are very close, resulting in a very small irregularity gap. If you need more accurate irregularity animation, you can customize it by downloading source code.

## [LICENSE](https://github.com/samlss/BarChartLoadingView/blob/master/LICENSE)
