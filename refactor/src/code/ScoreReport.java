package code; /**
 * 
 * SMTP implementation based on code by R�al Gagnon mailto:real@rgagnon.com
 * 
 */


import models.Bowler;
import models.Score;
import utils.PrintableText;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class ScoreReport {

	private StringBuilder content;
	
	public ScoreReport(Bowler bowler, int[] scores, int games ) {
		String nick = bowler.getNickName();
		String full = bowler.getFullName();
		Vector v = null;
		try{
			v = ScoreHistoryFile.getScores(nick);
		} catch (Exception e){System.err.println("Error: " + e);}
		Iterator scoreIt = v.iterator();
		content=new StringBuilder();
		content.append("--Lucky Strike Bowling Alley Score Report--\n\n");
//		content += "\n";
		content.append("Report for "+full+", aka \"" + nick + "\":\n\n\n");
//		content += "\n";
		content.append("Final scores for this session: "+scores[0]);
//		content += scores[0];
		for (int i = 1; i < games; i++){
			content.append(", " + scores[i]);
		}
		content.append(".\n\n\nPrevious scores by date: \n");
//		content += "\n";
//		content += "\n";
//		content += "Previous scores by date: \n";
		while (scoreIt.hasNext()){
			Score score = (Score) scoreIt.next();
			content.append("  " + score.getDate() + " - " +  score.getScore()+"\n");
//			content += "\n";
		}
		content.append("\n\n"+"Thank you for your continuing patronage.");
//		content += ;
	}

	public void sendEmail(String recipient) {
		try {
			Socket s = new Socket("osfmail.rit.edu", 25);
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(s.getInputStream(), "8859_1"));
			BufferedWriter out =
				new BufferedWriter(
					new OutputStreamWriter(s.getOutputStream(), "8859_1"));

			String boundary = "DataSeparatorString";

			// here you are supposed to send your username
			sendln(in, out, "HELO world");
			sendln(in, out, "MAIL FROM: <mda2376@rit.edu>");
			sendln(in, out, "RCPT TO: <" + recipient + ">");
			sendln(in, out, "DATA");
			sendln(out, "Subject: Bowling Score Report ");
			sendln(out, "From: <Lucky Strikes Bowling Club>");

			sendln(out, "Content-Type: text/plain; charset=\"us-ascii\"\r\n");
			sendln(out, content + "\n\n");
			sendln(out, "\r\n");

			sendln(in, out, ".");
			sendln(in, out, "QUIT");
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPrintout() {
		PrinterJob job = PrinterJob.getPrinterJob();

		PrintableText printobj = new PrintableText(content.toString());

		job.setPrintable(printobj);

		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException e) {
				System.out.println(e);
			}
		}

	}

	public void sendln(BufferedReader in, BufferedWriter out, String s) {
		try {
			out.write(s + "\r\n");
			out.flush();
			// System.out.println(s);
			s = in.readLine();
			// System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendln(BufferedWriter out, String s) {
		try {
			out.write(s + "\r\n");
			out.flush();
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
