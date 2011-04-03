package org.pb.x12;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.pb.x12.example.exampleParseX12FileOne;

public class X12ParserTest {

	private Cf loadCf() {
		Cf cfX12 = new Cf("X12");
		Cf cfISA = cfX12.addChild("ISA", "ISA");
		Cf cfGS = cfISA.addChild("GS", "GS");
		Cf cfST = cfGS.addChild("ST", "ST", "835", 1);
		cfST.addChild("1000A", "N1", "PR", 1);
		cfST.addChild("1000B", "N1", "PE", 1);
		Cf cf2000 = cfST.addChild("2000", "LX");
		Cf cf2100 = cf2000.addChild("2100", "CLP");
		cf2100.addChild("2110", "SVC");
		cfISA.addChild("GE", "GE");
		cfX12.addChild("IEA", "IEA");
		return cfX12;
	}

	@Test
	public void testParseFile() throws FormatException, IOException {
		Parser parser = new X12Parser(loadCf());
		URL url = exampleParseX12FileOne.class
				.getResource("/org/pb/x12/example/example835One.txt");
		File f1 = new File(url.getFile());

		X12 x12 = (X12) parser.parse(f1);

		assertEquals(
				"ISA*00*          *00*          *ZZ*SENDERID       *ZZ*RECEIVERID    *030409*0701*U*00401*0000000001*0*T*:~"
						+ "GS*1212*SENDERID*RECEIVERID*0701*000000001*X*00401~"
						+ "ST*835*000000001~"
						+ "BPR*DATA*NOT*VALID*RANDOM*TEXT~"
						+ "TRN*1*0000000000*1999999999~"
						+ "DTM*111*20090915~"
						+ "N1*PR*ALWAYS INSURANCE COMPANY~"
						+ "N7*AROUND THE CORNER~"
						+ "N4*SHINE CITY*GREEN STATE*ZIP~"
						+ "REF*DT*435864864~"
						+ "N1*PE*FI*888888888*P.O.BOX 456*SHINE CITY*GREEN STATE*ZIP*EARTH~"
						+ "LX*1~"
						+ "CLP*PCN123456789**5555.55**CCN987654321~"
						+ "CAS*PR*909099*100.00~"
						+ "NM1*QC*1*PATIENT*TREATED*ONE***34*333333333~"
						+ "DTM*273*20020824~"
						+ "AMT*A1*10.10~"
						+ "AMT*A2*20.20~"
						+ "LX*2~"
						+ "CLP*PCN123456789**4444.44**CCN987654321~"
						+ "CAS*PR*909099*200.00~"
						+ "NM1*QC*1*PATIENT*TREATED*TWO***34*444444444~"
						+ "DTM*273*20020824~"
						+ "AMT*A1*30.30~"
						+ "AMT*A2*40.40~"
						+ "SE*24*000000001~"
						+ "GE*1*000000001~" + "IEA*1*000000001~", x12
						.toString());

	}

	@Test
	public void testParseInputStream() throws FormatException, IOException {
		Parser parser = new X12Parser(loadCf());
		InputStream is = exampleParseX12FileOne.class
				.getResourceAsStream("/org/pb/x12/example/example835One.txt");

		X12 x12 = (X12) parser.parse(is);

		assertEquals(
				"ISA*00*          *00*          *ZZ*SENDERID       *ZZ*RECEIVERID    *030409*0701*U*00401*0000000001*0*T*:~"
						+ "GS*1212*SENDERID*RECEIVERID*0701*000000001*X*00401~"
						+ "ST*835*000000001~"
						+ "BPR*DATA*NOT*VALID*RANDOM*TEXT~"
						+ "TRN*1*0000000000*1999999999~"
						+ "DTM*111*20090915~"
						+ "N1*PR*ALWAYS INSURANCE COMPANY~"
						+ "N7*AROUND THE CORNER~"
						+ "N4*SHINE CITY*GREEN STATE*ZIP~"
						+ "REF*DT*435864864~"
						+ "N1*PE*FI*888888888*P.O.BOX 456*SHINE CITY*GREEN STATE*ZIP*EARTH~"
						+ "LX*1~"
						+ "CLP*PCN123456789**5555.55**CCN987654321~"
						+ "CAS*PR*909099*100.00~"
						+ "NM1*QC*1*PATIENT*TREATED*ONE***34*333333333~"
						+ "DTM*273*20020824~"
						+ "AMT*A1*10.10~"
						+ "AMT*A2*20.20~"
						+ "LX*2~"
						+ "CLP*PCN123456789**4444.44**CCN987654321~"
						+ "CAS*PR*909099*200.00~"
						+ "NM1*QC*1*PATIENT*TREATED*TWO***34*444444444~"
						+ "DTM*273*20020824~"
						+ "AMT*A1*30.30~"
						+ "AMT*A2*40.40~"
						+ "SE*24*000000001~"
						+ "GE*1*000000001~" + "IEA*1*000000001~", x12
						.toString());

	}

	@Test
	public void testParseString() throws FormatException {
		String inputString = "ISA*00*          *00*          *ZZ*SENDERID       *ZZ*RECEIVERID    *030409*0701*U*00401*0000000001*0*T*:~"
				+ "GS*1212*SENDERID*RECEIVERID*0701*000000001*X*00401~"
				+ "ST*835*000000001~"
				+ "BPR*DATA*NOT*VALID*RANDOM*TEXT~"
				+ "TRN*1*0000000000*1999999999~"
				+ "DTM*111*20090915~"
				+ "N1*PR*ALWAYS INSURANCE COMPANY~"
				+ "N7*AROUND THE CORNER~"
				+ "N4*SHINE CITY*GREEN STATE*ZIP~"
				+ "REF*DT*435864864~"
				+ "N1*PE*FI*888888888*P.O.BOX 456*SHINE CITY*GREEN STATE*ZIP*EARTH~"
				+ "LX*1~"
				+ "CLP*PCN123456789**5555.55**CCN987654321~"
				+ "CAS*PR*909099*100.00~"
				+ "NM1*QC*1*PATIENT*TREATED*ONE***34*333333333~"
				+ "DTM*273*20020824~"
				+ "AMT*A1*10.10~"
				+ "AMT*A2*20.20~"
				+ "LX*2~"
				+ "CLP*PCN123456789**4444.44**CCN987654321~"
				+ "CAS*PR*909099*200.00~"
				+ "NM1*QC*1*PATIENT*TREATED*TWO***34*444444444~"
				+ "DTM*273*20020824~"
				+ "AMT*A1*30.30~"
				+ "AMT*A2*40.40~"
				+ "SE*24*000000001~" + "GE*1*000000001~" + "IEA*1*000000001~";

		Parser parser = new X12Parser(loadCf());
		X12 x12 = (X12) parser.parse(inputString);

		assertEquals(inputString, x12.toString());
	}

}
