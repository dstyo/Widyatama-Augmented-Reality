package com.widyatama.ar.dstyo.fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dopanic.panicarkit.lib.PARController;
import com.dopanic.panicarkit.lib.PARFragment;
import com.dopanic.panicarkit.lib.PARPoiLabel;
import com.dopanic.panicarkit.lib.PARPoiLabelAdvanced;
import com.dopanic.panicsensorkit.PSKDeviceAttitude;
import com.dopanic.panicsensorkit.enums.PSKDeviceOrientation;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.widyatama.ar.dstyo.R;
import com.widyatama.ar.dstyo.adapter.WidyatamaAdapter;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by DSTYO 01.01.16.
 */

public class WidyatamaARFragment extends PARFragment {

    private static ArrayList<PARPoiLabel> labelRepo = new ArrayList<PARPoiLabel>();
    private static ArrayList<PARPoiLabel> labelRepoSearch = new ArrayList<PARPoiLabel>();
    private AutoCompleteTextView autoCompleteTextView;
    private WidyatamaAdapter widyatamaAdapter;
    private FloatingActionButton fabSearch, fabAbout;
    private FloatingActionMenu floatingActionMenu;
    private RelativeLayout rlSearch;
    private FloatingActionButton fabMyLocation;
    private CoordinatorLayout coordinatorLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.viewLayoutId = R.layout.widyatama_ar_view;
        View view = super.onCreateView(inflater, container, savedInstanceState);
        createWidyatamaPOI();
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorlayout);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoRuangan);
        fabSearch = (FloatingActionButton) view.findViewById(R.id.fabmenu_search);
        fabAbout = (FloatingActionButton) view.findViewById(R.id.fabmenu_about);
        fabMyLocation = (FloatingActionButton) view.findViewById(R.id.fabmenu_mylocation);
        floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.fabmenu);
        rlSearch = (RelativeLayout) view.findViewById(R.id.rlsearch);
        widyatamaAdapter = new WidyatamaAdapter(getActivity(), viewLayoutId, R.id.lbl_name, labelRepo);
        autoCompleteTextView.setAdapter(widyatamaAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                labelRepoSearch.clear();
                labelRepoSearch.add(labelRepo.get(position));
                PARController.getInstance().clearObjects();
                PARController.getInstance().addPoi(labelRepoSearch.get(0));
                hideKeyboard(autoCompleteTextView);
            }
        });
        getRadarView().setRadarRange(0);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlSearch.getVisibility() == View.VISIBLE) {
                    Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_scale_down);
                    rlSearch.startAnimation(out);
                    rlSearch.setVisibility(View.GONE);
                    floatingActionMenu.close(true);
                }
                else {
                    Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_scale_up);
                    rlSearch.startAnimation(in);
                    rlSearch.setVisibility(View.VISIBLE);
                    floatingActionMenu.close(true);
                }
            }
        });

        fabAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CompasActivity.class);
//                startActivity(intent);
            }
        });
        fabMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Location currentLocation = PSKDeviceAttitude.sharedDeviceAttitude().getLocation();
