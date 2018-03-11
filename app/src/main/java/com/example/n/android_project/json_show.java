package com.example.n.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class json_show extends AppCompatActivity {

    String text;
    JSONObject j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_show);
        Intent intent=getIntent();
        String text=intent.getStringExtra("json");   //获取json文本
        try {
            j= new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String sentText= null;  //处理json文本  获取关键信息
        try {
            sentText = j.getString("sentText");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String country= null;
        try {
            country = j.getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String city= null;
        try {
            city = j.getString("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String people= null;
        try {
            people = j.getString("people");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String relation= null;
        try {
            relation = j.getString("relation");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        text="正文："+sentText+"\n"+"国家："+country+"\n"+"城市："+city+"\n"+"关系:"+relation+"\n";  //呈现给用户
        TextView editText=(TextView)findViewById(R.id.textView);
        editText.setText("????????");  //未正确读取
        editText.setText(text);
    }
}
