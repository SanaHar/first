import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.*;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
public class graphic extends Canvas {
	 public static abstract class ground{}
	  public static class Node extends ground{
		  int name;
		  ArrayList<parts> connectedBranch = new ArrayList<parts>();
		  double V;
		  ArrayList<Integer> x = new ArrayList<Integer>();
		  ArrayList <Integer> draw = new ArrayList<Integer>();  
		  ArrayList<Integer> y = new ArrayList<Integer>();
		  int cond =0,cond1=0;
	  }
	 public static class parts{
		  Node startNode,endNode;
		  String name;
		  double value;
		  int draw=0;
		  ArrayList<Double> Voltage = new ArrayList<Double>();
		  ArrayList<Double> Current = new ArrayList<Double>();
		  ArrayList<Double> Power = new ArrayList<Double>();
		  public static class capacitor extends parts{
			  double I;
		  }
		  public static class resistor extends parts{}
		  public static class Inductor extends parts{
			  double I;
		  }
		  
		 public static class CurrentDV extends parts{
			 Node snode,enode;
			 double g;
		 }
		 public static class CurrentDI extends parts{
			 parts related;
			 double g;
		  }
		  
		  public static class CurrentIndependent extends parts{
			  double value;
			  double A,f,phase;
		  }
		  public static class VoltageDV extends parts{
			 double g;
			 Node snode,enode;
		  }
		  public static class VoltageDI extends parts{
			 parts related;
			 double g;
		  }
		  public static class VoltageIndependent extends parts{
			  double value;
			  double A,f,phase;
		  }
		  public static class Diod extends parts{
			  
		  }
	 }
	 public static void draw(parts e,JPanel panel,JLabel label,Node n,int a) {
		 int I=0,J=0,cond=0, c=0;
		 if((int)e.endNode.y.get(0)!=(int)e.startNode.y.get(0)) {
			 	if(n.equals(e.startNode)) { 
				  for(int i=a;i<e.startNode.x.size();i++) {
					for(int j=0;j<e.endNode.x.size();j++) 
						if((int)n.x.get(i)==(int)e.endNode.x.get(j)) {
							if((int)e.endNode.draw.get(j)==0 &&    //new
									   (int)e.startNode.draw.get(i)==0) {   //new
							I=i;
							J=j;
							
						    label.setBounds(e.startNode.x.get(I),e.startNode.y.get(I),e.endNode.x.get(J),e.endNode.y.get(J));
						     System.out.println( e.startNode.x.get(I));
							 System.out.println(e.startNode.y.get(I));
							 System.out.println( e.endNode.x.get(J));
							 System.out.println( e.endNode.y.get(J));
							 System.out.println( e.name);
						    JLabel name = new JLabel(e.name);
						    name.setBounds(e.startNode.x.get(I)+110,(e.startNode.y.get(I)+e.endNode.y.get(J))/2+25,
						    		e.startNode.x.get(I)+120,(e.startNode.y.get(I)+e.endNode.y.get(J))/2+35);
						   // int X = label.getX();
							//int Y = label.getY();
							//name.setLocation(X,Y);
						    panel.add(name);
						    panel.repaint();
						    c=1;
						    e.startNode.draw.set(i,1);    //new
						    e.endNode.draw.set(j,1);      //new
						    break;
						    }
						}
					if(c==1)
						break;
				}
				}
				
				if(n.equals(e.endNode)) {
				for(int i=a;i<e.endNode.x.size();i++) {
					for(int j=0;j<e.startNode.x.size();j++) 
						if((int)n.x.get(i)==(int)e.startNode.x.get(j)) {
							if((int)e.startNode.draw.get(j)==0 &&
							   (int)e.endNode.draw.get(i)==0) {
							     I=i;
							     J=j;
							     label.setBounds(e.startNode.x.get(J),e.startNode.y.get(J)+33,e.endNode.x.get(I),e.endNode.y.get(I)+33);
							     System.out.println( e.startNode.x.get(J));
								 System.out.println(e.startNode.y.get(J));
								 System.out.println( e.endNode.x.get(I));
								 System.out.println( e.endNode.y.get(I));
								 System.out.println(e.name);
								   JLabel name = new JLabel(e.name);
								  // int X = label.getX();
								  // int Y = label.getY();
								  // name.setLocation(X,Y);
								   name.setBounds(e.startNode.x.get(J)+130,(e.startNode.y.get(J)+e.endNode.y.get(I))/2+25,
								    		e.startNode.x.get(J)+135,(e.startNode.y.get(J)+e.endNode.y.get(I))/2+35);
								   panel.add(name);
								   panel.repaint();
								   c=1;
							       e.startNode.draw.set(j,1);
							       e.endNode.draw.set(i,1);
							       break;}
						}
					if(c==1)
						break;
				     }
				}
					
		 }
		 else { 
			 if(n.equals(e.startNode)) {
				 cond=0; int y=0,x=0,b=0,I1=0;
				 //new
				 if(n.cond1>0 && e.endNode.cond1>0) { 
					   ImageIcon im = (ImageIcon) label.getIcon();
					   Image scaled = im.getImage();
					   Image modified;
					   modified = scaled.getScaledInstance(150,50, java.awt.Image.SCALE_SMOOTH);
					   im = new ImageIcon(modified);
					   label.removeAll();
					   JLabel l = new JLabel(im);
					   label = l;
				 }
				///	 
				 if(a>=n.x.size()) {
					 I1=n.x.size()-1;
					 for(int j=0;j<e.endNode.x.size();j++)
						 if((double)n.y.get(I1)==(double)e.endNode.y.get(j)) {
							   if((int)n.x.get(I1)>(int)e.endNode.x.get(j)) { 
								 cond=1;}
				    }
			     }
				 for(int i=a;i<n.x.size();i++) {
					 for(int j=0;j<e.endNode.x.size();j++)
						 if((double)n.y.get(i)==(double)e.endNode.y.get(j)) {
						   if((int)n.x.get(i)>(int)e.endNode.x.get(j)) { 
							 cond=1;}
							 I1=i;
							 b=1;
							 break;
							 }
					 if(b==1)
						 break;
					 }	 
				 if(cond==1) {
					 label.setBounds(e.startNode.x.get(0),e.startNode.y.get(I1)-30,e.endNode.x.get(e.endNode.x.size()-1),e.endNode.y.get(I1)-30);
				     x=(e.startNode.x.get(0)+e.endNode.x.get(e.endNode.x.size()-1))/2;
				     y=e.startNode.y.get(I1)-30;
				 }
		     	else {
				     label.setBounds(e.startNode.x.get(I1)+10,e.startNode.y.get(I1)+33*(500-e.startNode.y.get(I1))/100,e.endNode.x.get(0)+10,e.endNode.y.get(0)+33*(500-e.startNode.y.get(I1))/100);
				     x=(e.startNode.x.get(I1)+10+e.endNode.x.get(0)+10)/2;
				     y=e.startNode.y.get(I1)+33;
				}   
		         System.out.println(e.startNode.x.get(n.x.size()-1));
				 System.out.println(e.startNode.y.get(n.x.size()-1));
				 System.out.println(e.endNode.x.get(0));
				 System.out.println(e.endNode.y.get(0));
				 System.out.println(e.name);
				 JLabel name = new JLabel(e.name);
				    name.setBounds(x+95,y-20+33*(500-e.startNode.y.get(I1))/100,x+100,y-15+33*(500-e.startNode.y.get(I1))/100);
				    panel.add(name);
				    panel.repaint();
			 }
			 if(n.equals(e.endNode)) {
				 cond=0; int y=0,x=0,b=0,I1=0;
				 if(a>=n.x.size()) {
					 I1=n.x.size()-1;
					 for(int j=0;j<e.startNode.x.size();j++)
						 if((double)n.y.get(I1)==(double)e.startNode.y.get(j)) {
							   if((int)n.x.get(I1)>(int)e.startNode.x.get(j)) { 
								 cond=1;}
				    }
			     }
				 for(int i=a;i<n.x.size();i++) {
					 for(int j=0;j<e.startNode.x.size();j++)
						 if((double)n.y.get(i)==(double)e.startNode.y.get(j)) {
						   if((int)n.x.get(i)>(int)e.startNode.x.get(j)) { 
							 cond=1;}
							 I1=i;
							 b=1;
							 break;
							 }
					 if(b==1)
						 break;
					 }	
							 
				 if(cond==1)
				 { label.setBounds(e.startNode.x.get(I1),e.startNode.y.get(I1)-30,e.endNode.x.get(0),e.endNode.y.get(0)-30);
				   x=(e.startNode.x.get(I1)+e.endNode.x.get(0))/2;
				   y=e.endNode.y.get(0)-30;
				 }
				 else
				 { label.setBounds(e.startNode.x.get(0)-6,e.startNode.y.get(0)+33*(500-e.endNode.y.get(I1))/100,e.endNode.x.get(I1)-6,e.endNode.y.get(I1)+33*(500-e.endNode.y.get(I1))/100);
				  
				 x=(e.startNode.x.get(0)-6+10+e.endNode.x.get(I1)-6)/2;
			       y=e.startNode.y.get(0)+33;
				 }
				 System.out.println(e.startNode.x.get(0));
				 System.out.println(e.startNode.y.get(0));
				 System.out.println(e.endNode.x.get(n.x.size()-1));
				 System.out.println(e.endNode.y.get(n.x.size()-1));
				 System.out.println(e.name);
				 JLabel name = new JLabel(e.name);
				    name.setBounds(x+95,y-20+33*(500-e.startNode.y.get(I1))/100,x+100,y-15+33*(500-e.startNode.y.get(I1))/100);
				    panel.add(name);
				    panel.repaint();
			 }
		 }
		 panel.add(label);
		 panel.repaint();
         
	 }
	 public static ImageIcon scaling(ImageIcon im, int condition) {
		 // condition=1 : icon should set vertically
		 // condition=0 : icon should set horizontally
		   Image scaled = im.getImage();
		   Image modified;
		   if(condition==1)
		       modified = scaled.getScaledInstance(50, 106, java.awt.Image.SCALE_SMOOTH);
		   else 
			   modified = scaled.getScaledInstance(90, 90 , java.awt.Image.SCALE_SMOOTH);
		   im = new ImageIcon(modified);
		   return im;
	 }

public static class MyCanvas extends JPanel {
	 static final int MAX_WIDTH = 700;
     static final int MAX_HEIGHT = 700; 
     static BufferedImage Image
 	   = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
     @Override
     public void paintComponent(Graphics g)
     {   super.paintComponent(g);
    	 Graphics2D g2 = (Graphics2D) g;
    	 g2 = Image.createGraphics();
    	 g2.setColor(Color.WHITE);
	     g2.drawLine(50,50,50,570);
	     g2.drawLine(50,570,590,570);
	     g2.setColor(Color.GRAY);
	     Stroke dashed = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[] {1,2},1);
	     g2.setStroke(dashed);
	     for(int i=0;i<10;i++)
	    	 g2.drawLine(50+i*54,50,50+i*54,570);
	     for(int i=0;i<10;i++)
	    	 g2.drawLine(50,570-i*52,590,570-i*52);
	     g2.drawString("Voltage", 30, 30);
	     g2.drawString("time", 595, 575);
 	     g.drawImage(Image, 0, 0, Color.BLUE, null);
 	  
 	   
     }

}
public static class MyCanvas1 extends JPanel
	   {
	 static final int MAX_WIDTH = 700;
   static final int MAX_HEIGHT = 700; 
   
   static BufferedImage Image
	   = new BufferedImage(10000, 10000, BufferedImage.TYPE_INT_RGB);
   public void paintComponent(Graphics g) {
	     super.paintComponent(g); 
	     Graphics2D g2 = (Graphics2D) g;
  	     g2 = Image.createGraphics();
  	     g2.setColor(Color.WHITE);
	     g2.drawLine(50,50,50,570);
	     g2.drawLine(50,570,590,570);
	     g2.setColor(Color.GRAY);
	     Stroke dashed = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[] {1,2},1);
	     g2.setStroke(dashed);
	     for(int i=0;i<10;i++)
	    	 g2.drawLine(50+i*54,50,50+i*54,570);
	     for(int i=0;i<10;i++)
	    	 g2.drawLine(50,570-i*52,590,570-i*52);
	     g2.drawString("Current", 30, 30);
	     g2.drawString("time", 595, 575);
	   g.drawImage(Image, 0, 0, Color.GREEN, null);
   }
	   }
