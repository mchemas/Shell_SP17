/* Student: Michel Chemas
 * CLASS:	CS340
 * PROJECT: 1B
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Shell extends Thread {

	static String comm[];
	
	public Shell(String inT){
		
		comm = inT.split(" ");
		
	}
	
	public void run(){
		commands(comm);
	}
		
	public static void commands(String[] x) {
		if (x[0].equals("clr")) {

			try {
				final String os = System.getProperty("os.name");
				if (os.contains("Windows")) {

					String output = clear("clr");
					System.out.print(output);

				} else {

					String output = clear("clear");
					System.out.print(output);

				}
			} catch (final Exception e) {
				
				System.out.println("Didnt work.");
			}

		} else if (x[0].equals("echo")) {

			for (int i = 1; i < x.length; i++) {
				System.out.print(x[i] + " ");
			}
			System.out.println();

		} else if (x[0].equals("help")) {

			try {
				BufferedReader readme = new BufferedReader(new FileReader(
						"readme"));
				String line;
				while ((line = readme.readLine()) != null) {
					System.out.println(line);
				}
				readme.close();
			} catch (IOException e) {
				System.err.println("readme file not found. shell will now exit.");
				System.exit(0);
			}

		} else if (x[0].equals("pause")) {

			System.console().readPassword();

		} else {
			otherProcess(x);
		}

	}

	private static String clear(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = "";

			while ((line = reader.readLine()) != null) {
				output.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
	
	public static void otherProcess(String[] x) {

		try{

			Process p = new ProcessBuilder(x).start();					

			InputStream Errorcheck = p.getErrorStream();
			InputStreamReader esr = new InputStreamReader(Errorcheck);
			BufferedReader errorReader = new BufferedReader(esr);
			String error = errorReader.readLine();

			if (error==null){ 

				InputStream is = p.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				String line;

				while ( (line = br.readLine()) != null){
					System.out.println(line);
				}
				br.close();
			}

			//Error spotted
			else if (error.contains("No such file or directory"))
			{
				System.err.println("The file does NOT exist or can not be opened!");
				System.err.println("Closing...");
				System.exit(0);
			}

			//Error spotted
			else if (error.contains("invalid option")){
				System.err.println("The command does not exist or can not be executed");
			}
			
		}
		catch (IOException InvalidCommand){
			System.err.println("The command does not exist or can not be executed");
		}
		catch (Exception e){
			System.err.println("Error");
		}	
		
	}

	public static void main(String[] args) {

		Scanner reader = new Scanner(System.in);
		String input = "";
		boolean exit = false;

		while (!(exit)) {
			System.out.print("chmi5992>");
			input = reader.nextLine();

			if (input.trim().isEmpty()) {
				continue;
			}

			String col[] = input.split(";");
			
			try {
				if (col[0].contains("exit")) {
					throw new Exception();
				}

				
				for(int i = 0; i < col.length; i++){
					col[i] = col[i].trim();
					
					Thread myT = new Thread(new Shell(col[i]));
					myT.start();	
				}
				
				

			} catch (Exception e) {
				exit = true;

			}

		}

	}

}

