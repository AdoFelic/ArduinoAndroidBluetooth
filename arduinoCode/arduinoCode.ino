
String komanda; 
boolean rezimRada; 

int trigPin = 37;
int echoPin = 36;
long trajanje, cm;

int brzina = 255;

int promjena;

#include <AFMotor.h>

AF_DCMotor motor(2);
AF_DCMotor motor1(1);

void setup()                    
{
  Serial.begin(9600); 
  Serial1.begin(9600);           

  rezimRada = true;
  promjena = 1;

  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT); 

  motor.run(RELEASE);
  motor1.run(RELEASE);
}

void loop()
{
  komanda = "";

  if(Serial1.available()){
    while(Serial1.available()){
      char karakter = Serial1.read();
      komanda += karakter;
      delay(10);
    }

    if(komanda.indexOf("promijeni") >= 0){
      rezimRada = !rezimRada;
      promjena = 2;
    }

    if(promjena == 2){
      if(rezimRada){
        brzina = 255;
        Serial1.print("{");
        Serial1.print("auto");
        Serial1.print("}");
      } else {
        brzina = 200;
        Serial1.print("{");
        Serial1.print("manu");
        Serial1.print("}");
      }
      promjena = 1;
    }

    if(rezimRada){
      if(komanda == "np"){
        motor.run(FORWARD);
        motor1.run(FORWARD);
        motor.setSpeed(brzina);
        motor1.setSpeed(brzina);
      }else if(komanda == "nz"){
        motor.run(BACKWARD);
        motor1.run(BACKWARD);
        motor.setSpeed(brzina);
        motor1.setSpeed(brzina);
      }else if(komanda == "vl"){
        motor.run(FORWARD);
        motor1.run(FORWARD);
        motor.setSpeed(brzina-75);
        motor1.setSpeed(0);
      }else if(komanda == "vd"){
        motor.run(FORWARD);
        motor1.run(FORWARD);
        motor.setSpeed(0);
        motor1.setSpeed(brzina-75);
      }else if(komanda == "bd"){
        motor.run(FORWARD);
        motor1.run(FORWARD);
        motor.setSpeed(brzina-125);
        motor1.setSpeed(brzina-25);
      }else if(komanda == "bl"){
        motor.run(FORWARD);
        motor1.run(FORWARD);
        motor.setSpeed(brzina-25);
        motor1.setSpeed(brzina-125);
      }else if(komanda == "nl"){
        motor.run(BACKWARD);
        motor1.run(BACKWARD);
        motor.setSpeed(brzina-25);
        motor1.setSpeed(brzina-125);
      }else if(komanda == "nd"){
        motor.run(BACKWARD);
        motor1.run(BACKWARD);
        motor.setSpeed(brzina-125);
        motor1.setSpeed(brzina-25);
      }else{
        motor.run(FORWARD);
        motor1.run(FORWARD);
        motor.setSpeed(0);
        motor1.setSpeed(0);
      }
    }
    
      
  }

  if(rezimRada == false)
  {
    automatskiRezim();
  }  
}

void automatskiRezim(){
  digitalWrite(trigPin, LOW);
  delayMicroseconds(5);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(5);
  digitalWrite(trigPin, LOW);
  trajanje = pulseIn(echoPin, HIGH);
  cm = (trajanje/2) / 29.1;

  if(cm>30)
  {
    motor.run(FORWARD);
    motor1.run(FORWARD);
    motor.setSpeed(brzina-50);
    motor1.setSpeed(brzina-50);
  }else if(cm<=30){
    motor.run(FORWARD);
    motor1.run(BACKWARD);
    motor.setSpeed(brzina);
    motor1.setSpeed(brzina);
  }
  
}
