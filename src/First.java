import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import com.sun.org.apache.regexp.internal.RE;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.*;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.*;
class Branch{
    public String name;
    public int NodeOut,NodeIn;
    public double value;
    double[] i,v,p;
}
class Resistor extends Branch{
    Resistor(){
        v=new double[10];
        i=new double[10];
    }
}
class Capacitor extends Branch{
    Capacitor(){
        v=new double[10];
        i=new double[10];
    }
}
class Inductor extends Branch{
    Inductor(){
        v=new double[10];
        i=new double[10];
    }
}
class CurrentSource extends Branch{
double amp,freq,phase;
}
class CurrentDI extends Branch{///i j c1 a ///current
    String deEl;
    Branch deElm;
    double gain;
}
class CurrentDV extends Branch{///i j k m a ///current
    int deNodeOut,deNodeIn;
    double gain;
}
class VoltageSource extends Branch{
    double amp,freq,phase;
}
class VoltageDI extends Branch{///i j c1 a ///voltage
    String deEl;
    Branch deElm;
    double gain;
}
class VoltageDV extends Branch{///i j k m a ///voltage
    int deNodeOut,deNodeIn;
    double gain;
}
class D extends Branch{
}
class Node{
    int union;
    double[] v,i;
    ArrayList<Branch> branches;
    Queue<Integer> neighbors;
    Node(int k)
    {
        v=new double[k];
        i=new double[k];
        branches=new ArrayList<Branch>();
        neighbors=new LinkedList<Integer>();
    }
}

