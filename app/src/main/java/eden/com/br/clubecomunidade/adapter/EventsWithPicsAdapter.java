package eden.com.br.clubecomunidade.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.bean.Events;

/**
 * Created by root on 25/04/15.
 */
public class EventsWithPicsAdapter extends BaseAdapter{


    private final Context context;
    private final ArrayList<Events> events;
    private final LayoutInflater mInflater;
    DisplayImageOptions options;
    ImageLoader imageLoader = ImageLoader.getInstance();

    public EventsWithPicsAdapter(Context context, ArrayList<Events> events) {

        this.context = context;
        this.events = events;
        this.mInflater = ((Activity) context).getLayoutInflater();

        //this.mInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {

        //ViewUtils.setListViewHeightBasedOnChildren();

        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.events_with_pics_item, null);
        }

//        options = new DisplayImageOptions.Builder()
//                .showImageOnFail(R.mipmap.ic_error)
//                .showStubImage(R.mipmap.ic_launcher)
//                .showImageForEmptyUri(R.mipmap.ic_empty).cacheInMemory()
//                .cacheOnDisc().build();
//
//        TextView nameTV = (TextView) convertView.findViewById(R.id.events_item_name);
//        if(nameTV != null) nameTV.setText(this.events.get(position).getName());
//
//        ImageView mImageView = (ImageView) convertView.findViewById(R.id.events_item_pic);
//        imageLoader.displayImage(
//                ((Events) events.get(position)).getImageUrl(), mImageView, options);

//        if(parent instanceof ListView && getCount() > 0)
//            ViewUtils.setListViewHeightBasedOnChildren((ListView) parent);

        return convertView;




    }

}
