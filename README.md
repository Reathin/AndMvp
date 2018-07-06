### AndMvp
A simple one MVP library for Android.

MVP框架，基类封装，集成工具类。

### 使用

#### BaseApplication
先新建一个类继承BaseApplication
```java
public class App extends BaseApplication {
    ......
}

```
在AndroidManifest.xml中配置name为刚才新建的App类
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
如果想调用内置的Log库请在Application中调用KLog的初始化方法
```java
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(true, "Rair");
    }
}

```
可在build.gradle中配置开发模式打印log
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
KLog.init(BuildConfig.LOG_DEBUG, "Rair");
```
如果想调用V的showToasty()方法，可以在App初始化时配置你喜欢的颜色
```java
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.LOG_DEBUG, "Rair");
        Toasty.Config.getInstance()
                .setInfoColor(ContextCompat.getColor(this, R.color.colorAccent))
                .apply();
    }
}
```
也可参考Toasty原项目的使用。

#### BaseActivity
Activity基类，继承自fragmentation的SupportActivity。
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
正常一个V对应一个P的情况
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


}
```

Fragment和Activity一样


### 使用到的开源库
```groovy
api 'com.android.support:appcompat-v7:27.1.1'
//Rx
api 'io.reactivex.rxjava2:rxjava:2.1.8'
api 'io.reactivex.rxjava2:rxandroid:2.0.1'
api 'com.squareup.retrofit2:retrofit:2.3.0'
api 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
api 'com.squareup.retrofit2:converter-gson:2.3.0'
//权限
api 'com.yanzhenjie:permission:1.1.2'
//ButterKnife
api 'com.jakewharton:butterknife:8.8.1'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
//Dialog
api 'com.afollestad.material-dialogs:core:0.9.6.0'
//Toasty
api 'com.github.GrenderG:Toasty:1.2.8'
//Klog
api 'com.github.zhaokaiqiang.klog:library:1.6.0'
//fragmentation
api 'me.yokeyword:fragmentation:1.3.3'
//状态栏
api 'com.gyf.barlibrary:barlibrary:2.3.0'
//鲁班图片压缩
api 'top.zibin:Luban:1.1.3'
//Picasso
api 'com.squareup.picasso:picasso:2.5.2'
//图片选择
api 'com.yanzhenjie:album:2.1.1'
//适配器
api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.36'
//EventBus
api "org.greenrobot:eventbus:3.1.1"
```
* AndPermission：动态权限申请
* RxJava2:RxJava2
* Retrofit2:Retrofit2网络请求
* ButterKnife:ButterKnife视图注入
* material-dialog:MD风格Dialog
* Toasty:Toasty，一款漂亮的Toast
* KLog:Log日志库
* fragmentation:对Activity和Fragment友好封装
* barlibrary:沉浸式状态栏
* Luban:鲁班图片压缩
* Picasso:Picasso图片加载
* Album:图片选择
* BaseRecyclerViewAdapterHelper：RecyclerView通用适配器
* EventBus：Eventbus
