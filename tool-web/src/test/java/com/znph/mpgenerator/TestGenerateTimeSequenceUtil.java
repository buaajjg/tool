package com.znph.mpgenerator;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author Minco
* @date 2017年10月31日 下午2:39:56
* 
*/
public class TestGenerateTimeSequenceUtil {
	
	  /** .log */
    private static final Logger logger = LoggerFactory.getLogger(TestGenerateTimeSequenceUtil.class);
 
    /** The FieldPosition. */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
 
    /** This Format for format the data to special format. */
    private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS");
 
    /** This Format for format the number to special format. */
    private final static NumberFormat numberFormat = new DecimalFormat("00");
 
    /** This int is the sequence number ,the default value is 0. */
    private static int seq = 0;
 
    private static final int MAX = 9999;
 
    /**
     * 时间格式生成序列
     * @return String
     */
    public static synchronized String generateSequenceNo() {
 
        Calendar rightNow = Calendar.getInstance();
 
        StringBuffer sb = new StringBuffer();
 
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
 
        numberFormat.format(seq, sb, HELPER_POSITION);
 
        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }
 
        logger.info("THE SQUENCE IS :" + sb.toString());
 
        return sb.toString();
    }
    
    
    public static void main(String[] args) {
		System.out.println(TestGenerateTimeSequenceUtil.generateSequenceNo());
	}

}
