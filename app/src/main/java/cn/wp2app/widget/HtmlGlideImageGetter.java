package cn.wp2app.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import cn.wp2app.R;

public class HtmlGlideImageGetter implements Html.ImageGetter, Drawable.Callback{
    private TextView container;
    private int containerWidth;
    private float scale = 1.0f;

    public HtmlGlideImageGetter(TextView textView, int w) {
        container = textView;
        containerWidth = w;
        container.setTag(R.id.html_image_getter_tag, this);
    }

    public static HtmlGlideImageGetter get(View view) {
        return (HtmlGlideImageGetter) view.getTag(R.id.html_image_getter_tag);
    }

    public Drawable getDrawable(final String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();

        /*
         另外一种判断文件是否是gif的方法，asFile, 当作文件下载下拉，从文件流中读取gif标志
         public class ImageKit {

            private static final String GIF_FILE_HEAD = "47494638";

            public static boolean isGif(InputStream inputStream) {
                if (inputStream.markSupported()) {
                    inputStream.mark(10);
                    byte[] bs = new byte[4];
                    try {
                        //noinspection ResultOfMethodCallIgnored
                        inputStream.read(bs, 0, bs.length);
                        inputStream.reset();
                        return GIF_FILE_HEAD.equals(bytesToHexString(bs));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            public static boolean isGif(byte[] bytes) {
                byte[] bs = new byte[4];
                System.arraycopy(bytes, 0, bs, 0, 4);
                return GIF_FILE_HEAD.equals(bytesToHexString(bs));
            }

            public static boolean isGif(String path) {
                FileInputStream fileInputStream = null;
                BufferedInputStream bufferedInputStream = null;
                try {
                    fileInputStream = new FileInputStream(path);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    return isGif(bufferedInputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            private static String bytesToHexString(byte[] src) {
                StringBuilder stringBuilder = new StringBuilder();
                if (src == null || src.length <= 0) {
                    return null;
                }
                for (byte aSrc : src) {
                    int v = aSrc & 0xFF;
                    String hv = Integer.toHexString(v);
                    if (hv.length() < 2) {
                        stringBuilder.append(0);
                    }
                    stringBuilder.append(hv);
                }
                return stringBuilder.toString();
            }

        }
         */
        final RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
        if( isGif(source) )
        {
            Glide.with(container.getContext()).asGif().apply(requestOptions).load(source).into(new SimpleTarget<GifDrawable>() {
                @Override
                public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                    int w = resource.getIntrinsicWidth();
                    int h = resource.getIntrinsicHeight();

                    int width = containerWidth;//container.getWidth() - container.getPaddingRight() - container.getPaddingLeft();
                    if( width == 0) width = w;
                    scale = ((float)width/(float)w);

                    urlDrawable.gifDrawable = resource;
                    urlDrawable.gifDrawable.setCallback(get(container));

                    urlDrawable.gifDrawable.setBounds(0, 0, width, (int)(h*scale));
                    urlDrawable.setBounds(0, 0, width, (int)(h*scale));
                    urlDrawable.gifDrawable.start();
                }
            });

        }
        else {

            Glide.with(container.getContext()).asBitmap().apply(requestOptions).load(source).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int w = resource.getWidth();
                    int h = resource.getHeight();
                    int width  = containerWidth;//container.getWidth() - container.getPaddingRight() - container.getPaddingLeft();

                    if(width == 0) width = w;

                    scale = ((float)width/(float)w);

                    if (scale > 0) {
                        //Matrix matrix = new Matrix();
                        //matrix.postScale(scaleWidth, scaleWidth);
                        //resource.setConfig(Bitmap.Config.ARGB_4444);

                        if (w > 0 && h  > 0) {
                            //Bitmap bmp = Bitmap.createBitmap(resource, 0, 0, w, h, matrix, false);
                            urlDrawable.bitmap = new BitmapDrawable(container.getResources(),resource);

                            urlDrawable.bitmap.setBounds(0, 0, width, (int)(h*scale));
                            urlDrawable.setBounds(0, 0, width, (int)(h*scale));

                            //container.invalidateDrawable(urlDrawable);
                            container.invalidateOutline();
                            container.setText(container.getText());
                        }
                    }
                }
            });
        }

        // return reference to URLDrawable which will asynchronously load the image specified in the src tag
        return urlDrawable;
    }



    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected BitmapDrawable bitmap = null;
        protected GifDrawable gifDrawable = null;

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.save();
            //canvas.clipRect(getBounds());
            //canvas.scale(scale, scale);
            if( gifDrawable != null )
            {
                gifDrawable.draw(canvas);
            }
            else
            {
                if (bitmap != null)     bitmap.draw(canvas);

            }
            canvas.restore();
        }
    }


    @Override
    public void invalidateDrawable(Drawable who) {
        if (container != null) {
            container.post(new Runnable() {
                @Override
                public void run() {
                    container.invalidateOutline();
                    container.setText(container.getText());
                }
            });
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {

    }


    private boolean isGif(String source)
    {
        int index = source.lastIndexOf('.');
        return index > 0 && "gif".toUpperCase().equals(source.substring(index + 1).toUpperCase());
    }
}

