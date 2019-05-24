package workshop.workshop.com;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myworkshop extends AppCompatActivity {


    private ListView alllist;
    private SharedPreferences sharedPreferences;
    private Helperclass helperclass;
    private Listworkshopall listworkshopall;
    private ArrayList<Workshop_details> allworkshop1 = new ArrayList<>();
    private RelativeLayout nodetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myworkshop);
        alllist = (ListView) findViewById(R.id.allworkshoplist);
        helperclass = new Helperclass(this);
        nodetails = (RelativeLayout) findViewById(R.id.nodetails);
        sharedPreferences = getSharedPreferences("workshop.com", MODE_PRIVATE);
        listworkshopall = new Listworkshopall(allworkshop1);
        alllist.setAdapter(listworkshopall);
        loadmyworkshop();
    }

    private void loadmyworkshop() {
        allworkshop1.addAll(helperclass.registeredworkshop(sharedPreferences.getString("email", "")));
        listworkshopall.notifyDataSetChanged();
        if (allworkshop1.size() <= 0) {
            nodetails.setVisibility(View.VISIBLE);
        }
    }


    public class Listworkshopall extends BaseAdapter {

        public ArrayList<Workshop_details> mainl;

        public Listworkshopall(ArrayList<Workshop_details> mainl) {
            this.mainl = mainl;
        }

        @Override
        public int getCount() {
            return mainl.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LayoutInflater layoutInflater = getLayoutInflater();
            final View view = layoutInflater.inflate(R.layout.listlayout, null);
            final Workshop_details lists = mainl.get(position);
            final ImageView imageView = (ImageView) view.findViewById(R.id.mainimage);
            final TextView title = (TextView) view.findViewById(R.id.texttitle);
            final TextView tech = (TextView) view.findViewById(R.id.tech);
            final Button apply = (Button) view.findViewById(R.id.apply);
            apply.setVisibility(View.GONE);
            Picasso.get().load(lists.getWork_details()).fit().into(imageView);
            title.setText(lists.getWork_head());
            tech.setText(lists.getColoumn_tech());
            return view;
        }
    }

}
