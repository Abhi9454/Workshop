package workshop.workshop.com;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class dashboard extends Fragment {


    private OnFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private TextView profile_name, profile_email, profile_state, profile_phone;
    private Button my_workshop;

    public dashboard() {
    }

    public static dashboard newInstance(String param1, String param2) {
        dashboard fragment = new dashboard();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getActivity().getSharedPreferences("workshop.com", Context.MODE_PRIVATE);
        Log.i("name", "Name value " + sharedPreferences.getString("name", ""));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        profile_name = (TextView) view.findViewById(R.id.profilename);
        profile_email = (TextView) view.findViewById(R.id.profileemail);
        profile_state = (TextView) view.findViewById(R.id.profilestate);
        profile_phone = (TextView) view.findViewById(R.id.profilephone);
        my_workshop = (Button) view.findViewById(R.id.my_workshop);
        my_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplication(), myworkshop.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        changevalue();
        return view;
    }


    public void changevalue() {
        profile_name.setText(sharedPreferences.getString("name", ""));
        profile_email.setText(sharedPreferences.getString("email", ""));
        profile_state.setText(sharedPreferences.getString("state", ""));
        profile_phone.setText(sharedPreferences.getString("phone", ""));
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
