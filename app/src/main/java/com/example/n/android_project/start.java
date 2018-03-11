package com.example.n.android_project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button button1=(Button)findViewById(R.id.button1);   //功能选择
        Button button2=(Button)findViewById(R.id.button2);
        Button button3=(Button)findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {            //文本编辑
                Intent intent = new Intent(start.this,do_write.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {          //文本查看
                Intent intent = new Intent(start.this,text_saved.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {            //标注结果查看
                Intent intent = new Intent(start.this,label_result.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {                //退出到手机的主菜单
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
        //Toast.makeText(do_write.this,"return",Toast.LENGTH_SHORT).show();
    }
}