class ReadTxt{
    double dv,di,dt=0,finalTime=0;
    int node=0;
    int CurrentCounter=0,VoltageCounter=0,r=0,c=0,l=0,iv=0,vi=0,vv=0,ii=0,d=0;
    int[][]n;
    ArrayList<Branch> Branch;
    CurrentSource[] I=new CurrentSource[5];
    VoltageSource[] V=new VoltageSource[5];
    Resistor[] R=new Resistor[5];
    Capacitor[] C=new Capacitor[5];
    Inductor[] L=new Inductor[5];
    CurrentDV[] CurrentDV=new CurrentDV[5];
    CurrentDI[] CurrentDI=new CurrentDI[5];
    VoltageDV[] VoltageDV=new VoltageDV[5];
    VoltageDI[] VoltageDI=new VoltageDI[5];
    D[] D=new D[5];
    Node[] Node;
    HashMap<Integer,ArrayList<Integer>> unions;
    BufferedReader bf;
    ReadTxt(String filename) throws IOException {
        bf=new BufferedReader(new FileReader(filename));
        Branch=new ArrayList<Branch>();
    }
       public void createElements() throws IOException {
       String line=bf.readLine();
       int lineCounter=1,max,Error=0;
       n=new int[15][15];
       for(int i=0;i<15;i++){for(int j=0;j<15;j++)n[i][j]=0;}
       String[] lineP;
       while(line!=null){
           line=line.trim();
           lineP=line.split("\\s+");
              if(line.matches("(I)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+[pnumKMG]?)(\\s+)(\\d+[pnumKMG]?)(\\s+)(\\d+[pnumKMG]?)(\\s+)(\\d+)")){
               I[CurrentCounter]=new CurrentSource();
               I[CurrentCounter].name=lineP[0];
               I[CurrentCounter].NodeIn=Integer.parseInt(lineP[1]);
               I[CurrentCounter].NodeOut=Integer.parseInt(lineP[2]);
               I[CurrentCounter].value=readNum(lineP[3]);
               I[CurrentCounter].amp=readNum(lineP[4]);
               I[CurrentCounter].freq=readNum(lineP[5]);
               I[CurrentCounter].phase=readNum(lineP[6]);
               n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
               n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
               max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
               Branch.add(I[CurrentCounter]);
               if(max>node)node=max;
              CurrentCounter++;}
           else if(line.matches("(V)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+[pnumKMG]?)(\\s+)(\\d+[pnumKMG]?)(\\s+)(\\d+[pnumKMG]?)(\\s+)(\\d+)")){
                   V[VoltageCounter]=new VoltageSource();
                   V[VoltageCounter].name=lineP[0];
                   V[VoltageCounter].NodeIn=Integer.parseInt(lineP[1]);
                   V[VoltageCounter].NodeOut=Integer.parseInt(lineP[2]);
                   V[VoltageCounter].value=readNum(lineP[3]);
                   V[VoltageCounter].amp=readNum(lineP[4]);
                   V[VoltageCounter].freq=readNum(lineP[5]);
                   V[VoltageCounter].phase=readNum(lineP[6]);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(V[VoltageCounter]);
                  if(max>node)node=max;
                   VoltageCounter++;}
           else if(line.matches("(R)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+[pnumKMG]?)")){
                   R[r]=new Resistor();
                   R[r].name=lineP[0];
                   R[r].NodeIn=Integer.parseInt(lineP[1]);
                   R[r].NodeOut=Integer.parseInt(lineP[2]);
                   R[r].value=readNum(lineP[3]);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(R[r]);
                  if(max>node)node=max;
                   r++;}
           else if(line.matches("(C)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+[pnumKMG]?)")){
                   C[c]=new Capacitor();
                   C[c].name=lineP[0];
                   C[c].NodeIn=Integer.parseInt(lineP[1]);
                   C[c].NodeOut=Integer.parseInt(lineP[2]);
                   C[c].value=readNum(lineP[3]);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(C[c]);
                  if(max>node)node=max;
                   c++;}
           else if(line.matches("(L)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+[pnumKMG]?)")){
                  L[l] =new Inductor();
                  L[l].name=lineP[0];
                  L[l].NodeIn=Integer.parseInt(lineP[1]);
                  L[l].NodeOut=Integer.parseInt(lineP[2]);
                  L[l].value=readNum(lineP[3]);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(L[l]);
                  if(max>node)node=max;
                   l++;}
           //current to voltage
           else if(line.matches("(G)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+[pnumKMG]?)")){
                   CurrentDV[iv] =new CurrentDV();
                   CurrentDV[iv].name=lineP[0];
                   CurrentDV[iv].NodeIn=Integer.parseInt(lineP[1]);
                   CurrentDV[iv].NodeOut=Integer.parseInt(lineP[2]);
                   CurrentDV[iv].deNodeIn=Integer.parseInt(lineP[3]);
                   CurrentDV[iv].deNodeOut=Integer.parseInt(lineP[4]);
                   CurrentDV[iv].gain=readNum(lineP[5]);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(CurrentDV[iv]);
                  if(max>node)node=max;
                   iv++;}
           ///voltage to voltage
           else if(line.matches("(E)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+[pnumKMG]?)")){
                  VoltageDV[vv] =new VoltageDV();
                  VoltageDV[vv].name=lineP[0];
                  VoltageDV[vv].NodeIn=Integer.parseInt(lineP[1]);
                  VoltageDV[vv].NodeOut=Integer.parseInt(lineP[2]);
                  VoltageDV[vv].deNodeIn=Integer.parseInt(lineP[3]);
                  VoltageDV[vv].deNodeOut=Integer.parseInt(lineP[4]);
                  VoltageDV[vv].gain=readNum(lineP[5]);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(VoltageDV[vv]);
                  if(max>node)node=max;
                   vv++;}
            //current to current
           else if(line.matches("(F)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)([RLC])([0-9a-z]+)(\\s+)(\\d+[pnumKMG]?)")){
                   CurrentDI[ii] =new CurrentDI();
                   CurrentDI[ii].name=lineP[0];
                   CurrentDI[ii].NodeIn=Integer.parseInt(lineP[1]);
                   CurrentDI[ii].NodeOut=Integer.parseInt(lineP[2]);
                   CurrentDI[ii].deEl=lineP[3];
                   CurrentDI[ii].gain=readNum(lineP[4]);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(CurrentDI[ii]);
                  if(max>node)node=max;
                   ii++;}
           //voltage to current
           else if(line.matches("(H)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)([RLC])([0-9a-z]+)(\\s+)(\\d+[pnumKMG]?)")){
                   VoltageDI[vi] =new VoltageDI();
                   VoltageDI[vi].name=lineP[0];
                   VoltageDI[vi].NodeIn=Integer.parseInt(lineP[1]);
                   VoltageDI[vi].NodeOut=Integer.parseInt(lineP[2]);
                   VoltageDI[vi].deEl=lineP[3];
                   VoltageDI[vi].gain=readNum(lineP[4]);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  Branch.add(VoltageDI[vi]);
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  if(max>node)node=max;
                   vi++;}
           else if(line.matches("(D)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(1)")){
                   D[d] =new D();
                   D[d].name=lineP[0];
                   D[d].NodeIn=Integer.parseInt(lineP[1]);
                   D[d].NodeOut=Integer.parseInt(lineP[2]);
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(D[d]);
                  if(max>node)node=max;
                   d++;}
           else if(line.matches("d[VIT](\\s+)(\\d+)[pnumKMG]?")){
               if(lineP[0].charAt(1)=='V'){dv=readNum(lineP[1]);}
               else if(lineP[0].charAt(1)=='I'){di=readNum(lineP[1]);}
               else if(lineP[0].charAt(1)=='T'){dt=readNum(lineP[1]);}
           Error++;}
           else if(line.matches(".tran(\\s+)(\\d+[pnumKMG]?)")){
               finalTime=readNum(lineP[1]);
               Error++;
           }
           else if(lineP[0].charAt(0)!='*'){
               System.out.println(lineCounter);
               System.exit(0);
              }
           lineCounter++;
           line=bf.readLine();
       }
       bf.close();
       if(Error!=4){System.out.println("-1");System.exit(0);}
       Node=new Node[node+1];
           for(int i=0;i<=node;i++){
               Node[i]=new Node((int) (finalTime/dt)+1);
               Node[i].union=i;
               for(int j=0;j<=node;j++){
                   if(n[i][j]!=0){Node[i].neighbors.add(j);}
               }
           }
           for(Branch i:Branch){
               Node[i.NodeOut].branches.add(i);
               Node[i.NodeIn].branches.add(i);
           }
           for(int i=0;i<ii;i++){
               for(Branch b:Branch){
                   if(CurrentDI[i].deEl.equals(b.name))
                       CurrentDI[i].deElm=b;
               }
           }
           for(int i=0;i<vi;i++){
               for(Branch b:Branch){
                   if(VoltageDI[i].deEl.equals(b.name))
                       VoltageDI[i].deElm=b;
               }
           }

    }
    public ArrayList<Integer> linkedNodes(int m){
        ArrayList<Integer> linkedNodes=new ArrayList<>();
        for(Branch branch:Branch){
            if((branch instanceof VoltageSource)||(branch instanceof VoltageDV)||(branch instanceof VoltageDI)){
        if(branch.NodeOut==m)
            linkedNodes.add(branch.NodeIn);
        else if(branch.NodeIn==m)
            linkedNodes.add(branch.NodeOut);
        }}
        return linkedNodes;
    }
   public void createUnions(int m){
        for(int i=0;i<=node;i++){
                if(!linkedNodes(i).isEmpty()){
                    for(Integer k:linkedNodes(i)){
                        Node[k].union=Math.min(Node[k].union,Node[i].union);
                    }
                }
            }

        unions=new HashMap<>();
        ArrayList<Integer> unionsNum=new ArrayList<>();
        int j=0;
        for(int i=0;i<=node;i++){if(!unionsNum.contains(Node[i].union))unionsNum.add(Node[i].union);}
        ArrayList<Integer>[] sameUnion=new ArrayList[unionsNum.size()];
        for(Integer i:unionsNum){
            sameUnion[j]=new ArrayList<Integer>();
            for(int k=0;k<=node;k++){
                if(Node[k].union==i){
                    sameUnion[j].add(k);
                }
            }
            unions.put(i,sameUnion[j]);
            j++;
        }
        if(m==0){
            for(Integer i:unions.keySet()){
                Node[i].v[0]=0;
                if(unions.get(i).size()!=1)
                {for(Branch branch:Branch){
                    if(((branch instanceof VoltageSource)||(branch instanceof VoltageDV)||(branch instanceof VoltageDI))){
                        if(branch.NodeIn==i)
                            Node[branch.NodeOut].v[0]=-branch.value;
                        else if(branch.NodeOut==i)
                            Node[branch.NodeIn].v[0]=branch.value;
                    }
                }}
            }
        }
        else{
        for(Integer i:unions.keySet()){
            Node[i].v[m]=Node[i].v[m-1];
            if(unions.get(i).size()!=1)
            {for(Branch branch:Branch){
                if(((branch instanceof VoltageSource)||(branch instanceof VoltageDV)||(branch instanceof VoltageDI))){
                    if(branch.NodeIn==i)
                    Node[branch.NodeOut].v[m]=Node[i].v[m]-branch.value;
                    else if(branch.NodeOut==i)
                    Node[branch.NodeIn].v[m]=Node[i].v[m]+branch.value;
                }
            }}
        } }}

