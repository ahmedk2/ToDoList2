package com.khalidapps.todolist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> adapter;
    ListView lstItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        lstItems = (ListView) findViewById(R.id.lstItems);

        showItemList();

    }

    private void showItemList() {
        ArrayList<String> itemList = dbHelper.getToDoList();
        if(adapter==null) {
            adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.item_title, itemList);
            lstItems.setAdapter(adapter);
        }
        else {
            adapter.clear();
            adapter.addAll(itemList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // all this code in comments made the app crash

        // change menu icon color
        //Drawable icon = menu.getItem(0).getIcon();
        //icon.mutate();
        //if (Build.VERSION.SDK_INT >= 15) {
          //  icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        //}

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_item:
                final EditText itemEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle ("Add New Item")
                .setMessage("What's Next")
                .setView(itemEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = String.valueOf(itemEditText.getText());
                        dbHelper.insertNewItem(item);
                        showItemList();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
                dialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteItem(View view) {
        View parent = (View)view.getParent();
        TextView itemTextView = (TextView) findViewById(R.id.item_title);
        String item = String.valueOf(itemTextView.getText());
        dbHelper.deleteItem(item);
        showItemList();
    }
}



