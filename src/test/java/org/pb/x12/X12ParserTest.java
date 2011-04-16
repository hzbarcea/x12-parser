package org.pb.x12;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.pb.x12.example.exampleParseX12FileOne;

public class X12ParserTest {

	public static final String EXPECTED_X12_TOSTRING = 
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
			+ "SE*24*000000001~" + "GE*1*000000001~" + "IEA*1*000000001~";
	
	public static final String EXPECTED_X12_TOXML = 
		"<LOOP NAME=\"X12\">" +
			"<LOOP NAME=\"ISA\">" +
				"<ISA><ISA01><![CDATA[00]]></ISA01><ISA02><![CDATA[          ]]></ISA02><ISA03><![CDATA[00]]></ISA03><ISA04><![CDATA[          ]]></ISA04><ISA05><![CDATA[ZZ]]></ISA05><ISA06><![CDATA[SENDERID       ]]></ISA06><ISA07><![CDATA[ZZ]]></ISA07><ISA08><![CDATA[RECEIVERID    ]]></ISA08><ISA09><![CDATA[030409]]></ISA09><ISA10><![CDATA[0701]]></ISA10><ISA11><![CDATA[U]]></ISA11><ISA12><![CDATA[00401]]></ISA12><ISA13><![CDATA[0000000001]]></ISA13><ISA14><![CDATA[0]]></ISA14><ISA15><![CDATA[T]]></ISA15><ISA16><![CDATA[:]]></ISA16></ISA>" +
				"<LOOP NAME=\"GS\">" +
					"<GS><GS01><![CDATA[1212]]></GS01><GS02><![CDATA[SENDERID]]></GS02><GS03><![CDATA[RECEIVERID]]></GS03><GS04><![CDATA[0701]]></GS04><GS05><![CDATA[000000001]]></GS05><GS06><![CDATA[X]]></GS06><GS07><![CDATA[00401]]></GS07></GS>" +
					"<LOOP NAME=\"ST\">" +
						"<ST><ST01><![CDATA[835]]></ST01><ST02><![CDATA[000000001]]></ST02></ST>" +
						"<BPR><BPR01><![CDATA[DATA]]></BPR01><BPR02><![CDATA[NOT]]></BPR02><BPR03><![CDATA[VALID]]></BPR03><BPR04><![CDATA[RANDOM]]></BPR04><BPR05><![CDATA[TEXT]]></BPR05></BPR>" +
						"<TRN><TRN01><![CDATA[1]]></TRN01><TRN02><![CDATA[0000000000]]></TRN02><TRN03><![CDATA[1999999999]]></TRN03></TRN>" +
						"<DTM><DTM01><![CDATA[111]]></DTM01><DTM02><![CDATA[20090915]]></DTM02></DTM>" +
						"<LOOP NAME=\"1000A\">" +
							"<N1><N101><![CDATA[PR]]></N101><N102><![CDATA[ALWAYS INSURANCE COMPANY]]></N102></N1>" +
							"<N7><N701><![CDATA[AROUND THE CORNER]]></N701></N7>" +
							"<N4><N401><![CDATA[SHINE CITY]]></N401><N402><![CDATA[GREEN STATE]]></N402><N403><![CDATA[ZIP]]></N403></N4>" +
							"<REF><REF01><![CDATA[DT]]></REF01><REF02><![CDATA[435864864]]></REF02></REF></LOOP>" +
						"<LOOP NAME=\"1000B\">" +
							"<N1><N101><![CDATA[PE]]></N101><N102><![CDATA[FI]]></N102><N103><![CDATA[888888888]]></N103><N104><![CDATA[P.O.BOX 456]]></N104><N105><![CDATA[SHINE CITY]]></N105><N106><![CDATA[GREEN STATE]]></N106><N107><![CDATA[ZIP]]></N107><N108><![CDATA[EARTH]]></N108></N1>" +
						"</LOOP>" +
						"<LOOP NAME=\"2000\">" +
							"<LX><LX01><![CDATA[1]]></LX01></LX>" +
							"<LOOP NAME=\"2100\">" +
								"<CLP><CLP01><![CDATA[PCN123456789]]></CLP01><CLP02><![CDATA[]]></CLP02><CLP03><![CDATA[5555.55]]></CLP03><CLP04><![CDATA[]]></CLP04><CLP05><![CDATA[CCN987654321]]></CLP05></CLP>" +
								"<CAS><CAS01><![CDATA[PR]]></CAS01><CAS02><![CDATA[909099]]></CAS02><CAS03><![CDATA[100.00]]></CAS03></CAS>" +
								"<NM1><NM101><![CDATA[QC]]></NM101><NM102><![CDATA[1]]></NM102><NM103><![CDATA[PATIENT]]></NM103><NM104><![CDATA[TREATED]]></NM104><NM105><![CDATA[ONE]]></NM105><NM106><![CDATA[]]></NM106><NM107><![CDATA[]]></NM107><NM108><![CDATA[34]]></NM108><NM109><![CDATA[333333333]]></NM109></NM1>" +
								"<DTM><DTM01><![CDATA[273]]></DTM01><DTM02><![CDATA[20020824]]></DTM02></DTM>" +
								"<AMT><AMT01><![CDATA[A1]]></AMT01><AMT02><![CDATA[10.10]]></AMT02></AMT>" +
								"<AMT><AMT01><![CDATA[A2]]></AMT01><AMT02><![CDATA[20.20]]></AMT02></AMT></LOOP>" +
							"</LOOP>" +
						"<LOOP NAME=\"2000\">" +
							"<LX><LX01><![CDATA[2]]></LX01></LX>" +
							"<LOOP NAME=\"2100\">" +
								"<CLP><CLP01><![CDATA[PCN123456789]]></CLP01><CLP02><![CDATA[]]></CLP02><CLP03><![CDATA[4444.44]]></CLP03><CLP04><![CDATA[]]></CLP04><CLP05><![CDATA[CCN987654321]]></CLP05></CLP>" +
								"<CAS><CAS01><![CDATA[PR]]></CAS01><CAS02><![CDATA[909099]]></CAS02><CAS03><![CDATA[200.00]]></CAS03></CAS>" +
								"<NM1><NM101><![CDATA[QC]]></NM101><NM102><![CDATA[1]]></NM102><NM103><![CDATA[PATIENT]]></NM103><NM104><![CDATA[TREATED]]></NM104><NM105><![CDATA[TWO]]></NM105><NM106><![CDATA[]]></NM106><NM107><![CDATA[]]></NM107><NM108><![CDATA[34]]></NM108><NM109><![CDATA[444444444]]></NM109></NM1>" +
								"<DTM><DTM01><![CDATA[273]]></DTM01><DTM02><![CDATA[20020824]]></DTM02></DTM>" +
								"<AMT><AMT01><![CDATA[A1]]></AMT01><AMT02><![CDATA[30.30]]></AMT02></AMT>" +
								"<AMT><AMT01><![CDATA[A2]]></AMT01><AMT02><![CDATA[40.40]]></AMT02></AMT>" +
							"</LOOP>" +
						"</LOOP>" +
					"</LOOP>" +
					"<LOOP NAME=\"SE\">" +
						"<SE><SE01><![CDATA[24]]></SE01><SE02><![CDATA[000000001]]></SE02></SE>" +
					"</LOOP>" +
				"</LOOP>" +
				"<LOOP NAME=\"GE\">" +
					"<GE><GE01><![CDATA[1]]></GE01><GE02><![CDATA[000000001]]></GE02></GE>" +
				"</LOOP>" +
			"</LOOP>" +
			"<LOOP NAME=\"IEA\">" +
				"<IEA><IEA01><![CDATA[1]]></IEA01><IEA02><![CDATA[000000001]]></IEA02></IEA>" +
			"</LOOP>" +
		"</LOOP>";
	
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
		cfGS.addChild("SE", "SE");
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

		assertEquals(EXPECTED_X12_TOSTRING, x12.toString());
		assertEquals(EXPECTED_X12_TOXML, x12.toXML());		
		assertEquals(28, x12.size());
	}

	@Test
	public void testParseInputStream() throws FormatException, IOException {
		Parser parser = new X12Parser(loadCf());
		InputStream is = exampleParseX12FileOne.class
				.getResourceAsStream("/org/pb/x12/example/example835One.txt");

		X12 x12 = (X12) parser.parse(is);

		assertEquals(EXPECTED_X12_TOSTRING, x12.toString());
		assertEquals(EXPECTED_X12_TOXML, x12.toXML());
		assertEquals(28, x12.size());
	}

	@Test
	public void testParseString() throws FormatException {
		String inputString = EXPECTED_X12_TOSTRING;

		Parser parser = new X12Parser(loadCf());
		X12 x12 = (X12) parser.parse(inputString);

		assertEquals(EXPECTED_X12_TOSTRING, x12.toString());
		assertEquals(EXPECTED_X12_TOXML, x12.toXML());
		assertEquals(28, x12.size());
	}

}
