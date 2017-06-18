package com.mta.livedataexample;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mta.livedataexample.databinding.FragmentLocBinding;


/**
 * Use the {@link LocFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocFragment extends LifecycleFragment {

    private static final String TAG = LocFragment.class.getSimpleName();
    //    private OnFragmentInteractionListener mListener;
    FragmentLocBinding binding;

    public LocFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LocFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocFragment newInstance() {
        LocFragment fragment = new LocFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LiveData<Location> myLocationListener = LocationLiveData.get(getActivity());
        Util.checkUserStatus(new Util.MyCallback() {
            @Override
            public void result(boolean ok) {
                if (ok) {
                    // observing the location and the lifecycle
                    myLocationListener.observe(LocFragment.this, new Observer<Location>() {
                        @Override
                        public void onChanged(@Nullable Location location) {
                            // update the UI

                            Log.i(TAG, "onChange " + location);

                            binding.setLoc(location);

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_loc, container, false);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_loc, container, false);

        Location user = new Location("stam");
        binding.setLoc(user);

        return binding.getRoot();
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
