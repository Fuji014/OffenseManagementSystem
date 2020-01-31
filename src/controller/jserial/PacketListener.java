package controller.jserial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import controller.StudentAddController;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vubon
 */
public final class PacketListener implements SerialPortPacketListener {

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public int getPacketSize() {
        return JSerialComm01.PACKET_SIZE_IN_BYTES;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] newData = event.getReceivedData();
        String str = new String(newData).split("\n", 2)[0].replaceAll("\\s+", "");
        int byteSize = 0;
        try {
            byteSize = str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PacketListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (byteSize == JSerialComm01.PACKET_SIZE_IN_BYTES) {
            //System.out.println("(Received data of size: " + byteSize + ")" + str);
            System.out.println("Received data: " + str);
            StudentAddController.getAddStudentController().rfidTagIdTxt.setText(str);
        }
    }
}
