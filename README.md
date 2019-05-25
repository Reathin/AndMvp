### AndMvp

A simple one MVP library for Android.

MVP框架，基类封装，集成工具类。

### 使用

```gradle
compile 'com.rairmmd:andmvp:1.0.2'
```

#### BaseApplication
必须自定义一个application继承自BaseApplication，并在manifest配置自定义的application：
```java
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志打印
        initLog(true, "rair");
        //初始化toasty颜色
        initToast(R.color.colorAccent);
        //初始化全局异常捕获
        initCrashHandler();
    }
}
```
在AndroidManifest.xml中配置自定义的application：
```xml
<application
    android:name=".App"
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:roundIcon="@mipmap/ic_launcher_round"
    android:supportsRtl="true"
    android:theme="@style/AppTheme">
    <activity android:name=".MainActivity">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
</application>

```
可在build.gradle中配置开发模式打印log：
```groovy
buildTypes {
    release {
        minifyEnabled false
        buildConfigField "boolean", "LOG_DEBUG", "false"
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
    debug {
        minifyEnabled false
        buildConfigField "boolean", "LOG_DEBUG", "true"
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
}
```
然后这么初始化：
```java
initLog(BuildConfig.LOG_DEBUG, "Rair");
```
如果想调用showToasty()方法，可以在App初始化时配置你喜欢的颜色：
```java
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initLog(BuildConfig.LOG_DEBUG, "Rair");
        initToasty(R.color.colorAccent);
    }
}
```
也可参考Toasty原项目的使用。

#### BaseActivity
Activity基类：
* 没有P的情况
```java
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolbar(toolbar, getString(R.string.app_name), false);
    }

    @Override
    public void initData() {
        loadRootFragment(R.id.fl_container, MainFragment.newInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }
}
```
* 正常一个V对应一个P的情况
```java
public class MainActivity extends BaseActivity<MainPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolbar(toolbar, getString(R.string.app_name), false);
    }

    @Override
    public void initData() {
        loadRootFragment(R.id.fl_container, MainFragment.newInstance());
    }

    public void showData("来自P的内容"){
        //显示到控件  或别的处理
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter newP() {
        return new MainPresenter();
    }
}
```
通过getP()得到P并可以调用P的方法

P继承自BasePresent<T>
```java
public class MainPresenter extends BasePresent<MainActivity> {
    public void loadInNet(){
        //do something...
        getV.showData("需要告诉给V的内容");
    }
}
```

```java
//显示加载对话框
showLoading();  
showLoading("loading...");
//显示toast
showToasy("123"); 
showToast("123");
//设置Toolbar 有多个重载的方法
setToolbar(); 
//是否允许Activity侧滑返回 Fragment没有此方法
slidrInterface.unlock();  
slidrInterface.lock();
```
BaseFragment和Activity用法几乎一样。

#### XRouter

XRouter.newIntent(Context context).to(who.class).launch();
```java
XRouter.newIntent(MainActivity.this).to(SecondActivity.class).launch();
```
#### ImageLoader
```java
//加载图片，多个重载的方法
load(String url, ImageView target);
load(String url, @DrawableRes int placeholder, ImageView target);
load(String url, Drawable placeholder, ImageView target);
load(@DrawableRes int resId, ImageView target)
```

### 使用到的开源库
```groovy
    api 'com.android.support:appcompat-v7:28.0.0'
    //Rx
    api 'io.reactivex.rxjava2:rxjava:2.2.2'
    api 'io.reactivex.rxjava2:rxandroid:2.1.0'
    api 'com.squareup.retrofit2:retrofit:2.4.0'
    api 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
    api 'com.squareup.retrofit2:converter-gson:2.4.0'
    api 'com.squareup.retrofit2:converter-scalars:2.4.0'
    //权限
    api 'com.yanzhenjie:permission:1.1.2'
    //ButterKnife
    api 'com.jakewharton:butterknife:8.8.1'
    //LoadingDialog
    api 'com.github.gittjy:LoadingDialog:1.0.2'
    //Toasty
    api 'com.github.GrenderG:Toasty:1.3.1'
    //Klog
    api 'com.github.zhaokaiqiang.klog:library:1.6.0'
    //fragmentation
    api 'me.yokeyword:fragmentation:1.3.6'
    //状态栏
    api 'com.gyf.barlibrary:barlibrary:2.3.0'
    //鲁班图片压缩
    api 'top.zibin:Luban:1.1.8'
    //Picasso
    api 'com.squareup.picasso:picasso:2.71828'
    //图片选择
    api 'com.yanzhenjie:album:2.1.3'
    //适配器
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.46'
    //EventBus
    api "org.greenrobot:eventbus:3.1.1"
    //侧滑退出
    api 'com.r0adkll:slidableactivity:2.0.6'
```
* AndPermission：动态权限申请
* RxJava2:RxJava2
* Retrofit2:Retrofit2网络请求
* ButterKnife:ButterKnife视图注入
* LoadingDialog:IOS风格Dialog
* Toasty:Toasty，一款漂亮的Toast
* KLog:Log日志库
* fragmentation:对Activity和Fragment友好封装
* barlibrary:沉浸式状态栏
* Luban:鲁班图片压缩
* Picasso:Picasso图片加载
* Album:图片选择
* BaseRecyclerViewAdapterHelper：RecyclerView通用适配器
* EventBus：Eventbus
