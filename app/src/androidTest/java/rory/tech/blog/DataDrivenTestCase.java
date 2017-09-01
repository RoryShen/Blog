package rory.tech.blog;

import android.os.Environment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

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


    @Test
    public void Print1() {
        System.out.println(appName + ";" + appPackage);
    }
}
