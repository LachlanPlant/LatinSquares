package assignment2;
import java.util.*;

/**
 *
 * @author Lachlan
 */
public class LatinSquares {

        int nodecount;
        int totalcount = 0;
        long timetaken = 0;
        long sum;
        long branch;
        Random rng;

    /**
     *
     * @return
     */
    
   /**************************************************
    * 
    * Part A
    * 
    * 
    ***************************************************/ 
        
    //Keeps track of time for a run of the actual value;
   public void run(int n){
      totalcount=0;
      timetaken = 0;
      long start = System.nanoTime();
      genAllSquares(n);
      long stop = System.nanoTime();
      timetaken = stop - start;
   }

    /**
     * Sets up latin square for recursive generation
     * @param n
     */
    public void genAllSquares(int n){
     int[][] square = new int[n][n];  
     nodecount = 1;
     for(int i=0;i<square.length;i++){
        square[i][0] =i; 
     }
     for(int i=0;i<square.length;i++){
        square[0][i] =i;
     }
                                
     for(int x=0;x < n;x++){
        if(verify(square,x,1,1)){
           square[1][1]=x;  
           if(n==2){
              GenSquares(square,1,2,n); //Special case
           }
           else{   
              GenSquares(square,2,1,n);
           }                
        }
     }
     return;
   }

    /**
     * Recursive, Branches over all candidates for a position
     * i,j in the square, if square is full, increments totalCount
     * @param square
     * @param i
     * @param j
     * @param n
     */
    private void GenSquares(int[][] square,int i, int j, int n){                                
      nodecount++;
      if(j==n){
         totalcount++;
         return;
      }      
      int newi=i+1;
      int newj= j;            
       
      if(newi==n){    
         newj=j+1;
         newi=1;
      }         
      for(int x=0;x < n;x++){
         if(verify(square,x,i,j)){
            square[i][j]=x;
            GenSquares(square,newi,newj,n);                
         }
      }
      return;
   }
   
   /**************************************************
    * 
    * Part B
    * 
    * 
    ***************************************************/

    

    /**
     * Runs tree estimation probe, reps times
     * @param n
     * @param reps
     * @return
     */
    public long runTreeEstRep(int n, int reps){
       
       rng= new Random();
       long total =0;
       for(int i=0;i<reps;i++){
           runTreEstTest(n);
           total+= sum;
       }
       return total/reps;
   }

    /**
     * Runner for tree size estimation
     * @param n
     */
    public void runTreEstTest(int n){       
      sum=1;
      branch=1;
      genAllSquaresTreeEst(n);
   }
    
    /**
     * Sets up latin squares then does initial random branch
     * @param n
     */
    public void genAllSquaresTreeEst(int n){
     int[][] square = new int[n][n];  
     
     for(int i=0;i<square.length;i++){
        square[i][0] =i; 
     }
     for(int i=0;i<square.length;i++){
        square[0][i] =i;
     }
     
     LinkedList<Integer> cand = new LinkedList<Integer>();
      for(int x=0;x < n;x++){
         if(verify(square,x,1,1)){
            cand.add(x);
         }
      }
      int cands = cand.size();
      branch = branch*cands;
      sum+= branch;
      int x = cand.get(rng.nextInt(cands));
      square[1][1]=x;
      if(n==2){
              GenSquaresTreeEst(square,1,2,n); //Special case
           }
           else{   
              GenSquaresTreeEst(square,2,1,n);
           } 
     return;
   }

    /**
     * Recursivly probes a random path, calculating the estimated tree size
     * @param square
     * @param i
     * @param j
     * @param n
     */
    private void GenSquaresTreeEst(int[][] square,int i, int j, int n){                             
     
      if(j==n){
         return;
      }
      
      int newi=i+1;
      int newj= j;            
       
      if(newi==n){    
         newj=j+1;
         newi=1;
      }
      LinkedList<Integer> cand = new LinkedList<Integer>();
      for(int x=0;x < n;x++){
         if(verify(square,x,i,j)){
            cand.add(x);
         }
      }
      int cands = cand.size();
      
      branch = branch*cands;
      sum+= branch;
      if(cands==0) return;
      int x = cand.get(rng.nextInt(cands));
      square[i][j]=x;
      GenSquaresTreeEst(square,newi,newj,n);                
       
      return;
   }
   
   /**************************************************
    * 
    * Part C
    * 
    * 
    ***************************************************/
   
   

    /**
     * Runs reps probing calls, averageing the number of estimated solutions
     * @param n
     * @param reps
     * @return
     */
    public long runTreeEstRepGeneralized(int n, int reps){
       
      rng= new Random();
       long total =0;
       for(int i=0;i<reps;i++){
           runTreEstTestGeneralized(n);
           total+= sum;
       }
       return total/reps;
   }

    public void runTreEstTestGeneralized(int n){       
      sum=1;
      branch=1;
      genAllSquaresTreeEstGeneralized(n);
   }
    /**
     * Sets up latin squares then does initial random branch
     * @param n
     */
    public void genAllSquaresTreeEstGeneralized(int n){
     int[][] square = new int[n][n];  
    
     for(int i=0;i<square.length;i++){
        square[i][0] =i; 
     }
     for(int i=0;i<square.length;i++){
        square[0][i] =i;
     }
     
     LinkedList<Integer> cand = new LinkedList<Integer>();
      for(int x=0;x < n;x++){
         if(verify(square,x,1,1)){
            cand.add(x);
         }
      }
      int cands = cand.size();
      branch = branch*cands;
      sum+= branch;
      int x = cand.get(rng.nextInt(cands));
      square[1][1]=x;
      if(n==2){
              GenSquaresTreeEstGeneralized(square,1,2,n); //Special case
           }
           else{   
              GenSquaresTreeEstGeneralized(square,2,1,n);
           } 
     return;
   }

