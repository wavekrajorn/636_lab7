import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab_MatrixMul {
    public static void main(String[] args) {
        int[][] inputA = { { 5, 6, 7 }, { 4, 8, 9 } };
        int[][] inputB = { { 6, 4 }, { 5, 7 }, { 1, 1 } };
        MyData matA = new MyData(inputA);
        MyData matB = new MyData(inputB);
        int matC_r = matA.data.length;// row
        int matC_c = matB.data[0].length;// col
        MyData matC = new MyData(matC_r, matC_c);
        // Q4 construct 2D array for each "thread" with respected to each cell in matC,
        // then start each thread
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < matC_r; i++) {
          for (int j = 0; j < matC_c; j++) {
            MatrixMulThread task = new MatrixMulThread(i, j, matA, matB, matC);
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
          }
        }
        // Q5 join each thread
        for (Thread thread : threads) {
          try {
            thread.join();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        threads.clear();
        matC.show();
      }
    }
    
    class MatrixMulThread implements Runnable {
      int processing_row;
      int processing_col;
      MyData datA;
      MyData datB;
      MyData datC;
    
      MatrixMulThread(int tRow, int tCol, MyData a, MyData b, MyData c) {
        // Q1 code here
        processing_row = tRow;// 0
        processing_col = tCol;// 1
        datA = a;
        datB = b;
        datC = c;
      }
    
      /* Q2 */ public void run() {
        // Q3
        // System.out.println("perform sum of multiplication of assigned row and col");
        datC.data[processing_row][processing_col] = 0;
        for (int i = 0; i < datB.data.length; i++)
          datC.data[processing_row][processing_col] += datA.data[processing_row][i] * datB.data[i][processing_col];
      }
    } // class
    
    class MyData {
      int[][] data;
    
      MyData(int[][] m) {
        data = m;
      }
    
      MyData(int r, int c) {
        data = new int[r][c];
        for (int[] aRow : data)
          Arrays.fill(aRow, 9);
        // 9 will be overwritten anyway
      }
    
      void show() {
        System.out.println(Arrays.deepToString(data));
      }
}
