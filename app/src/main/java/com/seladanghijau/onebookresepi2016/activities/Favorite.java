package com.seladanghijau.onebookresepi2016.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.renderscript.Type;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi2016.R;
import com.seladanghijau.onebookresepi2016.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi2016.adapters.ResepiListAdapter;
import com.seladanghijau.onebookresepi2016.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi2016.asynctask.FavoriteListAsyncTask;
import com.seladanghijau.onebookresepi2016.dto.Resepi;
import com.seladanghijau.onebookresepi2016.manager.ResepiManager;
import com.seladanghijau.onebookresepi2016.provider.ILoader;

import java.lang.ref.WeakReference;

public class Favorite extends AppCompatActivity implements ILoader, View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    View actionbarView;
    ImageButton ibMenu, ibSearch;
    ListView lvMenu, lvFavorite;
    DrawerLayout drawer;

    // variables
    ResepiManager resepiManager;
    String[] drawerMenuList, resepiNameList;
    WeakReference<ListView> lvMenuWeakRef, lvFavoriteWeakRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        initViews();
        initVars();
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() {
        // setup actionbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        actionbarView = getSupportActionBar().getCustomView();

        // setup views
        ibMenu = (ImageButton) actionbarView.findViewById(R.id.ibMenu);
        ibSearch = (ImageButton) actionbarView.findViewById(R.id.ibSearch);
        ibSearch.setVisibility(View.GONE);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        lvFavorite = (ListView) findViewById(R.id.lvFavorite);
        drawer = (DrawerLayout) findViewById(R.id.drawer);

        // setup listener
        ibMenu.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
        lvFavorite.setOnItemClickListener(this);

        lvMenuWeakRef = new WeakReference<>(lvMenu);
        lvFavoriteWeakRef = new WeakReference<>(lvFavorite);
    }

    private void initVars() {
        resepiManager = new ResepiManager(this);

        new DrawerMenuListAsyncTask(this, this).execute();
        new FavoriteListAsyncTask(this, this, resepiManager).execute();

        lvFavorite.invalidate();
        lvMenu.invalidate();
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onBackPressed() {
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMenu:
                buttonEffect(ibMenu);
                slideDrawer(drawer);
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
            case R.id.lvFavorite:
                startActivity(new Intent(this, ResepiInfo.class).putExtra("nama_resepi", resepiNameList[position])); // FIXME - masalah nak p ke resepi info
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

    public void onLoad(String[] resepiNameList, Bitmap[] bgResepiList) { // load favorite listview
        ListView listViewFavorite;

        listViewFavorite = lvFavoriteWeakRef.get();
        listViewFavorite.setAdapter(new ResepiListAdapter(this, resepiNameList, bgResepiList));

        this.resepiNameList = resepiNameList;
    }

    public void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList) {}
    public void onLoad(int[] resepiCount, String[] kategoriResepiList, Bitmap[] imejKategoriResepiList) {}
    public void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(Resepi resepiInfo) {}
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
    // ---------------------------------------------------------------------------------------------
}
