package rory.tech.blog;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

/**
 * Created by Rory on 2017/03/14.
 */

public class ContentProvider {


    //Define the content provider host url.
    private static Uri uri = ContactsContract.Data.CONTENT_URI;
    private static ContentResolver contentResolver = getTargetContext().getContentResolver();

    @Test
    public void testContentProvider() {
        Cursor cursorData = contentResolver.query(uri, null, null, null, null);
        if (cursorData != null) {
            while (cursorData.moveToNext()) {
                String mimeType = cursorData.getString(cursorData.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                String data1 = cursorData.getString(cursorData.getColumnIndex(ContactsContract.Contacts.Data.DATA1));
                System.out.println("AAA" + data1);
            }
        }
    }

    public static Context getTargetContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}

