package com.my.maven.utils;

//��д������,ʵ�����̺߳����̵߳Ĺ���
class Function{
  private boolean flag=false;
  //���߳�Ҫʵ�ֵĹ���
  public synchronized void sub(){
      while(flag){
          try {
              this.wait();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
             
      for(int i=0;i<10;i++){
          //forѭ���ڶ������̵߳Ĺ���,����򵥵ļ���Ϊ��ӡһ�仰,���߳�ͬ��
          System.out.println("sub"+i);
      }
      
      flag=true;
      this.notify();
  }
  //���߳�Ҫʵ�ֵĹ���
  public synchronized void main(){
      while(!flag){
          try {
              this.wait();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      for(int i=0;i<20;i++){
          System.out.println("main"+i);
      }
      
      flag=false;
      this.notify();
  }
  
}

public class rt {
	


  public static void main(String[] args) {
       final Function f=new Function();
      new Thread(
              new Runnable(){

                  public void run() {
                      for(int i=0;i<50;i++){
                          f.sub();
                      }
                  }
              
              }
              ).start();
      
      try {
		Thread.sleep(10);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      System.out.println("456");
      for(int i=0;i<50;i++){
          f.main();
          
      }
  }
}