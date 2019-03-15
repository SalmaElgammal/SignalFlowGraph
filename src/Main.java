
import java.util.Scanner;

public class Main {
	static int no_nodes ;
	static String[] nodes ;
	static int inputNode ;
	static int outputNode ;
	static int no_branches ;
	static int no_nonTouching ;
	static int no_initialbranches ;
	static int[] FirstNode ;
    static int[] SecondNode ;
    static int[] gains ;
    static int[][] paths ;
    static int no_paths = 0 ;
    static int no_loops = 0 ;
    static int len = 0 ;
    static int[][] path ;
    static int[][] loop ;
    static int[][] loops ;
	static int size = 0;
	static int counter = 0;
	static boolean check = false;
	static int lsize = 0;
	static int llen = 0 ;
	static int[][] Touching ;
	static int[][] TouchingLoops ;
	static int[][] nonTouching ;
	static int[][] nonTouchingLoops ;
	static int[][] gnonTouchingLoops1 ;
	static int[][] gnonTouchingLoops2 ;
	public Main(int nNodes,int nBranches,int[] startNodes,int[] endNodes,int[] gains) {
		no_nodes=nNodes;
		no_branches=nBranches;
		FirstNode=startNodes;
		SecondNode=endNodes;
		this.gains=gains;
		inputNode=1;
		outputNode=nNodes;
		build();
	}
	private void build() {
		 
		path = new int[no_branches][no_branches];
		for(int i = 0 ; i < no_branches ; i++){
		 if(FirstNode[i] == inputNode){
			 counter = 0;
		     size = 0;
		     findPath(i);
		     len ++;
		}
		}
		paths = new int[no_branches][no_branches];
		for(int i = 0 ; i < len ; i++){
			for(int j = 0 ; path[i][j]!=0 ; j++){
				if(path[i][j]==outputNode){
					for(int l =0 ; !(l > j) ; l++){
						paths[no_paths][l] = path[i][l];
					}
					no_paths++;
				}
		}
		}
		loop = new int[no_branches][no_branches];
		for(int i = 0 ; i < no_branches ; i++){
		 if(!(FirstNode[i] < SecondNode[i])){
			 counter = 0;
			 lsize = 0;
		     findLoops(i);
		}
		
		}
		loops =new int[no_branches][no_branches];
		for(int i = 0 ; i < llen ; i++){
			for(int j = 1 ; ((j<no_branches)&&(loop[i][j]!=0)) ; j++){
				if(loop[i][j]==loop[i][0]){
					for(int l =0 ; !(l > j) ; l++){
						loops[no_loops][l] = loop[i][l];
					}
					no_loops++;
				}
			}
			}
		TouchingLoops();
		nonTouchingLoops(2);
	}
    
	
	public int calculateDelta() {
		int delta=0;
		int gainLoops=1;
		for(int i=0;i<no_paths;i++) {
			gainLoops=gainLoops*calculateGain(paths[i]);
		}
		
		//calculate gain of non-Touching loops
		
		return 1-gainLoops;
		
	}
	public int calculateTF() {
		int tf=0;
		for(int i=0;i<no_paths;i++) {
			tf=tf+(deltaI(i)*calculateGain(paths[i]));
		}
		return tf/calculateDelta();
	}
	public int deltaI(int i) {
		int delta=calculateDelta();
		for(int j=0;j<no_loops;j++) {
			if(checkIfTouches(loops[j],paths[i])) {
				delta=delta-calculateGain(loops[j]);
			}
		}
		return delta;
		
	}
	
