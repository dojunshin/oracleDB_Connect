package com.example.oracledb_connect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    /**
     * TESTDB(개발기)
     * 200.100.1.116:1527
     * ID : testdb
     * PW : phisu01
     * */

    public static final String Pnuhurl= "http://200.100.1.116:1527/"; //개발기
    public static final int timeout = 5000;
    public static String Verkey="appver";
    public static String VerNo="1.0";
    public static String Apikey="apikey";
    public static String Apikeystr="";

    public static String ErrorCode;
    public static String Data_Mode;

    public static JSONArray ParamsArray;
    public static KEY_DATA Send_Keydata = new KEY_DATA();

    public static class KEY_DATA {
        String ExecuteTypeName="";
        String ParamsArray="";
        String QueryId="";
        String StatementType="";
        String SignValue="";
        String SignKey="";
        String SignDn="";
    }

    public static String REdata(String Fulldata) {
        return Fulldata;
    }

    Disposable backgroundTask;

    private void RxJava_Task() {
        HashMap<String, String> map = new HashMap<>();
        backgroundTask = Observable.fromCallable(() -> {    //fromCallable : 비동기 실행방식
            //doInBackground
            try {
                getJsonFromServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HashMap<String, String>>() {
            @Override
            public void accept(HashMap<String, String> stringStringHashMap) throws Throwable {
                //onPostExecute
                if(!ErrorCode.equals("")) {
                //데이터 없음.
                } else {
                    switch(Data_Mode) {
                        case "1":
                            break;
                        case  "2":
                            break;
                        case "3":
                            break;
                    }
                }
                backgroundTask.dispose();
            }
        });
    }

    public void getJsonFromServer() throws IOException {

        String RCV_json = "";
        JSONObject job = new JSONObject();

        OutputStream outputStream;
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream;

        try {
            //통신세팅
            HttpURLConnection connection;
            URL jsonUrl = new URL(MainActivity.Pnuhurl);
            connection = (HttpURLConnection) jsonUrl.openConnection();
            connection.setConnectTimeout(MainActivity.timeout);
            connection.setReadTimeout(MainActivity.timeout);
            connection.setRequestMethod("POST"); //POST방식
            connection.setRequestProperty("Cache-Control", "no-cache"); //컨트롤 캐쉬 설정
            connection.setRequestProperty("Content-Type", "application/json"); //타입설정(Request body 전달 시 application/json으로 서버에 전달)
            connection.setRequestProperty("Accept", "application/json"); //서버 Response Data를 JSON 형식의 타입으로 요청
            connection.setRequestProperty(MainActivity.Verkey, MainActivity.VerNo);//버전
            connection.setRequestProperty(MainActivity.Apikey, MainActivity.Apikeystr);//키
            connection.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨준다는 옵션
            connection.setDoInput(true); //InputStream으로 서버로 부터 응답을 받겠다는 옵션
            //통신세팅

            job.put("ExecuteTypeName", MainActivity.Send_Keydata.ExecuteTypeName);
            job.put("ParamsArray", MainActivity.ParamsArray);
            job.put("QueryId", MainActivity.Send_Keydata.QueryId);
            job.put("StatementType", MainActivity.Send_Keydata.StatementType);
            job.put("SignValue", MainActivity.Send_Keydata.SignValue);
            job.put("SignKey", MainActivity.Send_Keydata.SignKey);
            job.put("SignDn", MainActivity.Send_Keydata.SignDn);

            //전송
            outputStream = connection.getOutputStream();
            outputStream.write(job.toString().getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                Log.d("서비스 전송 ", " 성공!!!");

                inputStream = connection.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                int nLength = 0;
                while ((nLength = inputStream.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    byteArrayOutputStream.write(byteBuffer, 0, nLength);
                }

                RCV_json = byteArrayOutputStream.toString();
                String RCV_JDATA = MainActivity.REdata(RCV_json);

                if (RCV_JDATA.contains("ErrorCode")) {
                    ErrorCode = RCV_JDATA;
                } else {
                    if (!RCV_JDATA.equals("[]")) {
                        JSONArray jsonArray = new JSONArray(RCV_JDATA);
                        switch (Data_Mode) {
                            case "1":
                                break;
                            case "2":
                                break;
                            case "3":
                                break;
                        }
                    } else {
                        //데이터 없음...... Null..
                    }
                }
            } else {
                Log.e("서비스 전송 "," Error");
            }
            connection.disconnect();
        } catch (Exception e) {

            e.printStackTrace();

        }
    }


}









//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        send1();
//    }
//
//    public static void send1() {
//        JSONArray array = new JSONArray();
//
//        //들어가야할 파라미터...
//        array.put("Data1");
//        array.put("Data2");
//        array.put("Data3");
//        array.put("Data4");
//        array.put(""); //마지막은 그냥 공란...
//
//        MainActivity.ParamsArray = array;
//        MainActivity.Send_Keydata.ExecuteTypeName = "Fill";
//        MainActivity.Send_Keydata.QueryId = "QueryID"; //쿼리 ID.. 이건 차후 웹서버랑 연동하고 난 이후..
//        MainActivity.Send_Keydata.StatementType = "";
//        MainActivity.Send_Keydata.SignValue = "";
//        Data_Mode = "pc_ins_reminder_sms";
//
//        Send_Data(1);
//    }
//
//    public static void Send_Data(int Mode) {
//        ErrorCode = "";
//        MainActivity.JSON_TASK send_AsyncTask = new MainActivity.JSON_TASK();
//        send_AsyncTask.execute();
//    }
//
//public static class JSON_TASK extends AsyncTask<String, Void, String> {
//
//    @Override
//    protected String doInBackground(String... strings) {
//        try {
//            getJsonFromServer();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void getJsonFromServer() throws IOException {
//
//        String RCV_json = "";
//        JSONObject job = new JSONObject();
//
//        OutputStream outputStream;
//        InputStream inputStream;
//        ByteArrayOutputStream byteArrayOutputStream;
//
//        try {
//            //통신세팅
//            HttpURLConnection connection;
//            URL jsonUrl = new URL(MainActivity.Pnuhurl);
//            connection = (HttpURLConnection) jsonUrl.openConnection();
//            connection.setConnectTimeout(MainActivity.timeout);
//            connection.setReadTimeout(MainActivity.timeout);
//            connection.setRequestMethod("POST"); //POST방식
//            connection.setRequestProperty("Cache-Control", "no-cache"); //컨트롤 캐쉬 설정
//            connection.setRequestProperty("Content-Type", "application/json"); //타입설정(Request body 전달 시 application/json으로 서버에 전달)
//            connection.setRequestProperty("Accept", "application/json"); //서버 Response Data를 JSON 형식의 타입으로 요청
//            connection.setRequestProperty(MainActivity.Verkey, MainActivity.VerNo);//버전
//            connection.setRequestProperty(MainActivity.Apikey, MainActivity.Apikeystr);//키
//            connection.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨준다는 옵션
//            connection.setDoInput(true); //InputStream으로 서버로 부터 응답을 받겠다는 옵션
//            //통신세팅
//
//            job.put("ExecuteTypeName", MainActivity.Send_Keydata.ExecuteTypeName);
//            job.put("ParamsArray", MainActivity.ParamsArray);
//            job.put("QueryId", MainActivity.Send_Keydata.QueryId);
//            job.put("StatementType", MainActivity.Send_Keydata.StatementType);
//            job.put("SignValue", MainActivity.Send_Keydata.SignValue);
//            job.put("SignKey", MainActivity.Send_Keydata.SignKey);
//            job.put("SignDn", MainActivity.Send_Keydata.SignDn);
//
//            //전송
//            outputStream = connection.getOutputStream();
//            outputStream.write(job.toString().getBytes());
//            outputStream.flush();
//            outputStream.close();
//
//            int responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//
//                Log.d("서비스 전송 ", " 성공!!!");
//
//                inputStream = connection.getInputStream();
//                byteArrayOutputStream = new ByteArrayOutputStream();
//                byte[] byteBuffer = new byte[1024];
//                int nLength = 0;
//                while ((nLength = inputStream.read(byteBuffer, 0, byteBuffer.length)) != -1) {
//                    byteArrayOutputStream.write(byteBuffer, 0, nLength);
//                }
//
//                RCV_json = byteArrayOutputStream.toString();
//                String RCV_JDATA = MainActivity.REdata(RCV_json);
//
//                if (RCV_JDATA.contains("ErrorCode")) {
//                    ErrorCode = RCV_JDATA;
//                } else {
//                    if (!RCV_JDATA.equals("[]")) {
//                        JSONArray jsonArray = new JSONArray(RCV_JDATA);
//                        switch (Data_Mode) {
//                            case "1":
//                                break;
//                            case "2":
//                                break;
//                            case "3":
//                                break;
//                        }
//                    } else {
//                        //데이터 없음...... Null..
//                    }
//                }
//            } else {
//                Log.e("서비스 전송 "," Error");
//            }
//            connection.disconnect();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//
//        if(!ErrorCode.equals("")) {
//            //데이터 없음.
//        } else {
//            switch(Data_Mode) {
//                case "1":
//                    break;
//
//                case  "2":
//                    break;
//
//                case "3":
//                    break;
//            }
//        }
//
//    }
//}