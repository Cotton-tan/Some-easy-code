# Timer+TimerTask实现数字时钟

## 成果展示

![image-20230319154847869](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230319154847869.png)

## 布局

![image-20230319154939680](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230319154939680.png)

使用五个TextView，分别实现小时：分钟，秒钟，am，pm，周日，具体日期几个数值。

![image-20230319155229679](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230319155229679.png)

布局主题背景颜色采用#000000（纯黑色），各TextView字体颜色采用#FFFFFF(纯白色)。

**默认布局**

![image-20230319155449527](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230319155451688.png)

### 约束关系

![image-20230320192651458](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230320192651458.png)

对于几个TextView的位置布局，我们依靠一个非常实用的方法：**约束关系**(**ConstraintLayout**)。

- 默认布局，依靠约束关系来确定位置
- 能够灵活定位和调整界面元素的大小
- 无任何嵌套，减少布局层级，优化渲染性能
- 能完全代替其他布局![image-20230320192904698](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230320192904698.png)

![image-20230320193113262](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230320193113262.png)

![image-20230320193227863](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230320193227863.png)

![image-20230320193452070](C:\Users\23770\AppData\Roaming\Typora\typora-user-images\image-20230320193452070.png)

通过约束关系，组件布局将变得无比easy~

## 功能实现

使用Timer+TimerTask完成数字时钟，首先创建几个Timer和TimerTask的对象。

```java
Timer htimer = new Timer()			 //创建时分计时器
TimerTask hourtask = new TimerTask(){//创建时分计时任务--子线程
    public void run(){
  		...      
    }
};
```

我们在布局中使用的是五个TextView，实现时钟需要使用setText()函数，所以我们需要在TimerTask中创建TextView对象。

```java
TextView tv1 = (TextView) findViewById(R.id.hourshow);
```

并使用findViewById根据id值绑定组件。

如何获得实时的时间数据？有两个很好的日期API可以实现。

### Calendar类

首先，我们使用Calendar自带的getInstance()函数获得实例。

```java
//获得Calendar这个类的实例：
private Calendar c = Calendar.getInstance();
```

然后就可以通过实例c，使用get()函数去获得我们想要的数据，例如这里获取时分。

```java
int hour = c.get(Calendar.HOUR_OF_DAY);
int minute = c.get(Calendar.MINUTE);
```

然后时分已经被hour和minute捕获了，我们只需要将时间传给TextView。

```java
tv1.setText(String.format("%02d:%02d",hour,minute));
```

注意，从Calendar中get到的数据都是int型，我们这里要转化为String型再传输。类似时分，可以分别完成其他几项数据。

但还有一个更好用的API。

### SimpleDateFormat类

SimpleDateFormat是Java中用于格式化日期和时间的类。它提供了一种将日期和时间格式化为特定样式的方式，使其可以更好地与用户界面交互或存储在数据库中。

我们先创建一个SimpleDateFormat对象dateFormat。

```java
SimpleDateFormat dateFormat = new SimpleDateFormat("HH : mm", Locale.CHINA);
```

**"HH : mm"**:这个参数是一个字符串，其中HH和mm都是API自带的参数，HH表示显示两位小时，mm表示显示两位分钟；

**Locale.CHINA**:这个参数是一个**Locale**对象，指定格式化的语言环境，这里我们选择CHINA，即中文环境。

接下来，我们使用以下语句将当前时间格式化为上午下午时间格式的字符串：

```java
String currentTime = dateFormat.format(new Date());
```

在Java中，可以使用`new Date()`来创建一个表示当前时间的`Date`对象。当这个语句被执行时，它会调用系统时钟来获取当前时间，并将其封装为一个`Date`对象。

这里，我们使用了SimpleDateFormat的**format()**方法，将一个**Date**对象（表示当前时间）格式化为一个字符串。该方法返回一个String对象，包含指定格式的日期和时间字符串。

最后通过TextView使用setText函数即可实现功能，故此不再演示。

下面是SimpleDateFormat类部分参数对应的细节：

| 字母 | 日期/时间元素  | 表示              | 示例                                  |
| ---- | -------------- | ----------------- | ------------------------------------- |
| G    | Era            | 标志符            | AD                                    |
| y    | 年             | Year              | 1996; 96                              |
| M    | 年中的月份     | Month             | July; Jul; 07                         |
| w    | 年中的周数     | Number            | 27                                    |
| W    | 月份中的周数   | Number            | 2                                     |
| D    | 年中的天数     | Number            | 189                                   |
| d    | 月份中的天数   | Number            | 10                                    |
| F    | 月份中的星期   | Number            | 3                                     |
| E    | 星期中的天数   | Text              | Tuesday; Tue                          |
| a    | Am/pm 标记     | Text              | PM                                    |
| H    | 一天中的小时   | Number (0-23)     | 0                                     |
| k    | 一天中的小时   | Number (1-24)     | 24                                    |
| K    | am/pm 中的小时 | Number (0-11)     | 0                                     |
| h    | am/pm 中的小时 | Number (1-12)     | 12                                    |
| m    | 小时中的分钟   | Number            | 30                                    |
| s    | 分钟中的秒数   | Number            | 55                                    |
| S    | 毫秒数         | Number            | 978                                   |
| z    | 时区           | General time zone | Pacific Standard Time; PST; GMT-08:00 |
| Z    | 时区           | RFC 822 time zone | -0800                                 |

**注意**:大写HH 24小时,小写hh 12小时

### TimerTask与runOnUiThread()

我们已经获得了实时的时间数据，并实时传值给了TextView组件，但是现在我们将代码运行，会看见一晃而过的日期，以及我们最讨厌的意外——**闪退**。

为什么呢？

——Android规定只有主线程才能更新UI，在TimerTask线程中是不能直接更新UI的，但可调用**runOnUiThread**方法将一个Runnable子线程任务放到主线程中执行。 

所以一开始我们的TimerTask对象hourtask就遗留了问题。

```java
Timer htimer = new Timer()			 //创建时分计时器
TimerTask hourtask = new TimerTask(){//创建时分计时任务--子线程
    public void run(){
  		...     //这里应该写一个runOnUiThread()函数 
    }
};
```

那我们就通过runOnUiThread写一个new Runnable()进去吧。

```java
    TimerTask hourtask = new TimerTask() {         //时钟
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv1 = (TextView) findViewById(R.id.hourshow);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH : mm", Locale.CHINA);
                    String currentTime = dateFormat.format(new Date());
                    tv1.setText(currentTime);
                }
            });
        }
    };
```

一个完美的函数就这样写出来了。

然后我们就只需要在onCreate也就是我们最重要的函数中，完成hourtask的运用。

```java
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        htimer.schedule(hourtask, 0, 1000);
    }
```

```java
htimer.schedule(hourtask, 0, 1000);
timer.schedule(task, delay, period);
```

schedule是Timer的调度函数，**0**（delay）是经过0毫秒后执行一次hourtask任务，**1000**（period）是从经过0（delay）毫秒秒后，每隔1000（period）毫秒执行一次hourtask任务，并且是多次、重复执行的。

这里就是运行程序后，0毫秒就开始，然后每隔1秒更新一下数据。

最后，别忘了写一个onDestroy()函数。

```java
   @Override
    protected void onDestroy() {
        super.onDestroy();
        htimer.cancel();
    }
}
```

cancle()可以停止计时器。 

