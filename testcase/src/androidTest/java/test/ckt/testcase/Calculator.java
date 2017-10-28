package test.ckt.testcase;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Calculator {
    private String APP_DESC = "Apps";
    private String APPS_LIST_ID = "com.android.launcher3:id/apps_customize_pane_content";
    private String CALCULATOR_APP_TEXT = "Calculator";
    private String CALCULATOR_PACKAGE_PACK = "com.android.calculator2";
    private String NUMBER_PREFIX_ID = "com.android.calculator2:id/digit_";
    private String BUTTON_PREFIX_ID = "com.android.calculator2:id/";

    //定义数据源
    @Parameterized.Parameters
    public static Collection<Object[]> appname() {
        //根据需要定义参数的个数
        return Arrays.asList(new Object[][]{
                {1, 2, 3}, {2, 3, 5}, {6, 7, 8}, {9, 7, 15}, {9, 1, 10}
        });
    }

    //定义传参名字
    private int number1;
    private int number2;
    private int result;

    //定义参数的传递方式，这里是用到了两个参数
    public Calculator(int a, int b, int c) {
        number1 = a;
        number2 = b;
        result = c;
    }

    @Before
    public void openCalculator() throws UiObjectNotFoundException {
        DemoTools.getUiDevice().pressBack();
        DemoTools.getUiDevice().pressBack();
        DemoTools.getUiDevice().pressBack();
        DemoTools.getUiDevice().pressHome();

        //open App list and then open calculator.
        DemoTools.isClickByDesc(APP_DESC);
        UiScrollable appList = new UiScrollable(new UiSelector().resourceId(APPS_LIST_ID));
        appList.scrollTextIntoView(CALCULATOR_APP_TEXT);
        DemoTools.getUiDevice().wait(Until.hasObject(By.text(CALCULATOR_APP_TEXT)), 5000);
        DemoTools.isClickByText(CALCULATOR_APP_TEXT);
        Assume.assumeTrue("Open Calculator Fail!", DemoTools.getCurrentPackageName().equals(CALCULATOR_PACKAGE_PACK));

    }

    //运行的例子
    @Test
    public void AddTest() {

        try {
            inputNumber(number1);
            DemoTools.isClickByRes(BUTTON_PREFIX_ID + "op_add");
            inputNumber(number2);
            DemoTools.isClickByRes(BUTTON_PREFIX_ID + "eq");
            String actualResult = DemoTools.getUiObjectByRes(BUTTON_PREFIX_ID + "result").getText();
            Assert.assertEquals("The resutl do not match!", result + "", actualResult);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void inputNumber(int number) throws UiObjectNotFoundException {


        switch (number) {
            case 1:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 1);
                break;
            case 2:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 2);
                break;
            case 3:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 3);
                break;
            case 4:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 4);
                break;
            case 5:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 5);
                break;
            case 6:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 6);
                break;
            case 7:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 7);
                break;
            case 8:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 8);
                break;
            case 9:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 9);
                break;
            case 0:
                DemoTools.isClickByRes(NUMBER_PREFIX_ID + 0);
                break;


        }


    }
}