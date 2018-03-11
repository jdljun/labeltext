package com.example.n.android_project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class label_result extends AppCompatActivity {

    protected String [] file_name = {"","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","",
    };
    protected  int number=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_result);
        for (int i = 0; i < this.fileList().length; i++) {         //获取已保存json格式文本的文件名
            if (this.fileList()[i].indexOf(".json") > 0)
                file_name[number++] = this.fileList()[i];
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(            //用file_name 建立一个viewlist
                label_result.this, android.R.layout.simple_list_item_1, file_name);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String File_name=file_name[position];
                BufferedReader reader= null;
                try {
                    reader = new BufferedReader(           //建立文件读的流
                            new InputStreamReader(
                                    openFileInput(File_name)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String line="";
                String json="";
                try {
                    while((line=reader.readLine())!=null)
                        json=json+line;             //读取当前点击标题，即文本中的json文本
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(label_result.this,json_show.class);
                intent.putExtra("json",json);
                startActivity(intent);      ///将读取到的json文本传递到下一个活动
            }
        });
    }
}
