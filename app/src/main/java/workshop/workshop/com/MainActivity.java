package workshop.workshop.com;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private Integer count = 0;
    private ListView alllist;
    private Helperclass helperclass;
    private ArrayList<Integer> uwid = new ArrayList<>();
    private Listviewadapterall listviewadapter;
    private ArrayList<Workshop_details> allworkshop = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helperclass = new Helperclass(this);
        helperclass.clearrecord();
        helperclass.clearworkshop_detail();
        helperclass.insertworkshop("Android Workshop", "https://cdn.pixabay.com/photo/2015/01/19/18/35/android-604356_960_720.jpg", "Java,Android");
        helperclass.insertworkshop("IOS Workshop", "https://cdn.pixabay.com/photo/2018/01/28/10/59/internet-3113279_960_720.jpg", "Swift, C#");
        helperclass.insertworkshop("AI", "https://cdn.pixabay.com/photo/2015/03/08/09/30/head-663997_960_720.jpg", "Python");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("workshop.com", MODE_PRIVATE);
        alllist = (ListView) findViewById(R.id.allgrid);
        listviewadapter = new Listviewadapterall(allworkshop);
        alllist.setAdapter(listviewadapter);
        if (sharedPreferences.getString("login", "").equals("")) {
            sharedPreferences.edit().putString("login", "0").apply();
        }
        uwid.clear();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem nav_dashboard = menu.findItem(R.id.nav_dashboard);
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);
        if (sharedPreferences.getString("login", "").equals("0")) {
            nav_dashboard.setTitle("Login");
            nav_logout.setTitle("Register");
        }
        navigationView.setNavigationItemSelectedListener(this);
        readworkshop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            count++;
            if (count == 1) {
                Toast.makeText(getApplicationContext(), "Press one more time to exit.", Toast.LENGTH_LONG).show();
            } else if (count == 2) {
                finishAffinity();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void readworkshop() {
        allworkshop.addAll(helperclass.getAllWorkshop());
        listviewadapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_contact) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.aboutus).setTitle("Contact Email");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Log.i("fragment", "done" + sharedPreferences.getString("login", ""));
            if (sharedPreferences.getString("login", "").equals("1")) {
                alllist.setVisibility(View.GONE);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_main, new dashboard());
                fragmentTransaction.commit();
                Log.i("fragment", "done");
            } else if (sharedPreferences.getString("login", "").equals("0")) {
                startActivity(new Intent(getApplicationContext(), login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

        } else if (id == R.id.nav_logout) {


            if (sharedPreferences.getString("login", "").equals("1")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                this.recreate();
            } else if (sharedPreferences.getString("login", "").equals("0")) {
                startActivity(new Intent(getApplicationContext(), register.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        } else if (id == R.id.nav_home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class Listviewadapterall extends BaseAdapter {

        public ArrayList<Workshop_details> mainl;

        public Listviewadapterall(ArrayList<Workshop_details> mainl) {
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
            Picasso.get().load(lists.getWork_details()).fit().into(imageView);
            title.setText(lists.getWork_head());
            tech.setText(lists.getColoumn_tech());
            uwid.clear();
            uwid.addAll(helperclass.getworkshops(sharedPreferences.getString("email", "")));
            Log.i("entered", "list " + uwid);
            if (uwid.contains(lists.getId())) {
                apply.setText("Applied");
                apply.setEnabled(false);
            }
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (sharedPreferences.getString("login", "").equals("1")) {
                            if (!uwid.contains(lists.getId())) {
                                helperclass.user_workinsert(sharedPreferences.getString("email", ""), lists.getId());
                                uwid.clear();
                                uwid.addAll(helperclass.getworkshops(sharedPreferences.getString("email", "")));
                                Toast.makeText(getApplicationContext(), "Applied.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Already applied.", Toast.LENGTH_LONG).show();
                            }
                        } else if (sharedPreferences.getString("login", "").equals("0")) {
                            startActivity(new Intent(getApplicationContext(), login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return view;
        }
    }


}
