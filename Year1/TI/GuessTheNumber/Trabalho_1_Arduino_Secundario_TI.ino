//button 1 é para incrementar o número
//button 2 é para mandar a tentativa
const int bp1 = 7;//bp1 = buttonpin1
const int bp2 = 6;//bp2 = buttonpin2

int bs1;//bs1 = buttonState1
int bs2;//bs2 = buttonState2
int lbs1 = LOW;//lbs1 = lastbuttonState1
int lbs2 = LOW;//lbs2 = lastbuttonState2
int b = 0;

unsigned long ldt1 = 0;//ldt1 = lastdebouncetime1
unsigned long ldt2 = 0;//ldt2 = lastdebouncetime2
unsigned long debounceDelay = 50;

void setup() {
  Serial.begin(9600);
  pinMode(bp1, INPUT_PULLUP);
  pinMode(bp2, INPUT_PULLUP);
  for (int i = 8; i < 13; i++) {
    pinMode(i, OUTPUT);
    digitalWrite(i, HIGH);
  }
}

//Ligar todos os LEDs
void ligado(){
  for (int i = 0; i < 5; i++) {
    digitalWrite(i+8, HIGH);
  }
}
//Desligar todos os LEDs
void desligado(){
  for (int i = 0; i < 5; i++) {
    digitalWrite(i+8, LOW);
  }
}
//Piscar 3 vezes todos os LEDs
void piscar(){
  for (int j = 0; j < 3; j++) {
      desligado();
      delay(300);
      ligado();
      delay(300);
    }
  desligado();
}

void loop() {
  //button 1 (Incrementar número)
  int reading1 = !digitalRead(bp1);
  if (reading1 != lbs1) {
    ldt1 = millis();
    }
  if ((millis() - ldt1) > debounceDelay) {
    if (reading1 != bs1) {
      bs1 = reading1;
      if (bs1 == HIGH) {
        b++;
        if (b == 32)(b = 1);
        for (int i = 0; i < 5; i++) {
          int state = (b >> i) & 1;
          digitalWrite(i+8, state);
        }
      }
    }
  }
  lbs1 = reading1;

  //button 2 (Mandar tentativa)
  int reading2 = !digitalRead(bp2);
  if (reading2 != lbs2) {
    ldt2 = millis();
    }
  if ((millis() - ldt2) > debounceDelay) {
    if (reading2 != bs2) {
      bs2 = reading2;
      if (bs2 == HIGH) {
        Serial.write(b);
      }
    }
  }
  lbs2 = reading2;

  //Receber informações
  if (Serial.available() > 0){
    char r = Serial.read();
  	if (r == 'S') {
      piscar();
      r = ' ';
      b = 0;
    }
    if (r == 'N') {
      desligado();
      r = ' ';
      b = 0;
    }
    if (r =='Y') {
      ligado();
      r = ' ';
      b = 0;
    }
  }
}