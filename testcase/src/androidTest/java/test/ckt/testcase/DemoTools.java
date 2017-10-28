package test.ckt.testcase;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

/**
 * Created by Rory on 2017/09/02   .
 */

public class DemoTools {
    public static UiDevice getUiDevice() {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public static UiObject getUiObjectByText(String targetText) {
        return getUiDevice().findObject(new UiSelector().text(targetText));
    }

    public static String getCurrentPackageName() {
        return getUiDevice().getCurrentPackageName();
    }

    public static UiObject getUiObjectByRes(String targetRes) {
        return getUiDevice().findObject(new UiSelector().resourceId(targetRes));
    }

    public static UiObject getUiObjectByDesc(String desc) {
        return getUiDevice().findObject(new UiSelector().description(desc));
    }

    public static boolean isClickByDesc(String desc) throws UiObjectNotFoundException {
        return getUiObjectByDesc(desc).click();
    }

    public static boolean isClickByText(String targetText) throws UiObjectNotFoundException {
        return getUiObjectByText(targetText).click();
    }

    public static boolean isClickByRes(String targetRes) throws UiObjectNotFoundException {
        return getUiObjectByRes(targetRes).click();
    }


}
