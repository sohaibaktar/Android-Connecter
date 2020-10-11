package com.b.mydatabasehelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText nameEdittext ,ageEdittext ,genderEdittext;
private Button addbutton,displaydata;

    MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = new MyDatabase(this);
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();

        nameEdittext = (EditText) findViewById(R.id.nameid);
        ageEdittext = (EditText) findViewById(R.id.ageid);
        genderEdittext = (EditText) findViewById(R.id.genderid);

        addbutton = (Button) findViewById(R.id.addbuttonid);
        displaydata = (Button) findViewById(R.id.Displaydatabuttonid);


        addbutton.setOnClickListener(this);
        displaydata.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String name = nameEdittext.getText().toString();
        String age = ageEdittext.getText().toString();
        String gender = genderEdittext.getText().toString();
//This is for add data button
        if(view.getId() == R.id.addbuttonid)
        {

           long rowId =   myDatabase.insertData(name,age,gender);
           if (rowId==-1){
               Toast.makeText(getApplicationContext(), "unsuccesfull" , Toast.LENGTH_LONG).show();

           }else{
               Toast.makeText(getApplicationContext(), "Row "+rowId+"is succesfully inserted" , Toast.LENGTH_LONG).show();


           }
        }
        //This is for display buttton
        if(view.getId()==R.id.Displaydatabuttonid){

            Cursor cursor = myDatabase.DisplayAllData();
            if(cursor.getCount()==0)
            {
                //There is no data to display
                showData("Error ","data not found");
                return ;
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext())
            {
                stringBuffer.append("ID : "+cursor.getString(0)+"\n");
                stringBuffer.append("Name : "+cursor.getString(1)+"\n");
                stringBuffer.append("Age : "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender : "+cursor.getString(3)+"\n\n\n");
            }
            showData("ResultSet",stringBuffer.toString());
        }


    }

    public void showData(String title ,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}