package com.seladanghijau.onebookresepi2016.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.seladanghijau.onebookresepi2016.R;
import com.seladanghijau.onebookresepi2016.adapters.DrawerMenuListAdapter;
import com.seladanghijau.onebookresepi2016.adapters.ResepiBahanAdapter;
import com.seladanghijau.onebookresepi2016.adapters.ResepiLangkahAdapter;
import com.seladanghijau.onebookresepi2016.asynctask.DrawerMenuListAsyncTask;
import com.seladanghijau.onebookresepi2016.asynctask.ResepiInfoAsyncTask;
import com.seladanghijau.onebookresepi2016.dto.Resepi;
import com.seladanghijau.onebookresepi2016.manager.ResepiManager;
import com.seladanghijau.onebookresepi2016.provider.ILoader;
import com.seladanghijau.onebookresepi2016.provider.ResepiProvider;

import java.util.ArrayList;

public class ResepiInfo extends AppCompatActivity implements ILoader, View.OnClickListener, AdapterView.OnItemClickListener {
    // views
    View actionbarView;
    ImageButton ibMenu, ibSearch;
    ListView lvMenu, lvBahan, lvLangkah;
    ImageView ivResepiImg;
    TabHost thResepiInfo;
    DrawerLayout drawer;
    TextView tvResepiName, tvRingkasan;
    ImageButton ibShare, ibFavorite;

