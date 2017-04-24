package com.sitech.sessionshare.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
/**
 * 
 * <p>Title: 系统的调试信息和错误信息的文件记录的接口</p>
 * <p>Description: 系统的调试信息和错误信息的文件记录的接口</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: SI-TECH </p>
 * @version 1.0
 */
public class Log4JUtil {
	private static Logger debugLogger = null;      
    private static Logger errorLogger = null;
    private static Logger infoLogger = null;
    //kafka日志
    private static Logger kafkaLogger = null ;
    private static Logger kafkaErrorLogger = null ;
    //storm日志
    private static Logger stormLogger = null ;
    private static Logger stormErrorLogger = null ;
    //mongodb日志
    private static Logger mongodbLogger = null ;
    private static Logger mongodbErrorLogger = null ;
    static {
        loadLogger();
    }

    public Log4JUtil() {
        super();
    }

    /**
     * 装载系统使用的log
	 */
    static void loadLogger() {
        debugLogger = Logger.getLogger("");
        infoLogger = Logger.getLogger("info");
        errorLogger = Logger.getLogger("error");
        
//        kafkaLogger = Logger.getLogger("kafkaLogger");
//        kafkaErrorLogger = Logger.getLogger("kafkaErrorLogger");
//        stormLogger = Logger.getLogger("stormLogger");
//        stormErrorLogger = Logger.getLogger("stormErrorLogger");
//        mongodbLogger = Logger.getLogger("mongodbLogger");
//        mongodbErrorLogger = Logger.getLogger("mongodbErrorLogger");
	}
    
    public static void kafkaLogger(Object msg){
    	kafkaLogger.info(msg);
    }
    
    public static void kafkaErrorLogger(Object msg,Exception e){
    	kafkaErrorLogger.error(msg + "\n" + getExceptionTrace(e));
    }
    
    public static void stormLogger(Object msg){
    	stormLogger.info(msg);
    }
    
    public static void stormErrorLogger(Object msg,Exception e){
    	stormErrorLogger.error(msg + "\n" + getExceptionTrace(e));
    }
    
    public static void mongodbLogger(Object msg){
    	mongodbLogger.info(msg);
    }
    
    public static void mongodbErrorLogger(Object msg,Exception e){
    	mongodbErrorLogger.error(msg + "\n" + getExceptionTrace(e));
    }
    
    
    /**
     * @param msg: error级别的错误信息
	 */
    public static void errorLog(Object msg) {
        errorLogger.error(msg);
    }

    /**
     * @param e: error级别的异常信息
	 */
    public static void errorLog(Exception e) {
        errorLogger.error(getExceptionTrace(e));
    }

    /**
     * @param e: error级别的异常信息
     * @param msg: error级别的错误信息
	 */
    public static void errorLog( Object msg,Exception e) {
        errorLogger.error(msg + "\n" + getExceptionTrace(e));
    }

	/**
     * @param msg: debug级别的错误信息
	 */
    public static void debugLog(Object msg) {
    	if(debugLogger.isDebugEnabled())
        debugLogger.debug(msg);
    }

    /**
     * @param e: debug级别的异常信息
	 */
    public static void debugLog(Exception e) {
    	if(debugLogger.isDebugEnabled())
        debugLogger.debug(getExceptionTrace(e));
    }

    /**
     * @param e: debug级别的异常信息
     * @param msg: debug级别的错误信息
	 */
    public static void debugLog(Exception e, Object msg) {
    	if(debugLogger.isDebugEnabled())
        debugLogger.debug(msg + "\n" + getExceptionTrace(e));
    }

    /**
     * @param msg: info级别的错误信息
	 */
    public static void info(Object msg) {
        infoLogger.info(msg);
    }

	/**
     * @param e: info级别的异常信息
	 */
    public static void info(Exception e) {
        infoLogger.info(getExceptionTrace(e));
    }

    /**
     * @param e: debug级别的异常信息
     * @param msg: debug级别的错误信息
	 */
    public static void info(Exception e, Object msg) {
        infoLogger.info(msg + "\n" + getExceptionTrace(e));
    }

    /**
     * @param e: 异常信息输出
	 */
    public static void exOut(Exception e) {
        String print = getExceptionTrace(e);
        errorLogger.error(print);
    }

    /**
     * @param e: debug级别的异常信息
     * @param msg: debug级别的错误信息
	 */
    public static String getExceptionTrace(Exception e) {
    	if(e==null)
    		return "Error Null";
        String print = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintWriter wrt = new PrintWriter(bout);
        e.printStackTrace(wrt);
        wrt.close();
        print = bout.toString();
        return print;
    }
}
