const int bp = 7;//bp = buttonpin

int buttonState;
int lastButtonState = LOW;
int adivinha;

unsigned long lastDebounceTime = 0;
unsigned long debounceDelay = 50;

void setup() {
  Serial.begin(9600);
  pinMode(bp, INPUT_PULLUP);
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
//Liga os LEDs em função do número random
void func_adivinha(){
  adivinha = random(1,32);
  for (int i = 0; i < 5; i++) {
    int state = (adivinha >> i) & 1;
    digitalWrite(8+i, state);
  }
}

void loop() {
  //button 1 (número random)
  int reading = !digitalRead(bp);
  if (reading != lastButtonState) {
    lastDebounceTime = millis();
  }
  if ((millis() - lastDebounceTime) > debounceDelay) {
    if (reading != buttonState) {
      buttonState = reading;
      if (buttonState == HIGH) {
        func_adivinha();
        Serial.write('S');
      }
    }
  }
  lastButtonState = reading;

  //Receber informações
  if (Serial.available() > 0){
    int b = Serial.read();
    if (b == adivinha) {
      Serial.write('Y');
      ligado();
    }
    else {
      Serial.write('N');
    }
  }
}
