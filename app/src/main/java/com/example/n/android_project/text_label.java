package com.example.n.android_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class text_label extends AppCompatActivity {
    protected  String[] Sen={"","","","","","","","","",
                               "","","","","","","","","",
                               "","","","","","","","","",
                               "","","","","","","","","",
                               "","","","","","","","","",
                               "","","","","","","","",""};
    protected  String[] country={"","","","","",""};
    protected int country_num=0;
    protected  String[] city={"","","","","",""};
    protected int city_num=0;
    protected  String[] person={"","","","","",""};
    protected int person_num=0;
    protected  String relations[]={"","","","","","","","","","","","",""};
    protected  int relation_num=2;
    protected  int number=0;
    protected  int sen_now=0;
    protected  EditText editText;
    public String File_name;
    boolean[] checkedRelations = {false, false, false, false, false, false, false, false, false, false, false, false};
    String[] items = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", ""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_label);
        Intent intent = getIntent();
        File_name = intent.getStringExtra("File_name");
        get_Sentence(File_name);
        editText = (EditText) findViewById(R.id.edit_text);
        editText.setText(Sen[0]);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lable_text = "";
                int start = editText.getSelectionStart();
                int end = editText.getSelectionEnd();
                lable_text = editText.getText().subSequence(start, end).toString(); //获取当前用户选中的文本
                if (lable_text != null)
                    country[country_num++] = lable_text;    //当前用户选中的文本是一个国家 ，储存起来
                Toast.makeText(text_label.this, lable_text + " is a country", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lable_text = "";
                int start = editText.getSelectionStart();
                int end = editText.getSelectionEnd();
                lable_text = editText.getText().subSequence(start, end).toString();
                if (lable_text != null)
                    city[city_num++] = lable_text;  //当前用户选中的文本是一个城市 ，储存起来
                Toast.makeText(text_label.this, lable_text + " is a city", Toast.LENGTH_SHORT).show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                 //上一句
                String lable_text = "";
                int start = editText.getSelectionStart();
                int end = editText.getSelectionEnd();
                lable_text = editText.getText().subSequence(start, end).toString();
                if (lable_text != null)       //当前用户选中的文本是一个人 ，储存起来
                    person[person_num++] = lable_text;
                Toast.makeText(text_label.this, lable_text + " is a person", Toast.LENGTH_SHORT).show();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                 //获取上一句
                if (sen_now == 0) ;
                else {
                    sen_now--;
                    editText.setText(Sen[sen_now]);
                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                   //获取下一句
                if(country_num!=0 || city_num!=0 || person_num!=0){
                    json_save(sen_now+1,Sen[sen_now]);
                    Toast.makeText(text_label.this,"已保存",Toast.LENGTH_SHORT).show();
                }
                country_num=city_num=person_num=relation_num=0;
                sen_now++;
                editText.setText(Sen[sen_now]);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                 //关系的确定

                int num = 0;
                int i, j;
                for(i=0;i<checkedRelations.length;i++)
                    checkedRelations[i]=false;
                for(i=0;i<items.length;i++)
                    items[i]="";
                for (i = 0; i < country_num; i++)     //根据用户已经标注的国家 城市  人  生成可能的关系
                    for (j = 0; j < city_num; j++)
                        items[num++] = city[j] + "属于" + country[i];
                //checkedRelations[num++]=true;

                for (i = 0; i < country_num; i++)
                    for (j = 0; j < person_num; j++)
                        items[num++] = person[j] + "是" + country[i] + "人";
                //checkedRelations[num++]=true;
                final int Num = num;
                AlertDialog.Builder builder = new AlertDialog.Builder(text_label.this); //根据上面生成的可能关系生成listview
                //builder.setIcon(R.drawable.tools);//设置对话框的图标
                builder.setTitle("请对对象关系进行选择"); //设置对话框标题
                builder.setMultiChoiceItems(items, checkedRelations, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedRelations[which] = isChecked; //改变被操作列表项的状态
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = "";      //用于保存选择结果
                        for (int i = 0; i < Num; i++) {
                            if (checkedRelations[i]) {
                                relations[0] = relations[0]+items[i]+";";  //为了json格式保存，所以预先处理
                            }
                        }
                        Toast.makeText(text_label.this,relations[0],Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show(); // 创建对话框并显示
            }
        });
    }

    private void json_save(int sentID,String sentText) {
        int i;
        String p="";
        String c="";
        String C="";

        JSONObject label_result =new JSONObject();
        try {
            label_result.put("articleID",File_name);  //按照json格式处理字符串  保存
                     //entityMentions
            for(i=0;i<person_num;i++)
             p=p+person[i]+"; ";
            for(i=0;i<city_num;i++)
                c=c+city[i]+"; ";
            for(i=0;i<country_num;i++)
                C=C+country[i]+"; ";
            label_result.put("country",C);
            label_result.put("city",c);
            label_result.put("person",p);
            label_result.put("relation",relations[0]);
            label_result.put("sentID",sentID);
            label_result.put("sentText",sentText);
            Toast.makeText(text_label.this,label_result.toString(),Toast.LENGTH_SHORT).show();
            label_result_write(File_name+sentID+".json",label_result.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void label_result_write(String file_name, String s) {    //将字符串S 保存到文件名为file_name的文件中
        try {
            BufferedWriter write_context= new BufferedWriter(
                    new OutputStreamWriter(
                            openFileOutput(file_name,MODE_PRIVATE) ));
            write_context.write(s);
            write_context.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void get_Sentence(String File_name){         //将File_name中文本读取出来，按照句号分解成一句一句的话
        BufferedReader reader= null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            openFileInput(File_name)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line="";
        String article="";
        int i;
        try {
            while((line=reader.readLine())!=null)
                article=article+line;                //article=整篇文章的内容
            while((i=article.indexOf("。"))>0){
                Sen[number]=article.substring(0,i+1);
                article=article.substring(i+1);
                number++;
            }
            Sen[number++]=article;
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