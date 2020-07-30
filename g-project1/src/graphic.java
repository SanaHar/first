
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
public class graphic {
	 public static abstract class ground{}
	  public static class Node extends ground{
		  int name;
		  ArrayList<parts> connectedBranch = new ArrayList<parts>();
		  double V;
		  ArrayList<Integer> x = new ArrayList<Integer>();
		  ArrayList<Integer> y = new ArrayList<Integer>();
		  int cond =0;
	  }
	 public static class parts{
		  Node startNode,endNode;
		  String name;
		  double value;
		  int draw=0;
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
		 int I=0,J=0,cond=0;
		 if((int)e.endNode.y.get(0)!=(int)e.startNode.y.get(0)) { 
				if(n.equals(e.startNode)) { 
					for(int j=0;j<e.endNode.x.size();j++) 
						if((int)n.x.get(a)==(int)e.endNode.x.get(j)) {
							I=a;
							J=j;
						    label.setBounds(e.startNode.x.get(I),e.startNode.y.get(I),e.endNode.x.get(J),e.endNode.y.get(J));
						    /* System.out.println( e.startNode.x.get(I));
							 System.out.println(e.startNode.y.get(I));
							 System.out.println( e.endNode.x.get(J));
							 System.out.println( e.endNode.y.get(J));
							 System.out.println( e.name);*/
						    break;
						}
				}
				if(n.equals(e.endNode)) { 
					for(int j=0;j<e.startNode.x.size();j++) 
						if((int)n.x.get(a)==(int)e.startNode.x.get(j)) {
							I=a;
							J=j;
							label.setBounds(e.startNode.x.get(J),e.startNode.y.get(J),e.endNode.x.get(I),e.endNode.y.get(I));
							  System.out.println( e.startNode.x.get(J));
								 System.out.println(e.startNode.y.get(J));
								 System.out.println( e.endNode.x.get(I));
								 System.out.println( e.endNode.y.get(I));
							break;
						}
				}
					
		 }
		 else { 
			 if(n.equals(e.startNode)) { 
				 cond=0;
				 for(int i=0;i<n.x.size();i++)
					 for(int j=0;j<e.endNode.x.size();j++)
						 if((int)n.x.get(i)>(int)e.endNode.x.get(j)) 
							 cond=1;	 
				 if(cond==1)
					 label.setBounds(e.startNode.x.get(0),e.startNode.y.get(0)-30,e.endNode.x.get(e.endNode.x.size()-1),e.endNode.y.get(e.endNode.x.size()-1)-30);
				 else
				     label.setBounds(e.startNode.x.get(n.x.size()-1)+10,e.startNode.y.get(n.x.size()-1)+33,e.endNode.x.get(0)+10,e.endNode.y.get(0)+33);
				/* System.out.println(e.startNode.x.get(n.x.size()-1));
				 System.out.println(e.startNode.y.get(n.x.size()-1));
				 System.out.println(e.endNode.x.get(0));
				 System.out.println(e.endNode.y.get(0));*/
			 }
			 if(n.equals(e.endNode)) {
				 cond=0;
				 for(int i=0;i<n.x.size();i++)
					 for(int j=0;j<e.startNode.x.size();j++)
						 if((int)n.x.get(i)>(int)e.startNode.x.get(j)) 
							 cond=1;
							 
				 if(cond==1)
					 label.setBounds(e.startNode.x.get(e.startNode.x.size()-1),e.startNode.y.get(e.startNode.x.size()-1)-30,e.endNode.x.get(0),e.endNode.y.get(0)-30);
				 else
					 label.setBounds(e.startNode.x.get(0),e.startNode.y.get(0),e.endNode.x.get(n.x.size()-1),e.endNode.y.get(n.x.size()-1));
				  /*System.out.println(e.startNode.x.get(0));
				 System.out.println(e.startNode.y.get(0));
				 System.out.println(e.endNode.x.get(n.x.size()-1));
				 System.out.println(e.endNode.y.get(n.x.size()-1));*/
			 }
		 }
		 panel.add(label);
       
	 }
	 public static ImageIcon scaling(ImageIcon im, int condition) {
		 // condition=1 : icon should set vertically
		 // condition=0 : icon should set horizontally
		   Image scaled = im.getImage();
		   Image modified;
		   if(condition==1)
		       modified = scaled.getScaledInstance(50, 106, java.awt.Image.SCALE_SMOOTH);
		   else 
			   modified = scaled.getScaledInstance(80, 90 , java.awt.Image.SCALE_SMOOTH);
		   im = new ImageIcon(modified);
		   return im;
	 }
   public static void main(String []args) {
	   JFrame frame1 = new JFrame();
	   JPanel panel = new JPanel();
	   panel.setLayout(null);
	   JButton RUN = new JButton("RUN");
	   RUN.setBounds(200, 200, 100, 50);
	   panel.add(RUN);
	   JLabel t1  = new JLabel("press RUN to see the circuit image");
	   t1.setBounds(200, 150, 250, 50);
	   panel.add(t1);
	   ArrayList <Node> NODE = new ArrayList<Node>();
	   int x=100,Y = -1;
	   parts.resistor r = new parts.resistor();
	   r.name="r";
	   parts.capacitor C = new parts.capacitor();
	   C.name="c";
	   parts.Inductor l = new parts.Inductor();
	   l.name="l";
	   parts.capacitor ci = new parts.capacitor();
	   ci.name="ci";
	   parts. Diod D = new parts. Diod();
	   D.name="D";
	   Node a = new Node();
	   a.name = 0;
	   Node b = new Node();
	   b.name =1;
	   Node c = new Node();
	   c.name =2;
	   //Node d = new Node();
	  // d.name =3;
	   //Node e = new Node();
	   r.startNode=a; r.endNode=b; 
	   C.startNode=a; C.endNode=b; 
	   l.startNode=b; ci.endNode=c; 
	   ci.startNode=a; l.endNode=c;
	  // D.startNode=d; D.endNode=c;
	   a.connectedBranch.add(r);
	   a.connectedBranch.add(C);
	   a.connectedBranch.add(ci);
	   b.connectedBranch.add(l);
	   b.connectedBranch.add(C);
	   b.connectedBranch.add(r);
	  // c.connectedBranch.add(C);
	   //e.connectedBranch.add(D);
	   c.connectedBranch.add(l);
	   c.connectedBranch.add(ci);
	  // d.connectedBranch.add(D);
	  // d.connectedBranch.add(ci);
	   NODE.add(a);
	   NODE.add(b);
	   NODE.add(c);
	  // NODE.add(d);
	  // NODE.add(e);
	   int count = 0;
	   for(int i=0;i<NODE.size();i++) {
		   count=0;
		   for(int j=0;j<NODE.get(i).connectedBranch.size();j++) {
			   if(((int)NODE.get(i).connectedBranch.get(j).startNode.name==0 ||
					   (int)NODE.get(i).connectedBranch.get(j).endNode.name==0))
				   count++;
			  // System.out.println((int)NODE.get(i).connectedBranch.get(j).startNode.name);
			  // System.out.println((int)NODE.get(i).connectedBranch.get(j).endNode.name);
			   }
		   if(count>1)
			   NODE.get(i).cond = count;
	   }
	   for(int i=0;i<NODE.size();i++) {
		   if(i%6==1 || i==0)
			   x=200;
		   if(NODE.get(i).cond>0) {
		     for(int j=0;j<NODE.get(i).cond;j++) {
			   if(x+50*j==200) 
					  Y++; 
				   NODE.get(i).y.add((int) 400-Y*100);
				   NODE.get(i).x.add((int) (x+50*j));
				  // System.out.println( NODE.get(i).x.get(j));
				 //  System.out.println( NODE.get(i).y.get(j));
				  // System.out.println( NODE.get(i).name);

		     }
	       }
		   else {
			   if(x==200) 
					  Y++; 
			   NODE.get(i).y.add((int) 400-Y*100);
			   NODE.get(i).x.add((int) (x));
			     // System.out.println( NODE.get(i).x.get(0));
				 // System.out.println( NODE.get(i).y.get(0));
		   }   
		   x += 50*(NODE.get(i).x.size());

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
							   ImageIcon VoltageD = new ImageIcon("vd2.png");
							   VoltageD = scaling( VoltageD,1);
							   JLabel lVD2 = new JLabel(VoltageD);
							   ImageIcon VoltageD2 = new ImageIcon("vd3.png");
							   VoltageD2 = scaling( VoltageD2,1);
							   JLabel lVD3 = new JLabel(VoltageD2);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVD2,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVD3,NODE.get(i),j);
						   }
						   if(NODE.get(i).connectedBranch.get(j) instanceof parts.VoltageIndependent) {
							   ImageIcon VoltageIndependent = new ImageIcon("vi2.png");
							   VoltageIndependent = scaling(VoltageIndependent,1);
							   JLabel lVI2 = new JLabel(VoltageIndependent);
							   ImageIcon VoltageIndependent2 = new ImageIcon("vi3.png"); 
							   VoltageIndependent2 = scaling(VoltageIndependent2,1);
							   JLabel lVI3 = new JLabel(VoltageIndependent2);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).startNode))
							      draw(NODE.get(i).connectedBranch.get(j),panel,lVI2,NODE.get(i),j);
							   if(NODE.get(i).equals(NODE.get(i).connectedBranch.get(j).endNode))
								  draw(NODE.get(i).connectedBranch.get(j),panel,lVI3,NODE.get(i),j);
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
					   }
					   NODE.get(i).connectedBranch.get(j).draw=1;
					   } 
					 
				   }
				   for(int k=0;k<NODE.get(i).x.size()-1;k++) {
					   ImageIcon wire1 = new ImageIcon("w.png");
					   Image scaled = wire1.getImage();
					   Image modified = scaled.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
					   wire1 = new ImageIcon(modified);
					   JLabel lw = new JLabel(wire1);
					   lw.setBounds(NODE.get(i).x.get(k)+10, NODE.get(i).y.get(k),NODE.get(i).x.get(k+1)+7, NODE.get(i).y.get(k+1));
					   panel.add(lw);
					   // System.out.println( NODE.get(i).y.get(k));
				   }
			   }
			   
		}

	  });
	   frame1.setBounds(0,0, 800, 800);
	   panel.setBounds(0,0, 800, 800);
	   frame1.add(panel);
	   frame1.setLocationRelativeTo(null);
	   frame1.setVisible(true);
   }
}
