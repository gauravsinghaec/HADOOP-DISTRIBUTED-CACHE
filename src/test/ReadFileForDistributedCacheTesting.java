package test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import weather.station.NcdcStationMetadataParcer;

public class ReadFileForDistributedCacheTesting {

	public static void main(String arg[]) throws IOException{
		NcdcStationMetadataParcer parcer = new NcdcStationMetadataParcer();
		parcer.initialize(new File("E:\\gaurav_java\\department.txt"));
		System.out.print("Enter the Depatment ID to get the name: ");
		Scanner scan = new Scanner(System.in);
		String DEPT_ID = scan.next();
		String DEPT_NAME = parcer.getStationName(DEPT_ID);
		System.out.println("Department Name :" +DEPT_NAME );
	}
}
