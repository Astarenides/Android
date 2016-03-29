package org.micasa.extrasmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Astarenides on 01/10/2015.
 */
public class Botes extends Fragment {

    private ImageButton btnMenuBotes;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflator,ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflator.inflate(R.layout.botes, parent, false);

        btnMenuBotes = (ImageButton) rootView.findViewById(R.id.btnMenuBotes);
        btnMenuBotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });
        return rootView;
    }
}
