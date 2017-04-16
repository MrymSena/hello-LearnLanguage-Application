package com.sena.asus.learnlanguage;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardView cardview;
    private MenuAdapter adapter;
    private List<Menu> menuList;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardview=(CardView) findViewById(R.id.card_view);

        menuList = new ArrayList<>();
        adapter = new MenuAdapter(this, menuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
    //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(3), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareMenu();


        try {
            Glide.with(this).load(R.drawable.hellocover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        verifyStoragePermissions();
        if (checkExternalMedia())
            writeToSDFile();
    }






    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
       /* final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");*/
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

    }
    private void writeToSDFile() {
        File root = android.os.Environment.getExternalStorageDirectory();
         File dirMyFile = new File(root.getAbsolutePath() + "/download/" + "dic.txt");

            try {
                FileOutputStream f = new FileOutputStream(dirMyFile);
                PrintWriter pw = new PrintWriter(f);

                Scanner scanner = new Scanner(getResources().openRawResource(R.raw.mydict));
                while(scanner.hasNext()){
                    String line = scanner.nextLine();
                    String[] words = line.split("\t");
                    pw.println(words[0]+"\t"+words[1]);
                }
                scanner.close();

                pw.flush();
                pw.close();
                f.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //  Log.i(TAG, "******* File not found. Did you" +
                //        " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   tv.append("\n\nFile written to "+file);

    }



    public void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    private boolean checkExternalMedia() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    /**
     * Adding few albums for testing
     */
    private void prepareMenu() {
        int[] covers = new int[]{
                R.drawable.learnwords,
                R.drawable.addnewwords,
                R.drawable.exam,
                R.drawable.examnotes
               };

        Menu a = new Menu("Learn Words", covers[0]);
        menuList.add(a);

        a = new Menu("Add New Word", covers[1]);
        menuList.add(a);

        a = new Menu("Quiz", covers[2]);
        menuList.add(a);

        a = new Menu("Quiz Notes", covers[3]);

        menuList.add(a);

      //  menuList.add(a);

        adapter.notifyDataSetChanged();
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}