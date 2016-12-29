package com.example.siddhant.project;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static String[] mPermissions = { Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout mContainer;
    private ArrayAdapter<String> arrayAdapter;
    private MyApp.OnListRefreshListener onListRefreshListener;
    public HashMap<String,String>data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt=(TextView) findViewById(R.id.text);
        data=new HashMap<>();
        data.put("0117C555C6SF","Tamasha");
        data.put("4825","QD's res");
        data.put("18273624","Cafeteria and co");
        mContainer = (LinearLayout) findViewById(R.id.activity_main);
        if (!havePermissions()) {
            Log.i(TAG, "Requesting permissions needed for this app.");
            requestPermissions();
        }

        if(!isBlueEnable()){
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(bluetoothIntent);
        }
//        ArrayList<reviews> blah= MyApp.getInstance().Reviews;
//        if(blah!=null){
////            txt.setText(blah.get(0).getReview_text());
//        }
        final List<String> items = new ArrayList<>(MyApp.getInstance().regionNameList);
        final List<String> items1=new ArrayList<>();
        for(String s:items){
            items1.add(data.get(s));
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items1);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!MyApp.getInstance().regionNameList.isEmpty()) {
                    try {
                        //String beaconSSN = MyApp.getInstance().regionList.get(i).getId2().toHexString();
                        Intent regionIntent = new Intent(MainActivity.this,DisplayActivity.class);

                        regionIntent.putExtra("id", MyApp.getInstance().regionNameList.get(i));
                        startActivity(regionIntent);
                    } catch (ArrayIndexOutOfBoundsException e) {/*Do nothing*/}
                }
           }
        });
        listView.setAdapter(arrayAdapter);
    }
    private boolean isBlueEnable() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return bluetoothAdapter.isEnabled();

    }



    private boolean havePermissions() {
        for(String permission:mPermissions){
            if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                return  false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                mPermissions, PERMISSIONS_REQUEST_CODE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        onListRefreshListener = new MyApp.OnListRefreshListener() {
            @Override
            public void onListRefresh() {
                notifyListChange();
            }
        };
        MyApp.getInstance().onListRefreshListener = onListRefreshListener;
        MyApp.getInstance().context = this;
    }
    private void showLinkToSettingsSnackbar() {
        if (mContainer == null) {
            return;
        }
        Snackbar.make(mContainer,
                R.string.permission_denied_explanation,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Build intent that displays the App settings screen.
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",
                                BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).show();
    }

    /**
     * Displays {@link Snackbar} with button for the user to re-initiate the permission workflow.
     */
    private void showRequestPermissionsSnackbar() {
        if (mContainer == null) {
            return;
        }
        Snackbar.make(mContainer, R.string.permission_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Request permission.
                        ActivityCompat.requestPermissions(MainActivity.this,
                                mPermissions,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();
    }

    private void notifyListChange(){
        data=new HashMap<>();
        data.put("18238278","Tamasha");
        data.put("4825","QD's res");
        data.put("18273624","Cafeteria and co");
        if(arrayAdapter != null){

            List<String> items = new ArrayList<>(MyApp.getInstance().regionNameList);

            final List<String> items1=new ArrayList<>();
            for(String s:items){
                items1.add(data.get(s));
            }
            arrayAdapter.clear();
            arrayAdapter.addAll(items1);
            arrayAdapter.notifyDataSetChanged();
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != PERMISSIONS_REQUEST_CODE) {
            return;
        }
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,permission)) {
                    Log.i(TAG, "Permission denied without 'NEVER ASK AGAIN': " + permission);
                    showRequestPermissionsSnackbar();
                } else {
                    Log.i(TAG, "Permission denied with 'NEVER ASK AGAIN': " + permission);
                    showLinkToSettingsSnackbar();
                }
            } else {
                Log.i(TAG, "Permission granted, building GoogleApiClient");
            }
        }
    }
}
