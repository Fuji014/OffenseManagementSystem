package controller.jserial;

import java.io.InputStream;

import com.fazecast.jSerialComm.SerialPort;
import controller.PortSettingPageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test {
    public static String devicePortName = "com3";
    public static SerialPort arduinoPort = null;
    public static InputStream arduinoStream = null;
    public static int PACKET_SIZE_IN_BYTES = 8;

    // custom var
    public boolean isConnected = false;


    public ObservableList<String> haha(){
        int len = SerialPort.getCommPorts().length;
        SerialPort serialPorts[] = new SerialPort[len];
        serialPorts = SerialPort.getCommPorts();

        ObservableList list = FXCollections.observableArrayList();
        for(int i = 0; i < len; i++) {
            String portName = serialPorts[i].getDescriptivePortName();
            System.out.println(serialPorts[i].getSystemPortName() + ": " + portName + ": " + i);
            portName = serialPorts[i].getSystemPortName();
            list.add(portName);

            if(portName.contains(devicePortName)) {
                arduinoPort = serialPorts[i];
                arduinoPort.openPort();
                System.out.println("connected to: "+ portName + "{" + i + "}");
                isConnected = true;
                break;
            }else{
                isConnected = false;
                System.out.println("error");
            }
        }
        return list;


//        try{
//        if(arduinoPort != null){
//            PacketListener listener = new PacketListener();
//            arduinoPort.addDataListener(listener);
//        }

//        }catch (Exception e){
//            System.out.println("Please Configure Port In The Port Settings!");
//        }
    }


}
