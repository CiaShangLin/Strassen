package st;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

@SuppressWarnings("ALL")
public class TEST {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

			int[][] A,B;
			int a,b,c,d,n=0;
			st=new StringTokenizer(br.readLine()," ");
			a=Integer.parseInt(st.nextToken());
			b=Integer.parseInt(st.nextToken());
			c=Integer.parseInt(st.nextToken());
			d=Integer.parseInt(st.nextToken());


			int[] arr=new int[4];
			arr[0]=a; arr[1]=b; arr[2]=c; arr[3]=d;
			for(int i=0;i<4;i++){  //��̤j �M��ɫ�2�����
				if(arr[i]>n)
					n=arr[i];
			}
			

			
			if(n<=32){
				A=new int[a][b];       
				B=new int[c][d];
			}else{
                int two=n&(n-1);           //�P�_�O�_��2����� =0�O !=0���O
				if(two!=0){
					String bin=Integer.toBinaryString(n);
					n=(int)Math.pow(2,bin.length());
				}
				A=new int[n][n];       //�Ҽ{�ɫ�2������|�� ��0
				B=new int[n][n];

			}
			

			for(int i=0;i<a;i++){           //��J
				st=new StringTokenizer(br.readLine()," ");
				for(int j=0;j<b;j++){
					A[i][j]=Integer.parseInt(st.nextToken());
				}
			}
			for(int i=0;i<c;i++){
				st=new StringTokenizer(br.readLine()," ");
				for(int j=0;j<d;j++){
					B[i][j]=Integer.parseInt(st.nextToken());
				}
			}

			print(strassen(n,A,B),a,d);

	}
	
	static int[][] strassen(int n,int[][] A,int[][] B){

		if(n<=128){ //��M1~M7 AB�w�Q����2X2   �u���O16��8�n  32�S��֤F ���O�j�p�S��  �i�H�Ҽ{�W����n�p�G�p��32 �N�������F���θɫ�2����
			
			int[][] C=new int[A.length][B[0].length];

			for(int i=0;i<A.length;i++){
				for(int j=0;j<B[0].length;j++){
					int total=0;
					for(int k=0;k<A[0].length;k++){
						total+=(A[i][k]*B[k][j]);
					}
					C[i][j]=total;
				}
			}
			return C;
			
		}else{     //�����Ϊ���n/2=2
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
	
	static int[][] add(int[][] A,int[][] B){    //�x�}�ۥ[
		
		int[][] C=new int[A.length][A.length];
		for(int i=0;i<A.length;i++){
			for(int j=0;j<A.length;j++){
				C[i][j]=A[i][j]+B[i][j];
			}
		}
		return C;
		
	}
	
	static int[][] sub(int[][] A,int[][] B){    //�x�}�۴�
		
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