	public boolean checkIfTouches(int[] loop,int[] forwardPath) {
		for(int i=0;i<loop.length;i++) {
			for(int j=0;j<forwardPath.length;j++) {
				if(loop[i]==forwardPath[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int calculateGain(int[] A) {
		int gain=1;
		for(int i=0;i<A.length-1;i++) {
			gain=gain*getGain(A[i],A[i+1]);
		}
		return gain;
		
	}
	public int getGain(int start,int end) {
		for(int i=0;i<no_branches;i++) {
			for(int j=0;j<no_branches;j++) {
				if(start==FirstNode[i] && end==SecondNode[j]) {
					return gains[i];
				}
			}
		}
		return 1;
	}
	public static void nonTouchingLoops(int i){
		for(int j = 0 ; j < no_loops ; j++){
			
		}
	}
	public static void TouchingLoops(){
		int s = 0 ;
		Touching = new int[no_branches][no_branches];
		for(int i = 0 ; i < no_loops ; i++){
			Touching[i][0] = i+1 ;
			s=1;
			for(int j = i ; j < no_loops ; j++ ){
				check = false ;
				for(int l = 0 ; ((l<no_branches)&&(loops[i][l] != 0)) ; l++){
					for(int m = 0 ; ((m<no_branches)&&(loops[j][m] !=0)) ; m++){
						if(loops[i][l] == loops[j][m]){
							check = true ;
						}
					}
				}
				if(check){
					Touching[i][s] = j+1;
					s++;
			}
		}
	}
	}
	public static void nonTouchingLoops(){
		int s = 0 ;
		nonTouching = new int[no_loops*no_loops][2];
		for(int i = 0 ; i < no_loops ; i++){
			for(int j = i+1 ; j < no_loops ; j++ ){
				check = false ;
				for(int l = 0 ; ((l<no_branches)&&(loops[i][l] != 0)) ; l++){
					for(int m = 0 ; ((m<no_branches)&&(loops[j][m] !=0)) ; m++){
						if(loops[i][l] == loops[j][m]){
							check = true ;
						}
					}
				}
				if(!check){
					nonTouching[s][0] = i+1 ;
					nonTouching[s][1] = j+1;
					s++;
			}
		}
	}
		no_nonTouching = 0;
		for(int i = 0 ; i < s ; i++){
			if(nonTouching[i][1] != 0){
				nonTouchingLoops[no_nonTouching] = nonTouching[i];
				no_nonTouching++;
			}
		}
	}
	public static boolean Touch(int i , int j){
		check = false ;
		for(int l = 0 ; ((l<no_branches)&&(loops[i][l] != 0)) ; l++){
			for(int m = 0 ; ((m<no_branches)&&(loops[j][m] !=0)) ; m++){
				if(loops[i][l] == loops[j][m]){
					check = true ;
				}
			}
		}
		return check;
	}
	private static void findPath(int i){
		int z ;
		boolean exist = false;
		if(SecondNode[i]==outputNode){
			path[len][size] = FirstNode[i];
			size ++;
			path[len][size] = outputNode;
		}
		else if((SecondNode[i] > FirstNode[i])){
			path[len][size] = FirstNode[i];
			size++;
			for(int j = 0 ; j < no_branches ; j++ ){
				if(FirstNode[j] == SecondNode[i]){
					if(counter > 0){
						exist = false ;
						for(z = 0 ; z < size; z++){
							if(path[len][z]== FirstNode[j]){
								exist = true; 
						}
						}	
						if(exist){
						len++;
						}
						for(int l = 0 ; (exist && path[len-1][l]!= FirstNode[j]); l++){
							path[len][l]=path[len-1][l];
							size = l+1;
						}
						
						}
					counter ++;
					findPath(j);
				}
			}
			}
		}
	
    /*private static void findLoops(int i){
    	int z ;
		boolean exist = false;
		if(SecondNode[i] == loop[llen][0]){
	           loop[llen][lsize] = loop[llen][0] ;
	           lsize ++;
		   }
		else if((SecondNode[i] > FirstNode[i])){
			loop[llen][lsize] = FirstNode[i];
	        lsize++;
	        for(int j = 0 ; j < no_branches ; j++ ){
	        	if(FirstNode[j] == SecondNode[i]){
	        		if(counter > 0){
						exist = false ;
						for(z = 0 ; z < lsize; z++){
							if(loop[llen][z]== FirstNode[j]){
								exist = true; 
						}
						}	
						if(exist){
						llen++;
						}
						for(int l = 0 ; (exist && loop[llen-1][l]!= FirstNode[j]); l++){
							loop[llen][l]=loop[llen-1][l];
							lsize = l+1;
						}
						
						}
					counter ++;
					findLoops(j);
	        }
		}
		}
    }*/
	private static void findLoops(int i){
		if(SecondNode[i] == FirstNode[i]){
			   loop[llen][lsize] = FirstNode[i];
		       lsize++;
	           loop[llen][lsize] = loop[llen][0] ;
	           lsize ++;
	           llen ++;
		   }
		else if ((SecondNode[i] < FirstNode[i])){
			loop[llen][lsize] = FirstNode[i];
		    lsize++;
		    for(int l = 0; l < no_branches ; l++){
		    	if(FirstNode[l] == SecondNode[i]){
		           findLoopPath(l,loop[llen][0]);
		           llen++;
		           loop[llen][0]=loop[llen-1][0];
		           lsize = 1;
		}
		    }
		}
	}
	private static void findLoopPath(int i, int endNode){
		int z ;
		boolean exist = false;
		if(SecondNode[i]==endNode){
			loop[llen][lsize] = FirstNode[i];
			lsize ++;
			loop[llen][lsize] = endNode;
		}
		else if((SecondNode[i] > FirstNode[i])){
			loop[llen][lsize] = FirstNode[i];
			lsize++;
			for(int j = 0 ; j < no_branches ; j++ ){
				if(FirstNode[j] == SecondNode[i]){
					if(counter > 0){
						exist = false ;
						for(z = 0 ; z < lsize; z++){
							if(loop[len][z]== FirstNode[j]){
								exist = true; 
						}
						}	
						if(exist){
						llen++;
						}
						for(int l = 0 ; (exist && loop[llen-1][l]!= FirstNode[j]); l++){
							loop[llen][l]=loop[llen-1][l];
							lsize = l+1;
						}
						}
					counter ++;
					findLoopPath(j,endNode);
				}
			}
			}
	}
    }


