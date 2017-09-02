package rory.tech.blog;

import android.os.Environment;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static junit.framework.Assert.fail;

/**
 * 在Uiautomator中使用参数化的Demo.
 */

/*
1. 指定运行器为Parameterized.class
 */
@RunWith(Parameterized.class)
public class DataDrivenTestCase {
    /*
    定义需要的参数类型
     */
    // @Parameterized.Parameter(0)
    private String appName;
    private String appPackage;

    /*
    在构建方法里指定参数的对应关系
     */
    public DataDrivenTestCase(String target, String result) {

        this.appName = target;
        this.appPackage = result;

    }


    @Parameterized.Parameters
    public static Collection testDataSource() throws IOException {
        InputStream excelURL = new FileInputStream(Environment.getExternalStorageDirectory() + "/1.xls");
        return new ExcelReader(excelURL).getData();


    }

//    UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//    UiObject appTarget = mDevice.findObject(new UiSelector().text(appName));

    @Before
    public void clear() {
        DemoTools.getUiDevice().pressBack();
        DemoTools.getUiDevice().pressBack();
        DemoTools.getUiDevice().pressBack();
        DemoTools.getUiDevice().pressHome();
    }

    @Test
    public void OpenAppFormList() {
        try {
            DemoTools.isClickByDesc("Apps");
            UiScrollable uiScrollable = new UiScrollable(new UiSelector().resourceId("com.android.launcher3:id/apps_customize_pane_content"));
            if (DemoTools.getUiDevice().wait(Until.hasObject(By.res("com.android.launcher3:id/active")), 5000)) {
                uiScrollable.setAsHorizontalList();
                if (uiScrollable.scrollTextIntoView(appName)) {
                    DemoTools.isClickByText(appName);
                    Thread.sleep(1000);
                    Assert.assertTrue("Test Fail,Package does not match!", DemoTools.getCurrentPackageName().equals(appPackage));
                } else {
                    fail("The Target Object " + appName + " Not Found!");
                }
            }


        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            Log.i("BlogDemo", e.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void clickAllAppButton() {
        try {
            DemoTools.isClickByDesc("Apps");
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
