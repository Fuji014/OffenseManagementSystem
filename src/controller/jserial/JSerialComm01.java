package controller.jserial;

import com.fazecast.jSerialComm.SerialPort;
import controller.PortSettingPageController;

import java.io.InputStream;

/**
 *
 * @author vubon
 */
public class JSerialComm01 {

    public static String devicePortName = "COM3";
    public static SerialPort arduinoPort = null;
    public static InputStream arduinoStream = null;
    public static int PACKET_SIZE_IN_BYTES = 8;

    public void testRun() {
        int len = SerialPort.getCommPorts().length;
        SerialPort serialPorts[] = new SerialPort[len];
        serialPorts = SerialPort.getCommPorts();

        for (int i = 0; i < len; i++) {

            String portName = serialPorts[i].getDescriptivePortName();
            System.out.println(serialPorts[i].getSystemPortName() + ": " + portName + ": " + i);

            if (portName.contains(devicePortName)) {
                arduinoPort = serialPorts[i];
                arduinoPort.openPort();
                System.out.println("connected to: " + portName + "[" + i + "]");
                break;
            }
        }

        PacketListener listener = new PacketListener();
        arduinoPort.addDataListener(listener);

//--------give it a rest--------------------------------
//        try {
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        arduinoPort.removeDataListener();
//        arduinoPort.closePort();

    }

}
