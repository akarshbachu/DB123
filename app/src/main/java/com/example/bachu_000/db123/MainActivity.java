package com.example.bachu_000.db123;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText et1,et2,et3,et4;
    Button btn,vwdata,update,del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb=new DatabaseHelper(this);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        et4=(EditText)findViewById(R.id.et4);
        btn=(Button)findViewById(R.id.addbtn);
        vwdata=(Button)findViewById(R.id.viewall);
        update=(Button)findViewById(R.id.update);
        del=(Button)findViewById(R.id.del);
        //for inserting data

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                }
                else {
                    boolean isInserted = mydb.insertData(et1.getText().toString(), et2.getText().toString(), et3.getText().toString());
                    et1.setText("");et2.setText("");et3.setText("");
                    if (isInserted == true) {
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //to display data
        vwdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res= mydb.getAllData();
                if(res.getCount()==0){
                    showMessage("Error","No Data Inserted");
                    return;
                }
                StringBuffer sb=new StringBuffer();
                while(res.moveToNext()){
                    sb.append("ID :"+ res.getString(0)+"\n");
                    sb.append("Name :"+ res.getString(1)+"\n");
                    sb.append("SurName :"+ res.getString(2)+"\n");
                    sb.append("Marks :"+ res.getString(3)+"\n");
                }
                showMessage("Data", sb.toString());
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = mydb.updateData(et4.getText().toString(), et1.getText().toString(), et2.getText().toString(), et3.getText().toString());
                et1.setText("");et2.setText("");et3.setText("");et4.setText("");
                if (isUpdated == true) {
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                }
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows=mydb.deleteData(et4.getText().toString());
                et4.setText("");
                if(deletedRows>0){
                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //for representing data in alert window
    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