    /**
     * Recursivly probes a random path, calculating the estimated number of
     * solutions
     * @param square
     * @param i
     * @param j
     * @param n
     */
    public void GenSquaresTreeEstGeneralized(int[][] square,int i, int j, int n){                             
      
      if(j==n){
         //totalcount++;
         return;
      }
      int weight =0;
      if(i==n-1&&j==n-1){
          weight=1;
      }
      int newi=i+1;
      int newj= j;            
       
      if(newi==n){    
         newj=j+1;
         newi=1;
      }
      LinkedList<Integer> cand = new LinkedList<Integer>();
      for(int x=0;x < n;x++){
         if(verify(square,x,i,j)){
            cand.add(x);
         }
      }
      int cands = cand.size();
      
      branch = branch*cands;
      sum+= branch*weight;
      if(cands==0) return;
      int x = cand.get(rng.nextInt(cands));
      square[i][j]=x;
      GenSquaresTreeEstGeneralized(square,newi,newj,n);                
       
      return;
   }
   
   /**************************************************
    * 
    * Utilities
    * 
    * 
    ***************************************************/
   
    /**
     * Checks if x is valid at s[i][j]
     * 
     * @return
     */
    public boolean verify(int[][] s,int x, int i, int j){
      for(int a=0; a<i;a++){
         if(s[a][j]==x) return false;
      }
      for(int a=0; a<j;a++){
         if(s[i][a]==x) return false;
      }
      return true;
   }
   public int resultCount(){
            return totalcount;
    }

    
    public String getFormatedTime(long input){
       long nano = input%1000;
       long micro = (input/1000)%1000;
       long mili = (input/1000000)%1000;
       long seconds1 = input/1000000000;
       long mins1 = seconds1 /60;
       long hours1 = mins1 /60;
       long days1 = hours1 / 24;
       long years1 = days1/365 ;
       
       long seconds = seconds1 %60;
       long mins = mins1 %60;
       long hours = hours1 %24;
       long days = days1 %365;
       long years = years1 ;
       return String.format("$%dY:%dD:%dH:%dM:%dS:%dms:%d\\mu s:%dns $", 
               years,days,hours,mins,seconds,mili,micro,nano);
       
   }

    /**
     * Runs all experiments, printouts are in a latex table form
     * @param args
     */
    public static void main(String[] args){
      LatinSquares l= new LatinSquares();
      long avgTime =0;
      
      long[] cands = new long[8];
      long[] res = new long[8];
      System.out.println("Order & Total & Nodes & Time \\\\ \\hline");
      for(int i=2;i<=7;i++){
         l.run(i);
         cands[i]= l.nodecount;
         res[i] = l.totalcount;
         System.out.printf(" %d & %d & %d & %s \\\\ \\hline", 
                 i,l.resultCount(),l.nodecount, l.getFormatedTime(l.timetaken));
         avgTime+= l.timetaken/l.nodecount;
         System.out.println();
      } 
      avgTime /= 5;
      
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println("Order & Nodes & Repititions & Nodes Estimated & "
              + "$\\frac{Nodes}{Estimated}$ \\\\ \\hline");
      for(int i=2;i<=7;i++){
         for(int reps=1; reps <=5;reps ++){
             long runs= l.runTreeEstRep(i, reps*10000);
             double ratio = (double)cands[i]/(double)runs;
             System.out.printf(" %d & %d & %d & %d & %f \\\\ \\hline", i,
                     cands[i],reps*10000,runs,ratio);
             System.out.println();
         }
         System.out.println("& & & & \\\\ \\hline");
      }
      long eight=0;
      System.out.println("& & & & \\\\ \\hline");
      for(int reps=1; reps <=5;reps ++){
          long runs= l.runTreeEstRep(8, reps*10000);
          double ratio = (double)l.nodecount/(double)runs;
          System.out.printf(" %d & Na & %d & %d & Na \\\\ \\hline", 8,
                  reps*10000,runs);
          System.out.println();
          eight=runs;
      }
      System.out.println();
      System.out.println();
      
      System.out.println();
      System.out.println(avgTime);
      System.out.println(l.getFormatedTime(avgTime*eight));
      
      
      System.out.println();
      
      System.out.println("Order & Number of Squares & Squares Estimated & "
              + "$\\frac{Squares}{Estimated}$ \\\\ \\hline");
      for(int i=2;i<=7;i++){
         for(int reps=1; reps <=5;reps ++){
             long runs= l.runTreeEstRepGeneralized(i, reps*10000);
             double ratio = (double)res[i]/(double)runs;
             System.out.printf(" %d & %d & %d & %d & %f \\\\ \\hline", i,
                     res[i],runs,reps*10000,ratio);
             System.out.println();
         }
         System.out.println("& & & & \\\\ \\hline");
      }
      long answerSeven= 16942080;
      long answerEight= Long.parseLong("535281401856");
      
      System.out.println("& & & & \\\\ \\hline");
      for(int reps=1; reps <=5;reps ++){
          long runs= l.runTreeEstRepGeneralized(8, reps*10000);
             double ratio = (double)answerEight/(double)runs;
             System.out.printf(" %d & %d & %d & %d & %f \\\\ \\hline", 8,
                     answerEight,runs,reps*10000,ratio);
             System.out.println();
      }
              
                
   }
                      
}
                        
                        
