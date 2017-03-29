package rory.tech.blog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Test;



/**
 * Created by Rory on 2017/03/26   .
 */

public class ImageCompare {
    private static UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    /**
     * If you want compare two image is same or not,you can use this method.
     * I recommend use method to compare two screen short.
     * if you want to compare two photo,please use isSameAs(originalImagePath,compareImagePath).
     *
     * @param originalImagePath The original image path.
     * @param compareImagePath  the compare image path.
     * @param similarityPercent if the two image is same, Percent should be set to 100%
     * @return is two image is same,the result is true.
     */
    public static boolean isSameAs(String originalImagePath,
                                   String compareImagePath, double similarityPercent) {

        try {
            // Get the original image and compare image..
            Bitmap mBitmap1 = BitmapFactory.decodeFile(originalImagePath);
            Bitmap mBitmap2 = BitmapFactory.decodeFile(compareImagePath);
            //get the image width and height.
            int width = mBitmap2.getWidth();
            int height = mBitmap2.getHeight();
            // set the pixels number.
            int numDiffPixels = 0;
            // compare all pixels is same or not.
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // find all not same pixels.
                    if (mBitmap2.getPixel(x, y) != mBitmap1.getPixel(x, y)) {
                        numDiffPixels++;
                    }
                }
            }
            // get the all pixel of the picture.
            double totalPixels = height * width;
            // get the all different pixel of image..
            double diffPercent = numDiffPixels / totalPixels;
            //
            double result = 1.0 - diffPercent;
            System.out.println("The similarity Percent is:" + result);
            return similarityPercent <= result;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }
    @Test
    public void ImageCompare(){

        isSameAs();
    }
}
