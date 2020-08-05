package project;
import java.util.ArrayList;
import java.util.Scanner;
public class project {
  public abstract class ground{}
  public class Node extends ground{
	  int name;
	  ArrayList<parts> connectedBranch = new ArrayList<parts>();
	  double V;
  }
 public static class parts{
	  Node startNode,endNode;
	  String name;
	  double value;
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
 public static double calculateI(double dT,double dV,parts e,double t,int flagc,Node n) {
	 double I=0,a;
	 if(e instanceof parts.resistor)
		 I =(e.startNode.V- e.endNode.V)/e.value;
	 if(e instanceof parts.Inductor) {
		 for(int k=1;k<=t/dT;k++) 
			((parts.Inductor) e).I += 
				((e.startNode.V-e.endNode.V)*k*dT*dT)/e.value;
		 I = ((parts.Inductor) e).I;
	 }
	 if(e instanceof parts.capacitor) {
		 if(flagc==0) //flagc first turn of calculating = 0
			 I=0;
		 else if(flagc==1)    //flagc second turn of calculating = 1
			     I=e.value*dV/dT;	  
	 }
	 if(e instanceof parts.CurrentIndependent)
		 I=((parts.CurrentIndependent) e).value+((parts.CurrentIndependent) e).A*
		   Math.sin(2*Math.PI*((parts.CurrentIndependent) e).f*t+((parts.CurrentIndependent) e).phase);
	 if(e instanceof parts.CurrentDV)
		 I=(((parts.CurrentDV) e).snode.V-((parts.CurrentDV) e).enode.V)*
				 ((parts.CurrentDV) e).g;
	 if(e instanceof parts.CurrentDI) {
		 if(((parts.CurrentDI) e).related instanceof parts.Inductor) {
				 for(int k=1;k<=t/dT;k++) 
					((parts.Inductor)((parts.CurrentDI) e).related).I += 
							((((parts.Inductor)((parts.CurrentDI) e).related).startNode.V
									-((parts.Inductor)((parts.CurrentDI) e).related).endNode.V)*k*dT*dT)
							            /((parts.CurrentDI) e).related.value;
					 I = ((parts.CurrentDI) e).g*((parts.Inductor)((parts.CurrentDI) e).related).I;
	
		}
	     if(((parts.CurrentDI) e).related instanceof parts.resistor)	
		     I=((parts.CurrentDI) e).g*(((parts.CurrentDI) e).related.startNode.V-
		    	((parts.CurrentDI) e).related.endNode.V)/((parts.CurrentDI) e).related.value;
	     
		if(((parts.CurrentDI) e).related instanceof parts.capacitor) {
			if(e.startNode.equals(((parts.CurrentDI) e).related.startNode)||
			   e.endNode.equals(((parts.CurrentDI) e).related.endNode)||
			   e.endNode.equals(((parts.CurrentDI) e).related.startNode)||
			   e.startNode.equals(((parts.CurrentDI) e).related.endNode)) {
			 if(flagc==0) //flagc first turn of calculating = 0
				 I=0;
			 else if(flagc==1)     //flagc second turn of calculating = 1
				     I=((parts.CurrentDI) e).g*((parts.CurrentDI) e).related.value  *dV/dT;
		    }
			else
				I=((parts.capacitor)((parts.CurrentDI) e).related).I;
	     }
		}
   	 if(e instanceof parts.VoltageIndependent) {
         e.endNode.V=e.startNode.V-(((parts.VoltageIndependent) e).value+((parts.VoltageIndependent) e).A*
      		   Math.sin(2*Math.PI*((parts.VoltageIndependent) e).f*t+((parts.VoltageIndependent) e).phase));		    		 
     }
     if(e instanceof parts.VoltageDV) {
        e.endNode.V=e.startNode.V
        		 -(((parts.VoltageDV)e).snode.V-((parts.VoltageDV)e).enode.V)*((parts.VoltageDV)e).g;
     }
     if(e instanceof parts.VoltageDI) {
	     if(((parts.VoltageDI) e).related instanceof parts.Inductor) {
		    for(int k=1;k<=t/dT;k++) 
				((parts.Inductor)((parts.VoltageDI) e).related).I += 
						((((parts.Inductor)((parts.VoltageDI) e).related).startNode.V
								-((parts.Inductor)((parts.VoltageDI) e).related).endNode.V)*k*dT*dT)
						            /((parts.VoltageDI) e).related.value;
				 a = ((parts.VoltageDI) e).g*((parts.Inductor)((parts.VoltageDI) e).related).I;
				 e.endNode.V=e.startNode.V-a;		 
	     }
	     if(((parts.VoltageDI) e).related instanceof parts.resistor) { 
	    	 a = ((parts.VoltageDI) e).g*(((parts.VoltageDI) e).related.startNode.V-
	 		    	((parts.VoltageDI) e).related.endNode.V)/((parts.VoltageDI) e).related.value;;
			 e.endNode.V=e.startNode.V-a;
	     }
	     if(((parts.VoltageDI) e).related instanceof parts.capacitor) {
				if(e.startNode.equals(((parts.VoltageDI) e).related.startNode)||
				   e.endNode.equals(((parts.VoltageDI) e).related.endNode)||
				   e.endNode.equals(((parts.VoltageDI) e).related.startNode)||
				   e.startNode.equals(((parts.VoltageDI) e).related.endNode)) {
				 if(flagc==0) //flagc first turn of calculating = 0
					 I=0;
				 else if(flagc==1) {     //flagc second turn of calculating = 1
				     a =((parts.VoltageDI) e).g*((parts.VoltageDI) e).related.value *dV/dT;
				     e.endNode.V=e.startNode.V-a;
				 }
			    }
				else {
					a=((parts.capacitor)((parts.VoltageDI) e).related).I;
					e.endNode.V=e.startNode.V-a;
			    }
		     }
     }
     if(e instanceof parts.Diod) {
    	 if(e.endNode.V<e.startNode.V)
    		 I=0;
    	 if(e.endNode.V>=e.startNode.V)
    		 
     }
	return I;	 
 }
 public static void solveEqu(int n,double[][]currentEqu) {
	   double [] currentValue = new double [n];
	        for(int i=0;i<n;i++) {
	        	if(currentEqu[i][i]==0) {
	        		if(i<n-1) {
	        			for(int j=0;j<n+1;j++) {
	        				double temp;
	        				temp=currentEqu[i][j];
	        				currentEqu[i][j]=currentEqu[i+1][j];
	        				currentEqu[i+1][j]=temp;		
	        			}
	        		}	
	        	}
	        	double c = currentEqu[i][i];
			    for(int j=0;j<n+1;j++) {
				    currentEqu[i][j]/=c;
			    }
			    for(int j=0;j<n;j++) {
			    	if(j==i)
			    		continue;
			    	else {
			    	   c = currentEqu[j][i];
				       for(int k=0;k<n+1;k++) {
				           currentEqu[j][k]-=c*currentEqu[i][k];
				       }
				    }
			    }
	        }
	        for(int i=0;i<n;i++)
	        	currentValue[i]=currentEqu[i][n];
  }
  public static void main(String args[]) {
	 Scanner Sc = new Scanner(System.in);
	 double I1 = 0,I2=0;
	//*** tain vabastegi be eleman manba jaryan vabaste be jaryan:
	 String NAME ;
	 NAME=Sc.next(); // name scan shode eleman ke manba behesh vabastast!...
	 ArrayList<Node> node = new ArrayList<Node>();
	 parts.CurrentDI F =  new parts.CurrentDI();
	 for(int i=0;i<node.size();i++)
		 for(int j=0;j<node.get(i).connectedBranch.size();j++)
			 if(node.get(i).connectedBranch.get(j).name.equals(NAME))
				  F.related=node.get(i).connectedBranch.get(j);
	//*** end of determination .....		 
		 
     int n = 0;
     double [][] currentEqu = null;
     // tashkil dastgah:
    //madar moghavemati : flag=0
     int flag=0; int c=0,cflag=0;
     double dt=0,dV=0,dI=0,dT=0;
     ArrayList<Node> NODE = new ArrayList<Node>();
     ArrayList<ArrayList<Node>> union = new ArrayList<ArrayList<Node>>();
     if(flag==0) {
    	 for(int i=0;i<union.size();i++) {
    			 n=NODE.size()-1; //agar ground dar NODE nabashad.
    			 currentEqu = new double [n][n+1];
    		   //currentValue = new double [n];
    		     Node N;
    		     for(int k=0;k<union.get(i).size();k++){
    		     N=union.get(i).get(k);
    			 for(int j=0;j<N.connectedBranch.size();j++) {
    				 if(N.connectedBranch.get(j) instanceof parts.resistor) {
    					 if(N.connectedBranch.get(j).startNode.name!=0)
    					 currentEqu[c][N.connectedBranch.get(j).startNode.name-1]=
    							 1/N.connectedBranch.get(j).value;
    					 if(N.connectedBranch.get(j).endNode.name !=0)
        					 currentEqu[c][N.connectedBranch.get(j).endNode.name-1]=
        							 -1/N.connectedBranch.get(j).value; 
    				 }
    				 if(N.connectedBranch.get(j) instanceof parts.CurrentIndependent) {
    					 if(N.connectedBranch.get(j).startNode.name==N.name)
    					     currentEqu[c][n]=N.connectedBranch.get(j).value;
    					 if(N.connectedBranch.get(j).endNode.name==N.name)
    					     currentEqu[c][n]=-N.connectedBranch.get(j).value;
    					 cflag=1;
    				 }
    				 if(N.connectedBranch.get(j) instanceof parts.CurrentDV) {
    					 if(N.connectedBranch.get(j).startNode.name==N.name) {
    						 currentEqu[c][((parts.CurrentDV)N.connectedBranch.get(j))
    						               .snode.name-1]=-((parts.CurrentDV)N.connectedBranch.get(j)).g;
    						 currentEqu[c][((parts.CurrentDV)N.connectedBranch.get(j))
    						               .enode.name-1]=((parts.CurrentDV)N.connectedBranch.get(j)).g;
    					 }
    					 if(N.connectedBranch.get(j).endNode.name==N.name) {
    						 currentEqu[c][((parts.CurrentDV)N.connectedBranch.get(j))
    						               .snode.name-1]=((parts.CurrentDV)N.connectedBranch.get(j)).g;
    						 currentEqu[c][((parts.CurrentDV)N.connectedBranch.get(j))
    						               .enode.name-1]=-((parts.CurrentDV)N.connectedBranch.get(j)).g;
    					 }
    				 }
    				 if(N.connectedBranch.get(j) instanceof parts.CurrentDI) {
    					 if(N.connectedBranch.get(j).startNode.name==N.name) {
    						 currentEqu[c][((parts.CurrentDI)N.connectedBranch.get(j))
    						               .related.startNode.name-1] =-((parts.CurrentDI)N.connectedBranch.get(j)).g/
    						               ((parts.CurrentDI)N.connectedBranch.get(j)).related.value;
    						 currentEqu[c][((parts.CurrentDI)N.connectedBranch.get(j))
    						               .related.endNode.name-1] =((parts.CurrentDI)N.connectedBranch.get(j)).g/
    						               ((parts.CurrentDI)N.connectedBranch.get(j)).related.value;
    					 }
    					 if(N.connectedBranch.get(j).endNode.name==N.name) {
    						 currentEqu[c][((parts.CurrentDI)N.connectedBranch.get(j))
    						               .related.startNode.name-1] =((parts.CurrentDI)N.connectedBranch.get(j)).g/
    						               ((parts.CurrentDI)N.connectedBranch.get(j)).related.value;
    						 currentEqu[c][((parts.CurrentDI)N.connectedBranch.get(j))
    						               .related.endNode.name-1] =-((parts.CurrentDI)N.connectedBranch.get(j)).g/
    						               ((parts.CurrentDI)N.connectedBranch.get(j)).related.value;
    					 } 
    				 }
    				 if(N.connectedBranch.get(j) instanceof parts.VoltageIndependent) {
    					 currentEqu[c][N.connectedBranch.get(j).startNode.name-1]+=1;
    					 currentEqu[c][N.connectedBranch.get(j).endNode.name-1]+=-1;
    					 if(N.equals(N.connectedBranch.get(j).startNode))
    					    currentEqu[c][n]+=N.connectedBranch.get(j).value;
    					 cflag=1;
    				 }
    				 if(N.connectedBranch.get(j) instanceof parts.VoltageDV) {
    					 currentEqu[c][N.connectedBranch.get(j).startNode.name-1]+=1;
    					 currentEqu[c][N.connectedBranch.get(j).endNode.name-1]+=-1;
    					 currentEqu[c][((parts.VoltageDV)N.connectedBranch.get(j)).snode.name-1]+=-((parts.VoltageDV)N.connectedBranch.get(j)).g;
    					 currentEqu[c][((parts.VoltageDV)N.connectedBranch.get(j)).enode.name-1]+=+((parts.VoltageDV)N.connectedBranch.get(j)).g;
    				 }
    				 if(N.connectedBranch.get(j) instanceof parts.VoltageDI) {
    					 currentEqu[c][N.connectedBranch.get(j).startNode.name-1]+=1;
    					 currentEqu[c][N.connectedBranch.get(j).endNode.name-1]+=-1;
    					 currentEqu[c][((parts.VoltageDI)N.connectedBranch.get(j)).related.startNode.name-1]+=
    							 -((parts.VoltageDI)N.connectedBranch.get(j)).g*((parts.VoltageDI)N.connectedBranch.get(j)).related.value;
    					 currentEqu[c][((parts.VoltageDI)N.connectedBranch.get(j)).related.endNode.name-1]+=
    							 +((parts.VoltageDI)N.connectedBranch.get(j)).g*((parts.VoltageDI)N.connectedBranch.get(j)).related.value;
    				 }
    				 if(cflag==0)
    					 currentEqu[c][n]=0; 
    			 }
    		    }
    			 c++;
    	 }
    	 solveEqu(n,currentEqu);
     } 
     else if(flag==1) {
    	 Node N;
    	 int t=0;
    	 while(t<dT) {
    		 I1=0; I2=0;
    		 for(int i=0;i<union.size();i++) {
    			 for(int k=0;k<union.get(i).size();k++) {
        		     N=union.get(i).get(k);
    			     for(int j=0;j<N.connectedBranch.size();j++) {
    			
    			    	 
    				         I1+=calculateI(dT, dV, N.connectedBranch.get(j), t, 0,N);
    				     }
    			     }
    			 for(int k=0;k<union.get(i).size();k++) {
        		     N=union.get(i).get(k);
    			     N.V+=dV;
    			     for(int j=0;j<N.connectedBranch.size();j++) {
    			    	/* if(N.connectedBranch.get(j) instanceof parts.VoltageDependent) {
    			    		 if(N.equals(N.connectedBranch.get(j).startNode))
    			    	         N.connectedBranch.get(j).endNode.V=N.connectedBranch.get(j).startNode.V-N.connectedBranch.get(j).value;
    			    		 if(N.equals(N.connectedBranch.get(j).endNode))
    			    			 N.connectedBranch.get(j).startNode.V=N.connectedBranch.get(j).endNode.V+N.connectedBranch.get(j).value;
    			    	     }
    			    	 else*/
    				         I2+=calculateI(dT, dV, N.connectedBranch.get(j), t, 1,N);
    				     }
    			 }
    			 for(int k=0;k<union.get(i).size();k++) {
        		     N=union.get(i).get(k);
    			     N.V+=(I2-I1)*dV/dI;
    			 }
    			 
    		 } 
    		 t+=dT; 
    	 }
     }
     
     
     
 }
}