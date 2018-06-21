package st;

import java.util.Scanner;

public class JAVA {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		while(sc.hasNext()){
			int a,b,c,d;
			a=sc.nextInt();
			b=sc.nextInt();
			c=sc.nextInt();
			d=sc.nextInt();

			int n=0;
			
			int[] arr=new int[4];
			arr[0]=a; arr[1]=b; arr[2]=c; arr[3]=d;
			for(int i=0;i<4;i++){  //找最大 然後補城2的刺方
				if(arr[i]>n)
					n=arr[i];
			}

			String bin=Integer.toBinaryString(n);
			int two=0;
			for(int i=0;i<bin.length();i++){
				if(bin.charAt(i)=='1')
					two++;
			}
			if(two>1)
			    n=(int)Math.pow(2,bin.length());
			else if(n==1)
			    n=2;

			
			int[][] A=new int[n][n];       //考慮補城2的刺方舉證 補0
			int[][] B=new int[n][n];
			
			for(int i=0;i<n;i++){           //補0
				for(int j=0;j<n;j++){
					A[i][j]=0;
					B[i][j]=0;
				}
			}

		
			for(int i=0;i<a;i++){
				for(int j=0;j<b;j++){
					A[i][j]=sc.nextInt();
				}
			}
			for(int i=0;i<c;i++){
				for(int j=0;j<d;j++){
					B[i][j]=sc.nextInt();
				}
			}



			print(strassen(n,A,B),a,d);
			System.gc();
			
		}
		
		

	}
	
	static int[][] strassen(int n,int[][] A,int[][] B){

		if(n<=32){ //做M1~M7 AB已被切成2X2   真的是16比8好  32又更快了 但是大小沒變  可以考慮上面的n如果小於32 就直接成了不用補城2次方
			
			int[][] C=new int[A.length][B[0].length];
			for(int i=0;i<A.length;i++){
				for(int j=0;j<A.length;j++){
					int total=0;
					for(int k=0;k<B.length;k++){
						total+=(A[i][k]*B[k][j]);
					}
					C[i][j]=total;
				}
			}
			return C;
			
		}else{     //做切割直到n/2=2
			int i,j,m=n/2;
			int[][] a11=new int[m][m];
			int[][] a12=new int[m][m];
			int[][] a21=new int[m][m];
			int[][] a22=new int[m][m];
			
			int[][] b11=new int[m][m];
			int[][] b12=new int[m][m];
			int[][] b21=new int[m][m];
			int[][] b22=new int[m][m];
			
 			for(i=0;i<m;i++){
 				for(j=0;j<m;j++){
 					a11[i][j]=A[i][j];
 					b11[i][j]=B[i][j];
 				}
 			}
 			
 			for(i=0;i<m;i++){
 				for(j=m;j<n;j++){
 					a12[i][j-m]=A[i][j];
 					b12[i][j-m]=B[i][j];
 				}
 			}
 			
 			for(i=n/2;i<n;i++){
 				for(j=0;j<m;j++){
 					a21[i-m][j]=A[i][j];
 					b21[i-m][j]=B[i][j];
 				}
 			}
 			
 			for(i=n/2;i<n;i++){
 				for(j=n/2;j<n;j++){
 					a22[i-m][j-m]=A[i][j];
 					b22[i-m][j-m]=B[i][j];
 				}
 			}

             return merge(strassen(m,add(a11,a22),add(b11,b22)),    //M1
 					strassen(m,add(a21,a22),b11),              //M2
 					strassen(m,a11,sub(b12,b22)),              //M3
 					strassen(m,a22,sub(b21,b11)),              //M4
 					strassen(m,add(a11,a12),b22),              //M5
 					strassen(m,sub(a21,a11),add(b11,b12)),     //M6
 					strassen(m,sub(a12,a22),add(b21,b22))       //M7
 					,n);
 			//return C;
		}
	}
	
	static int[][] merge(int[][] M1,int[][] M2,int[][] M3,int[][] M4,int[][] M5,int[][] M6,int[][] M7,int n){
		int m=n/2,i,j;
		int[][] c1=new int[m][m];
		int[][] c2=new int[m][m];
		int[][] c3=new int[m][m];
		int[][] c4=new int[m][m];
		
		c1=add(sub(add(M1,M4),M5),M7);
	    c2=add(M3,M5);
	    c3=add(M2,M4);
	    c4=add(sub(add(M1,M3),M2),M6);
	    
	    int[][] C=new int[n][n];
	    for(i=0;i<m;i++){
	    	for(j=0;j<m;j++){
	    		C[i][j]=c1[i][j];
	    	}
	    }
	    
	    for(i=0;i<m;i++){
	    	for(j=0;j<m;j++){
	    		C[i][j+m]=c2[i][j];
	    	}
	    }
	    
	    for(i=0;i<m;i++){
	    	for(j=0;j<m;j++){
	    		C[i+m][j]=c3[i][j];
	    	}
	    }
	    for(i=0;i<m;i++){
	    	for(j=0;j<m;j++){
	    		C[i+m][j+m]=c4[i][j];
	    	}
	    }
	    

		return C;
	}
	
	static int[][] add(int[][] A,int[][] B){    //矩陣相加
		
		int[][] C=new int[A.length][A.length];
		for(int i=0;i<A.length;i++){
			for(int j=0;j<A.length;j++){
				C[i][j]=A[i][j]+B[i][j];
			}
		}
		return C;
		
	}
	
	static int[][] sub(int[][] A,int[][] B){    //矩陣相減
		
		int[][] C=new int[A.length][A.length];
		for(int i=0;i<A.length;i++){
			for(int j=0;j<A.length;j++){
				C[i][j]=A[i][j]-B[i][j];
			}
		}
		return C;
		
	}
	
	
	
	static void print(int[][] A,int a,int d){
		int i,j;
		for(i=0;i<a;i++){
			for(j=0;j<d;j++){
				System.out.print(A[i][j]+" ");
			}
			System.out.println();
		}

	}

}

