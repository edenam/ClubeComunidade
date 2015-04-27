package eden.com.br.clubecomunidade.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import eden.com.br.clubecomunidade.DAO.DAO;
import eden.com.br.clubecomunidade.DAO.DAOAccessCallback;
import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.bean.Event;
import eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EventsWithPicsFragment extends Fragment {

    public static final String FRAGMENT_TAG = "events_with_pics_fragment";
    private static final int EVENTS_COUNT_ON_MAIN_LIST = 4;

    private OnFragmentInteractionListener mListener;

    LayoutInflater inflater;
    View rootView;

    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;

    public EventsWithPicsFragment() {

        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(90))
                .showImageOnFail(R.mipmap.ic_error)
                .showStubImage(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_empty).cacheInMemory(true)
                .cacheOnDisc().build();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(this.inflater == null)
            this.inflater = inflater;

        View rootView = this.inflater.inflate(R.layout.fragment_events_with_pics, container, false);
        this.rootView = rootView;

        populateEventsList(EVENTS_COUNT_ON_MAIN_LIST);

        return rootView;
    }

    private void populateEventsList(int count) {

        DAO.getInstance().getEventsWithPics(count, new DAOAccessCallback() {
            @Override
            public void done(List<?> events, Exception e) {

                if (e == null) {

                    if (rootView != null) {
                        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.loadingEvents);
                        if(progressBar != null)
                            progressBar.setVisibility(View.GONE);
                    }

                    for (Event tempEvent : (List<Event>) events) {

                        View eventItem = inflater.inflate(R.layout.events_with_pics_item, null);

                        if (eventItem != null) {

                            ImageView picIV = (ImageView) eventItem.
                                    findViewById(R.id.events_item_pic);
                            String imageUrl = tempEvent.getImageUrl().trim();
                            if (picIV != null && imageUrl.length() > 0) {
                                imageLoader.displayImage(imageUrl, picIV, options);
                            }

                            TextView nameTV = (TextView) eventItem.findViewById(R.id.events_item_name);
                            if (nameTV != null) nameTV.setText(tempEvent.getName());

                            TextView dateTV = (TextView) eventItem.findViewById(R.id.events_item_date);
                            if (dateTV != null) {

                                String dateFormat = DateFormat
                                        .getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"))
                                        .format(tempEvent.getDate());

                                dateTV.setText(dateFormat);
                            }

                            TextView placeTV = (TextView) eventItem.findViewById(R.id.events_item_place);
                            if (placeTV != null) placeTV.setText(tempEvent.getPlace());

                            if (rootView != null) {

                                eventItem.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        mListener.onFragmentInteraction(new HelloAppFragment());

                                    }
                                });

                                ((ViewGroup) rootView).addView(eventItem);
                            }


                        }

                    }
                }
            }

        });
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
}
