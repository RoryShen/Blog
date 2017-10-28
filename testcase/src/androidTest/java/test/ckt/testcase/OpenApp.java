package test.ckt.testcase;

import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OpenApp {
    //定义数据源
    @Parameterized.Parameters
    public static Collection<Object[]> appname() {
//根据需要定义参数的个数
        return Arrays.asList(new Object[][]{
                {"电话", "com.android.dialer",}, {"电子邮件", "com.android.email"}, {"短信", "com.android.dialer"}
        });
    }
    //定义传参名字
    private String target;
    private String result;
//定义参数的传递方式，这里是用到了两个参数
    public OpenApp(String a, String b) {
        target = a;
        result = b;
    }
//运行的例子
    @Test
    public void openapp() throws InterruptedException, UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        PackageManager packageManager = InstrumentationRegistry.getTargetContext().getPackageManager();
        mDevice.findObject(new UiSelector().description("应用")).click();
        Thread.sleep(1000);
        mDevice.findObject(new UiSelector().text(target)).click();
        Thread.sleep(1000);
        String curentPackageName = mDevice.getCurrentPackageName();
        Thread.sleep(1000);
        System.out.println("ABS" + curentPackageName);
        Assert.assertEquals("Test Fail,The Package info not match!", result, curentPackageName);
        mDevice.pressBack();
        mDevice.pressBack();
        mDevice.pressBack();
        mDevice.pressBack();
    }
}