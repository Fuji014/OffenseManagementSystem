/*
  Name:    CardReader.ino
  Created:  8/26/2016 6:58:24 PM
  Author: vubon
*/


/* Pin layout should be as follows:
  Signal     Pin              Pin               Pin
             Arduino Uno      Arduino Mega      MFRC522 board
  ------------------------------------------------------------
  Reset      9                5                 RST
  SPI SS     10               53                SDA
  SPI MOSI   11               51                MOSI
  SPI MISO   12               50                MISO
  SPI SCK    13               52                SCK
*/




#include <MFRC522.h>
#include <stdlib.h>
#define SS_PIN 10
#define RST_PIN 9


String a;
String p = "";
int buzzer = 5;
int recv = 0;
MFRC522 mfrc522(SS_PIN, RST_PIN); // Create MFRC522 instance.

void setup() {
  pinMode(buzzer, OUTPUT);
  Serial.begin(9600); // Initialize serial communications with the PC
  SPI.begin();      // Init SPI bus
  mfrc522.PCD_Init(); // Init MFRC522 card
  //Serial.println("Scan PICC to see UID and type...");
}

void loop() {
  mfrc522.PICC_ReadCardSerial();
  // Look for new cards
  mfrc522.PICC_IsNewCardPresent();

  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) {
    a = "";
    return;
  }

  // Dump debug info about the card. PICC_HaltA() is automatically called.
  for (byte i = 0; i < (&(mfrc522.uid))->size; i++) {
    if ((&(mfrc522.uid))->uidByte[i] < 0x10) {
      Serial.print(F(" 0"));
      a += "0";
    }
    else {
      //Serial.print(F(" "));
    }
    a += String((&(mfrc522.uid))->uidByte[i], HEX);
  }
  if (a == (p + p)) {
    a = p;
    return;
  } else {
    if (a.startsWith(p)) {
      a.remove(0, p.length());
    }
    p = a;
    Serial.println(a);
  }


  //---get input from pc---//
  if (Serial.available() > 0) {
    recv = Serial.read();
    if (recv == 110) {
      tone(buzzer, 8000); // Send 1KHz sound signal...
      //delay(1000);        // ...for 1 sec
    } else {
      noTone(buzzer);
    }
  }

}
