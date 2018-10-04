package com.study.android.project_exam;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class orderOk extends AppCompatActivity {
    HashMap<String, String> orderlist;
    TextView mymenu,total,tvCode;
    int sum;
    String price;
    private static final String TAG = "lecture";
    String menu="",totalsum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ok);
        mymenu = findViewById(R.id.mymenu);
        total = findViewById(R.id.total);
        tvCode = findViewById(R.id.tvCode);


        Intent intent = getIntent();
        orderlist = (HashMap<String, String>) intent.getSerializableExtra("orderlist");
       // Iterator<String> keys = orderlist.keySet().iterator();
        int i=0;
        for( String key : orderlist.keySet() ){
            mymenu.append(key+"  /  "+orderlist.get(key)+"\n");
            price = orderlist.get(key);
            price = price.substring(0,price.length()-1);
            sum = sum + Integer.parseInt(price);
            menu = menu+key+"|";
        }
        menu = menu.substring(0,menu.length()-1);
        total.setText("\n지불하실 금액은 "+sum+"원 입니다.");
        totalsum = Integer.toString(sum);
        randomCode(menu);
    }

    private void randomCode(String order){

        Random ran = new Random();
        int code = ran.nextInt(1000);
        String result = String.format("%04d",code);

        String sUrl ="http://ec2-13-209-64-83.ap-northeast-2.compute.amazonaws.com:8081/menulist/inputOrder.jsp";
        HashMap<String, String> values = new HashMap<>();
        values.put("menu",order);
        values.put("code",result);
        values.put("price",totalsum);

        NetworkTask networkTask = new NetworkTask(sUrl, values);
        networkTask.execute();

        //이 난수가 db에 있으면 다시 실행시켜야 함
        //json 1 or 1로 다시 받자
        //data 접근 깔끔하게 하는 메소드 만들기
        //코드 발생시키고, db에 넣고

        tvCode.setText(result);
    }

    public class NetworkTask extends AsyncTask<Object,Void,JSONObject> {

        private String surl;
        private HashMap<String,String> values;
        StringBuffer sbParams = new StringBuffer();
        String key;
        String value;
        boolean isAnd = false;

        public NetworkTask(String url, HashMap values) {
            this.surl = url;
            this.values = values;
        }

        @Override
        protected JSONObject doInBackground(Object... params) {
            JSONObject result = null;
            RequestHttpURLConnection request = new RequestHttpURLConnection();
            result=request.jsonReturn(surl,values);
            return result;
        }

        public void onPostExecute(JSONObject s) {
            super.onPostExecute(s);

            if(s!=null){
                try {
                    Log.d(TAG, "s : " + s.getString("result"));
                    Toast.makeText(getApplicationContext(), "db 입력 결과는! "+s.getString("result"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Log.d(TAG,"결과없음!");
                Toast.makeText(getApplicationContext(), "결과없음!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