//                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Latitude="+currentLocation.getLatitude()+",Longtitude="+currentLocation.getLongitude(), Snackbar.LENGTH_LONG);
//                View sbView = snackbar.getView();
//                sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueds));
//                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                textView.setTextColor(Color.WHITE);
//                snackbar.show();
//                Intent intent = new Intent(getActivity(), MapsActivity.class);
//                if(labelRepoSearch.size()!=0) {
//                    intent.putExtra("latitude", labelRepoSearch.get(0).getLocation().getLatitude());
//                    intent.putExtra("longitude", labelRepoSearch.get(0).getLocation().getLongitude());
//                    intent.putExtra("title", labelRepoSearch.get(0).getTitle());
//                }
//                startActivity(intent);

            }
        });
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_random_poi:
                int random = (new Random().nextInt(labelRepo.size() - 1) + 0);
                PARController.getInstance().addPoi(labelRepo.get(random));
                Toast.makeText(this.getActivity(), "Added: " + labelRepo.get(random).getTitle(), Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            case R.id.action_delete_last_poi:
                if (PARController.getInstance().numberOfObjects() > 0) {
                    int lastObject = PARController.getInstance().numberOfObjects() - 1;
                    Toast.makeText(this.getActivity(), "Removing: " + ((PARPoiLabel) PARController.getInstance().getObject(lastObject)).getTitle(), Toast.LENGTH_SHORT).show();
                    PARController.getInstance().removeObject(lastObject);
                }
                return super.onOptionsItemSelected(item);
            case R.id.action_delete_all_pois:
                PARController.getInstance().clearObjects();
                return super.onOptionsItemSelected(item);
            case R.id.lokasi:
                Location currentLocation = PSKDeviceAttitude.sharedDeviceAttitude().getLocation();
                Toast.makeText(getActivity(), "LAT=" + currentLocation.getLatitude() + "," + "LONG" + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    //==============================================================================================
    // Callback
    //==============================================================================================
    @Override
    public void onDeviceOrientationChanged(PSKDeviceOrientation newOrientation) {
        super.onDeviceOrientationChanged(newOrientation);
        //komen
        //Toast.makeText(getActivity(), "onDeviceOrientationChanged: " + PSKDeviceAttitude.rotationToString(newOrientation), Toast.LENGTH_LONG).show();
    }

    //==============================================================================================
    // Create Label
    //==============================================================================================

    /**
     * Create a poi with title, description and position
     *
     * @param title       Title of poi
     * @param description Description of poi (if you want none, set this to "")
     * @param lat         Latitude of poi
     * @param lon         Longitude of poi
     * @return PARPoiLabel which is a subclass of PARPoi (extended for title, description and so on)
     */
    public PARPoiLabel createPoi(String title, String description, double lat, double lon) {
        Location poiLocation = new Location(title);
        poiLocation.setLatitude(lat);
        poiLocation.setLongitude(lon);

        final PARPoiLabel parPoiLabel = new PARPoiLabel(poiLocation, title, description, R.layout.widyatama_ar_poilabel, R.drawable.radar_dot_teal);

        parPoiLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), parPoiLabel.getTitle() + " - " + parPoiLabel.getDescription(), Toast.LENGTH_LONG).show();
            }
        });

        return parPoiLabel;
    }

    /**
     * Create a poi with title, description and position
     *
     * @param title       Title of poi
     * @param description Description of poi (if you want none, set this to "")
     * @param lat         Latitude of poi
     * @param lon         Longitude of poi
     * @return PARPoiLabelAdvanced which is a subclass of PARPoiLabel (extended for altitude support)
     */
    public PARPoiLabelAdvanced createPoi(String title, String description, double lat, double lon, double alt) {
        Location poiLocation = new Location(title);
        poiLocation.setLatitude(lat);
        poiLocation.setLongitude(lon);
        poiLocation.setAltitude(alt);

        final PARPoiLabelAdvanced parPoiLabel = new PARPoiLabelAdvanced(poiLocation, title, description, R.layout.widyatama_ar_poilabel, R.drawable.radar_dot_teal);
        parPoiLabel.setIsAltitudeEnabled(true);
        parPoiLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), parPoiLabel.getTitle() + " - " + parPoiLabel.getDescription(), Toast.LENGTH_LONG).show();
            }
        });

        return parPoiLabel;
    }

    //==============================================================================================
    // Helper methods
    //==============================================================================================
    private PARPoiLabel createRepoPoi(
            String title,
            String description,
            double latitude,
            double longitude) {
        return createPoi(title, description, latitude, longitude);
    }


    private void addWidyatamaPOI() {
        for (int i = 0; i < labelRepo.size(); i++) {
            PARController.getInstance().addPoi(labelRepo.get(i));
        }

    }

    private void createWidyatamaPOI() {
        labelRepo.add(createRepoPoi("Ruangan C101", "Universitas Widyatama", -6.8985470f, 107.645055f));
        labelRepo.add(createRepoPoi("Ruangan C102", "Universitas Widyatama", -6.898588f, 107.645378f));
        labelRepo.add(createRepoPoi("Ruangan C103", "Universitas Widyatama", -6.8985476f, 107.645055f));
        labelRepo.add(createRepoPoi("Ruangan C104", "Universitas Widyatama", -6.8985279f, 107.649775f));
        labelRepo.add(createRepoPoi("Ruangan C105", "Universitas Widyatama", -6.8985480f, 107.645055f));
        labelRepo.add(createRepoPoi("Ruangan C106", "Universitas Widyatama", -6.8985283f, 107.649775f));
        labelRepo.add(createRepoPoi("Ruangan C107", "Universitas Widyatama", -6.8985486f, 107.645055f));
        labelRepo.add(createRepoPoi("Ruangan C108", "Universitas Widyatama", -6.8985291f, 107.649775f));
        labelRepo.add(createRepoPoi("Ruangan C109", "Universitas Widyatama", -6.8985494f, 107.645055f));
        addWidyatamaPOI();

    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager;
        try {
            inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (view == null) {
                view = new View(getActivity());
            }

            inputMethodManager.hideSoftInputFromWindow(
                    view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (NullPointerException ignored) {
        }

    }

}