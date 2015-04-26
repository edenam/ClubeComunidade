package eden.com.br.clubecomunidade.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.bean.Events;
import eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener;
import eden.com.br.clubecomunidade.parse.ParseReader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EventsWithPicsFragment extends Fragment {

    public static final String FRAGMENT_TAG = "events_with_pics_fragment";
    private OnFragmentInteractionListener mListener;
    private ArrayList<Events> events = new ArrayList<Events>();
    ListView eventsListView;
    LayoutInflater inflater;
    View rootView;

    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private ImageLoadingListener imageListener;

    public EventsWithPicsFragment() {
        // Required empty public constructor

        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(90))
                .showImageOnFail(R.mipmap.ic_error)
                .showStubImage(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_empty).cacheInMemory()
                .cacheOnDisc().build();

        //imageListener = (ImageLoadingListener) new ImageDisplayListener();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(this.inflater == null)
            this.inflater = inflater;

        View rootView = this.inflater.inflate(R.layout.fragment_events_with_pics, container, false);
        this.rootView = rootView;

        getEventsWithPicsData(4);

//        eventsListView = (ListView) rootView.findViewById(R.id.events_with_pics_listview);
//        eventsListView.setAdapter(new EventsWithPicsAdapter(getActivity(), events));

        return rootView;
    }

    private void getEventsWithPicsData(int count) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");

        //query.whereEqualTo("visible", true);

        query
            .setLimit(count)
            .selectKeys(
                    Arrays.asList("objectId", "name", "picture", "date", "place")
            )
            .orderByAscending("date");


        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventsList, ParseException e) {
                if (e == null) {

                    ArrayList<Events> parsedEvents = ParseReader.getEventsForList(eventsList);
                    for (Events tempEvent : parsedEvents) {

                        //events.add(tempEvent);

                        View eventItem = inflater.inflate(R.layout.events_with_pics_item, null);

                        if(eventItem != null){

                            ImageView picIV = (ImageView) eventItem.
                                                        findViewById(R.id.events_item_pic);
                            String imageUrl = tempEvent.getImageUrl().trim();
                            if(picIV != null && imageUrl.length() > 0) {
                                imageLoader.displayImage(imageUrl, picIV, options);
                            }

                            TextView nameTV = (TextView) eventItem.findViewById(R.id.events_item_name);
                            if(nameTV != null) nameTV.setText(tempEvent.getName());

                            TextView dateTV = (TextView) eventItem.findViewById(R.id.events_item_date);
                            if(dateTV != null){

                                String dateFormat = DateFormat
                                        .getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"))
                                        .format(tempEvent.getDate());

                                dateTV.setText(dateFormat);
                            }

                            TextView placeTV = (TextView) eventItem.findViewById(R.id.events_item_place);
                            if(placeTV != null) placeTV.setText(tempEvent.getPlace());

                            if(rootView != null) {

                                eventItem.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        mListener.onFragmentInteraction( new HelloAppFragment() );

                                    }
                                });

                                ((ViewGroup) rootView).addView(eventItem);
                            }


                        }

                    }

//                    if(eventsListView != null)
//                        eventsListView.setAdapter(new EventsWithPicsAdapter(getActivity(), events));

                    Log.d("Parse", "Parsed: " + events.toString());

                } else {
                    Log.d("Parse", "Error: " + e.getMessage());
                }
            }
        });


    }

    private void teste() {

        Log.d("EventsTest", "Size: " + this.events.size());

    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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
