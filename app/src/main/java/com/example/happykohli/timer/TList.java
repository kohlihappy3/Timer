package com.example.happykohli.timer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TList extends AppCompatActivity {
    ListView Tlist;
    ArrayList<String> Timer;
    ArrayAdapter<String> ada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlist);
        Tlist=(ListView)findViewById(R.id.Tlist);
        gettimerlist();
    }
    void gettimerlist()
    {
        Timer=new ArrayList<String>();
        try{
            SQLiteDatabase db=this.openOrCreateDatabase("TimeDataBase",MODE_PRIVATE,null);
            Cursor c=db.rawQuery("Select * from timer",null);
            c.moveToFirst();
            int index=c.getColumnIndex("Time");
            int index2=c.getColumnIndex("Date");
            while(c!=null){
                Timer.add("\n Timer Set :- " +c.getString(index)+"\n On Date :- " +c.getString(index2)+"\n");
                c.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            ada=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Timer);
            Tlist.setAdapter(ada);
        }

    }
}
