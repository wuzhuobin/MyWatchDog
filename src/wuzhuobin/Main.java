package wuzhuobin;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Main {
	static { 
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
    static public void main(String[] args){
        System.out.println("HelloWorld!");
        Mat mat = Mat.eye(3, 3, CvType.CV_32F);
        System.out.println(mat);
    }
}