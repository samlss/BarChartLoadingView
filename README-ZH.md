# BarChartLoadingView
一个可以指定条形数量的条形图loading view

 <br/>

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/BarChartLoadingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/BarChartLoadingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)


![gif1](https://github.com/samlss/BarChartLoadingView/blob/master/screenshots/screenshot1.gif)

![gif2](https://github.com/samlss/BarChartLoadingView/blob/master/screenshots/screenshot2.gif)

### 使用<br>
在根目录的build.gradle添加这一句代码：
```java
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```java
dependencies {
    implementation 'com.github.samlss:BarChartLoadingView:1.0'
}
```


布局中使用：
```java
 <com.iigo.library.BarChartLoadingView
            android:id="@+id/bclv_loading1"
            app:barNumber="4"
            android:layout_centerInParent="true"
            android:layout_width="100dp"
            android:layout_height="100dp" />

```

<br>

代码中使用：
```java
  barChartLoadingView.setBarNumber(6); //设置条形数量
  barChartLoadingView.setColorSchemeColors(new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.GRAY, Color.YELLOW}); //设置颜色数组
  
  barChartLoadingView.start(); //开始动画
  barChartLoadingView.stop(); //结束动画
  
  barChartLoadingView.release(); //在你不需要该loadingview时使用
```
<br>

关于 **setColorSchemeColors** 方法:

我会以下面代码举例
```java
barChartLoadingView.setColorSchemeColors(new int[]{
            Color.parseColor("#F47E60"), //color1
            Color.parseColor("#E15B64"), //color2
            Color.parseColor("#ABBD81"), //color3
            Color.parseColor("#F8B26A"), //color4
    });
```
![picture](https://github.com/samlss/BarChartLoadingView/blob/master/screenshots/description.png)

针对每条bar，会根据设置的颜色数组，设置对应颜色数组下标的颜色，如果bar数量超过颜色数组的大小，则会通过对颜色数组取余来进行颜色设置


属性说明：

| 属性            |         说明         |
| --------------- | :-------------------------: |
| barNumber  | 设置条形数量 |

<br>

# 注意

我没有限制bar的数量（默认为4条），在你设置barNumber的时候，你应该结合实际的数量需求和性能影响， 从而设置一个合适的数量

另外，如果你需要设置bar的动画的相关属性，你可以通过下载源码来进行修改

------

```
valueAnimator.setStartDelay(startDelay);
```
目前bar动是通过设置不同的随机startDelay值使视觉产生动画的不规则性，因此有可能造成这个startDelay值非常接近导致不规则性差距非常小，如果你需要更准确的不规则性动画，你可以通过下载源码来进行自定义

## [LICENSE](https://github.com/samlss/BarChartLoadingView/blob/master/LICENSE)
