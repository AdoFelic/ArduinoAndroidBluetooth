package com.proj.arduinobluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btnConnect, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    private static final int REQUEST_ACTIVATION = 1;
    private static final int REQUEST_CONNECTION = 2;
    private static final int MESSAGE_READ = 3;

    ConnectedThread connectedThread;

    Handler mHandler;
    StringBuilder dataBluetooth = new StringBuilder();

    BluetoothAdapter myBluetoothAdapter = null;
    BluetoothDevice myDevice = null;
    BluetoothSocket mySocket = null;

    boolean connection = false;

    boolean podignutPrst = false;
    boolean rezimRada = false;

    private static String MAC = null;
    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = (Button)findViewById(R.id.btnConnect);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(myBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Uređaj nema Bluetooth!", Toast.LENGTH_LONG).show();
        } else if(!myBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ACTIVATION);
        }

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection){
                    try{
                        mySocket.close();
                        connection = false;
                        btnConnect.setText("Connect");
                        Toast.makeText(getApplicationContext(), "Bluetooth veza je prekinuta!", Toast.LENGTH_SHORT).show();
                    } catch(IOException erro) {
                        Toast.makeText(getApplicationContext(), "Došlo je do greške: " + erro, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent openList = new Intent(MainActivity.this, DeviceList.class);
                    startActivityForResult(openList, REQUEST_CONNECTION);
                }
            }
        });

        btn1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        if(podignutPrst == false && connection == true){
                            podignutPrst = true;
                            new VoziNaprijed().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                        }
                    break;
                    case MotionEvent.ACTION_UP:
                        podignutPrst = false;


                }
                return true;
            }
        });

        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(podignutPrst == false && connection == true ){
                            podignutPrst = true;
                            new BlagoDesno().execute();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        podignutPrst = false;
                }
                return true;
            }
        });

        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(podignutPrst == false && connection == true){
                            podignutPrst = true;
                            new BlagoLijevo().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        podignutPrst = false;
                }
                return true;
            }
        });

        btn4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(podignutPrst == false && connection == true){
                            podignutPrst = true;
                            new Desno().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        podignutPrst = false;
                }
                return true;
            }
        });

        btn5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(podignutPrst == false && connection == true){
                            podignutPrst = true;
                            new Lijevo().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        podignutPrst = false;
                }
                return true;
            }
        });

        btn6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(podignutPrst == false && connection == true){
                            podignutPrst = true;
                            new NazadDesno().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        podignutPrst = false;
                }
                return true;
            }
        });

        btn7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(podignutPrst == false && connection == true){
                            podignutPrst = true;
                            new NazadLijevo().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        podignutPrst = false;
                }
                return true;
            }
        });

        btn8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(podignutPrst == false && connection == true){
                            podignutPrst = true;
                            new Nazad().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        podignutPrst = false;
                }
                return true;
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection){
                    connectedThread.write("promijeni");
                    Toast.makeText(getApplicationContext(),
                            "Promijenjen režim rada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_READ){
                    String received = (String) msg.obj;
                    dataBluetooth.append(received);
                    int krajInformacije = dataBluetooth.indexOf("}");
                    if(krajInformacije>0){
                        String kompletanPodatak = dataBluetooth.substring(0, krajInformacije);
                        int duzina = kompletanPodatak.length();
                        if(dataBluetooth.charAt(0) == '{'){
                            String konacniPodatak = dataBluetooth.substring(1, duzina);
                            if(konacniPodatak.contains("auto")){
                                btn9.setText("Manu");
                            } else if(konacniPodatak.contains("manu")){
                                btn9.setText("Auto");
                            }
                        }
                        dataBluetooth.delete(0, dataBluetooth.length());
                    }
                }
            }
        };


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){
            case REQUEST_ACTIVATION:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "Bluetooth je aktiviran!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nije aktiviran, prekida se izvršavanje aplikacije!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case REQUEST_CONNECTION:
                if(resultCode == Activity.RESULT_OK){
                    MAC = data.getExtras().getString(DeviceList.MAC_ADDRESS);

                    myDevice = myBluetoothAdapter.getRemoteDevice(MAC);

                    try{
                        mySocket = myDevice.createRfcommSocketToServiceRecord(MY_UUID);

                        mySocket.connect();

                        connection = true;

                        connectedThread = new ConnectedThread(mySocket);
                        connectedThread.start();

                        btnConnect.setText("Disconnect");

                        Toast.makeText(getApplicationContext(), "Spojeni ste sa: " + MAC, Toast.LENGTH_SHORT).show();

                    } catch(IOException erro) {
                        connection = false;
                        Toast.makeText(getApplicationContext(), "Došlo je do greške: " + erro, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Greška pri preuzimanju MAC adrese", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    class VoziNaprijed extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            while (podignutPrst) {
                if(connection){
                    connectedThread.write("np");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectedThread.write("a");
            return null;
        }
    }

    class BlagoDesno extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            while (podignutPrst) {
                if(connection){
                    connectedThread.write("bd");
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectedThread.write("a");
            return null;
        }
    }

    class BlagoLijevo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            while (podignutPrst) {
                if(connection){
                    connectedThread.write("bl");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectedThread.write("a");
            return null;
        }
    }

    class Desno extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            while (podignutPrst) {
                if(connection){
                    connectedThread.write("vd");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectedThread.write("a");
            return null;
        }
    }

    class Lijevo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            while (podignutPrst) {
                if(connection){
                    connectedThread.write("vl");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectedThread.write("a");
            return null;
        }
    }

    class NazadDesno extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            while (podignutPrst) {
                if(connection){
                    connectedThread.write("nd");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectedThread.write("a");
            return null;
        }
    }

    class NazadLijevo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            while (podignutPrst) {
                if(connection){
                    connectedThread.write("nl");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectedThread.write("a");
            return null;
        }
    }

    class Nazad extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            while (podignutPrst) {
                if(connection){
                    connectedThread.write("nz");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nije povezan", Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connectedThread.write("a");
            return null;
        }
    }

    private class ConnectedThread extends Thread {
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;
            private byte[] mmBuffer; 

            public ConnectedThread(BluetoothSocket socket) {
                mySocket = socket;
                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                try {
                    tmpIn = socket.getInputStream();
                } catch (IOException e) {

                }
                try {
                    tmpOut = socket.getOutputStream();
                } catch (IOException e) {

                }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
            }

            public void run() {
                mmBuffer = new byte[1024];
                int numBytes; 

                while (true) {
                    try {

                        numBytes = mmInStream.read(mmBuffer);

                        String dataBt = new String(mmBuffer, 0, numBytes);

                        mHandler.obtainMessage(MESSAGE_READ, numBytes, -1, dataBt).sendToTarget();

                    } catch (IOException e) {
                        break;
                    }
                }

            }

            public void write(String dataSend) {
                byte[] myBuffer = dataSend.getBytes();
                try {
                    mmOutStream.write(myBuffer);
                } catch (IOException e) {

                }
            }

        }
    }

