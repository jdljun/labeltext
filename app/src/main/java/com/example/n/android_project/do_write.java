package com.example.n.android_project;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;


public class do_write extends AppCompatActivity {

    private EditText editText;
    private String [] Text={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    private int page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_write);
        Button button1=(Button)findViewById(R.id.button5);     //下一页
        Button button2=(Button)findViewById(R.id.button4);    //上一页
        editText=(EditText)findViewById(R.id.edit_text);

        Intent intent = this.getIntent();
        if(intent.getExtras()!=null){
            Bundle b = intent.getExtras();
            Text = b.getStringArray("Text");
        }
        editText.setText(Text[0]);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {                 //储存本页文本，并且翻页到下一页
                Text[page]=editText.getText().toString();
                page++;
                editText.setHint("当前page"+""+(page+1)+"请输入文本");
                editText.setText(Text[page]);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {         //储存本页文本，并且翻页到上一页
                if(page==0)  ;//do nothing           //第一页 无法向前翻页
                else if(page==1){
                    Text[page] = editText.getText().toString();
                    page--;
                    editText.setHint("当前page" + "" + (page + 1) + "请输入标题");
                    editText.setText(Text[page]);
                }

                else {
                    Text[page] = editText.getText().toString();
                    page--;
                    editText.setHint("当前page" + "" + (page + 1) + "请输入文本");
                    editText.setText(Text[page]);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {            //询问用户是否保存文本
        AlertDialog.Builder dialog = new AlertDialog.Builder(do_write.this);
        dialog.setTitle("attention!");
        dialog.setMessage("是否保存？");
        dialog.setCancelable(true);
        dialog.setPositiveButton("YES",new  DialogInterface.OnClickListener(){
            @Override
            public  void onClick(DialogInterface dialog, int which){     //确认保存
                //store the text
                String name=Text[0];           //储存标题
                Text[page]=editText.getText().toString();
                String text="";
                for(int i=1;i<=page;i++)       //text临时存储用户输入的文本内容
                    text=text+Text[i];
                try {
                    BufferedWriter write_context= new BufferedWriter(
                                          new OutputStreamWriter(
                                                  openFileOutput(name,MODE_PRIVATE) )); //建立文件写的流，写文本
                    write_context.write(text);
                    write_context.close();

                    BufferedWriter write_file_name= new BufferedWriter(
                            new OutputStreamWriter(
                                    openFileOutput("File_name",MODE_APPEND) ));
                    write_file_name.write(name+"\r\n");      //文件标题输出到另一个记录已经保存文件标题的文件中，在第二个功能中使用
                    write_file_name.close();

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                Intent back=new Intent(do_write.this,start.class);
                startActivity(back);
            }

        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {       //否，返回菜单
                Intent back=new Intent(do_write.this,start.class);
                startActivity(back);
            }
        });
        dialog.show();

        //Toast.makeText(do_write.this,"return",Toast.LENGTH_SHORT).show();
    }
}
