package com.seladanghijau.onebookresepi2016.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi2016.R;
import com.seladanghijau.onebookresepi2016.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi2016.adapters.KategoriResepiListAdapter;
import com.seladanghijau.onebookresepi2016.asynctask.DaftarCabutanAsyncTask;
import com.seladanghijau.onebookresepi2016.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi2016.dto.Resepi;
import com.seladanghijau.onebookresepi2016.provider.CabutanPreference;
import com.seladanghijau.onebookresepi2016.provider.ILoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class Cabutan extends AppCompatActivity implements ILoader, View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    ListView lvMenu;
    View actionbarView;
    ImageButton ibMenu, ibSearch;
    TextView tvWebAdddress;
    DrawerLayout drawer;
    EditText etEmail, etPhoneNo, etNama, etIc;
    Button btnDaftar;

    // variables
    String webAddress;
    String[] drawerMenuList;
    WeakReference<ListView> lvMenuWeakRef;
    SharedPreferences cabutanPref;
    SharedPreferences.Editor cabutanPrefEditor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabutan);

        initViews(); // initialize views
        initVars(); // initialize variables

        // invalidate views
        lvMenu.invalidate(); // invalidate drawer menu
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() { // initialize views
        // setup actionbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        actionbarView = getSupportActionBar().getCustomView();

        // setup views
        ibMenu = (ImageButton) actionbarView.findViewById(R.id.ibMenu); // menu imagebutton
        ibSearch = (ImageButton) actionbarView.findViewById(R.id.ibSearch); // search imagebutton
        ibSearch.setVisibility(View.GONE); // setup visibility of search imagebutton, this activity doesn't use it
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhoneNo = (EditText) findViewById(R.id.etPhoneNo);
        etNama = (EditText) findViewById(R.id.etNama);
        etIc = (EditText) findViewById(R.id.etIc);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        lvMenu = (ListView) findViewById(R.id.lvMenu); // setup menu listview in drawer menu
        tvWebAdddress = (TextView) findViewById(R.id.tvWebAdddress); // setup goto web click textview
        drawer = (DrawerLayout) findViewById(R.id.drawer); // setup drawer menu layout

        // setup listener
        ibMenu.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
        tvWebAdddress.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);

        // setup weak ref for listviews
        lvMenuWeakRef = new WeakReference<>(lvMenu);
    }

    private void initVars() { // initialize variables
        webAddress = "http://www.onebook.com.my/html/cabutan.html"; // default web address

        new DrawerMenuListAsyncTask(this, this).execute(); // get drawer menu list

        // get daftar pref
        cabutanPref = getSharedPreferences(CabutanPreference.PREF, Context.MODE_PRIVATE);
        cabutanPrefEditor = cabutanPref.edit();

        if(!cabutanPref.getString(CabutanPreference.PREF_STATUS, "").isEmpty()) {
            String tempUID, tempDate, tempEmail, tempName, tempPhoneNo, tempIc;
            TextView tvUID, tvTarikh, tvEmel, tvNama, tvNoTel, tvNoIC;
            JSONArray jsonArray;
            JSONObject jsonObject;
            String data;

            setContentView(R.layout.layout_cabutan_registered);

            tvUID = (TextView) findViewById(R.id.tvUID);
            tvTarikh = (TextView) findViewById(R.id.tvTarikh);
            tvEmel = (TextView) findViewById(R.id.tvEmel);
            tvNama = (TextView) findViewById(R.id.tvNama);
            tvNoTel = (TextView) findViewById(R.id.tvNoTel);
            tvNoIC = (TextView) findViewById(R.id.tvNoIC);

            tempUID = "";
            tempDate = "";
            tempEmail = "";
            tempName = "";
            tempPhoneNo = "";
            tempIc = "";

            try {
                data = cabutanPref.getString(CabutanPreference.PREF_STATUS, "");
                jsonObject = new JSONObject(data);
                jsonArray = jsonObject.getJSONArray("daftar");

                for(int x=0 ; x<jsonArray.length() ; x++) {
                    JSONObject tempJsonObject;

                    tempJsonObject = jsonArray.getJSONObject(x);

                    tempUID = tempJsonObject.getString("daftarUID");
                    tempDate = tempJsonObject.getString("daftarDate");
                    tempEmail = tempJsonObject.getString("daftarEmail");
                    tempName = tempJsonObject.getString("daftarNama");
                    tempPhoneNo = tempJsonObject.getString("daftarPhoneNo");
                    tempIc = tempJsonObject.getString("daftarNoIc");
                }
            } catch (Exception e) { e.printStackTrace(); }

            tvUID.setText(tempUID);
            tvTarikh.setText(tempDate);
            tvEmel.setText(tempEmail);
            tvNama.setText(tempName);
            tvNoTel.setText(tempPhoneNo);
            tvNoIC.setText(tempIc);
        }
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onBackPressed() { // on back button pressed, kill this activity
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMenu: // clicked menu button
                buttonEffect(ibMenu); // animate button click
                slideDrawer(drawer); // slide menu drawer to open or close
                break;
            case R.id.tvWebAdddress: // clicked goto web textview
                buttonEffect(tvWebAdddress); // animate button click

                Uri webUri = Uri.parse(webAddress).buildUpon().build(); // build uri
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, webUri), "Choose web browser")); // goto website with own browser
                break;
            case R.id.btnDaftar:
                String email, phoneNo, nama, noIc;

                email = etEmail.getText().toString();
                phoneNo = etPhoneNo.getText().toString();
                nama = etNama.getText().toString();
                noIc = etIc.getText().toString();

                new DaftarCabutanAsyncTask(this, email, phoneNo, nama, noIc).execute();
                break;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lvMenu:
                if(position == 0)
                    startActivity(new Intent(this, MainActivity.class));
                else if(position == 1)
                    startActivity(new Intent(this, TipMasakan.class));
                else if(position == 2)
                    startActivity(new Intent(this, Favorite.class));
                else if((position >= 3) && (position <= 21))
                    startActivity(new Intent(this, ResepiList.class).putExtra("kategori_resepi", drawerMenuList[position]));
                else if(position == 22)
                    startActivity(new Intent(this, Cabutan.class));
                else if(position == 23)
                    startActivity(new Intent(this, TentangKami.class));
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------

    // loader interface methods --------------------------------------------------------------------
    public void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList) { // load menu drawer listview
        ListView listViewMenu;

        listViewMenu = lvMenuWeakRef.get();
        listViewMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));

        this.drawerMenuList = drawerMenuList;
    }

    public void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList) {}
    public void onLoad(int[] resepiCount, String[] kategoriResepiList, Bitmap[] imejKategoriResepiList) {}
    public void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(Resepi resepiInfo) {}
    public void onLoad(String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(String[] tipTitle, String[] tipDesc) {}
    public void onLoad(TypedArray rempahImgList) {}
    // ---------------------------------------------------------------------------------------------

    // util  methods -------------------------------------------------------------------------------
    private void slideDrawer(DrawerLayout drawer) {
        if(drawer.isDrawerOpen(Gravity.LEFT))
            drawer.closeDrawer(Gravity.LEFT);
        else if(!drawer.isDrawerOpen(Gravity.LEFT))
            drawer.openDrawer(Gravity.LEFT);
    }

    private void buttonEffect(View view) {
        AlphaAnimation buttonClick = new AlphaAnimation(1f, 0.5f);

        view.startAnimation(buttonClick);
    }

    public void setCabutanPref(String pref) {
        cabutanPrefEditor.putString(CabutanPreference.PREF_STATUS, pref);
        cabutanPrefEditor.commit();
    }
    // ---------------------------------------------------------------------------------------------
}
