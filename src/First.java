import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.text.AttributedCharacterIterator;
import java.util.*;
class Node{
    int union;
    int cond=0,cond1=0;
    boolean visited=false;
    double[] v,i;
    ArrayList<Branch> branches;
    Queue<Integer> neighbors;
    ArrayList<Integer> x;
    ArrayList<Integer> y;
    Node(int k)
    {
        v=new double[k];
        i=new double[k];
        branches=new ArrayList<Branch>();
        neighbors=new LinkedList<Integer>();
        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();
    }
}
class Branch{
    public String name;
    public int NodeOut,NodeIn,draw=0;
    public double value;
    double[] i;
    double[] v;
    double[] p;
    Branch(){

    }
}
class Resistor extends Branch{
    Resistor(){
        super();
    }
}
class Capacitor extends Branch{
    Capacitor(){
       super();
    }
}
class Inductor extends Branch{
    Inductor(){
        super();
    }
}
class CurrentSource extends Branch{
double V,amp,freq,phase;
CurrentSource(){super();}
}
class CurrentDI extends Branch{
    String deEl;
    Branch deElm;
    double gain;
    CurrentDI(){super();}
}
class CurrentDV extends Branch{
    int deNodeOut,deNodeIn;
    double gain;
    CurrentDV(){super();}
}
class VoltageSource extends Branch{
    double V,amp,freq,phase;
    VoltageSource(){super();}
}
class VoltageDI extends Branch{///i j c1 a ///voltage
    String deEl;
    Branch deElm;
    double gain;
    VoltageDI(){super();}
}
class VoltageDV extends Branch{///i j k m a ///voltage
    int deNodeOut,deNodeIn;
    double gain;
    VoltageDV(){super();}
}
class D extends Branch{
    int index;
    D(){super();}
}
class ReadTxt{
    double dv,di,dt=0,finalTime=0;
    int node=0;
    int CurrentCounter=0,VoltageCounter=0,r=0,c=0,l=0,iv=0,vi=0,vv=0,ii=0,d=0,error=0;
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
    VoltageSource[] sc=new VoltageSource[5];
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
              if(line.matches("(I)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)(\\s+)(\\d+)")){
               I[CurrentCounter]=new CurrentSource();
               I[CurrentCounter].name=lineP[0];
               I[CurrentCounter].NodeIn=Integer.parseInt(lineP[1]);
               I[CurrentCounter].NodeOut=Integer.parseInt(lineP[2]);
               I[CurrentCounter].V=readNum(lineP[3]);
               I[CurrentCounter].amp=readNum(lineP[4]);
               I[CurrentCounter].freq=readNum(lineP[5]);
               I[CurrentCounter].phase=readNum(lineP[6]);
               I[CurrentCounter].value=I[CurrentCounter].V+I[CurrentCounter].amp*Math.sin(I[CurrentCounter].phase);
               n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
               n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
               max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
               Branch.add(I[CurrentCounter]);
               if(max>node)node=max;
              CurrentCounter++;}
           else if(line.matches("(V)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)(\\s+)(\\d+)")){
                   V[VoltageCounter]=new VoltageSource();
                   V[VoltageCounter].name=lineP[0];
                   V[VoltageCounter].NodeIn=Integer.parseInt(lineP[1]);
                   V[VoltageCounter].NodeOut=Integer.parseInt(lineP[2]);
                   V[VoltageCounter].V=readNum(lineP[3]);
                   V[VoltageCounter].amp=readNum(lineP[4]);
                   V[VoltageCounter].freq=readNum(lineP[5]);
                   V[VoltageCounter].phase=readNum(lineP[6]);
                   V[VoltageCounter].value=V[VoltageCounter].V+(V[VoltageCounter].amp)*Math.sin(V[VoltageCounter].phase);
                  n[Integer.parseInt(lineP[1])][Integer.parseInt(lineP[2])]=1;
                  n[Integer.parseInt(lineP[2])][Integer.parseInt(lineP[1])]=-1;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(V[VoltageCounter]);
                  if(max>node)node=max;
                   VoltageCounter++;}
           else if(line.matches("(R)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)")){
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
           else if(line.matches("(C)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)")){
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
           else if(line.matches("(L)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)")){
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
           else if(line.matches("(G)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)")){
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
           else if(line.matches("(E)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)")){
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
           else if(line.matches("(F)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)([RLC])([0-9a-z]+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)")){
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
           else if(line.matches("(H)([0-9a-z]+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)([RLC])([0-9a-z]+)(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)")){
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
                   D[d].index=d;
                   sc[d]=new VoltageSource();
                   sc[d].value=0;
                   sc[d].amp=0;
                   sc[d].NodeIn=D[d].NodeIn;
                   sc[d].NodeOut=D[d].NodeOut;
                   sc[d].freq=0;
                   sc[d].phase=0;
                  max=Math.max(Integer.parseInt(lineP[1]),Integer.parseInt(lineP[2]));
                  Branch.add(D[d]);
                  if(max>node)node=max;
                   d++;}
           else if(line.matches("d[VIT](\\s+)(\\d+)([.][0-9]+)?[pnumKMG]?")){
               if(lineP[0].charAt(1)=='V'){dv=readNum(lineP[1]);}
               else if(lineP[0].charAt(1)=='I'){di=readNum(lineP[1]);}
               else if(lineP[0].charAt(1)=='T'){dt=readNum(lineP[1]);}
           Error++;}
           else if(line.matches(".tran(\\s+)(\\d+([.][0-9]+)?[pnumKMG]?)")){
               finalTime=readNum(lineP[1]);
               Error++;
           }
           else if(lineP[0].charAt(0)!='*'){
               //System.exit(0);
               error=lineCounter;
                  System.out.println(lineCounter);
              }
           lineCounter++;
           line=bf.readLine();
       }
       bf.close();
       if(Error!=4)error=-1;//System.exit(0);}
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
               i.v=new double[(int) (finalTime/dt)];
               i.i=new double[(int) (finalTime/dt)];
               i.p=new double[(int) (finalTime/dt)];
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

    public void findError_5(){
        Node[0].visited=true;
    for(Integer i:Node[0].neighbors){
        Node[i].visited=true;
        for(Integer j:Node[i].neighbors)
            Node[j].visited=true;
    }
    for(int i=1;i<=node;i++){
        if (!Node[i].visited) {
            error = -5;
            break;
            //System.exit(0);
        }
    }
    }
    public void findError_2(){
        int e=0;
        ArrayList<Branch> serI=new ArrayList<>();
        for(Branch b:Branch){
            if((b instanceof CurrentSource)||(b instanceof CurrentDI)||(b instanceof CurrentDV))
                serI.add(b);
            }

        for(int i=0;i<serI.size()-1;i++){
            for(int j=i+1;j<serI.size();j++){
                if(serI.get(i).NodeOut==serI.get(j).NodeIn){if(serI.get(i).value!=serI.get(j).value)e++;}
                else if(serI.get(i).NodeOut==serI.get(j).NodeOut){if(serI.get(i).value!=-serI.get(j).value)e++;}
                else if(serI.get(i).NodeIn==serI.get(j).NodeIn){if(serI.get(i).value!=-serI.get(j).value)e++;}
                else if(serI.get(i).NodeIn==serI.get(j).NodeOut){if(serI.get(i).value!=serI.get(j).value)e++;}
            }
        }
        if(e!=0)error=-2;//System.exit(0);}
    }
        public void findError_3(){
            int c=0;
            ArrayList<Branch> parV=new ArrayList<>();
            for(Branch b:Branch){
                if ((b instanceof VoltageSource) || (b instanceof VoltageDV) || (b instanceof VoltageDI))
                    parV.add(b);
            }
            for(int i=0;i<parV.size()-1;i++){
                for(int j=i+1;j<parV.size();j++){
                    if((parV.get(i).NodeOut==parV.get(j).NodeOut)&&(parV.get(i).NodeIn==parV.get(j).NodeIn)){
                        if(parV.get(i).value!=parV.get(j).value)c++;
                    }
                    else if((parV.get(i).NodeOut==parV.get(j).NodeIn)&&(parV.get(i).NodeIn==parV.get(j).NodeOut)){
                        if(parV.get(i).value!=-parV.get(j).value)c++;
                    }
                }
            }
            if(c!=0){/*System.exit(0);*/error=-3;}
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
              if(unions.get(i).size()!=1){
                  for(Branch branch:Branch){
                    if(((branch instanceof VoltageSource)||(branch instanceof VoltageDV)||(branch instanceof VoltageDI))){
                    if(branch.NodeIn==i){
                    Node[branch.NodeOut].v[m]=Node[i].v[m]-branch.value;Node[branch.NodeOut].visited=true;}
                    else if(branch.NodeOut==i){
                    Node[branch.NodeIn].v[m]=Node[i].v[m]+branch.value;Node[branch.NodeIn].visited=true;}
                   }
                }
            }
         }
        }}

    public double readNum(String num){
    double numR=Double.parseDouble(num.replaceAll("[pnumKMG]",""));
    if(num.matches("(\\\\d+)([.][0-9]+)?p")){return Math.pow(10,-12);}
    else if(num.matches("(\\d+)([.][0-9]+)?n")){return numR*Math.pow(10,-9);}//(\\d+)([.][0-9]+)?[pnumKMG]?")
    else if(num.matches("(\\d+)([.][0-9]+)?u")){return numR*Math.pow(10,-6);}
    else if(num.matches("(\\d+)([.][0-9]+)?m")){return numR*Math.pow(10,-3);}
    else if(num.matches("(\\d+)([.][0-9]+)?K")){return numR*Math.pow(10,3);}
    else if(num.matches("(\\d+)([.][0-9]+)?M")){return numR*Math.pow(10,6);}
    else if(num.matches("(\\d+)([.][0-9]+)?G")){return numR*Math.pow(10,9);}
return numR; }
}
      class SolveCircuit{
          ReadTxt data;
          SolveCircuit(String FileName) throws IOException {
              data=new ReadTxt(FileName);
              data.createElements();
              data.findError_3();
              data.findError_2();
              data.createUnions(0);
          }
          public void solveRLC(){
              Node N;
              double I1,I2z,I2k,t=0;
              int c= (int) (data.finalTime/data.dt),m=1;
              ///m:step number   m=1: 0-dt    m=2:dt-2dt
              while(t<data.finalTime&&m<=c){
                  updateV(data.dt, t,m,0);
                  data.findError_3();
                  data.createUnions(m);
                  for(Integer i:data.unions.keySet()){
                      if(i!=0){
                      I1=0; I2z=0; I2k=0;
                      for(Integer k:data.unions.get(i)){
                          N=data.Node[k];
                          for(Branch branch:N.branches){
                              I1+=calculateI(data.dt, data.dv, branch, t,m,k,0);
                          }
                      }
                      data.findError_2();
                      updateV(data.dt, t,m,1);
                      for(Integer k:data.unions.get(i)){///ziadesho hesab mikonim
                          N=data.Node[k];
                          N.v[m-1]+=data.dv;
                          for(Branch branch:N.branches) {
                              I2z+=calculateI(data.dt, data.dv, branch, t,m,k,1);
                          }
                          N.v[m-1]-=data.dv;
                      }
                      updateV(data.dt,t,m,-1);
                          for(Integer k:data.unions.get(i)){//kamesho hesab mikonim
                              N=data.Node[k];
                              N.v[m-1]-=data.dv;
                              for(Branch branch:N.branches) {
                                  I2k+=calculateI(data.dt, data.dv, branch, t,m,k,-1);
                              }
                              N.v[m-1]+=data.dv;
                          }
                      for(Integer k:data.unions.get(i)) {
                          N=data.Node[k];
                          double itk=Math.abs(I1)-Math.abs(I2k);
                          double itz=Math.abs(I1)-Math.abs(I2z);
                          if(Math.abs(itz/data.di)>Math.abs(itk/data.di))
                          N.v[m]+=-data.dv+(itk*data.dv/data.di);
                          else
                          N.v[m]+=data.dv+(itz*data.dv/data.di);
                       System.out.println(I1+" "+" "+I2k+" "+I2z+" "+data.Node[2].v[m-1]+" "+data.Node[2].v[m]);
                       //   System.out.println(I1+" "+" "+I2k+" "+I2z+" "+data.Node[3].v[m-1]+" "+data.Node[3].v[m]);
                         System.out.println(data.Node[1].v[m]);
                      }

                  }
              }
                  t+=data.dt;m++;}
          }
      public void updateV(double dT,double t,int m,int flag){
              int sc=0;
              D diode = null;
              for(Branch e:data.Branch) {
                  if (e instanceof VoltageSource) {
                      if(flag!=0)t+=dT;
                      if (((VoltageSource) e).amp != 0){
                          e.value = (((VoltageSource) e).V + ((VoltageSource) e).amp *
                                  Math.sin(2 * Math.PI * ((VoltageSource) e).freq * (t) + ((VoltageSource) e).phase));}}
              if(flag==0){
                    if (e instanceof VoltageDV) {
                      e.value = (data.Node[((VoltageDV) e).deNodeIn].v[m - 1] - data.Node[((VoltageDV) e).deNodeOut].v[m - 1]) * ((VoltageDV) e).gain;
                  } else if (e instanceof VoltageDI) {
                      if (((VoltageDI) e).deElm instanceof Inductor) {
                          if (m == 1) e.value = 0;

                          else {
                              for (int k = 1; k < m; k++)
                                  e.value += (data.Node[((Inductor) ((VoltageDI) e).deElm).NodeIn].v[m]
                                          - data.Node[((Inductor) ((VoltageDI) e).deElm).NodeOut].v[m]);
                              e.value = e.value * dT / ((VoltageDI) e).deElm.value;
                          }
                          
                          e.value = e.value * dT / ((VoltageDI) e).deElm.value;
                      } else if (((VoltageDI) e).deElm instanceof Resistor) {
                          e.value = ((VoltageDI) e).gain * (data.Node[((VoltageDI) e).deElm.NodeIn].v[m - 1] -
                                  data.Node[((VoltageDI) e).deElm.NodeOut].v[m - 1]) / ((VoltageDI) e).deElm.value;

                      } else if (((VoltageDI) e).deElm instanceof Capacitor) {
                          if (m == 1) e.value = 0;
                          else {
                              e.value = (((VoltageDI) e).deElm.value / dT) * ((data.Node[((VoltageDI) e).deElm.NodeIn].v[m - 1] - data.Node[((VoltageDI) e).deElm.NodeOut].v[m - 1])
                                      - (data.Node[((VoltageDI) e).deElm.NodeIn].v[m - 2] - data.Node[((VoltageDI) e).deElm.NodeOut].v[m - 2])) * ((VoltageDI) e).gain;

                          }}
                      }
                   else if (e instanceof D) {
                        diode= (D) e;
                      if (data.Node[e.NodeIn].v[m - 1] >= data.Node[e.NodeOut].v[m - 1]) {
                         e.v[m-1]=0;sc++;
                      }
                      else{e.i[m-1]=0;}
                  }
              }}
              if(data.d!=0&&flag==0){
              if(sc!=0){
              if(!data.Branch.contains(data.sc[diode.index]))
              data.Branch.add(data.sc[diode.index]);}
              else if(data.Branch.contains(data.sc[diode.index])) {data.Branch.remove(data.sc[diode.index]);}}
          }
    public double calculateI(double dT,double dV,Branch e,double t,int m,int nodeNum,int flag) {
        double I=0;
        int aN;
        if(e.NodeOut==nodeNum)aN=e.NodeIn;
        else aN=e.NodeOut;

        if(e instanceof Resistor){
            I=(data.Node[aN].v[m-1]-data.Node[nodeNum].v[m-1])/e.value;
            //System.out.println(I+"r");
        }
        else if(e instanceof Inductor) {
             if(m==1)I=-1*flag*dT*dV/e.value;
            else{
            for(int k=1;k<m;k++)// Inductor.i[0]==0
                I+=(data.Node[aN].v[k]-data.Node[nodeNum].v[k]);
            I =(I*dT)/e.value;}
            if(flag==0)e.i[m-1]=I;
//System.out.println(I);
        }
        else if(e instanceof Capacitor) {
                double V=0;
                if(m!=1)V= ((data.Node[aN].v[m - 1] - data.Node[nodeNum].v[m - 1]) - (data.Node[aN].v[m - 2] - data.Node[nodeNum].v[m - 2]));

                if(flag!=0){ I=-1*flag*e.value*dV/dT;}
                else{
                    if(m==1)I=0;
                    else{
                        I=(e.value / dT) * V;}
                }
               // System.out.println(I);
            }
        else if(e instanceof CurrentSource){
            I=((CurrentSource) e).V+((CurrentSource) e).amp*Math.sin(2*Math.PI*((CurrentSource) e).freq*t+((CurrentSource) e).phase);
            e.value=I;
            if(flag==0)e.i[m-1]=I;
            if(e.NodeOut==nodeNum)I=-1*I;
        }
        else if(e instanceof CurrentDV){
            I=data.Node[((CurrentDV) e).deNodeIn].v[m-1]-data.Node[((CurrentDV) e).deNodeOut].v[m-1]*((CurrentDV) e).gain;
            e.value=I;
            if(e.NodeOut==nodeNum)I=-1*I;
        }
        else if(e instanceof CurrentDI) {
            if(((CurrentDI) e).deElm instanceof Inductor) {
                if(m==1)I=0;
               for(int k=1;k<m;k++)
                I+=(data.Node[((CurrentDI) e).deElm.NodeIn].v[k]-data.Node[((CurrentDI) e).deElm.NodeOut].v[k]);
                I=(I*dT/((CurrentDI) e).deElm.value)*((CurrentDI) e).gain;
                e.value=I;
                if(e.NodeOut==nodeNum)I=-1*I;
            }
            else if(((CurrentDI) e).deElm instanceof Resistor){
                I=((CurrentDI) e).gain*(data.Node[((CurrentDI) e).deElm.NodeIn].v[m-1]-
                        data.Node[((CurrentDI) e).deElm.NodeOut].v[m-1])/((CurrentDI) e).deElm.value;
                e.value=I;
                if(e.NodeOut==nodeNum)I=-1*I;
            }

            else if(((CurrentDI) e).deElm instanceof Capacitor) {
                if(flag==0){
                if(m==1)I=0;
                else{
                    I=data.Node[(((CurrentDI) e).deElm).NodeIn].v[m-1]-data.Node[(((CurrentDI) e).deElm).NodeOut].v[m-1]
                            -(data.Node[(((CurrentDI) e).deElm).NodeIn].v[m-2]-data.Node[(((CurrentDI) e).deElm).NodeOut].v[m-2])
                    *((CurrentDI) e).deElm.value*((CurrentDI) e).gain/dT;

                }}
                else if(flag==1){I=((CurrentDI) e).deElm.value*dV/dT;}
                else I=-((CurrentDI) e).deElm.value*dV/dT;
                if(nodeNum==e.NodeOut)I=-1*I;
                e.value=I;
            }

        }

        return I;
    }
   /* public double[] solveMatrix(double[][] a,int n){
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
    }*/
    public void calculateResult(){
 if(data.d!=0){if(data.Branch.contains(data.sc[0]))data.Branch.remove(data.sc[0]);}
              for(int c=0;c<data.finalTime/data.dt-1;c++){
              for(Branch b:data.Branch){
                  if(b instanceof D){
                      if(b.i[c]==0)b.v[c]=data.Node[b.NodeIn].v[c]-data.Node[b.NodeOut].v[c];
                      else if(b.v[c]==0){
                          b.i[c]=0;
                          int n=Math.max(b.NodeOut,b.NodeIn);
                          for(Branch branch:data.Node[n].branches){
                              if(branch.NodeIn==n) b.i[c]+=branch.i[c];
                              else b.i[c]-=branch.i[c];
                          }
                          if(n==b.NodeOut)b.i[c]*=-1;
                      }
                  }
                  else if(!(b instanceof VoltageDI)&&!(b instanceof VoltageSource)&&!(b instanceof VoltageDV))
                  {b.v[c]=data.Node[b.NodeIn].v[c]-data.Node[b.NodeOut].v[c];
                  if(b instanceof Resistor){
                      b.i[c]=b.v[c]/b.value;
                  }
                  else if(b instanceof Capacitor){
                      if(c==0)b.i[c]=0;
                      else{
                          if(b.v[c]-b.v[c-1]<0.001)b.i[c]=0;
                          else b.i[c]=b.value*(b.v[c]-b.v[c-1])/data.dt;
                      }
                  }
                  else if(b instanceof Inductor){
                      if(c==0)
                      b.i[c]=0;
                      /*if(c!=0){///for(int k=1;k<m;k++)// Inductor.i[0]==0
                          //I+=(data.Node[aN].v[k]-data.Node[nodeNum].v[k]);
                          for(int i=1;i<=c;i++)b.i[c]+=b.v[i];
                      }
                      b.i[c]=b.i[c]*data.dt/b.value;*/
                  }
                  else if(b instanceof CurrentDI){
                     b.i[c]=((CurrentDI) b).gain*((CurrentDI) b).deElm.i[c];
                  }
                  else if(b instanceof CurrentDV){
                      b.i[c]=((CurrentDV) b).gain*(data.Node[((CurrentDV) b).deNodeIn].v[c]-data.Node[((CurrentDV) b).deNodeOut].v[c]);
                  }}
                  else {
                      b.i[c]=0;
                      int n=Math.max(b.NodeOut,b.NodeIn);
                      for(Branch branch:data.Node[n].branches){
                         if(branch.NodeIn==n) b.i[c]+=branch.i[c];
                         else b.i[c]-=branch.i[c];
                      }
                      if(n==b.NodeOut)b.i[c]*=-1;
                      if(b instanceof VoltageDV){
                          b.v[c]=((VoltageDV) b).gain*(data.Node[((VoltageDV) b).deNodeIn].v[c]-data.Node[((VoltageDV) b).deNodeOut].v[c]);
                      }
                      else if(b instanceof VoltageDI){
                          b.v[c]=((VoltageDI) b).gain*((VoltageDI) b).deElm.i[c];
                      }
                  }
              }}
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
             for(Branch b:data.Branch){
                 bw.write(b.name);
                 for(int j=0;j<((int)  (data.finalTime / data.dt));j++){
                     bw.write(" "+b.v[j]+" "+b.i[j]+" "+(b.v[j])*(b.i[j]));
                 }
                 bw.newLine();
             }
              bw.close();
}}
/*class Phase2 {
    ReadTxt data;
    Phase2(String fileName) throws IOException {
        data = new ReadTxt(fileName);
        data.createElements();
        JFrame frame1 = new JFrame();
        Container container = frame1.getContentPane();
        JRootPane root = frame1.getRootPane();
        LayoutManager mgr = new GroupLayout(container);
        frame1.setLayout(mgr);
        JPanel panel = new JPanel();
        int x = 100, Y = -1;
        int count = 0;
        for (int i = 0; i <= data.node; i++) {
            count = 0;
            for (Branch branch : data.Node[i].branches) {
                if ((branch.NodeIn == 0 || branch.NodeOut == 0))
                    count++;
            }
            if (count > 1)
                data.Node[i].cond = count;
        }
        for (int i = 0; i <= data.node; i++) {
            if (i % 6 == 1 || i == 0)
                x = 200;
            if (data.Node[i].cond > 0) {
                for (int j = 0; j < data.Node[i].cond; j++) {
                    if (x + 50 * j == 200)
                        Y++;
                    data.Node[i].y.add((int) 400 - Y * 100);
                    data.Node[i].x.add((int) (x + 50 * j));

                }
            } else {
                if (x == 200)
                    Y++;
                data.Node[i].y.add((int) 400 - Y * 100);
                data.Node[i].x.add((int) (x));
            }
            x += 50 * (data.Node[i].x.size());

        }
        for (int i = 0; i <= data.node; i++) {
            int j = 0;
            for (Branch branch : data.Node[i].branches) {
                if (branch.draw == 0) {
                    if (data.Node[branch.NodeIn].y.get(0) !=
                            (int) data.Node[branch.NodeOut].y.get(0)) {
                        if (branch instanceof Resistor) {
                            ImageIcon resistor = new ImageIcon("r2.png");
                            resistor = scaling(resistor,1);
                            JLabel lr2 = new JLabel(resistor);
                            draw(branch, panel, lr2, data.Node[i], j);
                        }
                        if (branch instanceof Capacitor) {
                            ImageIcon capacitor = new ImageIcon("c2.png");
                            capacitor = scaling(capacitor,1);
                            JLabel lc2 = new JLabel(capacitor);
                            draw(branch, panel, lc2, data.Node[i], j);
                        }
                        if (branch instanceof Inductor) {
                            ImageIcon Inductor = new ImageIcon("l2.jpg");
                            Inductor = scaling(Inductor,1);
                            JLabel lL2 = new JLabel(Inductor);
                            draw(branch, panel, lL2, data.Node[i], j);
                        }
                        if (branch instanceof D) {
                            ImageIcon Diod = new ImageIcon("d2.png");
                            Diod=scaling(Diod,1);
                            JLabel ld2 = new JLabel(Diod);
                            ImageIcon Diod2 = new ImageIcon("d3.png");
                            Diod2 = scaling(Diod2,1);
                            JLabel ld3 = new JLabel(Diod2);
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, ld2, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, ld3, data.Node[i], j);
                        }
                        if (branch instanceof CurrentDI ||
                                branch instanceof CurrentDV) {
                            ImageIcon CurrentD = new ImageIcon("cd2.png");
                            CurrentD = scaling(CurrentD,1);
                            JLabel lCD2 = new JLabel(CurrentD);
                            ImageIcon CurrentD2 = new ImageIcon("cd3.png");
                            CurrentD2 = scaling(CurrentD2,1);
                            JLabel lCD3 = new JLabel(CurrentD2);
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, lCD2, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, lCD3, data.Node[i], j);
                        }
                        if (branch instanceof CurrentSource) {
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
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, lCI2, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, lCI3, data.Node[i], j);
                        }
                        if (branch instanceof VoltageDI ||
                                branch instanceof VoltageDV) {
                            ImageIcon VoltageD = new ImageIcon("vd2.png");
                            VoltageD = scaling( VoltageD,1);
                            JLabel lVD2 = new JLabel(VoltageD);
                            ImageIcon VoltageD2 = new ImageIcon("vd3.png");
                            VoltageD2 = scaling( VoltageD2,1);
                            JLabel lVD3 = new JLabel(VoltageD2);
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, lVD2, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, lVD3, data.Node[i], j);
                        }
                        if (branch instanceof VoltageSource) {
                            ImageIcon VoltageIndependent = new ImageIcon("vi2.png");
                            VoltageIndependent = scaling(VoltageIndependent,1);
                            JLabel lVI2 = new JLabel(VoltageIndependent);
                            ImageIcon VoltageIndependent2 = new ImageIcon("vi3.png");
                            VoltageIndependent2 = scaling(VoltageIndependent2,1);
                            JLabel lVI3 = new JLabel(VoltageIndependent2);
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, lVI2, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, lVI3, data.Node[i], j);
                        }
                    } else {
                        //System.out.println("l");
                        if (branch instanceof Resistor) {
                            ImageIcon resistor1 = new ImageIcon("r1.png");
                            resistor1 = scaling(resistor1,0);
                            JLabel lr1 = new JLabel(resistor1);
                            draw(branch, panel, lr1, data.Node[i], j);
                        }
                        if (branch instanceof Capacitor) {
                            ImageIcon capacitor1 = new ImageIcon("c1.png");
                            capacitor1 = scaling(capacitor1,0);
                            JLabel lc1 = new JLabel(capacitor1);
                            draw(branch, panel, lc1, data.Node[i], j);
                        }
                        if (branch instanceof Inductor) {
                            ImageIcon Inductor1 = new ImageIcon("l1.jpg");
                            Inductor1 = scaling(Inductor1,0);
                            JLabel lL1 = new JLabel(Inductor1);
                            draw(branch, panel, lL1, data.Node[i], j);
                        }
                        if (branch instanceof D) {
                            ImageIcon Diod1 = new ImageIcon("d1.png");
                            Diod1=scaling(Diod1,0);
                            JLabel ld1 = new JLabel(Diod1);
                            ImageIcon Diod4 = new ImageIcon("d4.png");
                            Diod4 = scaling(Diod4,0);
                            JLabel ld4 = new JLabel(Diod4);
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, ld1, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, ld4, data.Node[i], j);
                        }
                        if (branch instanceof CurrentDI ||
                                branch instanceof CurrentDV) {
                            ImageIcon CurrentD1 = new ImageIcon("cd1.png");
                            CurrentD1 = scaling(CurrentD1,0);
                            JLabel lCD1 = new JLabel(CurrentD1);
                            ImageIcon CurrentD4 = new ImageIcon("cd4.png");
                            CurrentD4 = scaling(CurrentD4,0);
                            JLabel lCD4 = new JLabel(CurrentD4);
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, lCD1, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, lCD4, data.Node[i], j);
                        }
                        if (branch instanceof CurrentSource) {
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
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, lCI1, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, lCI4, data.Node[i], j);
                        }
                        if (branch instanceof VoltageDI ||
                                branch instanceof VoltageDV) {
                            ImageIcon VoltageD1 = new ImageIcon("vd1.png");
                            VoltageD1 = scaling( VoltageD1,0);
                            JLabel lVD1 = new JLabel(VoltageD1);
                            ImageIcon VoltageD4 = new ImageIcon("vd4.png");
                            VoltageD4 = scaling( VoltageD4,0);
                            JLabel lVD4 = new JLabel(VoltageD4);
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, lVD1, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, lVD4, data.Node[i], j);
                        }
                        if (branch instanceof VoltageSource) {
                            ImageIcon VoltageIndependent1 = new ImageIcon("vi1.png");
                            VoltageIndependent1 = scaling(VoltageIndependent1,0);
                            JLabel lVI1 = new JLabel(VoltageIndependent1);
                            ImageIcon VoltageIndependent4 = new ImageIcon("vi4.png");
                            VoltageIndependent4 = scaling(VoltageIndependent4,0);
                            JLabel lVI4 = new JLabel(VoltageIndependent4);
                            if (data.Node[i].equals(data.Node[branch.NodeIn]))
                                draw(branch, panel, lVI1, data.Node[i], j);
                            if (data.Node[i].equals(data.Node[branch.NodeOut]))
                                draw(branch, panel, lVI4, data.Node[i], j);
                        }
                    }
                    j++;
                    branch.draw = 1;
                }
            }

            for (int k = 0; k < data.Node[i].x.size() - 1; k++) {
                ImageIcon wire1 = new ImageIcon("w.png");
                Image scaled = wire1.getImage();
                Image modified = scaled.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
                wire1 = new ImageIcon(modified);
                JLabel lw = new JLabel(wire1);
                lw.setBounds(data.Node[i].x.get(k) + 10, data.Node[i].y.get(k), data.Node[i].x.get(k + 1) + 7, data.Node[i].y.get(k + 1));
                panel.add(lw);

            }
        }

        frame1.setBounds(0,0, 1000, 1000);
        panel.setBounds(0,0, 1000, 1000);
        panel.setLayout(null);
        container.add(panel);
        frame1.add(panel);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
    }
    public void draw(Branch e, JPanel panel, JLabel label, Node n, int a) {
        int I = 0, J = 0, cond = 0,c=0;
        if ((int) data.Node[e.NodeOut].y.get(0) != (int) data.Node[e.NodeIn].y.get(0)) {
            if (n.equals(data.Node[e.NodeIn])) {
                for (int i = a; i < data.Node[e.NodeIn].x.size(); i++) {
                    for (int j = 0; j < data.Node[e.NodeOut].x.size(); j++)
                        if ((int) n.x.get(i) == (int) data.Node[e.NodeOut].x.get(j)) {
                            I = i;
                            J = j;
                            label.setBounds(data.Node[e.NodeIn].x.get(I), data.Node[e.NodeIn].y.get(I), data.Node[e.NodeOut].x.get(J), data.Node[e.NodeOut].y.get(J));
                            JLabel name = new JLabel(e.name);
                            name.setBounds(data.Node[e.NodeIn].x.get(I) + 110, (data.Node[e.NodeIn].y.get(I) + data.Node[e.NodeOut].y.get(J)) / 2 + 25,
                                    data.Node[e.NodeIn].x.get(I) + 120, (data.Node[e.NodeIn].y.get(I) + data.Node[e.NodeOut].y.get(J)) / 2 + 35);
                            panel.add(name);
                            c = 1;
                            break;
                        }
                    if (c == 1)
                        break;
                }
            }
            if (n.equals(data.Node[e.NodeOut])) {
                for (int i = a; i < data.Node[e.NodeOut].x.size(); i++) {
                    for (int j = 0; j < data.Node[e.NodeIn].x.size(); j++)
                        if ((int) n.x.get(i) == (int) data.Node[e.NodeIn].x.get(j)) {
                            I = i;
                            J = j;
                            label.setBounds(data.Node[e.NodeIn].x.get(J), data.Node[e.NodeIn].y.get(J)+33, data.Node[e.NodeOut].x.get(I), data.Node[e.NodeOut].y.get(I)+33);
                            JLabel name = new JLabel(e.name);
                            name.setBounds(data.Node[e.NodeIn].x.get(J)+110,(data.Node[e.NodeIn].y.get(J)+data.Node[e.NodeOut].y.get(I))/2+25,
                                    data.Node[e.NodeIn].x.get(J)+120,(data.Node[e.NodeIn].y.get(J)+data.Node[e.NodeOut].y.get(I))/2+35);
                            panel.add(name);
                            c=1;
                            break;
                        }
                    if (c == 1)
                        break;
                }
            }

        } else {
            if (n.equals(data.Node[e.NodeIn])) {
                cond = 0;
                for (int i = 0; i < n.x.size(); i++)
                    for (int j = 0; j < data.Node[e.NodeOut].x.size(); j++)
                        if ((int) n.x.get(i) > (int) data.Node[e.NodeOut].x.get(j)) {
                            cond = 1;
                            break;
                        }

                if (cond == 1)
                    label.setBounds(data.Node[e.NodeIn].x.get(0), data.Node[e.NodeIn].y.get(0) - 30, data.Node[e.NodeOut].x.get(data.Node[e.NodeOut].x.size() - 1), data.Node[e.NodeOut].y.get(data.Node[e.NodeOut].x.size() - 1) - 30);
                else
                    label.setBounds(data.Node[e.NodeIn].x.get(n.x.size() - 1) + 10, data.Node[e.NodeIn].y.get(n.x.size() - 1) + 33, data.Node[e.NodeOut].x.get(0) + 10, data.Node[e.NodeOut].y.get(0) + 33);
            }
            if (n.equals(data.Node[e.NodeOut])) {
                cond = 0;
                for (int i = 0; i < n.x.size(); i++)
                    for (int j = 0; j < data.Node[e.NodeIn].x.size(); j++)
                        if ((int) n.x.get(i) > (int) data.Node[e.NodeIn].x.get(j))
                            cond = 1;

                if (cond == 1)
                    label.setBounds(data.Node[e.NodeIn].x.get(data.Node[e.NodeIn].x.size() - 1), data.Node[e.NodeIn].y.get(data.Node[e.NodeIn].x.size() - 1) - 30, data.Node[e.NodeOut].x.get(0), data.Node[e.NodeOut].y.get(0) - 30);
                else
                    label.setBounds(data.Node[e.NodeIn].x.get(0), data.Node[e.NodeIn].y.get(0), data.Node[e.NodeOut].x.get(n.x.size() - 1), data.Node[e.NodeOut].y.get(n.x.size() - 1));
            }
        }
        panel.add(label);

    }

    public  ImageIcon scaling(ImageIcon im, int condition) {
        // condition=1 : icon should set vertically
        // condition=0 : icon should set horizontally
        Image scaled = im.getImage();
        Image modified;
        if (condition == 1)
            modified = scaled.getScaledInstance(50, 106, java.awt.Image.SCALE_SMOOTH);
        else
            modified = scaled.getScaledInstance(80, 90, java.awt.Image.SCALE_SMOOTH);
        im = new ImageIcon(modified);
        return im;
    }
}*/
class Phase{
JFrame frame=new JFrame("Solve circuit");
String fileName;
    Phase() {
        Container container=frame.getContentPane();
        JRootPane root=frame.getRootPane();
        LayoutManager mgr=new GroupLayout(container);
        frame.setLayout(mgr);
        Image icon = Toolkit.getDefaultToolkit().getImage("ci.png");
        //frame.setIconImage(new ImageIcon("ci.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
        frame.setIconImage(icon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton run= new JButton("RUN");
        JButton load = new JButton("LOAD");
        JButton draw= new JButton("DRAW");
        run.setBounds(1400,10,120,40);
        draw.setBounds(1200,10,120,40);
        load.setBounds(1000,10,120,40);
        JTextArea text=new JTextArea();
        text.setBounds(10,10,400,500);
        frame.add(text);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        MyChooser fileChooser = new MyChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\m1997\\IdeaProjects\\First"));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getName();
            /*try {
                Desktop.getDesktop().open(new File(fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }*/
            System.out.println("Selected file: " + fileName);
        }

            }
        });
        run.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileName==null){
                    JOptionPane.showMessageDialog(frame,"Choose the file first!","",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    try {
                        BufferedReader bf=new BufferedReader(new FileReader(fileName));
                        String line=bf.readLine();
                        text.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
                        while(line!=null){
                            text.append(line+"\n");
                            line=bf.readLine();
                        }
                        bf.close();
                        SolveCircuit solveCircuit=new SolveCircuit(fileName);
                        solveCircuit.solveRLC();
                        if(solveCircuit.data.error!=0){
                            JOptionPane.showMessageDialog(frame,"ERROR   =  "+Integer.toString(solveCircuit.data.error)," ",JOptionPane.WARNING_MESSAGE);
                        }
                        solveCircuit.calculateResult();
                        solveCircuit.saveResult();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        draw.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileName==null){
                    JOptionPane.showMessageDialog(frame,"Choose the file first!","",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        frame.add(run); frame.add(load);frame.add(draw);
        frame.setBounds(0,0,1600,900);
        frame.setVisible(true);
    }
}
 class MyChooser extends JFileChooser {
    protected JDialog createDialog(Component parent)
            throws HeadlessException {
        JDialog dlg = super.createDialog(parent);
        dlg.setLocation(100, 100);
        return dlg;
    }
}
//////////////////////////////////
class Show extends Canvas {
    ReadTxt data;
    public  void draw(Branch e,JPanel panel,JLabel label,Node n,int a) {
        int I=0,J=0,cond=0, c=0;
        if((int)data.Node[e.NodeOut].y.get(0)!=(int)data.Node[e.NodeIn].y.get(0)) {
            if(n.equals(data.Node[e.NodeIn])) {
                for(int i=a;i<data.Node[e.NodeIn].x.size();i++) {
                    for(int j=0;j<data.Node[e.NodeOut].x.size();j++)
                        if((int)n.x.get(i)==(int)data.Node[e.NodeOut].x.get(j)) {
                            I=i;
                            J=j;

                            label.setBounds(data.Node[e.NodeIn].x.get(I),data.Node[e.NodeIn].y.get(I),data.Node[e.NodeOut].x.get(J),data.Node[e.NodeOut].y.get(J));
                            JLabel name = new JLabel(e.name);
                            name.setBounds(data.Node[e.NodeIn].x.get(I)+110,(data.Node[e.NodeIn].y.get(I)+data.Node[e.NodeOut].y.get(J))/2+25,
                                    data.Node[e.NodeIn].x.get(I)+120,(data.Node[e.NodeIn].y.get(I)+data.Node[e.NodeOut].y.get(J))/2+35);
                            panel.add(name);
                            c=1;
                            break;
                        }
                    if(c==1)
                        break;
                }
            }

            if(n.equals(data.Node[e.NodeOut])) {
                for(int i=a;i<data.Node[e.NodeOut].x.size();i++) {
                    for(int j=0;j<data.Node[e.NodeIn].x.size();j++)
                        if((int)n.x.get(i)==(int)data.Node[e.NodeIn].x.get(j)) {
                            I=i;
                            J=j;
                            label.setBounds(data.Node[e.NodeIn].x.get(J),data.Node[e.NodeIn].y.get(J)+33,data.Node[e.NodeOut].x.get(I),data.Node[e.NodeOut].y.get(I)+33);
                            JLabel name = new JLabel(e.name);
                            name.setBounds(data.Node[e.NodeIn].x.get(J)+110,(data.Node[e.NodeIn].y.get(J)+data.Node[e.NodeOut].y.get(I))/2+25,
                                    data.Node[e.NodeIn].x.get(J)+120,(data.Node[e.NodeIn].y.get(J)+data.Node[e.NodeOut].y.get(I))/2+35);
                            panel.add(name);
                            c=1;
                            break;
                        }
                    if(c==1)
                        break;
                }
            }

        }
        else {
            if(n.equals(data.Node[e.NodeIn])) {
                cond=0; int y=0,x=0,b=0,I1=0;
                if(a>=n.x.size()) {
                    I1=n.x.size()-1;
                    for(int j=0;j<data.Node[e.NodeOut].x.size();j++)
                        if((double)n.y.get(I1)==(double)data.Node[e.NodeOut].y.get(j)) {
                            if((int)n.x.get(I1)>(int)data.Node[e.NodeOut].x.get(j)) {
                                cond=1;}
                        }
                }
                for(int i=a;i<n.x.size();i++) {
                    for(int j=0;j<data.Node[e.NodeOut].x.size();j++)
                        if((double)n.y.get(i)==(double)data.Node[e.NodeOut].y.get(j)) {
                            if((int)n.x.get(i)>(int)data.Node[e.NodeOut].x.get(j)) {
                                cond=1;}
                            I1=i;
                            b=1;
                            break;
                        }
                    if(b==1)
                        break;
                }
                if(cond==1) {
                    label.setBounds(data.Node[e.NodeIn].x.get(0),data.Node[e.NodeIn].y.get(I1)-30,data.Node[e.NodeOut].x.get(data.Node[e.NodeOut].x.size()-1),data.Node[e.NodeOut].y.get(I1)-30);
                    x=(data.Node[e.NodeIn].x.get(0)+data.Node[e.NodeOut].x.get(data.Node[e.NodeOut].x.size()-1))/2;
                    y=data.Node[e.NodeIn].y.get(I1)-30;
                }
                else {
                    label.setBounds(data.Node[e.NodeIn].x.get(I1)+10,data.Node[e.NodeIn].y.get(I1)+33*(500-data.Node[e.NodeIn].y.get(I1))/100,data.Node[e.NodeOut].x.get(0)+10,data.Node[e.NodeOut].y.get(0)+33*(500-data.Node[e.NodeIn].y.get(I1))/100);
                    x=(data.Node[e.NodeIn].x.get(I1)+10+data.Node[e.NodeOut].x.get(0)+10)/2;
                    y=data.Node[e.NodeIn].y.get(I1)+33;
                }
                JLabel name = new JLabel(e.name);
                name.setBounds(x+80,y-10+33*(500-data.Node[e.NodeIn].y.get(I1))/100,x+85,y-5+33*(500-data.Node[e.NodeIn].y.get(I1))/100);
                panel.add(name);
            }
            if(n.equals(data.Node[e.NodeOut])) {
                cond=0; int y=0,x=0,b=0,I1=0;
                if(a>=n.x.size()) {
                    I1=n.x.size()-1;
                    for(int j=0;j<data.Node[e.NodeIn].x.size();j++)
                        if((double)n.y.get(I1)==(double)data.Node[e.NodeIn].y.get(j)) {
                            if((int)n.x.get(I1)>(int)data.Node[e.NodeIn].x.get(j)) {
                                cond=1;}
                        }
                }
                for(int i=a;i<n.x.size();i++) {
                    for(int j=0;j<data.Node[e.NodeIn].x.size();j++)
                        if((double)n.y.get(i)==(double)data.Node[e.NodeIn].y.get(j)) {
                            if((int)n.x.get(i)>(int)data.Node[e.NodeIn].x.get(j)) {
                                cond=1;}
                            I1=i;
                            b=1;
                            break;
                        }
                    if(b==1)
                        break;
                }

                if(cond==1)
                {
                    label.setBounds(data.Node[e.NodeIn].x.get(I1),data.Node[e.NodeIn].y.get(I1)-30,data.Node[e.NodeOut].x.get(0),data.Node[e.NodeOut].y.get(0)-30);
                    x=(data.Node[e.NodeIn].x.get(I1)+data.Node[e.NodeOut].x.get(0))/2;
                    y=data.Node[e.NodeOut].y.get(0)-30;
                }
                else
                { label.setBounds(data.Node[e.NodeIn].x.get(0)-6,data.Node[e.NodeIn].y.get(0)+33*(500-data.Node[e.NodeOut].y.get(I1))/100,data.Node[e.NodeOut].x.get(I1)-6,data.Node[e.NodeOut].y.get(I1)+33*(500-data.Node[e.NodeOut].y.get(I1))/100);
                    x=(data.Node[e.NodeIn].x.get(0)-6+10+data.Node[e.NodeOut].x.get(I1)-6)/2;
                    y=data.Node[e.NodeIn].y.get(0)+33;
                }

                JLabel name = new JLabel(e.name);
                name.setBounds(x+80,y-10+33*(500-data.Node[e.NodeIn].y.get(I1))/100,x+85,y-5+33*(500-data.Node[e.NodeIn].y.get(I1))/100);
                panel.add(name);
            }
        }
        panel.add(label);

    }
    public ImageIcon scaling(ImageIcon im, int condition) {
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

    public static class MyCanvas extends JPanel{
       static final int MAX_WIDTH = 700;
       static final int MAX_HEIGHT = 700;
        static BufferedImage Image
                = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        public void paintComponent(Graphics g)
        {   super.paintComponent(g);
            Graphics2D g2= (Graphics2D) g;
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

      static    BufferedImage Image
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
    public int R(double vs , double ve) {
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
    public double drawlabel(JPanel p,double T,double[] A) {
        double max=0,min=0;
        for(int a=0;a<=10;a++)
        {JLabel l = new JLabel(String.format("%6.1e",a*T/10));
            l.setBounds(50+a*54,385,55+a*54,390);
            p.add(l);
        }
        for(int i=0;i<A.length;i++) {
            if(A[i]>max)
                max=A[i];
            if(A[i]<min)
                min=A[i];
        }

        for(int a=0;a<=10;a++) {
            JLabel l = new JLabel(String.format("%6.1e",min+a*(max-min)/10));
            l.setBounds(2,400-a*35,50,-52+395-a*35); //****//
            p.add(l);
        }
        return max;
    }
   public Show(ReadTxt data) throws IOException {

        double dt=data.dt,T=data.finalTime;
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
        int x=100,Y = -1;
        int count = 0;
        for(int i=0;i<=data.node;i++) {
            ArrayList<Node> connectedNode = new ArrayList<Node>();
            count=0;
            for(Branch b:data.Node[i].branches) {
                if(b.NodeIn==0 ||
                        (int)b.NodeOut==0)
                    count++;
                if(i==b.NodeIn)
                    connectedNode.add(data.Node[b.NodeOut]);
                else
                    connectedNode.add(data.Node[b.NodeIn]);
            }
            if(count>1) data.Node[i].cond = count;
            for(int k=0;k<connectedNode.size();k++)
                for(int q=k+1;q<connectedNode.size();q++) {
                    if(connectedNode.get(k).equals(connectedNode.get(q)))
                        data.Node[i].cond1++;

                }
        }

        int X=0;
        for(int i=0;i<=data.node;i++) {
            if(i%6==1 || i==0)
                x=200;
            if(data.Node[i].cond>0) {
                if(i == 0)
                    for(int j=0;j<6;j++) {
                        data.Node[i].y.add((int) 500);
                        data.Node[i].x.add((int) (x+50*j));

                    }
                else {

                    for(int j=0;j<data.Node[i].cond;j++) {
                        if(x+50*j==200)
                            Y++;
                       data.Node[i].y.add((int) 400-Y*100);
                       data.Node[i].x.add((int) (x+50*j));
                    }

                }
                X=data.Node[i].x.size();
            }


            else {
                if(x==200)
                    Y++;
                data.Node[i].y.add((int) 400-Y*100);
                data.Node[i].x.add((int) (x));
            }
            if(data.Node[i].cond1>0) {
                for(int j=1;j<=data.Node[i].cond1;j++) {
                    data.Node[i].y.add((int) 400-(Y+j)*100);
                    data.Node[i].x.add((int)data.Node[i].x.get(0));

                X=data.Node[i].x.size()-data.Node[i].cond1;
            }
            x += 50*(X);

        }

        RUN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                int l=0;
                for(int i=0;i<=data.node;i++) {
                    // int cte =0 ;
                    for(Branch b:data.Node[i].branches) {
                        if(b.draw==0) {
                            if(data.Node[b.NodeIn].y.get(0) !=(int)data.Node[b.NodeOut].y.get(0)) {
                                if(b instanceof Resistor) {
                                    ImageIcon resistor = new ImageIcon("r2.png");
                                    resistor = scaling(resistor,1);
                                    JLabel lr2 = new JLabel(resistor);
                                    draw(b,panel,lr2,data.Node[i],l);

                                }
                                if(b instanceof Capacitor) {

                                    ImageIcon capacitor = new ImageIcon("c2.png");
                                    capacitor = scaling(capacitor,1);
                                    JLabel lc2 = new JLabel(capacitor);
                                    draw(b,panel,lc2,data.Node[i],l);
                                }
                                if(b instanceof Inductor) {
                                    ImageIcon Inductor = new ImageIcon("l2.jpg");
                                    Inductor = scaling(Inductor,1);
                                    JLabel lL2 = new JLabel(Inductor);
                                    draw(b,panel,lL2,data.Node[i],l);
                                }
                                if(b instanceof D) {
                                    ImageIcon Diod = new ImageIcon("d2.png");
                                    Diod=scaling(Diod,1);
                                    JLabel ld2 = new JLabel(Diod);
                                    ImageIcon Diod2 = new ImageIcon("d3.png");
                                    Diod2 = scaling(Diod2,1);
                                    JLabel ld3 = new JLabel(Diod2);
                                    if(data.Node[i].equals(data.Node[b.NodeIn]))
                                        draw(b,panel,ld2,data.Node[i],l);
                                    if(data.Node[i].equals(data.Node[b.NodeOut]))
                                        draw(b,panel,ld3,data.Node[i],l);
                                }
                                if(b instanceof CurrentDI ||
                                        b instanceof CurrentDV) {
                                    ImageIcon CurrentD = new ImageIcon("cd2.png");
                                    CurrentD = scaling(CurrentD,1);
                                    JLabel lCD2 = new JLabel(CurrentD);
                                    ImageIcon CurrentD2 = new ImageIcon("cd3.png");
                                    CurrentD2 = scaling(CurrentD2,1);
                                    JLabel lCD3 = new JLabel(CurrentD2);
                                    if(data.Node[i].equals(data.Node[b.NodeIn]))
                                        draw(b,panel,lCD2,data.Node[i],l);
                                    if(data.Node[i].equals(data.Node[b.NodeOut]))
                                        draw(b,panel,lCD3,data.Node[i],l);
                                }
                                if(b instanceof CurrentSource) {
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
                                    if(data.Node[i].equals(data.Node[b.NodeIn]))
                                        draw(b,panel,lCI2,data.Node[i],l);
                                    if(data.Node[i].equals(data.Node[b.NodeOut]))
                                        draw(b,panel,lCI3,data.Node[i],l);
                                }
                                if(b instanceof VoltageDI ||
                                        b instanceof VoltageDV) {
                                    ImageIcon VoltageD = new ImageIcon("vd3.png");
                                    VoltageD = scaling( VoltageD,1);
                                    JLabel lVD2 = new JLabel(VoltageD);
                                    ImageIcon VoltageD2 = new ImageIcon("vd2.png");
                                    VoltageD2 = scaling( VoltageD2,1);
                                    JLabel lVD3 = new JLabel(VoltageD2);
                                    if(data.Node[i].equals(data.Node[b.NodeIn]))
                                        draw(b,panel,lVD2,data.Node[i],l);
                                    if(data.Node[i].equals(data.Node[b.NodeOut]))
                                        draw(b,panel,lVD3,data.Node[i],l);
                                }
                                if(b instanceof VoltageSource) {
                                    if(((VoltageSource)b).freq==0) {
                                        ImageIcon VoltageIndependent = new ImageIcon("vi3.png");
                                        VoltageIndependent = scaling(VoltageIndependent,1);
                                        JLabel lVI2 = new JLabel(VoltageIndependent);
                                        ImageIcon VoltageIndependent2 = new ImageIcon("vi2.png");
                                        VoltageIndependent2 = scaling(VoltageIndependent2,1);
                                        JLabel lVI3 = new JLabel(VoltageIndependent2);
                                        if(data.Node[i].equals(data.Node[b.NodeIn]))
                                            draw(b,panel,lVI2,data.Node[i],l);
                                        if(data.Node[i].equals(data.Node[b.NodeOut]))
                                            draw(b,panel,lVI3,data.Node[i],l);
                                    }
                                    else {
                                        ImageIcon AC = new ImageIcon("ac2.png");
                                        AC = scaling(AC,1);
                                        JLabel lAC = new JLabel(AC);
                                        draw(b,panel,lAC,data.Node[i],l);

                                    }

                                }
                            }
                            else {
                                //System.out.println("l");
                                if(b instanceof Resistor) {
                                    ImageIcon resistor1 = new ImageIcon("r1.png");
                                    resistor1 = scaling(resistor1,0);
                                    JLabel lr1 = new JLabel(resistor1);
                                    draw(b,panel,lr1,data.Node[i],l);
                                }
                                if(b instanceof Capacitor) {
                                    ImageIcon capacitor1 = new ImageIcon("c1.png");
                                    capacitor1 = scaling(capacitor1,0);
                                    JLabel lc1 = new JLabel(capacitor1);
                                    draw(b,panel,lc1,data.Node[i],l);
                                }
                                if(b instanceof Inductor) {
                                    ImageIcon Inductor1 = new ImageIcon("l1.jpg");
                                    Inductor1 = scaling(Inductor1,0);
                                    JLabel lL1 = new JLabel(Inductor1);
                                    draw(b,panel,lL1,data.Node[i],l);
                                }
                                if(b instanceof D) {
                                    ImageIcon Diod1 = new ImageIcon("d1.png");
                                    Diod1=scaling(Diod1,0);
                                    JLabel ld1 = new JLabel(Diod1);
                                    ImageIcon Diod4 = new ImageIcon("d4.png");
                                    Diod4 = scaling(Diod4,0);
                                    JLabel ld4 = new JLabel(Diod4);
                                    if(data.Node[i].equals(data.Node[b.NodeIn]))
                                        draw(b,panel,ld1,data.Node[i],l);
                                    if(data.Node[i].equals(data.Node[b.NodeOut]))
                                        draw(b,panel,ld4,data.Node[i],l);
                                }
                                if(b instanceof CurrentDI ||
                                        b instanceof CurrentDV) {
                                    ImageIcon CurrentD1 = new ImageIcon("cd1.png");
                                    CurrentD1 = scaling(CurrentD1,0);
                                    JLabel lCD1 = new JLabel(CurrentD1);
                                    ImageIcon CurrentD4 = new ImageIcon("cd4.png");
                                    CurrentD4 = scaling(CurrentD4,0);
                                    JLabel lCD4 = new JLabel(CurrentD4);
                                    if(data.Node[i].equals(data.Node[b.NodeIn]))
                                        draw(b,panel,lCD1,data.Node[i],l);
                                    if(data.Node[i].equals(data.Node[b.NodeOut]))
                                        draw(b,panel,lCD4,data.Node[i],l);
                                }
                                if(b instanceof CurrentSource) {
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
                                    if(data.Node[i].equals(data.Node[b.NodeIn]))
                                        draw(b,panel,lCI1,data.Node[i],l);
                                    if(data.Node[i].equals(data.Node[b.NodeOut]))
                                        draw(b,panel,lCI4,data.Node[i],l);
                                }
                                if(b instanceof VoltageDI ||
                                        b instanceof VoltageDV) {
                                    ImageIcon VoltageD1 = new ImageIcon("vd1.png");
                                    VoltageD1 = scaling( VoltageD1,0);
                                    JLabel lVD1 = new JLabel(VoltageD1);
                                    ImageIcon VoltageD4 = new ImageIcon("vd4.png");
                                    VoltageD4 = scaling( VoltageD4,0);
                                    JLabel lVD4 = new JLabel(VoltageD4);
                                    if(data.Node[i].equals(data.Node[b.NodeIn]))
                                        draw(b,panel,lVD1,data.Node[i],l);
                                    if(data.Node[i].equals(data.Node[b.NodeOut]))
                                        draw(b,panel,lVD4,data.Node[i],l);
                                }
                                if(b instanceof VoltageSource) {
                                    if(((VoltageSource)b).freq==0) {
                                        ImageIcon VoltageIndependent1 = new ImageIcon("vi1.png");
                                        VoltageIndependent1 = scaling(VoltageIndependent1,0);
                                        JLabel lVI1 = new JLabel(VoltageIndependent1);
                                        ImageIcon VoltageIndependent4 = new ImageIcon("vi4.png");
                                        VoltageIndependent4 = scaling(VoltageIndependent4,0);
                                        JLabel lVI4 = new JLabel(VoltageIndependent4);
                                        if(data.Node[i].equals(data.Node[b.NodeIn]))
                                            draw(b,panel,lVI1,data.Node[i],l);
                                        if(data.Node[i].equals(data.Node[b.NodeOut]))
                                            draw(b,panel,lVI4,data.Node[i],l);
                                    }
                                    else {
                                        ImageIcon AC = new ImageIcon("ac1.png");
                                        AC = scaling(AC,0);
                                        JLabel lAC = new JLabel(AC);
                                        draw(b,panel,lAC,data.Node[i],l);

                                    }
                                }
                            }
                            b.draw=1;
                        }
                        l++;
                    }
                    int size = 0;
                    if(i==0) {
                        for(int j=0;j<=data.node;j++) {
                            if(data.Node[j].equals(data.Node[i]))
                                continue;
                            for(int k=0;k<data.Node[j].x.size();k++)
                                if(data.Node[j].x.get(k)>size)
                                    size = data.Node[j].x.get(k);
                        }
                        size = (size - 200)/50;
                    }
                    else
                        size = data.Node[i].x.size()-1-data.Node[i].cond1;
                    for(int k=0;k< size;k++) {
                        ImageIcon wire1 = new ImageIcon("w.png");
                        Image scaled = wire1.getImage();
                        Image modified = scaled.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
                        wire1 = new ImageIcon(modified);
                        JLabel lw = new JLabel(wire1);
                        if( data.Node[i].y.get(k)<500)
                            lw.setBounds(data.Node[i].x.get(k)+10, data.Node[i].y.get(k)+33,data.Node[i].x.get(k+1)+7, data.Node[i].y.get(k+1)+33);
                        else
                            lw.setBounds(data.Node[i].x.get(k)+10, data.Node[i].y.get(k),data.Node[i].x.get(k+1)+7, data.Node[i].y.get(k+1));
                        panel.add(lw);
                    }
                    for(int k=0;k<data.Node[i].x.size()-1;k++) {
                        if((int)data.Node[i].x.get(k)==(int)data.Node[i].x.get(k+1)) {
                            ImageIcon wire1 = new ImageIcon("w1.png");
                            Image scaled = wire1.getImage();
                            Image modified = scaled.getScaledInstance(40, 50, java.awt.Image.SCALE_SMOOTH);
                            wire1 = new ImageIcon(modified);
                            JLabel lw = new JLabel(wire1);
                            lw.setBounds(data.Node[i].x.get(k)-3, data.Node[i].y.get(k)+50,data.Node[i].x.get(k+1)+4, data.Node[i].y.get(k+1)+50);
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
                       int l=0;
                        for(Branch Element:data.Branch) {
                            System.out.println(Element.v[3]);
                            if(Element.name.equals(e.getText())) {
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
                                double max=0;
                                max=drawlabel(pic,T,Element.v);
                                for(int j=0;j<T/dt;j++) {
                                    int r1 = 0;

                                    if(Element.v[0]<1 ||Element.v[(Element.v.length-1)]<1 )
                                        r1= R(Element.v[0],Element.v[(Element.v.length-1)]);
                                    //							       
                                    int k = j;
                                    int c = j;
                                    while(Element.v[c]==Element.v[k]) {
                                        MyCanvas.Image.setRGB((int)(50+k*540*dt/T),(int)(Math.round(570-(520/max*Element.v[k]))),Color.BLUE.getBlue());
                                        if(k==Element.v.length-1)
                                            break;
                                        k++;
                                        j=k-1;
                                    }
                                }
                                double max1=0;
                                max1=drawlabel(pic1,T,Element.i);
                                for(int j=0;j<T/dt;j++) {
                                    int r2=0;
                                    if(Element.i[0]<1 ||Element.i[(Element.i.length-1)]<1 )
                                        r2= R(Element.i[0],Element.i[(Element.i.length-1)]);
                                    int k = j;
                                    int c = j;
                                    while((double)Element.i[c]==(double)Element.i[k]) {
                                        MyCanvas1.Image.setRGB((int)(50+k*540*dt/T),(int)(Math.round(570-(520/max1*Element.i[k]))),Color.GREEN.getGreen());
                                        if(k==Element.i.length-1)
                                            break;
                                        k++;
                                        j=k-1;
                                    }
                                }
                                double max2=0;
                                max2=drawlabel(pic2,T,Element.p);
                                for(int j=0;j<T/dt;j++) {
                                    int r3=0;
                                    if(Element.p[0]<1 ||Element.p[(Element.p.length-1)]<1 )
                                        r3=R(Element.p[0],Element.p[(Element.p.length-1)]);
                                    int k = j;
                                    int c = j;
                                    while((double)Element.p[c]==(double)Element.p[k]) {
                                        MyCanvas2.Image.setRGB((int)(50+k*540*dt/T),(int)(Math.round(570-(520/max2*Element.p[k]))),Color.RED.getRed());
                                        if(k==Element.p.length-1)
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
                        l++;
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
}}

public class First {
    public static void main(String[] args) throws IOException {
   //Phase2 p=new Phase2("RCV.txt");
        //Phase p=new Phase();
        //graphic g=new graphic();
        SolveCircuit sC=new SolveCircuit("DVS.txt");
   sC.solveRLC();
   sC.calculateResult();
   sC.saveResult();
  //Show s=new Show(sC.data);
        ///int k= (int) (sC.data.finalTime/sC.data.dt);
        //System.out.println(sC.data.unions);
///for(int j=0;j<=sC.data.node;j++)System.out.println(sC.data.Node[j].v[k]);
     ///   System.out.println(sC.data.finalTime+" "+sC.data.dt);
       //// for(int i=0;i<k;i++)System.out.println(sC.data.R[0].i[i]+" "+sC.data.C[0].i[i]);
    }}
