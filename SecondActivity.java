package pl.froger.hellointents;

import android.app.Activity;
import android.os.Bundle;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SecondActivity extends Activity implements SensorEventListener{


    private SensorManager menedzer;
    private Sensor czujnik;

    public static String ipAddress="172.24.1.1";// ur ip
    public static int portNumber=1234;// portnumber

    DatagramSocket socket;
    InetAddress address;

    public int a,b;
    public String msg;
    public String up = "u";
    public String down = "d";
    public int msg_length;
    public byte[] message = new byte[2];
    public byte[] plus = new byte[1];
    public byte[] minus = new byte[1];





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getStringExtra("ip")!="172.24.1.1")
            ipAddress=getIntent().getStringExtra("ip");
        else ipAddress="172.24.1.1";
        if(getIntent().getStringExtra("port")!="1234")
            portNumber=Integer.parseInt(getIntent().getStringExtra("port"));
        else portNumber=1234;


        setContentView(R.layout.second);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        menedzer = (SensorManager) getSystemService(SENSOR_SERVICE);


        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://172.24.1.1/html/cam_pic_new.php");


        final Button btn1 = (Button)findViewById(R.id.button);
        final Button btn2 = (Button)findViewById(R.id.button2);

        try {

            socket = new DatagramSocket();
            address = InetAddress.getByName(ipAddress);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    plus = up.getBytes();
                    DatagramPacket cameraup = new DatagramPacket(plus, 1, address, portNumber);
                    socket.send(cameraup);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    minus = down.getBytes();
                    DatagramPacket cameradown = new DatagramPacket(minus, 1, address, portNumber);
                    socket.send(cameradown);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Wybieramy Akcelerometr
        czujnik = menedzer.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        menedzer.registerListener(this, czujnik,SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // W trakcie wstrzymania aplikacji zatrzymuje pobieranie aktualizowanych danych w celu
        // zaoszcz�dzenia energii
        menedzer.unregisterListener(this);

    }

    public void onAccuracyChanged(Sensor czujnik, int dokladnosc) {
        // Do zrobienia: automatycznie wygenerowany zal��ek metody
    }

    public void onSensorChanged(SensorEvent zdarzenie) {



        try {

            msg =  Integer.toString(a) + Integer.toString(b);
            message = msg.getBytes();
            msg_length = message.length;
            DatagramPacket packet = new DatagramPacket(message, msg_length, address, portNumber);
            socket.send(packet);
            Thread.sleep(10);

            //prosto
            if(zdarzenie.values[1]>=-2&&zdarzenie.values[1]<=2)
                b=5;

            //prawo
            if(zdarzenie.values[1]>2&&zdarzenie.values[1]<=4)
                b=6;
            if(zdarzenie.values[1]>4&&zdarzenie.values[1]<=6)
                b=7;
            if(zdarzenie.values[1]>6&&zdarzenie.values[1]<=8)
                b=8;
            if(zdarzenie.values[1]>8)
                b=9;

            //lewo
            if(zdarzenie.values[1]<-2&&zdarzenie.values[1]>=-4)
                b=4;
            if(zdarzenie.values[1]<-4&&zdarzenie.values[1]>=-6)
                b=3;
            if(zdarzenie.values[1]<-6&&zdarzenie.values[1]>=-8)
                b=2;
            if(zdarzenie.values[1]<-8)
                b=1;

            //stanie

            if(zdarzenie.values[2]>=2&&zdarzenie.values[2]<7)//3-5//1.25
                a=5;

            //przod

            if(zdarzenie.values[2]>7&&zdarzenie.values[2]<=7.5)
                a=6;
            if(zdarzenie.values[2]>7.5&&zdarzenie.values[2]<=8.5)
                a=7;
            if(zdarzenie.values[2]>8.5&&zdarzenie.values[2]<=9)
                a=8;
            if(zdarzenie.values[2]>9)
                a=9;


            if(zdarzenie.values[2]>5&&zdarzenie.values[2]<=6&&zdarzenie.values[0]<3)
                a=7+1;
            if(zdarzenie.values[2]>6&&zdarzenie.values[2]<=6.5&&zdarzenie.values[0]<3)
                a=8+1;
            if(zdarzenie.values[2]>6.5&&zdarzenie.values[0]<3)
                a=9;

            //wstecz
            if(zdarzenie.values[2]<2&&zdarzenie.values[2]>=0.5)
                a=4;
            if(zdarzenie.values[2]<0.5&&zdarzenie.values[2]>=-1.5)
                a=3;
            if(zdarzenie.values[2]<-1.5&&zdarzenie.values[2]>=-3)
                a=2;
            if(zdarzenie.values[2]<-3)
                a=1;



        }
        catch (SocketException e){
            Log.e("Udp:", "Socket Error:", e);
        } catch (Exception e) {
            Log.e(" ", "Error:", e);
        }


    }
}

