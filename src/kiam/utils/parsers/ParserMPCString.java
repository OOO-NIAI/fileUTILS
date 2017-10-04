/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiam.utils.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ParserMPCString extends AbstractParserString {

    private final String COD = "COD";
    private final String OBS = "OBS";
    private final String MEA = "MEA";
    private final String TEL = "TEL";
    private final String ACK = "ACK";
    private final String AC2 = "AC2";
    private final String NET = "NET";

    public ParserMPCString() {
    }

    public ParserMPCString(String dataToParse) {
        super(dataToParse);
    }

    @Override
    public void blockGenerator(String str) {

        if (str == null) {
            dataBlocks = null;
            return;
        }
        if (str.length() == 0) {
            dataBlocks = null;
            return;
        }
        boolean isCorrectRecord = true;
        String[] records = str.split(System.lineSeparator());
        for (int k = 0; k < records.length; k++) {
            String line = records[k];
            boolean tmpH = isCorrectHead(line);
            boolean tmpD = testCorrectString(line);
            if (tmpD) {
                dataBlocks.add(line);
            }
//            System.out.println(k + ": " + line + " tmpH " + tmpH + "  tmpD " + tmpD);
//            System.out.println("   --- line.length(): " + line.length() + "   tmpD " + tmpD);

        }
        System.out.println(" dataBlocks.size() " + dataBlocks.size());
    }

    private boolean isCorrectHead(String str) {
        boolean result = true;
        int p = 0;
        String[] heads = str.split("\\s");

        //for(int k = 0; k<heads.length; k++){
        String tmp = heads[0].trim();
        result
                = tmp.equals(this.AC2) || tmp.equals(this.ACK)
                || tmp.equals(this.COD) || tmp.equals(this.MEA)
                || tmp.equals(this.NET) || tmp.equals(this.OBS) || tmp.endsWith(this.TEL);
        //}
        return result;
    }
    /*
     FILE 02.txt
     0   37344
     1   C2017
     2   02
     3   16.80540503
     4   01
     5   30.409-07
     6   42
     7   54.57
     8   13.37

     FILE 02a,txt
     0   SPEKTR-R
     1   C2017
     2   03
     3   04.81329
     4   06
     5   43
     6   34.00
     7   +53
     8   28
     9   25.4
     10   15.6
     11   R
     12   C40
     1 2 3 4 8 9 10 11 12  +

     5 6 7
     */

    private boolean testCorrectString(String str) {
        if (str == null) {
            return false;
        }
        boolean result = str.length() > 0;
        int p = 0;
        StringTokenizer st = new StringTokenizer(str);
        //System.out.println("      str  " + str + "  " + st.countTokens());
        result = result && st.countTokens() > 3;
        while (st.hasMoreTokens()) {
            String tmp = st.nextToken().trim();
            //System.out.println(p + " tmp:  " + tmp + ";  tmp.length():  " + tmp.length());
            //result = result && tmp.length() > 0;
            if (p == 1) {
                result = result && tmp.length() > 4;
                tmp = GeneralValidation.exludeFirstCharacter(tmp);
                result = result && GeneralValidation.validateDateTime(tmp, "YYYY");
            }
            if (p == 2 || p == 4) {
                result = result && GeneralValidation.validatePureNumberStr(tmp);
//                    System.out.println(p + "  tmp: " + tmp + "   : " + isCorrectRecord);

            }
            if (p == 3 || p == 9 || p == 10) {
                result = result && GeneralValidation.validatePureDoubleStr(tmp);
            }
            //                if (p == 5) {
//                    System.out.println("  p: " + tmp);
//                    String[] tmpParts = tmp.split("\\.");
//                    fithElementhasPoint = tmpParts.length == 2;
//                    System.out.println(" tmpPartsLen: " + tmpParts.length);
//                    if (!fithElementhasPoint) {
//                        isCorrectRecord = isCorrectRecord && GeneralValidation.validatePureNumberStr(tmp);
//                    } else {
//
//                    }
//
//                }

            if (p == 8) {
                result = result
                        && (GeneralValidation.validatePureNumberStr(tmp)
                        || GeneralValidation.validatePureDoubleStr(tmp));
            }

            if (p == 11) {
                result = result && (tmp.length() == 1);
            }
            if (p == 12) {
                tmp = GeneralValidation.exludeFirstCharacter(tmp);
                result = result && GeneralValidation.validatePureNumberStr(tmp);
            }

            p++;
        }
        return result;
    }

    private boolean testStr(String str) {
        boolean result = true;
        int p = 0;
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            String tmp = st.nextToken();
            System.out.println(p + " " + tmp);
            if (p == 1) {
                tmp = GeneralValidation.exludeFirstCharacter(tmp);
                result = result && GeneralValidation.validateDateTime(tmp, "YYYY");
                //setNULLDataBlocs(isCorrectRecord);
            }
            if (p == 2 || p == 4) {
                result = result && GeneralValidation.validatePureNumberStr(tmp);
//                    System.out.println(p + "  tmp: " + tmp + "   : " + isCorrectRecord);
                //setNULLDataBlocs(isCorrectRecord);
            }
            if (p == 3 || p == 9 || p == 10) {
                result = result && GeneralValidation.validatePureDoubleStr(tmp);
                System.out.println("  isCorrectRecord      " + result);
                //setNULLDataBlocs(isCorrectRecord);
            }
            if (p == 3 || p == 9 || p == 10) {
                result = result && GeneralValidation.validatePureDoubleStr(tmp);
                System.out.println("  isCorrectRecord      " + result);

            }
            if (p == 8) {
                result = result
                        && (GeneralValidation.validatePureNumberStr(tmp)
                        || GeneralValidation.validatePureDoubleStr(tmp));
                //setNULLDataBlocs(isCorrectRecord);
            }

            if (p == 11) {
                result = result && (tmp.length() == 1);
            }
            if (p == 12) {
                tmp = GeneralValidation.exludeFirstCharacter(tmp);
                result = result && GeneralValidation.validatePureNumberStr(tmp);
                //setNULLDataBlocs(isCorrectRecord);
            }

            p++;
        }

        return result;
    }

    @Override
    public String getDataToParse() {
        return super.getDataToParse(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidDataBlocks() {
        if (dataBlocks == null) {
            return false;
        }
//        System.out.println("   dataBlocks.size()  " + dataBlocks.size());
        //System.out.println("   dataBlocks.size()  " + dataBlocks.get(0));
        boolean result = (dataBlocks.size() > 0);

//        TestMPCString testValidStr = new TestMPCString();
//        for (int k = 0; k < dataBlocks.size(); k++) {
//            String block = dataBlocks.get(k);
//            result = result && testValidStr.validateBlockData(block);
//            System.out.println(k + "     " + block);
//
//        }
        return result;
    }

}
