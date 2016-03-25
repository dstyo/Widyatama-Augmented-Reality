package com.widyatama.ar.dstyo.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.widyatama.ar.dstyo.R;
import com.widyatama.ar.dstyo.fragment.WidyatamaARFragment;

/**
 * Created by DSTYO on 1/24/16.
 */

public abstract class ARActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new WidyatamaARFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}