public static class MyCanvas2 extends JPanel
{
static final int MAX_WIDTH = 700;
static final int MAX_HEIGHT = 700; 

static BufferedImage Image
 = new BufferedImage(10000, 10000, BufferedImage.TYPE_INT_RGB);
public void paintComponent(Graphics g)
{
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	g2 = Image.createGraphics();
    g2.setColor(Color.WHITE);
    g2.drawLine(50,50,50,570);
    g2.drawLine(50,570,590,570);
    g2.setColor(Color.GRAY);
    Stroke dashed = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[] {1,2},1);
    g2.setStroke(dashed);
    for(int i=0;i<10;i++)
   	 g2.drawLine(50+i*54,50,50+i*54,570);
    for(int i=0;i<10;i++)
   	 g2.drawLine(50,570-i*52,590,570-i*52);
    g2.drawString("Power", 30, 30);
    g2.drawString("time", 595, 575);
    g.drawImage(Image, 0, 0, Color.RED, null);
}

}	 
   public static int R(double vs , double ve) {
	   double v=0; int r=0;
	  	if((double)vs<=(double)ve) {
	           v = vs;
	    	   while(v<1) {
	    		   v=v*10;
	    		   r++;
	    	   }
	    	}
	    	if((double)vs>(double)ve) {
		           v = ve;
		    	   while(v<1) {
		    		   v=v*10;
		    		   r++;
		    	   }
		    	}
	    	return r;
   }
   public static double drawlabel(JPanel p,double T,ArrayList<Double> A) {
	   double max=0,min=0;
		for(int a=0;a<=10;a++)
		  {JLabel l = new JLabel(String.format("%6.1e",a*T/10));
	      l.setBounds(50+a*54,385,55+a*54,390);
	      p.add(l);
	      }
		for(int i=0;i<A.size();i++) {
			if(A.get(i)>max)
				max=A.get(i);
			if(A.get(i)<min)
				min=A.get(i);
		}
	
		for(int a=0;a<=10;a++) {
			JLabel l = new JLabel(String.format("%6.1e",min+a*(max-min)/10));
		    l.setBounds(2,400-a*35,50,-52+395-a*35);
		    p.add(l);
		}
		return max;
   }
   public static void main(String []args) {
	   double dt=0.00001,T=0.02;
	   JFrame frame1 = new JFrame();
	   JPanel panel = new JPanel();
	   panel.setLayout(null);
	   JFrame frame2 = new JFrame();
	   JPanel drawpanel = new JPanel();
	   drawpanel.setLayout(null);
	   JButton RUN = new JButton("RUN");
	   RUN.setBounds(200, 200, 100, 50);
	   panel.add(RUN);
	   JLabel t1  = new JLabel("press RUN to see the circuit image");
	   t1.setBounds(200, 150, 250, 50);
	   panel.add(t1);
	   JButton DRAW = new JButton("DRAW");
	   DRAW.setBounds(200, 300, 120, 50);
	   panel.add(DRAW);
	   ArrayList <Node> NODE = new ArrayList<Node>();
	   ArrayList <parts> Element = new ArrayList<parts>();
	   int x=100,Y = -1;
	   parts.resistor r1 = new parts.resistor();
	   r1.name="r1";
	   parts.resistor r2 = new parts.resistor();
	   r2.name="r2";
	   parts.resistor r3 = new parts.resistor();
	   r3.name="r3";
	   parts.resistor r4 = new parts.resistor();
	   r4.name="r4";
	   parts.resistor r5 = new parts.resistor();
	   r5.name="r5";
	   parts.resistor r6 = new parts.resistor();
	   r6.name="r6";
	   parts.resistor r7 = new parts.resistor();
	   r7.name="r7";
	   parts.resistor r8 = new parts.resistor();
	   r8.name="r8";
	   parts.resistor r9 = new parts.resistor();
	   r9.name="r9";
	   parts.VoltageIndependent v1 = new parts.VoltageIndependent();
	   v1.name="v1";
	   parts.CurrentIndependent i1 = new parts.CurrentIndependent();
	   i1.name="i1";
	  /* parts.CurrentDI h1 = new parts.CurrentDI();
	   h1.name="H1";
	   parts.capacitor C = new parts.capacitor();
	   C.name="c1";
	   parts.capacitor C1 = new parts.capacitor();
	   C1.name="c2";
	   parts.capacitor C2 = new parts.capacitor();
	   C2.name="c3";
	   parts.Inductor l = new parts.Inductor();
	   l.name="l1";
	   parts.Inductor l2 = new parts.Inductor();
	   l2.name="l2";
	   parts.Inductor l3 = new parts.Inductor();
	   l3.name="l3";
	   parts.VoltageIndependent ci = new parts.VoltageIndependent();
	   ci.name="I1";
	   parts. Diod D = new parts. Diod();
	   D.name="D1";*/
	   Node a = new Node();
	   a.name = 0;
	   Node b = new Node();
	   b.name =1;
	   Node c = new Node();
	   c.name =2;
	   Node d = new Node();
	   d.name =3;
	 /*  Node e = new Node();
	   e.name =9;
	   Node f = new Node();
	   f.name =13;
	   Node g = new Node();
	   g.name =14;
	   Node h = new Node();
	   h.name =15;*/
	   r1.startNode=b; r1.endNode=c; 
	   r2.startNode=c; r2.endNode=d;
	   r3.startNode=b; r3.endNode=d;
	   r4.startNode=c; r4.endNode=a;
	   r5.startNode=d; r5.endNode=a;
	  /* r6.startNode=h; r6.endNode=e;
	   r7.startNode=h; r7.endNode=e;
	   r8.startNode=f; r8.endNode=g;
	   r9.startNode=g; r9.endNode=h;*/
	   i1.startNode=b; i1.endNode=a;
	  // v1.startNode=f; v1.endNode=a;
	   a.connectedBranch.add(i1);
	   a.connectedBranch.add(r4);
	   a.connectedBranch.add(r5);
	  // a.connectedBranch.add(v1);
	   b.connectedBranch.add(i1);
	   b.connectedBranch.add(r1);
	   b.connectedBranch.add(r3);
	   c.connectedBranch.add(r1);
	   c.connectedBranch.add(r2);
	   c.connectedBranch.add(r3);
	   d.connectedBranch.add(r2);
	   d.connectedBranch.add(r3);
	   d.connectedBranch.add(r5);
	  /* e.connectedBranch.add(r7);
	   f.connectedBranch.add(v1);
	   f.connectedBranch.add(r8);
	   g.connectedBranch.add(r1);
	   g.connectedBranch.add(r9);
	   h.connectedBranch.add(r6);
	   h.connectedBranch.add(r7);
	   h.connectedBranch.add(r9);*/
	   NODE.add(a);
	   NODE.add(b);
	   NODE.add(c);
	   NODE.add(d);
	 /*  NODE.add(e);
	   NODE.add(f);
	   NODE.add(g);
	   NODE.add(h);*/
	  /* parts.resistor r = new parts.resistor();
	   r.name="r1";
	   parts.VoltageIndependent C = new parts.VoltageIndependent();
	   C.name="v1";
	   parts.VoltageIndependent l = new parts.VoltageIndependent();
	   l.name="v2";
	   Node a = new Node();
	   a.name = 0;
	   Node b = new Node();
	   b.name =1;
	   NODE.add(a);
	   NODE.add(b);
	   a.connectedBranch.add(C);
	   a.connectedBranch.add(r);
	   a.connectedBranch.add(l);
	   b.connectedBranch.add(l);
	   b.connectedBranch.add(C);
	   b.connectedBranch.add(r);
	   r.startNode=a; r.endNode=b; 
	   C.startNode=a; C.endNode=b; 
	   l.startNode=a; l.endNode=b;*/
	   Element.add(r1);
	  for(int i=0;i<2000;i++)
	  { r1.Voltage.add((double) 0.00001*(1-Math.pow(Math.E, -i*dt/0.001))) ;
	   }
	 
	   //for(int i=1;i<=350;i++)
		  // r1.Voltage.add((double) 0.0005-(0.000000857*i)) ;
	 //  for(int i=0;i<150;i++)
	     //  r1.Voltage.add((double) 0.008) ;
	 //  for(int i=1;i<=2000;i++)
		 //  r1.Voltage.add((double) 0.008-(0.00000857*i)) ;
	   for(int i=0;i<2000;i++)
		  r1.Current.add((double) Math.sin(1000*i*dt)) ;
	   for(int i=0;i<2000;i++)   System.out.println( r1.Current.get(i));
	 //  for(int i=1;i<=350;i++)
		  // r1.Current.add((double) 0.008-(0.00000857*i)) ;
	   /*for(int i=0;i<400;i++)
		   r.Current.add((double) (0.0005+i*0.00001)) ;*/
	   for(int i=0;i<2000;i++)
		   r1.Power.add((double) (1+i*0.01)) ;
	   int count = 0;
	  
	   for(int i=0;i<NODE.size();i++) {
		   ArrayList<Node> connectedNode = new ArrayList<Node>();
		   count=0;
		   for(int j=0;j<NODE.get(i).connectedBranch.size();j++) {
			   if(((int)NODE.get(i).connectedBranch.get(j).startNode.name==0 ||
					   (int)NODE.get(i).connectedBranch.get(j).endNode.name==0))
				   count++;
			   if((int)NODE.get(i).name==(int)NODE.get(i).connectedBranch.get(j).startNode.name)
				   connectedNode.add(NODE.get(i).connectedBranch.get(j).endNode);
			   else
				   connectedNode.add(NODE.get(i).connectedBranch.get(j).startNode);
           }
		   if(count>1)
			   NODE.get(i).cond = count;
		   //***** new:
		   for(int k=0;k<connectedNode.size();k++) {
				   if(Math.abs((int)connectedNode.get(k).name-(int)NODE.get(i).name)==2
						   && (int)connectedNode.get(k).name!=0 && (int)NODE.get(i).name!=0)
					   NODE.get(i).cond1++;
				      
					   }
	   }
	   
	   int X=0;   //new
	   for(int i=0;i<NODE.size();i++) {
		   int yscale = (int) NODE.get(i).name/6;  //new
		   Y=yscale;                               //new
		   if(i%6==1 || i==0)
			   x=200;
		   if(NODE.get(i).cond>0) {
			 if(NODE.get(i).name == 0)
				 for(int j=0;j<6;j++) {
					 NODE.get(i).y.add((int) 500);
					 NODE.get(i).x.add((int) (x+50*j));
					 NODE.get(i).draw.add((int) 0); 
					 System.out.println( NODE.get(i).x.get(j));
					   System.out.println( NODE.get(i).y.get(j));
					   System.out.println( NODE.get(i).name);
				 }
			 else {
				 
		     for(int j=0;j<NODE.get(i).cond;j++) {
			  // if(x+50*j==200) 
					 // Y++; 
				   NODE.get(i).y.add((int) 400-(Y)*100);
				   NODE.get(i).x.add((int) (x+50*j));
				   NODE.get(i).draw.add((int) 0);
				  System.out.println( NODE.get(i).x.get(j));
				   System.out.println( NODE.get(i).y.get(j));
				   System.out.println( NODE.get(i).name);
		     }
		     
		     }
			 X=NODE.get(i).x.size();   //new
	       }
		    
			      
		   else {
			  // if(x==200) 
					 // Y++; 
			   NODE.get(i).y.add((int) 400-(Y)*100);
			   NODE.get(i).x.add((int) (x));
			   NODE.get(i).draw.add((int) 0);
			   System.out.println( NODE.get(i).x.get(0));
			   System.out.println( NODE.get(i).y.get(0));
			   System.out.println( NODE.get(i).name);
		   }
		   X=NODE.get(i).x.size();     //new
		   /*if(NODE.get(i).cond1>0) {
			   for(int j=1;j<=NODE.get(i).cond1;j++) {
			        NODE.get(i).y.add((int) 400-(Y+j)*100);
			        NODE.get(i).x.add((int) NODE.get(i).x.get(0));
			        NODE.get(i).draw.add((int) 0);
			        System.out.println( NODE.get(i).cond1);
					   System.out.println( NODE.get(i).y.get(1));
					   System.out.println( NODE.get(i).name);
			   }
			   X=NODE.get(i).x.size()-NODE.get(i).cond1;
			   
			   
		   }*/
		   
		   //*** new:
		   if(NODE.get(i).cond1>0) {
			   NODE.get(i).y.add((int) 400-(Y+1)*100);
		       NODE.get(i).x.add((int) NODE.get(i).x.get(0));
		       X=NODE.get(i).x.size()-NODE.get(i).cond1; 
		   }
		    //***
		   System.out.print("ll");
		   System.out.println(X);
		   x += 50*(X);  //new 

	   }
	 
	  RUN.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			 for(int i=0;i<NODE.size();i++) {
				  // int cte =0 ;
				   for(int j=0;j<NODE.get(i).connectedBranch.size();j++) {
					   if(NODE.get(i).connectedBranch.get(j).draw==0) {
					   if((int)NODE.get(i).connectedBranch.get(j).startNode.y.get(0) !=
						  (int)NODE.get(i).connectedBranch.get(j).endNode.y.get(0)) {
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.resistor) {
							   ImageIcon resistor = new ImageIcon("r2.png");
							   resistor = scaling(resistor,1);
							   JLabel lr2 = new JLabel(resistor);
							   draw(NODE.get(i).connectedBranch.get(j),panel,lr2,NODE.get(i),j);
							  
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.capacitor) {
							   
							   ImageIcon capacitor = new ImageIcon("c2.png");
							   capacitor = scaling(capacitor,1);
							   JLabel lc2 = new JLabel(capacitor);
							  draw(NODE.get(i).connectedBranch.get(j),panel,lc2,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.Inductor) {
							   ImageIcon Inductor = new ImageIcon("l2.jpg");
							   Inductor = scaling(Inductor,1);
							   JLabel lL2 = new JLabel(Inductor);
							   draw(NODE.get(i).connectedBranch.get(j),panel,lL2,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.Diod) {
							   ImageIcon Diod = new ImageIcon("d2.png");
							   Diod=scaling(Diod,1);
							   JLabel ld2 = new JLabel(Diod);
							   ImageIcon Diod2 = new ImageIcon("d3.png");
							   Diod2 = scaling(Diod2,1);
							   JLabel ld3 = new JLabel(Diod2);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
							      draw(NODE.get(i).connectedBranch.get(j),panel,ld2,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,ld3,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.CurrentDI ||
						      NODE.get(i).connectedBranch.get(j) instanceof parts.CurrentDV) {
							   ImageIcon CurrentD = new ImageIcon("cd2.png");
							   CurrentD = scaling(CurrentD,1);
							   JLabel lCD2 = new JLabel(CurrentD);
							   ImageIcon CurrentD2 = new ImageIcon("cd3.png");
							   CurrentD2 = scaling(CurrentD2,1);
							   JLabel lCD3 = new JLabel(CurrentD2);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
								   draw(NODE.get(i).connectedBranch.get(j),panel,lCD2,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								   draw(NODE.get(i).connectedBranch.get(j),panel,lCD3,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.CurrentIndependent) {
							   ImageIcon CurrentIndependent = new ImageIcon("ii22.png");
							   Image scaled = CurrentIndependent.getImage();
							   Image modified = scaled.getScaledInstance(100, 130, java.awt.Image.SCALE_SMOOTH);
							   CurrentIndependent = new ImageIcon(modified);
							   JLabel lCI2 = new JLabel(CurrentIndependent);
							   ImageIcon CurrentIndependent2 = new ImageIcon("ii3.png");
							   scaled = CurrentIndependent2.getImage();
							   modified = scaled.getScaledInstance(100, 130, java.awt.Image.SCALE_SMOOTH);
							   CurrentIndependent2 = new ImageIcon(modified);
							   JLabel lCI3 = new JLabel(CurrentIndependent2);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
							      draw(NODE.get(i).connectedBranch.get(j),panel,lCI2,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lCI3,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.VoltageDI ||
						      NODE.get(i).connectedBranch.get(j) instanceof parts.VoltageDV) {
							   ImageIcon VoltageD = new ImageIcon("vd3.png");
							   VoltageD = scaling( VoltageD,1);
							   JLabel lVD2 = new JLabel(VoltageD);
							   ImageIcon VoltageD2 = new ImageIcon("vd2.png");
							   VoltageD2 = scaling( VoltageD2,1);
							   JLabel lVD3 = new JLabel(VoltageD2);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVD2,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVD3,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.VoltageIndependent) {
							   if(((parts.VoltageIndependent)NODE.get(i).connectedBranch.get(j)).f==0) {
							   ImageIcon VoltageIndependent = new ImageIcon("vi3.png");
							   VoltageIndependent = scaling(VoltageIndependent,1);
							   JLabel lVI2 = new JLabel(VoltageIndependent);
							   ImageIcon VoltageIndependent2 = new ImageIcon("vi2.png"); 
							   VoltageIndependent2 = scaling(VoltageIndependent2,1);
							   JLabel lVI3 = new JLabel(VoltageIndependent2);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
							      draw(NODE.get(i).connectedBranch.get(j),panel,lVI2,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVI3,NODE.get(i),j);
							   }
							   else {
								   ImageIcon AC = new ImageIcon("ac2.png");
								   AC = scaling(AC,1);
								   JLabel lAC = new JLabel(AC);
								   draw(NODE.get(i).connectedBranch.get(j),panel,lAC,NODE.get(i),j);
								  
								   }
							  
						   }
					   }
					   else {
						   //System.out.println("l");
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.resistor) {
							   ImageIcon resistor1 = new ImageIcon("r1.png");
							   resistor1 = scaling(resistor1,0);
							   JLabel lr1 = new JLabel(resistor1);
							   draw(NODE.get(i).connectedBranch.get(j),panel,lr1,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.capacitor) {
							   ImageIcon capacitor1 = new ImageIcon("c1.png");
							   capacitor1 = scaling(capacitor1,0);
							   JLabel lc1 = new JLabel(capacitor1);
							  draw(NODE.get(i).connectedBranch.get(j),panel,lc1,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.Inductor) {
							   ImageIcon Inductor1 = new ImageIcon("l1.jpg");
							   Inductor1 = scaling(Inductor1,0);
							   JLabel lL1 = new JLabel(Inductor1);
							   draw(NODE.get(i).connectedBranch.get(j),panel,lL1,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.Diod) {
							   ImageIcon Diod1 = new ImageIcon("d1.png");
							   Diod1=scaling(Diod1,0);
							   JLabel ld1 = new JLabel(Diod1);
							   ImageIcon Diod4 = new ImageIcon("d4.png");
							   Diod4 = scaling(Diod4,0);
							   JLabel ld4 = new JLabel(Diod4);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
							      draw(NODE.get(i).connectedBranch.get(j),panel,ld1,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,ld4,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.CurrentDI ||
						      NODE.get(i).connectedBranch.get(j) instanceof parts.CurrentDV) {
							   ImageIcon CurrentD1 = new ImageIcon("cd1.png");
							   CurrentD1 = scaling(CurrentD1,0);
							   JLabel lCD1 = new JLabel(CurrentD1);
							   ImageIcon CurrentD4 = new ImageIcon("cd4.png");
							   CurrentD4 = scaling(CurrentD4,0);
							   JLabel lCD4 = new JLabel(CurrentD4);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
								   draw(NODE.get(i).connectedBranch.get(j),panel,lCD1,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								   draw(NODE.get(i).connectedBranch.get(j),panel,lCD4,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.CurrentIndependent) {
							   ImageIcon CurrentIndependent1 = new ImageIcon("ii1.png");
							   Image scaled = CurrentIndependent1.getImage();
							   Image modified = scaled.getScaledInstance(130, 100, java.awt.Image.SCALE_SMOOTH);
							   CurrentIndependent1 = new ImageIcon(modified);
							   JLabel lCI1 = new JLabel(CurrentIndependent1);
							   ImageIcon CurrentIndependent4 = new ImageIcon("ii4.png");
							   scaled = CurrentIndependent4.getImage();
							   modified = scaled.getScaledInstance(130, 100, java.awt.Image.SCALE_SMOOTH);
							   CurrentIndependent4 = new ImageIcon(modified);
							   JLabel lCI4 = new JLabel(CurrentIndependent4);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
							      draw(NODE.get(i).connectedBranch.get(j),panel,lCI1,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lCI4,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.VoltageDI ||
						      NODE.get(i).connectedBranch.get(j) instanceof parts.VoltageDV) {
							   ImageIcon VoltageD1 = new ImageIcon("vd1.png");
							   VoltageD1 = scaling( VoltageD1,0);
							   JLabel lVD1 = new JLabel(VoltageD1);
							   ImageIcon VoltageD4 = new ImageIcon("vd4.png");
							   VoltageD4 = scaling( VoltageD4,0);
							   JLabel lVD4 = new JLabel(VoltageD4);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVD1,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVD4,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.VoltageIndependent) {
							   if(((parts.VoltageIndependent)NODE.get(i).connectedBranch.get(j)).f==0) {
							   ImageIcon VoltageIndependent1 = new ImageIcon("vi1.png");
							   VoltageIndependent1 = scaling(VoltageIndependent1,0);
							   JLabel lVI1 = new JLabel(VoltageIndependent1);
							   ImageIcon VoltageIndependent4 = new ImageIcon("vi4.png"); 
							   VoltageIndependent4 = scaling(VoltageIndependent4,0);
							   JLabel lVI4 = new JLabel(VoltageIndependent4);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
							      draw(NODE.get(i).connectedBranch.get(j),panel,lVI1,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVI4,NODE.get(i),j);
							   }
							   else {
								   ImageIcon AC = new ImageIcon("ac1.png");
								   AC = scaling(AC,0);
								   JLabel lAC = new JLabel(AC);
								   draw(NODE.get(i).connectedBranch.get(j),panel,lAC,NODE.get(i),j);
								  
								   }
						   }
					   }
					   NODE.get(i).connectedBranch.get(j).draw=1;
					   } 
					 
				   }
				   int size = 0;
				   if(NODE.get(i).name==0) {
					   for(int j=0;j<NODE.size();j++) {
						   if(NODE.get(j).equals(NODE.get(i)))
							   continue;
						 for(int k=0;k<NODE.get(j).x.size();k++)
							 if(NODE.get(j).x.get(k)>size)
								 size = NODE.get(j).x.get(k);
						 
						 }
					   size = (size - 200)/50;
					   }
				   else
					   size = NODE.get(i).x.size()-1-NODE.get(i).cond1;
				   for(int k=0;k< size;k++) {
					   ImageIcon wire1 = new ImageIcon("w.png");
					   Image scaled = wire1.getImage();
					   Image modified = scaled.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
					   wire1 = new ImageIcon(modified);
					   JLabel lw = new JLabel(wire1);
					   if( NODE.get(i).y.get(k)<500) 
					      lw.setBounds(NODE.get(i).x.get(k)+10, NODE.get(i).y.get(k)+33,NODE.get(i).x.get(k+1)+7, NODE.get(i).y.get(k+1)+33);
					   else
						   lw.setBounds(NODE.get(i).x.get(k)+10, NODE.get(i).y.get(k),NODE.get(i).x.get(k+1)+7, NODE.get(i).y.get(k+1));
					   panel.add(lw);
				   }
				   for(int k=0;k<NODE.get(i).x.size()-1;k++) {
					   if((int)NODE.get(i).x.get(k)==(int)NODE.get(i).x.get(k+1)) {
					   ImageIcon wire1 = new ImageIcon("w1.png");
					   Image scaled = wire1.getImage();
					   Image modified = scaled.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					   wire1 = new ImageIcon(modified);
					   JLabel lw = new JLabel(wire1); 
					   lw.setBounds(NODE.get(i).x.get(k)-3, NODE.get(i).y.get(k)+50,NODE.get(i).x.get(k+1)+4, NODE.get(i).y.get(k+1)+50);
					   panel.add(lw);
					   }
				   }
			   }
			   
		}

	  });
	  DRAW.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton draw = new JButton("DRAW");
			draw.setBounds(50,150,80,30); 
			frame2.add(draw);
			frame2.setBounds(0,0,400,400);
			drawpanel.setBounds(0,0,200,200);
			JLabel Elementname = new JLabel("Element name:");
			Elementname.setBounds(50,50,150,20);
			drawpanel.add(Elementname);
			JTextField e = new JTextField("");
			e.setBounds(50,100,100,20);
			drawpanel.add(e);
		    draw.addActionListener(new ActionListener() {
		    	@Override
		    	public void actionPerformed(ActionEvent arg0) {
		    		double value,value1,value2;
					for(int i=0;i<Element.size();i++) {
						if(Element.get(i).name.equals(e.getText())) { 
							 JFrame fv = new JFrame(); 
							 JFrame fc = new JFrame();
							 JFrame fp = new JFrame();
						   	 JPanel pic = new MyCanvas();
						   	 pic.setLayout(null);
						   	 JPanel pic1 = new MyCanvas1();
						   	 pic1.setLayout(null);
						   	 JPanel pic2 = new MyCanvas2();  
						   	 pic2.setLayout(null);
						   	 fv.setSize(700,700); 
						   	 fc.setSize(700,700);
						   	 fp.setSize(700,700);
						   	double max=0; int cond=0;  //new
						   	max=drawlabel(pic,T,Element.get(i).Voltage);
						   	for(int a=0;a<Element.get(i).Voltage.size();a++)
								if((double)Element.get(i).Voltage.get(a)<0)
									cond=1;
							for(int j=0;j<T/dt;j++) {
							    int k = j;
							    int c = j;
							 while((double)Element.get(i).Voltage.get(c)==(double)Element.get(i).Voltage.get(k)) {
								 //new:
								 if(cond==1)
									 value = Element.get(i).Voltage.get(k)+max*(max-Element.get(i).Voltage.get(k))/2;
								 else
									 value = Element.get(i).Voltage.get(k);
								 MyCanvas.Image.setRGB((int)(50+k*540*dt/T),(int)(Math.round(570-(520/max*value))),Color.BLUE.getBlue());
								 if(k==Element.get(i).Voltage.size()-1)
							    	 break;
								 k++;
							     j=k-1;  
							 }
							}
							double max1=0; int cond1=0;
							max1=drawlabel(pic1,T,Element.get(i).Current);
							for(int a=0;a<Element.get(i).Current.size();a++)
								if((double)Element.get(i).Current.get(a)<0)
									cond1=1; 
							for(int j=0;j<T/dt;j++) {
								int r2=0;
								// if(Element.get(i).Current.get(0)<1 ||Element.get(i).Current.get(Element.get(i).Current.size()-1)<1 ) 
								     //  r2= R(Element.get(i).Current.get(0),Element.get(i).Current.get(Element.get(i).Current.size()-1)); 
								int k = j;
							    int c = j;
							 while((double)Element.get(i).Current.get(c)==(double)Element.get(i).Current.get(k)) {
								 //new:
								     if(cond1==1)
								    	  value1 = Element.get(i).Current.get(k)+max1*(max1-Element.get(i).Current.get(k))/2;
								     else
								    	   value1 = Element.get(i).Current.get(k);
								     MyCanvas1.Image.setRGB((int)(50+k*540*dt/T),(int)(Math.round(570-(520/max1*value1))),Color.GREEN.getGreen());
								 if(k==Element.get(i).Current.size()-1)
							    	 break;
								 k++;
							     j=k-1;  
							 }  
							}
							double max2=0; int cond2=0; //new
						 	for(int a=0;a<Element.get(i).Power.size();a++)
								if((double)Element.get(i).Power.get(a)<0)
									cond2=1;
							max2=drawlabel(pic2,T,Element.get(i).Power);
							for(int j=0;j<T/dt;j++) {
								int k = j;
							    int c = j;
							 while((double)Element.get(i).Power.get(c)==(double)Element.get(i).Power.get(k)) {
								 //new :
								 if(cond2==1)
									 value2 = Element.get(i).Power.get(k)+max2*(max2-Element.get(i).Power.get(k))/2;
								 else
									 value2 = Element.get(i).Power.get(k);
								 MyCanvas2.Image.setRGB((int)(50+k*540*dt/T),(int)(Math.round(570-(520/max2*value2))),Color.RED.getRed());
								 if(k==Element.get(i).Power.size()-1)
							    	 break;
								 k++;
							     j=k-1;  
							 }
							}
						    pic.repaint();
							pic1.repaint();
							pic2.repaint();
							 fv.add("Center",pic);  
						   	 fc.add("Center", pic1);
						   	 
						   	 fp.add("Center", pic2);
							 fv.setVisible(true);
						   	 fc.setVisible(true);
						   	 fp.setVisible(true);
							
						}
						 
					}
		    	}
		    });	
		
			
		frame2.add(drawpanel);	
		frame2.setVisible(true);	
		}
	  });
	   frame1.setBounds(0,0, 800, 800);
	   panel.setBounds(0,0, 800, 800);
	   frame1.add(panel);
	   frame1.setLocationRelativeTo(null);
	   frame1.setVisible(true);
   }
}

