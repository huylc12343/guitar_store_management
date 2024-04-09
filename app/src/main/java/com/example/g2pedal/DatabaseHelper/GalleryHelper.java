package com.example.g2pedal.DatabaseHelper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GalleryHelper {
    public static List<Uri> getAllImages(Context context,String category) {
        List<Uri> imageUris = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID};
        String selection = MediaStore.Images.Media.DATA + " LIKE ?";
        String queryString = "%Downloads/"+category+"%";
        String[] selectionArgs = new String[]{queryString};
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

        Cursor cursor = contentResolver.query(imageUri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                long imageId = cursor.getLong(columnIndex);
                Uri uri = Uri.withAppendedPath(imageUri, String.valueOf(imageId));
                imageUris.add(uri);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return imageUris;
    }
}
