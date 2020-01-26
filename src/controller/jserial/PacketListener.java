package controller.jserial;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

final class PacketListener implements SerialPortPacketListener
{
    private String str;

    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

    @Override
    public int getPacketSize() { return Test.PACKET_SIZE_IN_BYTES; }

    @Override
    public void serialEvent(SerialPortEvent event)
    {
        byte[] newData = event.getReceivedData();
        str = new String(newData).split("\n", 2)[0].replaceAll("\\s+", "");

        int byteSize = 0 ;
        try {
            byteSize = str.getBytes("UTF-8").length;
        }catch(UnsupportedEncodingException ex) {
            Logger.getLogger(PacketListener.class.getName()).log(Level.SEVERE, null, ex);

        }

        if(byteSize == Test.PACKET_SIZE_IN_BYTES) {
            System.out.println("Recieved datas: " + str);
//            testing();

        }


    }
//    public void testing() {
//        System.out.println("hahaha"+str);
//    }



}
