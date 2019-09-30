package infracom.abcr.sarthamicrofinance.slidingtab.contacts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import infracom.abcr.sarthamicrofinance.MainActivity;
import infracom.abcr.sarthamicrofinance.R;

/**
 * Created by samsung on 24/10/2016.
 */
public class TaskAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    public TaskAdapter(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        CircleImageView profile_pic;
        TextView member_name;
        TextView status;
        TextView contactType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.member_name = (TextView) convertView
                    .findViewById(R.id.member_name);
            holder.profile_pic = (CircleImageView) convertView
                    .findViewById(R.id.profile_pic);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            holder.contactType = (TextView) convertView
                    .findViewById(R.id.contact_typetime);

            RowItem row_pos = rowItems.get(position);
            Glide.with(context)
                    .load(row_pos.getProfile_pic_id().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.placeholder(R.drawable.ic_launcher)
                    .into(holder.profile_pic);
            holder.member_name.setText(row_pos.getMember_name());
            holder.status.setText(row_pos.getStatus());
            holder.contactType.setText(row_pos.getContactType());
            final ViewHolder finalHolder = holder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String Product = finalHolder.member_name.getText().toString().trim();
                    Toast.makeText(finalConvertView.getContext(), ""+Product, Toast.LENGTH_SHORT).show();
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();


//if(Product.toString().trim()=="Mobile"){}
                    ((MainActivity) context).privecyTerms();

                }
            });




            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


}