    public double readNum(String num){
    double numR=Double.parseDouble(num.replaceAll("\\D",""));
    if(num.matches("\\d+p")){return Math.pow(10,-12);}
    else if(num.matches("\\d+n")){return numR*Math.pow(10,-9);}
    else if(num.matches("\\d+u")){return numR*Math.pow(10,-6);}
    else if(num.matches("\\d+m")){return numR*Math.pow(10,-3);}
    else if(num.matches("\\d+K")){return numR*Math.pow(10,3);}
    else if(num.matches("\\d+M")){return numR*Math.pow(10,6);}
    else if(num.matches("\\d+G")){return numR*Math.pow(10,9);}
return numR; }
}
      class SolveCircuit{
          ReadTxt data;
          SolveCircuit(String FileName) throws IOException {
              data=new ReadTxt(FileName);
              data.createElements();
          }
          public void solveRLC(){
              Node N;
              double I1,I2z,I2k, t=0;
              int c= (int) (data.finalTime/data.dt),m=1;
              ///m: step number m=1: 0-dt m=2:dt-2dt
              data.createUnions(0);//initialize nodes' voltage
              while(t<data.finalTime&&m<=c){
                  updateV(data.dt, t,m);
                  data.createUnions(m);
                  for(Integer i:data.unions.keySet()){
                      if(i!=0){
                      I1=0; I2z=0; I2k=0;
                      for(Integer k:data.unions.get(i)){
                          N=data.Node[k];
                          for(Branch branch:N.branches){
                              I1+=calculateI(data.dt, data.dv, branch, t,m,k);
                          }
                      }
                      for(Integer k:data.unions.get(i)){///ziadesho hesab mikonim
                          N=data.Node[k];
                          N.v[m-1]+=data.dv;
                          for(Branch branch:N.branches) {
                              I2z+=calculateI(data.dt, data.dv, branch, t,m,k);
                          }
                          N.v[m-1]-=data.dv;
                      }
                          for(Integer k:data.unions.get(i)){//kamesho hesab mikonim
                              N=data.Node[k];
                              N.v[m-1]-=data.dv;
                              for(Branch branch:N.branches) {
                                  I2k+=calculateI(data.dt, data.dv, branch, t,m,k);
                              }
                              N.v[m-1]+=data.dv;
                          }
                      for(Integer k:data.unions.get(i)) {
                          N=data.Node[k];
                          N.v[m]+=Math.min(Math.abs(I2k-I1),Math.abs(I2z-I1))*data.dv/data.di;

                      }

                  }
              }
                  t+=data.dt;m++;}
          }
      public void updateV(double dT,double t,int m){
              for(Branch e:data.Branch) {
                  if (e instanceof VoltageSource) {
                      if(((VoltageSource) e).amp!=0)
                      e.value = (((VoltageSource) e).value + ((VoltageSource) e).amp *
                              Math.sin(2 * Math.PI * ((VoltageSource) e).freq * (t) + ((VoltageSource) e).phase));
                  }
                  else if (e instanceof VoltageDV) {
                      e.value = (data.Node[((VoltageDV) e).deNodeIn].v[m - 1] - data.Node[((VoltageDV) e).deNodeOut].v[m - 1]) * ((VoltageDV) e).gain;
                  }
                  else if (e instanceof VoltageDI) {
                      if (((VoltageDI) e).deElm instanceof Inductor) {
                          if (m == 1) e.value = 0;
                          else {
                              for (int k = 1; k < m; k++)
                                  e.value += (data.Node[((Inductor) ((VoltageDI) e).deElm).NodeIn].v[m]
                                          - data.Node[((Inductor) ((VoltageDI) e).deElm).NodeOut].v[m]);
                              e.value = e.value * dT / ((VoltageDI) e).deElm.value;
                          }
                      }
                  else if (((VoltageDI) e).deElm instanceof Resistor) {
                          e.value = ((VoltageDI) e).gain * (data.Node[((VoltageDI) e).deElm.NodeIn].v[m - 1] -
                                  data.Node[((VoltageDI) e).deElm.NodeOut].v[m - 1]) / ((VoltageDI) e).deElm.value;
                          ;

                      }
                  else if (((VoltageDI) e).deElm instanceof Capacitor) {
                          if (m == 1) e.value = 0;
                          else {
                              e.value = (((VoltageDI) e).deElm.value / dT) * ((data.Node[((VoltageDI) e).deElm.NodeIn].v[m - 1] - data.Node[((VoltageDI) e).deElm.NodeOut].v[m - 1])
                                      - (data.Node[((VoltageDI) e).deElm.NodeIn].v[m - 2] - data.Node[((VoltageDI) e).deElm.NodeOut].v[m - 2])) * ((VoltageDI) e).gain;

                          }
                      }
                  }
              }
          }
    public double calculateI(double dT,double dV,Branch e,double t,int m,int nodeNum) {
        double I=0;
        int aN;
        if(e.NodeOut==nodeNum)aN=e.NodeIn;
        else aN=e.NodeOut;

        if(e instanceof Resistor){
            I=(data.Node[aN].v[m-1]-data.Node[nodeNum].v[m-1])/e.value;
        }
        if(e instanceof Inductor) {
            if(m==1)I=0;
            for(int k=1;k<m;k++)// Inductor.i[0]==0
                I+=(data.Node[aN].v[k]-data.Node[nodeNum].v[k]);
            I =I*dT/e.value;
        }
        if(e instanceof Capacitor) {
            if(m==1)I=0;
            else {
             double V= ((data.Node[aN].v[m - 1] - data.Node[nodeNum].v[m - 1]) - (data.Node[aN].v[m - 2] - data.Node[nodeNum].v[m - 2]));
                I = (e.value / dT) * V;


            }
           System.out.println(data.Node[aN].v[m-1]+" "+data.Node[nodeNum].v[m-1]+" "+I);
        }
        if(e instanceof CurrentSource){
            I=((CurrentSource) e).value+((CurrentSource) e).amp*Math.sin(2*Math.PI*((CurrentSource) e).freq*t+((CurrentSource) e).phase);
            if(e.NodeOut==nodeNum)I=-1*I;
        }
        if(e instanceof CurrentDV){
            I=data.Node[((CurrentDV) e).deNodeIn].v[m-1]-data.Node[((CurrentDV) e).deNodeOut].v[m-1]*((CurrentDV) e).gain;
            if(e.NodeOut==nodeNum)I=-1*I;
        }
        if(e instanceof CurrentDI) {
            if(((CurrentDI) e).deElm instanceof Inductor) {
                if(m==1)I=0;
               for(int k=1;k<m;k++)
                I+=(data.Node[((CurrentDI) e).deElm.NodeIn].v[k]-data.Node[((CurrentDI) e).deElm.NodeOut].v[k]);
                I=(I*dT/((CurrentDI) e).deElm.value)*((CurrentDI) e).gain;
                if(e.NodeOut==nodeNum)I=-1*I;
            }
            if(((CurrentDI) e).deElm instanceof Resistor){
                I=((CurrentDI) e).gain*(data.Node[((CurrentDI) e).deElm.NodeIn].v[m-1]-
                        data.Node[((CurrentDI) e).deElm.NodeOut].v[m-1])/((CurrentDI) e).deElm.value;
                if(e.NodeOut==nodeNum)I=-1*I;
            }

            if(((CurrentDI) e).deElm instanceof Capacitor) {
                if(m==1)I=0;
                else{
                    I=data.Node[(((CurrentDI) e).deElm).NodeIn].v[m-1]-data.Node[(((CurrentDI) e).deElm).NodeOut].v[m-1]
                            -(data.Node[(((CurrentDI) e).deElm).NodeIn].v[m-2]-data.Node[(((CurrentDI) e).deElm).NodeOut].v[m-2])
                    *((CurrentDI) e).deElm.value*((CurrentDI) e).gain/dT;
                    if(nodeNum==e.NodeOut)I=-1*I;
                }
            }

        }
        if(e instanceof D) {
            VoltageSource dv=new VoltageSource();
            if(data.Node[e.NodeOut].v[m]<data.Node[e.NodeIn].v[m]){
            I=0;
            data.Branch.add(dv);}
            if(data.Node[e.NodeOut].v[m]>=data.Node[e.NodeIn].v[m]){}
            dv.amp=0;
            dv.value=0;
            dv.NodeOut=e.NodeOut;
            dv.NodeIn=e.NodeIn;
            data.Branch.add(dv);
        }
        return I;
    }
    public double[] solveMatrix(double[][] a,int n){
        int i, j, k = 0, c;
        double[] result=new double[n];
        for (i = 0; i < n; i++){
            if (a[i][i] == 0){
                c = 1;
                while ((i + c) < n && a[i + c][i] == 0) c++;
                if ((i + c) == n) {
                    break;
                }
                for (j = i, k = 0; k <= n; k++){
                    double  temp =a[j][k];
                    a[j][k] = a[j+c][k];
                    a[j+c][k] = temp;}}
            for (j = 0; j < n; j++){
                if (i != j){
                    double p = a[j][i] / a[i][i];
                    for (k = 0; k <= n; k++)
                        a[j][k] = a[j][k] - (a[i][k]) * p;}
            }
        }
        for (i = 0; i < n; i++)
            result[i]=a[i][n] / a[i][i];
        return result;
    }
    public double[] solveResistanceCircuit() {
        int n=data.node;
        double[][] G=new double[n][n+1];
        for(int i=0;i<n;i++){
            for(int j=0;j<=n;j++)G[i][j]=0;
        }
        int i,j;
        for(Branch branch:data.Branch){
            i=branch.NodeIn-1;j=branch.NodeOut-1;
            if(branch instanceof Resistor){
                if(j==-1)
                    G[i][i]+=1/((Resistor) branch).value;

                else if(i==-1)
                    G[j][j]+=1/((Resistor) branch).value;

                else{
                    G[i][i]+=1/((Resistor) branch).value;

                    G[j][j]+=1/((Resistor) branch).value;

                    G[i][j]-=1/((Resistor) branch).value;

                    G[j][i]-=1/((Resistor) branch).value;}
            }
            else if(branch instanceof CurrentSource){
                if(i!=-1) G[i][n]+=branch.value;
                if(j!=-1) G[j][n]-=branch.value;
            }
        }
        return solveMatrix(G,n);
    }
    public void calculateResult(){
              int c=0;
              for(Branch b:data.Branch){
                  b.v[c]=data.Node[b.NodeIn].v[c]-data.Node[b.NodeOut].v[c];
                  if(b instanceof Resistor){
                      b.i[c]=b.v[c]/b.value;
                  }
                  else if(b instanceof Capacitor){
                      if(c==0)b.i[c]=0;
                      else{b.i[c]=b.v[c]-b.v[c-1];}
                  }
                  else if(b instanceof Inductor){
                      b.i[c]=0;
                      if(c!=0){
                          for(int i=1;i<=c;i++)b.i[c]+=b.v[i];
                      }
                  }

              }
    }
    public void saveResult() throws IOException {
              BufferedWriter bw=new BufferedWriter(new FileWriter("result.txt"));
             for(int i=0;i<=data.node;i++){
                 bw.write(Integer.toString(i));
                 for(int j=0;j<=((int)  (data.finalTime / data.dt));j++){
                   bw.write(" "+data.Node[i].v[j]);
                 }
                bw.newLine();
             }
              bw.close();
}}
        class GraphicsF extends JFrame{
        GraphicsF(){
            setBounds(300,300,700,500);
            setLayout(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
            }
            public void paint(Graphics g){
                Graphics2D graphics2D = (Graphics2D) g;
                Stroke s=new BasicStroke(10);
                graphics2D.setStroke(s);
                graphics2D.drawLine(2,3,400,500);
                g.setColor(Color.ORANGE);  g.drawRect(100,200,50,60);
                g.fillRect(20,20,50,50);
                g.fillOval(100,10,100,200);
                g.fillArc(100,200,50,50,90,45);
                g.drawLine(100,200,500,200);
            }
        }
public class First {
    private static Object GraphicsF;

    public static void main(String[] args) throws IOException {
      //  GraphicsF g=new GraphicsF();//
        //ReadTxt r=new ReadTxt("E.txt");
    SolveCircuit sC=new SolveCircuit("RCV.txt");
   sC.solveRLC();
        sC.saveResult();
   /* for(Branch b:sC.data.Branch){
        if(!(b instanceof Resistor)&&!(b instanceof CurrentSource))
            r++;
    }
    if(r==0) System.out.println(sC.solveResistanceCircuit());
    else sC.solveRLC();*/

        /*for(double i: sC.solveResistanceCircuit())
            System.out.println(i);*/
    }    }
