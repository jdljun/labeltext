package com.example.n.android_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class text_saved extends AppCompatActivity {

    private String[] file_name={"","","","","","","","","",
                                  "","","","","","","","","","",""};
    private int number=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_saved);
        get_File_name();
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                text_saved.this,android.R.layout.simple_list_item_1,file_name);
        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                final String File_name=file_name[position];  //记录当前操作文件名
                AlertDialog.Builder dialog = new AlertDialog.Builder(text_saved.this);//选择编辑或者标注
                dialog.setTitle("选择文本操作");
                dialog.setMessage("编辑or标注");
                dialog.setCancelable(true);
                dialog.setPositiveButton("文本编辑",new  DialogInterface.OnClickListener(){
                    @Override
                    public  void onClick(DialogInterface dialog, int which){           //文本编辑
                        BufferedReader reader= null;
                        try {
                            reader = new BufferedReader(
                                    new InputStreamReader(
                                            openFileInput(File_name)));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        String line="";
                        String[] Text_Pass={File_name,"","","","","","",
                                "","","","","",""};
                        try {
                            while((line=reader.readLine())!=null)
                                Text_Pass[1]=Text_Pass[1]+line;
                            Bundle b=new Bundle();
                            b.putStringArray("Text",Text_Pass);
                            Intent intent =new Intent(text_saved.this,do_write.class);
                            intent.putExtras(b);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                });
                dialog.setNegativeButton("文本标注", new DialogInterface.OnClickListener() {    //文本标注
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         Intent intent=new Intent(text_saved.this,text_label.class);
                         intent.putExtra("File_name",File_name);
                         startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }
    public void get_File_name(){   //根据File_name文件中储存的文件读取所有已经保存文件名

        BufferedReader reader= null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            openFileInput("File_name")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line="";
        try {
            while((line=reader.readLine())!=null) {
                file_name[number]=line;
                number++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}


