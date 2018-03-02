import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class DoublePendulum extends PApplet {

float line1 = 200;
float line2 = 200;

float mass1 = 20;
float mass2 = 20;

float theta1 = PI/2;
float theta2 = PI/8;

float theta1Velocity = 0;
float theta2Velocity = 0;

float previousX2 = 0;
float previousY2 = 0;

float g = 1.098f;  

PGraphics canvas;

public void setup() {
  
 
  canvas = createGraphics(900, 900);
  canvas.beginDraw();
  canvas.background(0);
  canvas.endDraw();
}

public void draw() {
  
  float numerator1 = -g * (2 * mass1 + mass2) * sin(theta1);
  float numerator2 = -mass2 * g * sin(theta1 - 2 * theta2);
  float numerator3 = -2 * sin(theta1-theta2) * mass2;
  float numerator4 = theta2Velocity * theta2Velocity * line2 + theta1Velocity * theta1Velocity * line1* cos(theta1-theta2);
  
  float denominator = (line1 * (2 * mass1 + mass2 - mass2 * cos(2 * theta1 - 2 * theta2)));
  
  float theta1Acceleration = (numerator1 + numerator2 + numerator3 * numerator4) / denominator;
  
  numerator1 = 2 * sin(theta1 - theta2);
  numerator2 = (theta1Velocity * theta1Velocity * line1 * (mass1 + mass2));
  numerator3 = g * (mass1 +mass2) * cos(theta1);
  numerator4 = theta2Velocity * theta2Velocity * line2 * mass2 * cos(theta1 - theta2);
  denominator = line2 * (2 * mass1 + mass2 - mass2 * cos(2 * theta1 - 2 * theta2));
   
  float theta2Acceleration = (numerator1 * (numerator2 + numerator3 + numerator4)) / denominator;

  
  image(canvas,0,0);
  
  translate(450, 450);
  
  stroke(255);
  strokeWeight(1);
  fill(255);
  
  float x1 = line1 * sin(theta1);
  float y1 = line1 * cos(theta1);
   
  float x2 = x1 + line2 * sin(theta2);
  float y2 = y1 + line2 * cos(theta2);
     
  line(0,0, x1, y1);
  ellipse(x1, y1, mass1, mass1);
  fill(255);
  
  line(x1, y1, x2, y2);
  fill(255);
  ellipse(x2, y2, mass2, mass2);
 
  theta1Velocity += theta1Acceleration;
  theta2Velocity += theta2Acceleration;
  
  theta1 += theta1Velocity;
  theta2 += theta2Velocity;

  theta1Velocity *= .99999f;
  theta2Velocity *= .99999f;
 
  canvas.beginDraw();
  canvas.translate(450, 450);
  canvas.strokeWeight(1);
  canvas.stroke(255);
  if (frameCount > 1) {
    canvas.line(previousX2, previousY2, x2, y2);
  }
  canvas.endDraw();

  previousX2 = x2;
  previousY2 = y2;
  
}  
  public void settings() {  size(900, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "DoublePendulum" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
