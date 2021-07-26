package com.example.hamiltontevin_ce06.adapter;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.hamiltontevin_ce06.R;

import java.io.File;

public class ImageAdapter extends BaseAdapter {

    private final File[] mImagesArray ;
    private final Context mContext ;

    public ImageAdapter(Context _context ,File[] _imagesArray) {
        this.mImagesArray = _imagesArray;
        mContext = _context;
    }

    @Override
    public int getCount() {
        if(mImagesArray != null){
            return mImagesArray.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mImagesArray != null && mImagesArray.length > 0){
            return mImagesArray[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.gridview_image_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (mImagesArray != null) {
            File getFile = (File) getItem(position);
            vh.imageHolder.setImageBitmap(fileToBitmap(position));

            return convertView;
        }
        return null;
    }

    private Bitmap fileToBitmap(int pos){
        int reqHeight = 120;
        int reqWidth = 120;

        BitmapFactory.Options options = new BitmapFactory.Options();

        BitmapFactory.decodeFile(String.valueOf(mImagesArray[pos]), options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        return BitmapFactory.decodeFile(String.valueOf(mImagesArray[pos]), options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    static class ViewHolder{
        final ImageView imageHolder;

        ViewHolder(View layout) {
            this.imageHolder = layout.findViewById(R.id.iv_imageViewHolder);
        }
    }
}
