package com.example.pogorelova_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class newfilm extends AppCompatActivity {
    TextView tvInfo;
    EditText tvName;
    newfilm.MyTask mt;
    newfilm.MyTaskN mtn;
    newfilm.MyTaskTF mttf;
    ListView lvMain;
    //    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfilm);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvName = (EditText) findViewById(R.id.editTextTextPersonName);
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mt = new newfilm.MyTask();
        mt.execute();
    }
    public void onclick(View v) {
        mtn = new newfilm.MyTaskN();
        mtn.execute(tvName.getText().toString());
        // lvMain.getCheckedItemPositions();
    }
    class MyTaskTF extends AsyncTask<String, Void, Void > {
        @Override
        protected Void doInBackground(String... params) {
            String line = null;
            String total = null;
            HttpURLConnection myConnection=null;
            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino/");
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myConnection.setRequestProperty("Accept",
                    "application/vnd.github.v3+json");
            myConnection.setRequestProperty("Contact-Me",
                    "hathibelagal@example.com");
            try {
                myConnection.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            myConnection.setDoOutput(true);
            try {
                myConnection.getOutputStream().write( ("id=3&cinemaID=" + params[0]+"&movieID="+params[1]).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            };

            int i=0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                tvInfo.setText(str);
            if (i==200) {
                InputStream responseBody=null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
                while (true) {
                    try {
                        if (!((line = r.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    total=line;
                }
            }

            return null;
        }

    }
    class MyTaskN extends AsyncTask<String, Void, String >{
        @Override
        protected String doInBackground(String... params) {
            String line = null;
            String total = null;
            HttpURLConnection myConnection=null;
            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino/");
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myConnection.setRequestProperty("Accept",
                    "application/vnd.github.v3+json");
            myConnection.setRequestProperty("Contact-Me",
                    "hathibelagal@example.com");
            try {
                myConnection.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            myConnection.setDoOutput(true);
            try {
                myConnection.getOutputStream().write( ("id=2&Name=" + params[0]).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            };

            int i=0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                tvInfo.setText(str);
            if (i==200) {
                InputStream responseBody=null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
                while (true) {
                    try {
                        if (!((line = r.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    total=line;
                }
            }
            return total;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            ID=result;
            int n=lvMain.getChildCount();

            for (int i = 0; i < n; i++) {
                String[] st=(String[])lvMain.getAdapter().getItem(i);
                LinearLayout ll=(LinearLayout)lvMain.getChildAt(i);
                CheckBox ch=(CheckBox)ll.getChildAt(0);
                if (ch.isChecked()){
                    mttf = new newfilm.MyTaskTF();
                    mttf.execute(st[1],result);
                };
            }
            tvInfo.setText("Фильм успешно добавлен");
        }
    }
    class MyTask extends AsyncTask<Void, Void, ArrayList<String[]>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Begin");
        }

        @Override
        protected ArrayList<String[]> doInBackground(Void... params) {
            ArrayList<String[]> res = new ArrayList<>();
            HttpURLConnection myConnection = null;
            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino?id=3");
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                tvInfo.setText("1");

            } catch (IOException e) {
                e.printStackTrace();
                tvInfo.setText("2");

            }

            int i=0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i==200) {
                InputStream responseBody=null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStreamReader responseBodyReader =null;
                try {
                    responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                JsonReader jsonReader;
                jsonReader = null;
                jsonReader = new JsonReader(responseBodyReader);
                try {
                    jsonReader.beginArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String key=null;
                String value =null;

                while (true) {
                    try {
                        if (!jsonReader.hasNext()) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonReader.beginObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    };
                    String[] str=new String[2];
                    int n=0;
                    while (true) {
                        try {
                            if (!jsonReader.hasNext()) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            key = jsonReader.nextName();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        sb.append("\r\n : " +key);
                        try {
                            value = jsonReader.nextString();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
//                        sb.append("\r\n : " +value);
                        str[n]=value;
                        n++;
                    }
                    try {
                        jsonReader.endObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    res.add(str);
                }
                try {
                    jsonReader.endArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            myConnection.disconnect();

            return res;
        }
        @Override
        protected void onPostExecute(ArrayList<String[]> result) {
            super.onPostExecute(result);
            newfilm.ClAdapter clAdapter=new newfilm.ClAdapter(tvInfo.getContext(),result);
//            lvMain = (ListView) findViewById(R.id.lvMain);
            lvMain.setAdapter(clAdapter);
            tvInfo.setText("Кинотеатры для показа добавленного фильма:");
        }

    }
    class ClAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater lInflater;
        List<String[]> lines;
        ClAdapter(Context context, List<String[]> elines){
            ctx = context;
            lines = elines;
            lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return lines.size();
        }
        @Override
        public Object getItem(int position) {
            return lines.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.itemt, parent, false);
            };
            String[] p =(String[]) getItem(position);
            ((TextView) view.findViewById(R.id.tvText)).setText(p[0]);
            //           ((TextView) view.findViewById(R.id.tvText1)).setText(p[1]);

            return view;
        };
        public boolean getCheck (int position){

            return true;
        }
    }
}