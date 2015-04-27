/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */

package eden.com.br.clubecomunidade.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import eden.com.br.clubecomunidade.DAO.DAO;
import eden.com.br.clubecomunidade.DAO.DAOAccessCallback;
import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.adapter.ImageSlideAdapter;
import eden.com.br.clubecomunidade.bean.News;
import eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener;
import eden.com.br.clubecomunidade.json.GetJSONObject;
import eden.com.br.clubecomunidade.json.JsonReader;
import eden.com.br.clubecomunidade.utils.CheckNetworkConnection;
import eden.com.br.clubecomunidade.utils.CirclePageIndicator;
import eden.com.br.clubecomunidade.utils.PageIndicator;
import eden.com.br.clubecomunidade.utils.TagName;

public class BannerNewsFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public static final String FRAGMENT_TAG = "banner_news_fragment";

    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

    private static final int NEWS_COUNT_ON_BANNER = 5;

    // UI References
    private ViewPager mViewPager;
    TextView imgNameTxt;
    PageIndicator mIndicator;
    ProgressBar loadingProgressBar;

    AlertDialog alertDialog;

    List<News> news;
    RequestImgTask task;
    boolean stopSliding = false;
    String message;

    private Runnable animateViewPager;
    private Handler handler;

    String url = "http://192.168.0.3:8080/json2.php";
    FragmentActivity activity;



    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BannerNewsFragment newInstance(int sectionNumber) {
        BannerNewsFragment fragment = new BannerNewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public BannerNewsFragment() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Fragment fragment, Bundle arguments) {
        if (mListener != null) {
            mListener.onFragmentInteraction(fragment);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    // TODO remover as referencias para variável "activity" que está redundante (já há referencia na variável this.mListener)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banner_news, container, false);

        findViewById(rootView);

        mIndicator.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager
                        if (news != null && news.size() != 0) {
                            stopSliding = false;
                            runnable(news.size());
                            handler.postDelayed(animateViewPager,
                                    ANIM_VIEWPAGER_DELAY_USER_VIEW);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && stopSliding == false) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        if (news == null) {
            // Fetch data via HTTPCLIENT (Json format)
            // sendRequest();
            // Fetch data via the Parse BAAS
            populateBanner();

        } else {
            mViewPager.setAdapter(new ImageSlideAdapter(activity, news,
                    BannerNewsFragment.this, mListener));

            mIndicator.setViewPager(mViewPager);
            imgNameTxt.setText(""
                    + ((News) news.get(mViewPager.getCurrentItem()))
                    .getHeadLine());
            runnable(news.size());
            //Re-run callback
            handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (task != null)
            task.cancel(true);
        if (handler != null) {
            //Remove callback
            handler.removeCallbacks(animateViewPager);
        }
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void populateBanner() {
        if (CheckNetworkConnection.isConnectionAvailable(activity)) {

            DAO.getInstance().getNewsForHomeBanner(NEWS_COUNT_ON_BANNER, new DAOAccessCallback() {
                @Override
                public void done(List<?> news, Exception e) {

                    if (news != null && news.size() != 0) {

                        imgNameTxt.setText(""
                                + ((News) news.get(mViewPager
                                .getCurrentItem())).getHeadLine());
                        runnable(news.size());
                        handler.postDelayed(animateViewPager,
                                ANIM_VIEWPAGER_DELAY);

                        mViewPager.setAdapter(new ImageSlideAdapter(activity, (List<News>) news, BannerNewsFragment.this, mListener ));

                        mIndicator.setViewPager(mViewPager);

                        loadingProgressBar.setVisibility(View.GONE);



                    }
                }
            });



        } else {

            // TODO pensar numa melhor implementação para quando usuário nao estiver conectado
            message = getResources().getString(R.string.no_internet_connection);
            showAlertDialog(message, true);
        }
    }

    private void sendRequest() {
        if (CheckNetworkConnection.isConnectionAvailable(activity)) {
            task = new RequestImgTask(activity);
            task.execute(url);
        } else {
            message = getResources().getString(R.string.no_internet_connection);
            showAlertDialog(message, true);
        }
    }

    public void showAlertDialog(String message, final boolean finish) {
        alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (finish)
                            activity.finish();
                    }
                });
        alertDialog.show();
    }

    private class RequestImgTask extends AsyncTask<String, Void, List<News>> {
        private final WeakReference<Activity> activityWeakRef;
        Throwable error;

        public RequestImgTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<News> doInBackground(String... urls) {
            try {
                JSONObject jsonObject = getJsonObject(urls[0]);
                if (jsonObject != null) {
                    boolean status = jsonObject.getBoolean(TagName.TAG_STATUS);

                    if (status) {
                        JSONObject jsonData = jsonObject
                                .getJSONObject(TagName.TAG_DATA);

                        if (jsonData != null) {
                            news = JsonReader.getHome(jsonData);

                        } else {
                            message = jsonObject.getString(TagName.TAG_DATA);
                        }
                    } else {
                        message = jsonObject.getString(TagName.TAG_DATA);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return news;
        }

        /**
         * It returns jsonObject for the specified url.
         *
         * @param url
         * @return JSONObject
         */
        public JSONObject getJsonObject(String url) {
            JSONObject jsonObject = null;
            try {
                // MOCK!!!
                //jsonObject = new JSONObject("{ \"status\": true, \"data\": { \"products\": [ { \"id\": \"434\", \"headline\": \"Pattern - Fractal Wallpaper\", \"image_url\": \"http://images5.alphacoders.com/350/350374.jpg\" }, { \"id\": \"431\", \"headline\": \"Mickey Mouse\", \"image_url\": \"http://www.iconsdb.com/icons/download/icon-sets/sketchy-pink/mickey-mouse-20-512.png\" }, { \"id\": \"424\", \"headline\": \"Pattern - Wallpaper\", \"image_url\": \"http://images7.alphacoders.com/421/421423.jpg\" }, { \"id\": \"426\", \"headline\": \"Batman\", \"image_url\": \"http://www.iconsdb.com/icons/download/icon-sets/sketchy-pink/batman-6-512.png\" }, { \"id\": \"419\", \"headline\": \"Pattern - Music\", \"image_url\": \"http://images3.alphacoders.com/169/169085.jpg\" } ] } }");
                jsonObject = GetJSONObject.getJSONObject(url);
            } catch (Exception e) {
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(List<News> result) {
            super.onPostExecute(result);

            if (activityWeakRef != null && !activityWeakRef.get().isFinishing()) {
                if (error != null && error instanceof IOException) {
                    message = getResources().getString(R.string.time_out);
                    showAlertDialog(message, true);
                } else if (error != null) {
                    message = getResources().getString(R.string.error_occured);
                    showAlertDialog(message, true);
                } else {
                    news = result;
                    if (result != null) {
                        if (news != null && news.size() != 0) {

                            mViewPager.setAdapter(new ImageSlideAdapter(
                                    activity, news, BannerNewsFragment.this, mListener));

                            mIndicator.setViewPager(mViewPager);
                            imgNameTxt.setText(""
                                    + ((News) news.get(mViewPager
                                    .getCurrentItem())).getHeadLine());
                            runnable(news.size());
                            handler.postDelayed(animateViewPager,
                                    ANIM_VIEWPAGER_DELAY);
                        } else {
                            imgNameTxt.setText("No Products");
                        }
                    } else {
                    }
                }
            }
        }
    }

    private void findViewById(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        imgNameTxt = (TextView) view.findViewById(R.id.img_name);
        loadingProgressBar = (ProgressBar) view.findViewById(R.id.loadingNews);
    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mViewPager.getCurrentItem() == size - 1) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }



    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (news != null) {
                    imgNameTxt.setText(""
                            + ((News) news.get(mViewPager.getCurrentItem())).getHeadLine());
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
        }
    }

}