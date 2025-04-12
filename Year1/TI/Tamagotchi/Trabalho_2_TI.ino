//ADC
int leituras[6];
int valor;
int total_leituras = 0;
int media = 0;
float margem_luminosidade = (2*1023/3); //dois terços da gama do ADC (gama do ADC=1023)
unsigned long i = 0;

//Botões
int bs1; //buttonState1
int bs2; //buttonState2
int bs3; //buttonState3
int lbs1 = LOW; //lastbuttonState1
int lbs2 = LOW; //lastbuttonState2
int lbs3 = LOW; //lastbuttonState3
unsigned long ldt1 = 0; //lastdebounceTime1
unsigned long ldt2 = 0; //lastdebounceTime2
unsigned long ldt3 = 0; //lastdebounceTime3
unsigned long debounceDelay = 50;

//luzes ativadas
bool acionou_d = false;
bool acionou_b = false;
bool acionou_c = false;
unsigned long tempo_inicial_d;
unsigned long tempo_inicial_b;
unsigned long tempo_inicial_c;

//estados
bool acordado = true;
bool vivo = true;

//contagem
unsigned long millis_anterior = 0;
unsigned long margem_dormir = 0;
unsigned long tempo_comeco_dormir;

//penalidade
int penalidades = 0;

void setup() {
  Serial.begin(9600);
  for (int j = 2; j < 5; j++) {
    pinMode(j, INPUT_PULLUP);
  }
  for (int j = 11; j < 14; j++) {
    pinMode(j, OUTPUT);
  }
  randomSeed(analogRead(0));
}

int func_penalidades(bool acionou, unsigned long tempo_inicial,int penalidades){
  if (acionou){
    if (millis()-tempo_inicial>=(30000)){
      if ((millis()-tempo_inicial-(30000))%(30000) == 0){
        penalidades += 5;
      }
    }
  }
  return penalidades;
}

void loop() {
  if (vivo){
    if (acordado){
      //botão dormir
      int r1 = !digitalRead(4);
      if (r1 != lbs1) {
        ldt1 = millis();
      }
      if ((millis() - ldt1) > debounceDelay) {
        if (r1 != bs1) {
          bs1 = r1;
          if (bs1 == HIGH) {
            digitalWrite(13, LOW);
            Serial.println("Boa noite...vou dormir...");
            if (millis()-tempo_inicial_d<(7500) && penalidades!=0){
              penalidades-=5;
            }
            tempo_comeco_dormir = millis();
            acionou_d = false;
            acordado = false;
            i = 0;
            media = 0;
            //caso estejam outras luzes ligadas vai dormir e desliga-as
            digitalWrite(12, LOW);
            digitalWrite(11, LOW);
            acionou_b = false;
            acionou_c = false;
          }
        }
      }
      lbs1 = r1;

      //botão brincar
      int r2 = !digitalRead(3);
      if (r2 != lbs2) {
        ldt2 = millis();
      }
      if ((millis() - ldt2) > debounceDelay) {
        if (r2 != bs2) {
          bs2 = r2;
          if (bs2 == HIGH) {
            digitalWrite(12, LOW);
            if (millis()-tempo_inicial_b<(7500) && penalidades!=0){
              penalidades-=5;
            }
            acionou_b = false;
          }
        }
      }
      lbs2 = r2;

      //botão comer
      int r3 = !digitalRead(2);
      if (r3 != lbs3) {
        ldt3 = millis();
      }
      if ((millis() - ldt3) > debounceDelay) {
        if (r3 != bs3) {
          bs3 = r3;
          if (bs3 == HIGH) {
            digitalWrite(11, LOW);
            if (millis()-tempo_inicial_c<(7500) && penalidades!=0){
              penalidades-=5;
            }
            acionou_c = false;
          }
        }
      }
      lbs3 = r3;
      if (millis()!=millis_anterior){
        //valores luminosidade e escrita do valor total das penalizacoes
        if ((millis()-margem_dormir)%(30000) == 0 && millis()-margem_dormir != 0){
          Serial.print("Valor total de penalidades: ");
          Serial.println(penalidades);
          valor = analogRead(A0);
          total_leituras = total_leituras - leituras[i%6] + valor;
          leituras[i%6] = valor;
          if (i>=5){
            media = total_leituras/6;
          }
          i++;
        }
          
        //atenção comer
        if ((millis()-margem_dormir)%(120000-random((-30000),(30000))) == 0 && millis()-margem_dormir != 0 && !acionou_c){
          digitalWrite(11, HIGH);
          tempo_inicial_c = millis();
          acionou_c = true;
        }

        //atenção brincar
        if ((millis()-margem_dormir)%(90000-random((-30000),(30000))) == 0 && millis()-margem_dormir != 0 && !acionou_b){
          digitalWrite(12, HIGH);
          tempo_inicial_b = millis();
          acionou_b = true;
        }

        //atenção dormir
        if (millis()-margem_dormir != 0 && ((millis()-margem_dormir)%(300000-random((-30000),(30000))) ==  0 || media>margem_luminosidade) && !acionou_d){
          digitalWrite(13, HIGH);
          tempo_inicial_d = millis();
          acionou_d = true;
        }
        penalidades = func_penalidades(acionou_c,tempo_inicial_c,penalidades);
        penalidades = func_penalidades(acionou_b,tempo_inicial_b,penalidades);
        penalidades = func_penalidades(acionou_d,tempo_inicial_d,penalidades);
        millis_anterior = millis();
      }

      //morrer
      if (penalidades >= 25){
        Serial.println("Não me trataste bem...XAUUU!!!");
        for (int j=11; j<14; j++){
          digitalWrite(j, LOW);
        }
        vivo = false;
      }
    }
    
    //5 minutos de sono
    if (!acordado && millis()-tempo_comeco_dormir == 150000){
      Serial.println("WWWWAAAAANNNNMMMM...Acordei!");
      acordado = true;
      margem_dormir = millis();
    }
  }
  else{}
}