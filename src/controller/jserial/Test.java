package controller.jserial;

import java.io.InputStream;

import com.fazecast.jSerialComm.SerialPort;
import controller.PortSettingPageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test {
    public static String devicePortName = "";
    public static SerialPort arduinoPort = null;
    public static InputStream arduinoStream = null;
    public static int PACKET_SIZE_IN_BYTES = 8;
    private String portName;
    private int i;

    // custom var
    public boolean isConnected = false;


    public ObservableList<String> getOpenPort(){
        int len = SerialPort.getCommPorts().length;
        SerialPort serialPorts[] = new SerialPort[len];
        serialPorts = SerialPort.getCommPorts();

        ObservableList list = FXCollections.observableArrayList();
        for(i = 0; i < len; i++) {
            portName = serialPorts[i].getDescriptivePortName();
            portName = serialPorts[i].getSystemPortName();
            arduinoPort = serialPorts[i];
            arduinoPort.openPort();
            list.add(portName);
        }
        return list;
    }
    public boolean getConnectionStatus(){
        System.out.println(portName);
        devicePortName = PortSettingPageController.getPortSettingPageController().choosePortComboBox.getSelectionModel().getSelectedItem();
        if(portName.contains(devicePortName)) {
            System.out.println("connected to: "+ portName + "{" + i + "}");
            isConnected = true;
        }else{
            isConnected = false;
            System.out.println("error");
        }
        return isConnected;
    }
    public void readArduinoData(){
        if(arduinoPort != null){
            PacketListener listener = new PacketListener();
            arduinoPort.addDataListener(listener);
            System.out.println("not null");
        }

    }

}
