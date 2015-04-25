package eden.com.br.clubecomunidade.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.bean.News;
import eden.com.br.clubecomunidade.fragment.NewsDetailFragment;
import eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener;

public class ImageSlideAdapter extends PagerAdapter {
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private ImageLoadingListener imageListener;
    FragmentActivity activity;
    List<News> news;
    Fragment fragment;

    private OnFragmentInteractionListener mListener;

    public ImageSlideAdapter(FragmentActivity activity, List<News> news,
                             Fragment fragment, OnFragmentInteractionListener mListener) {

        this.activity = activity;
        this.fragment = fragment;
        this.news = news;
        this.mListener = mListener;

        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_error)
                .showStubImage(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_empty).cacheInMemory()
                .cacheOnDisc().build();

        imageListener = (ImageLoadingListener) new ImageDisplayListener();
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public View instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        final View view = inflater.inflate(R.layout.vp_image, container, false);
        ImageView mImageView = (ImageView) view.findViewById(R.id.image_display);

        if(mImageView != null) {
            mImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle arguments = new Bundle();
                    NewsDetailFragment fragment;

                    News singleNew = news.get(position);

                    fragment = new NewsDetailFragment();

                    arguments.putParcelable("singleProduct", singleNew);
                    fragment.setArguments(arguments);

                    mListener.onFragmentInteraction(fragment);
                }
            });
        }

        imageLoader.displayImage(
                ((News) news.get(position)).getImageUrl(), mImageView,
                options, imageListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private static class ImageDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}