    // variables
    int resepiId;
    String namaResepi;
    boolean favorite;
    String[] drawerMenuList;
    ResepiManager resepiManager;
    TabHost.TabSpec tsRingkasan, tsBahan, tsLangkah;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resepi_info);

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
        ivResepiImg = (ImageView) findViewById(R.id.ivResepiImg);
        tvResepiName = (TextView) findViewById(R.id.tvResepiName);
        tvRingkasan = (TextView) findViewById(R.id.tvRingkasan);
        lvMenu = (ListView) findViewById(R.id.lvMenu);
        lvBahan = (ListView) findViewById(R.id.lvBahan);
        lvLangkah = (ListView) findViewById(R.id.lvLangkah);
        thResepiInfo = (TabHost) findViewById(R.id.thResepiInfo);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        ibShare = (ImageButton) findViewById(R.id.ibShare);
        ibFavorite = (ImageButton) findViewById(R.id.ibFavorite);

        // setup listener
        ibMenu.setOnClickListener(this);
        ibShare.setOnClickListener(this);
        ibFavorite.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
    }

    private void initVars() {
        resepiManager = new ResepiManager(this);
        namaResepi = getIntent().getStringExtra("nama_resepi");

        setupTabhost(); // setup tabhost
        new DrawerMenuListAsyncTask(this, this).execute();
        new ResepiInfoAsyncTask(this, this, resepiManager, namaResepi).execute(); // setup ui
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMenu:
                buttonEffect(ibMenu);
                slideDrawer(drawer);
                break;
            case R.id.ibFavorite:
                buttonEffect(ibFavorite);

                if(favorite) {
                    resepiManager.removeFavorite(resepiId);

                    ibFavorite.setImageResource(R.drawable.ic_star_black_100);
                    Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show();
                } else {
                    resepiManager.addFavorite(resepiId);

                    ibFavorite.setImageResource(R.drawable.ic_star_border_purple_24dp);
                    Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show();
                }
                favorite = !favorite;

                ibFavorite.invalidate();
                break;
            case R.id.ibShare:
                buttonEffect(ibShare);
                shareResepi(namaResepi);
                break;
        }
    }

    public void onBackPressed() {
        finish();
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

    // interface methods ---------------------------------------------------------------------------
    public void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList) {
        lvMenu.setAdapter(new DrawerMenuListAdapter(this, drawerMenuList, ikonDrawerMenuList));

        lvMenu.invalidate();

        this.drawerMenuList = drawerMenuList;
    }

    public void onLoad(Resepi resepiInfo) {
        resepiId = resepiInfo.getId();

        ivResepiImg.setImageBitmap(resepiInfo.getResepiImg());
        tvResepiName.setText(resepiInfo.getName());
        tvRingkasan.setText(resepiInfo.getRingkasan());
        lvLangkah.setAdapter(new ResepiLangkahAdapter(this, resepiInfo.getLangkah()));
        lvBahan.setAdapter(new ResepiBahanAdapter(this, resepiInfo.getBahan()));
        favorite = resepiInfo.getFavorite();
        if(favorite)
            ibFavorite.setImageResource(R.drawable.ic_star_border_purple_24dp);
        else
            ibFavorite.setImageResource(R.drawable.ic_star_black_100);

        ibFavorite.invalidate();
        lvLangkah.invalidate();
        lvBahan.invalidate();
    }

    public void onLoad(String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList) {}
    public void onLoad(int[] resepiCount, String[] kategoriResepiList, Bitmap[] imejKategoriResepiList) {}
    public void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList) {}
    public void onLoad(String[] tipTitle, String[] tipDesc) {}
    public void onLoad(TypedArray rempahImgList) {}
    // ---------------------------------------------------------------------------------------------

    // util methods --------------------------------------------------------------------------------
    private void slideDrawer(DrawerLayout drawer) {
        if(drawer.isDrawerOpen(Gravity.LEFT))
            drawer.closeDrawer(Gravity.LEFT);
        else if(!drawer.isDrawerOpen(Gravity.LEFT))
            drawer.openDrawer(Gravity.LEFT);
    }

    private void shareResepi(String resepiName) {
        Intent shareIntent;
        String shareMessage, nama, ringkasan, bahan, langkah;
        String currentBahanDesc;
        Resepi tempResepi;

        tempResepi = resepiManager.getResepiInfo(resepiManager.getResepiId(resepiName));

        nama = tempResepi.getName();
        ringkasan = tempResepi.getRingkasan();

        int a=1;
        bahan = "";
        currentBahanDesc = "";
        for(int x=0 ; x<tempResepi.getBahan().size() ; x++) {
            if(x == 0) {
                currentBahanDesc = tempResepi.getBahan().get(x).first;

                a = 1;
                bahan += "\n-Bahan " + currentBahanDesc + "-\n" +
                        a + ") " + tempResepi.getBahan().get(x).second + "\n";
            } else if(!currentBahanDesc.equalsIgnoreCase(tempResepi.getBahan().get(x).first)) {
                currentBahanDesc = tempResepi.getBahan().get(x).first;

                a = 1;
                bahan += "\n-Bahan " + currentBahanDesc + "-\n" +
                        a + ") " + tempResepi.getBahan().get(x).second + "\n";
            } else
                bahan += a + ") " + tempResepi.getBahan().get(x).second + "\n";

            a++;
        }

        langkah = "";
        for(int x=0 ; x<tempResepi.getLangkah().length ; x++) {
            langkah += (x+1) + ") " + tempResepi.getLangkah()[x] + "\n";
        }

        shareMessage = "Nama Resepi:\n" + nama + "\n\n" +
                "Ringkasan:\n" + ringkasan + "\n" +
                "Bahan:\n" + bahan + "\n" +
                "Cara Masak:\n" + langkah;

        shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        Intent.createChooser(shareIntent, "Onebook Resepi 100");

        startActivity(Intent.createChooser(shareIntent, this.getPackageName()));
    }

    private void buttonEffect(View view) {
        AlphaAnimation buttonClick = new AlphaAnimation(1f, 0.5f);

        view.startAnimation(buttonClick);
    }
    // ---------------------------------------------------------------------------------------------

    // other methods -------------------------------------------------------------------------------
    private void setupTabhost() {
        // setup tabspec
        thResepiInfo.setup();
        tsRingkasan = thResepiInfo.newTabSpec("Ringkasan"); // ringkasan tab -----------------------
        tsRingkasan.setIndicator(LayoutInflater.from(this).inflate(R.layout.tab_ringkasan, null));
        tsRingkasan.setContent(R.id.tabRingkasan);
        tsBahan = thResepiInfo.newTabSpec("Bahan"); // bahan tab -----------------------------------
        tsBahan.setIndicator(LayoutInflater.from(this).inflate(R.layout.tab_bahan, null));
        tsBahan.setContent(R.id.tabBahan);
        tsLangkah = thResepiInfo.newTabSpec("Langkah"); // langkah tab -----------------------------
        tsLangkah.setIndicator(LayoutInflater.from(this).inflate(R.layout.tab_langkah, null));
        tsLangkah.setContent(R.id.tabLangkah);

        thResepiInfo.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                // for unselected tab
                for (int i = 0; i < thResepiInfo.getTabWidget().getChildCount(); i++) {
                    thResepiInfo.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#92278f"));
                }

                // for selected tab
                thResepiInfo.getTabWidget().getChildAt(thResepiInfo.getCurrentTab()).setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        // add tab to tab host
        thResepiInfo.addTab(tsRingkasan);
        thResepiInfo.addTab(tsBahan);
        thResepiInfo.addTab(tsLangkah);
    }
    // ---------------------------------------------------------------------------------------------
}
